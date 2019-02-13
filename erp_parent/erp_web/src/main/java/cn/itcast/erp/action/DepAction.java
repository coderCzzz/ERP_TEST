package cn.itcast.erp.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
//分页
private int page;
private int rows;
public int getPage() {
	return page;
}
public void setPage(int page) {
	this.page = page;
}
public int getRows() {
	return rows;
}
public void setRows(int rows) {
	this.rows = rows;
}
//更多查询
private Dep dep2;
private Object param;
public Dep getDep2() {
	return dep2;
}
public void setDep2(Dep dep2) {
	this.dep2 = dep2;
}
public Object getParam() {
	return param;
}
public void setParam(Object param) {
	this.param = param;
}
//新增部门
private Dep dep;
public Dep getDep() {
	return dep;
}
public void setDep(Dep dep) {
	this.dep = dep;
}
//部门id
private long id;
public long getId() {
	return id;
}
public void setId(long id) {
	this.id = id;
}
//列表初始化
public void list(){
	List<Dep> list=depBiz.getList();
	String jsonString = JSON.toJSONString(list);
	this.Write(jsonString);
}
//条件查询
public void getList(){
	int firsrResult=(page-1)*rows;
	List<Dep> list = depBiz.getList(dep1,dep2,param,firsrResult,rows);
	long total = depBiz.getCount(dep1,dep2,param);
	Map<String, Object> map=new HashMap<String, Object>();
	map.put("total",total);
	map.put("rows",list);
	String jsonString = JSON.toJSONString(map);
	this.Write(jsonString);
}
public void add(){
	try {
		depBiz.add(dep);
		this.ajaxReturn(true, "添加成功");
	} catch (Exception e) {
		// TODO: handle exception
		this.ajaxReturn(false, "添加失败");
	}
	
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
//返回信息封装
public void ajaxReturn(boolean success,String message){
	Map<String, Object> rtn=new HashMap<String, Object>();
	rtn.put("success", success);
	rtn.put("message", message);
	this.Write(JSON.toJSONString(rtn));
}
//删除部门
public void delete(){
	try {
		depBiz.delete(id);
		this.ajaxReturn(true, "删除成功");
	} catch (Exception e) {
		// TODO: handle exception
		this.ajaxReturn(false, "删除失败");
	}
}
}
