package cn.itcast.erp.action;
import java.util.List;

import com.alibaba.fastjson.JSON;

import cn.itcast.erp.biz.IOrdersBiz;
import cn.itcast.erp.entity.Emp;
import cn.itcast.erp.entity.Orderdetail;
import cn.itcast.erp.entity.Orders;

/**
 * 订单Action 
 * @author Administrator
 *
 */
public class OrdersAction extends BaseAction<Orders> {

	private IOrdersBiz ordersBiz;
	
	public void setOrdersBiz(IOrdersBiz ordersBiz) {
		this.ordersBiz = ordersBiz;
		super.setBaseBiz(this.ordersBiz);
	}
	//商品明细
	private String json;

	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}
	@Override
	public void add() {
		Emp loginUser = getLoginUser();
		if(null==loginUser){
			ajaxReturn(false, "请登录");
		}
		try {
			Orders orders = getT();//获取提交的订单
			//设置订单创建人
			orders.setCreater(loginUser.getUuid());
			//获取订单明细
			List<Orderdetail> orderDetailList = JSON.parseArray(json, Orderdetail.class);
			//设置订单明细
			orders.setOrderDetails(orderDetailList);
			//保存订单
			ordersBiz.add(orders);
			ajaxReturn(true, "添加订单成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ajaxReturn(true, "添加订单失败");
		}
	}
	
}
