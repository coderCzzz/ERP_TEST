package cn.itcast.erp.action;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;

import cn.itcast.erp.biz.IDepBiz;
import cn.itcast.erp.entity.Dep;

public class DepAction {
private IDepBiz depBiz;
public void setDepBiz(IDepBiz depBiz) {
	this.depBiz = depBiz;
}
//条件过滤
private Dep dep1;
public Dep getDep1() {
	return dep1;
}
public void setDep1(Dep dep1) {
	this.dep1 = dep1;
}
//列表初始化
public void list(){
	List<Dep> list=depBiz.getList();
	String jsonString = JSON.toJSONString(list);
	this.Write(jsonString);
}
//条件查询
public void getList(){
	List<Dep> list = depBiz.getList(dep1);
	String jsonString = JSON.toJSONString(list);
	this.Write(jsonString);
}
public void Write(String jsonString){
	HttpServletResponse response = ServletActionContext.getResponse();
	response.setContentType("text/html;charset=utf-8");
	try {
		response.getWriter().write(jsonString);
	} catch (Exception e) {
		// TODO: handle exception
	}	
}
}
