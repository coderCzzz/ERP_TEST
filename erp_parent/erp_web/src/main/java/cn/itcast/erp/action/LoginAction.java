package cn.itcast.erp.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionContext;

import cn.itcast.erp.biz.IEmpBiz;
import cn.itcast.erp.entity.Emp;

public class LoginAction {
private String username;
private String pwd;
private IEmpBiz empBiz;
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getPwd() {
	return pwd;
}
public void setPwd(String pwd) {
	this.pwd = pwd;
}
public IEmpBiz getEmpBiz() {
	return empBiz;
}
public void setEmpBiz(IEmpBiz empBiz) {
	this.empBiz = empBiz;
}
/**
 * 登录验证请求
 */
public void checkUser(){
	try {
		Emp loginUser = empBiz.findByUsernameAndPwd(username, pwd);
		if(null==loginUser){
			ajaxReturn(false, "用户名或密码不正确");
			return;
		}
		ActionContext.getContext().getSession().put("loginUser", loginUser);
		ajaxReturn(true, "");
	} catch (Exception e) {
		// TODO: handle exception
		ajaxReturn(false, "登录失败");
	}
}
/**
 * 显示登录用户名
 */
public void showName(){
	Emp loginUser = (Emp) ActionContext.getContext().getSession().get("loginUser");
	if(null!=loginUser){
		ajaxReturn(true, loginUser.getName());
	}else{
		ajaxReturn(false, "");
	}
}
/**
 * 退出登录
 */
public void loginOut(){
	ActionContext.getContext().getSession().remove("loginUser");
}
public void ajaxReturn(boolean success,String message){
	Map<String,Object> rtn=new HashMap<String,Object>();
	rtn.put("success", success);
	rtn.put("message", message);
	write(JSON.toJSONString(rtn));
}
public void write(String jsonString){
	try {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(jsonString);
	} catch (Exception e) {
		// TODO: handle exception
	}
}
}
