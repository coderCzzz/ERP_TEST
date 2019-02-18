   package cn.itcast.erp.action;
import java.util.List;

import com.alibaba.fastjson.JSON;

import cn.itcast.erp.biz.IOrdersBiz;
import cn.itcast.erp.entity.Emp;
import cn.itcast.erp.entity.Orderdetail;
import cn.itcast.erp.entity.Orders;
import cn.itcast.erp.exception.ErpException;

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
		write(ajaxReturn(false, "请登录"));
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
			write(ajaxReturn(true, "添加订单成功"));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			write(ajaxReturn(true, "添加订单失败"));;
		}
	}
	/**
	 * 采购订单审核
	 */
	public void doCheck(){
		Emp loginUser = getLoginUser();
		if(null==loginUser){
			write(ajaxReturn(false, "请登录"));;
			return;
		}
		try {
			ordersBiz.doCheck(getId(), loginUser.getUuid());
			write(ajaxReturn(true, "审核成功"));;
		} catch (ErpException e) {
			// TODO: handle exception
			write(ajaxReturn(false, e.getMessage()));;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			write(ajaxReturn(false, e.getMessage()));;
		}
	}
	/**
	 * 采购订单确认
	 */
	public void doStart(){
		Emp loginUser = getLoginUser();
		if(null==loginUser){
			write(ajaxReturn(false, "请登录"));
			return;
		}
		try {
			ordersBiz.doStart(getId(), loginUser.getUuid());
			write(ajaxReturn(true, "确认成功"));;
		} catch (ErpException e) {
			// TODO: handle exception
			write(ajaxReturn(false, e.getMessage()));;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			write(ajaxReturn(false,"确认失败"));;
		}
	}
	
}
