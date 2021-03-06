   package cn.itcast.erp.action;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;

import cn.itcast.erp.biz.IOrdersBiz;
import cn.itcast.erp.entity.Emp;
import cn.itcast.erp.entity.Orderdetail;
import cn.itcast.erp.entity.Orders;
import cn.itcast.erp.exception.ErpException;
import cn.itcast.redsun.ws.Waybilldetail;
import cn.itcast.redsun.ws.impl.IWaybillWs;

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
	private IWaybillWs waybillWs;
	//运单
	private Long waybillSn;
	
	public void setWaybillSn(Long waybillSn) {
		this.waybillSn = waybillSn;
	}
	public void setWaybillWs(IWaybillWs waybillWs) {
		this.waybillWs = waybillWs;
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
	/**
	 * 我的订单
	 */
	public void myListByPage(){
		if(null==getT1()){
			setT1(new Orders());
		}
		Emp loginUser = getLoginUser();
		getT1().setCreater(loginUser.getUuid());
		super.listByPage();
	}
	/**
	 * 导出
	 */
	public void export(){
		String filename="_orders"+getId()+".xls";;
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			response.setHeader("Content-Disposition", "attachment;filename="+new String(filename.getBytes(),"ISO-8859-1"));
			ordersBiz.exportById(response.getOutputStream(), getId());
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public void waybilldetailList(){
		List<Waybilldetail> waybilldetailList = ordersBiz.waybilldetailList(waybillSn);
		write(JSON.toJSONString(waybilldetailList));
	}
	
}
