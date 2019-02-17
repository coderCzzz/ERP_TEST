package cn.itcast.erp.biz.impl;
import java.util.Date;

import cn.itcast.erp.biz.IOrdersBiz;
import cn.itcast.erp.dao.IOrdersDao;
import cn.itcast.erp.entity.Orderdetail;
import cn.itcast.erp.entity.Orders;
/**
 * 订单业务逻辑类
 * @author Administrator
 *
 */
public class OrdersBiz extends BaseBiz<Orders> implements IOrdersBiz {

	private IOrdersDao ordersDao;
	
	public void setOrdersDao(IOrdersDao ordersDao) {
		this.ordersDao = ordersDao;
		setBaseDao(ordersDao);
	}

	@Override
	public void add(Orders orders) {
		// TODO Auto-generated method stub
		orders.setState(Orders.STATE_CREATE);//未审核
		orders.setType(Orders.TYPE_IN);//采购
		orders.setCreatetime(new Date());
		//计算总金额
		double total=0;
		for(Orderdetail detail:orders.getOrderDetails()){
			total+=detail.getMoney();//累计金额
			detail.setState(Orderdetail.STATE_NOT_IN);//设置未未入库
			detail.setOrders(orders);//设置明细对应的订单，
		}
		orders.setTotalmoney(total);//设置总金额
		ordersDao.add(orders);//保存订单 
	}
	

	
}
