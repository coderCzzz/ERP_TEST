package cn.itcast.erp.biz.impl;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.itcast.erp.biz.IOrdersBiz;
import cn.itcast.erp.dao.IEmpDao;
import cn.itcast.erp.dao.IOrdersDao;
import cn.itcast.erp.dao.ISupplierDao;
import cn.itcast.erp.entity.Orderdetail;
import cn.itcast.erp.entity.Orders;
import cn.itcast.erp.exception.ErpException;
/**
 * 订单业务逻辑类
 * @author Administrator
 *
 */
public class OrdersBiz extends BaseBiz<Orders> implements IOrdersBiz {

	private IOrdersDao ordersDao;
	private IEmpDao empDao;
	private ISupplierDao supplierDao;
	public IEmpDao getEmpDao() {
		return empDao;
	}

	public void setEmpDao(IEmpDao empDao) {
		this.empDao = empDao;
	}

	public ISupplierDao getSupplierDao() {
		return supplierDao;
	}

	public void setSupplierDao(ISupplierDao supplierDao) {
		this.supplierDao = supplierDao;
	}

	public void setOrdersDao(IOrdersDao ordersDao) {
		this.ordersDao = ordersDao;
		setBaseDao(ordersDao);
	}

	@Override
	public void add(Orders orders) {
		// TODO Auto-generated method stub 
		orders.setState(Orders.STATE_CREATE);//未审核
		//orders.setType(Orders.TYPE_IN);//采购
		orders.setCreatetime(new Date());
		//计算总金额
		double total=0;
		for(Orderdetail detail:orders.getOrderDetails()){
			total+=detail.getMoney();//累计金额
			detail.setState(Orderdetail.STATE_NOT_IN);//设置未入库
			detail.setOrders(orders);//设置明细对应的订单，
		}
		orders.setTotalmoney(total);//设置总金额
		ordersDao.add(orders);//保存订单 
	}
	@Override
	public List<Orders> getListByPage(Orders t1, Orders t2, Object param, int firstResult, int maxResults) {
		//获取分页结果
		List<Orders> ordersList = super.getListByPage(t1, t2, param, firstResult, maxResults);
		//缓存员工名称,key为员工编号，value为员工编号
		Map<Long,String> empNameMap=new HashMap<Long,String>();
		//缓存供应商名称
		Map<Long,String> supplierNameMap=new HashMap<Long,String>();
		for(Orders o:ordersList){
			o.setCreaterName(getEmpName(o.getCreater(), empNameMap,empDao));
			o.setCheckName(getEmpName(o.getChecker(), empNameMap,empDao));
			o.setStarterName(getEmpName(o.getStarter(), empNameMap,empDao));
			o.setEnderName(getEmpName(o.getEnder(), empNameMap,empDao));
			o.setSupplierName(getSupplierName(o.getSupplieruuid(), supplierNameMap,supplierDao));
		}
		return ordersList;
	}
	/**
	 * 采购订单审核
	 */
	@Override
	public void doCheck(Long uuid, Long empUuid) {
		// TODO Auto-generated method stub
		Orders orders = ordersDao.get(uuid);
		if(!orders.STATE_CREATE.equals(orders.getState())){
			throw new ErpException("该订单已经审核！");
		}
		//更新审核员
		orders.setChecker(empUuid);
		//更新审核时间
		orders.setChecktime(new Date());
		//更新订单状态为已审核
		orders.setState(orders.STATE_CHECK);
		
	}
	/**
	 * 采购确认
	 */
	@Override
	public void doStart(Long uuid, Long empUuid) {
		// TODO Auto-generated method stub
		Orders orders = ordersDao.get(uuid);
		if(!orders.STATE_CHECK.equals(orders.getState())){
			throw new ErpException("该订单已经审核！");
		}
		//更新审核员
		orders.setStarter(empUuid);
		//更新审核时间
		orders.setStarttime(new Date());
		//更新订单状态为已审核
		orders.setState(orders.STATE_START);
		
	}

	
	
}
