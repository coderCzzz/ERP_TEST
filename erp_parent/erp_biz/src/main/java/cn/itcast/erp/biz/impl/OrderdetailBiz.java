package cn.itcast.erp.biz.impl;
import java.util.Calendar;
import java.util.List;

import cn.itcast.erp.biz.IOrderdetailBiz;
import cn.itcast.erp.dao.IOrderdetailDao;
import cn.itcast.erp.dao.IStoredetailDao;
import cn.itcast.erp.dao.IStoreoperDao;
import cn.itcast.erp.entity.Orderdetail;
import cn.itcast.erp.entity.Orders;
import cn.itcast.erp.entity.Storedetail;
import cn.itcast.erp.entity.Storeoper;
import cn.itcast.erp.exception.ErpException;
/**
 * 订单明细业务逻辑类
 * @author Administrator
 *
 */
public class OrderdetailBiz extends BaseBiz<Orderdetail> implements IOrderdetailBiz {

	private IOrderdetailDao orderdetailDao;
	/**
	 * 商品库存dao
	 * @param orderdetailDao
	 */
	private IStoredetailDao storedetailDao;
	/**
	 * 商品库存变更记录
	 * @param orderdetailDao
	 */
	private IStoreoperDao storeoperDao;
	
	public void setOrderdetailDao(IOrderdetailDao orderdetailDao) {
		this.orderdetailDao = orderdetailDao;
		setBaseDao(orderdetailDao);
	}

	public IStoredetailDao getStoredetailDao() {
		return storedetailDao;
	}

	public void setStoredetailDao(IStoredetailDao storedetailDao) {
		this.storedetailDao = storedetailDao;
	}

	public IStoreoperDao getStoreoperDao() {
		return storeoperDao;
	}

	public void setStoreoperDao(IStoreoperDao storeoperDao) {
		this.storeoperDao = storeoperDao;
	}
	/**
	 * 采购入库
	 * uuid 订单编号
	 * storeUuid 仓库编号
	 * empUuid 库管员编号
	 */
	public void doOutStore(Long uuid, Long storeUuid, Long empUuid) {
		// TODO Auto-generated method stub
		//1.更新商品明细
		Orderdetail orderdetail = this.orderdetailDao.get(uuid);
		//检查是否已入库
		if(!Orderdetail.STATE_NOT_OUT.equals(orderdetail.getState())){
			throw new ErpException("该明细已出库");
		}
		//状态为已入库
		orderdetail.setState(Orderdetail.STATE_OUT);
		//出库操作员
		orderdetail.setEnder(empUuid);
		//从哪个仓库出
		orderdetail.setStoreuuid(storeUuid);
		//出库时间
		orderdetail.setEndtime(Calendar.getInstance().getTime());
		//2.更新商品仓库库存
		//构建查询条件
		Storedetail storeDetail=new Storedetail();
		storeDetail.setGoodsuuid(orderdetail.getGoodsuuid());
		storeDetail.setStoreuuid(storeUuid);
		List<Storedetail> storeList = storedetailDao.getList(storeDetail, null, null);
		//判断是否存在库存消息
		if(null!=storeList&&storeList.size()>0){
			//如果存在，则更新数量
			storeList.get(0).setNum(storeList.get(0).getNum()-orderdetail.getNum());
		}else{
			//
			throw new ErpException("库存不足！");
		}
		//3.增加商品仓库库存记录
		Storeoper operLog=new Storeoper();
		//设置操作人
		operLog.setEmpuuid(empUuid);
		//出库哪个商品
		operLog.setGoodsuuid(orderdetail.getGoodsuuid());
		//出库数量
		operLog.setNum(orderdetail.getNum());
		//从哪个仓库出
		operLog.setStoreuuid(storeUuid);
		//出库时间
		operLog.setOpertime(orderdetail.getEndtime());
		//操作类型为出库
		operLog.setType(Storeoper.TYPE_OUT);
		storeoperDao.add(operLog);
		//第四步是否需要更新订单的状态的判断
		Orders orders=orderdetail.getOrders();
		//统计该订单所有state=0的明细个数，看是否还有没有入库的明细
		Orderdetail  countParam=new Orderdetail();
		countParam.setState(Orderdetail.STATE_NOT_OUT);
		countParam.setOrders(orders);
		long count = orderdetailDao.getCount(countParam, null, null);
		if(count==0){//说明明细全部入库，这时需要更新订单的状态，入库完成时间，入库操作员
			orders.setState(Orders.STATE_OUT);
			orders.setEnder(empUuid) ;
			orders.setEndtime(orderdetail.getEndtime());	
		}
		
		
	}
	public void doInStore(Long uuid, Long storeUuid, Long empUuid) {
		// TODO Auto-generated method stub
		//1.更新商品明细
		Orderdetail orderdetail = this.orderdetailDao.get(uuid);
		//检查是否已入库
		if(!Orderdetail.STATE_NOT_IN.equals(orderdetail.getState())){
			throw new ErpException("该明细已入库");
		}
		//状态为已入库
		orderdetail.setState(Orderdetail.STATE_IN);
		//入库操作员
		orderdetail.setEnder(empUuid);
		//入到哪个仓库
		orderdetail.setStoreuuid(storeUuid);
		//入库时间
		orderdetail.setEndtime(Calendar.getInstance().getTime());
		//2.更新商品仓库库存
		//构建查询条件
		Storedetail storeDetail=new Storedetail();
		storeDetail.setGoodsuuid(orderdetail.getGoodsuuid());
		storeDetail.setStoreuuid(storeUuid);
		List<Storedetail> storeList = storedetailDao.getList(storeDetail, null, null);
		//判断是否存在库存消息
		if(null!=storeList&&storeList.size()>0){
			//如果存在，则更新数量
			storeList.get(0).setNum(storeList.get(0).getNum()+orderdetail.getNum());
		}else{
			//增加库存信息
			storedetailDao.add(storeDetail);
		}
		//3.增加商品仓库库存记录
		Storeoper operLog=new Storeoper();
		//设置操作人
		operLog.setEmpuuid(empUuid);
		//入库哪个商品
		operLog.setGoodsuuid(orderdetail.getGoodsuuid());
		//入库数量
		operLog.setNum(orderdetail.getNum());
		//入在哪个仓库
		operLog.setStoreuuid(storeUuid);
		//入库时间
		operLog.setOpertime(orderdetail.getEndtime());
		//操作类型为入库
		operLog.setType(Storeoper.TYPE_IN);
		storeoperDao.add(operLog);
		//第四步是否需要更新订单的状态的判断
		Orders orders=orderdetail.getOrders();
		//统计该订单所有state=0的明细个数，看是否还有没有入库的明细
		Orderdetail  countParam=new Orderdetail();
		countParam.setState(Orderdetail.STATE_NOT_IN);
		countParam.setOrders(orders);
		long count = orderdetailDao.getCount(countParam, null, null);
		if(count==0){//说明明细全部入库，这时需要更新订单的状态，入库完成时间，入库操作员
			orders.setState(Orders.STATE_END);
			orders.setEnder(empUuid) ;
			orders.setEndtime(orderdetail.getEndtime());	
		}
	}
}
