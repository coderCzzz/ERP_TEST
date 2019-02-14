package cn.itcast.erp.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;

import cn.itcast.erp.biz.IBaseBiz;
public class BaseAction<T> {
	private IBaseBiz<T> baseBiz;
	public void setBaseBiz(IBaseBiz<T> baseBiz) {
		this.baseBiz = baseBiz;
	}
	//条件过滤
	private T t1;
	public T getT1() {
		return t1;
	}
	public void setT1(T t1) {
		this.t1 = t1;
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
	private T t2;
	private Object param;
	public T getT2() {
		return t2;
	}
	public void setT2(T t2) {
		this.t2 = t2;
	}
	public Object getParam() {
		return param;
	}
	public void setParam(Object param) {
		this.param = param;
	}
	//新增部门
	private T t;
	public T getT() {
		return t;
	}
	public void setT(T t) {
		this.t = t;
	}
	//部门id
	private long id;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	//条件查询
	public void getList(){
		int firsrResult=(page-1)*rows;
		List<T> list = baseBiz.getList(t1,t2,param,firsrResult,rows);
		long total = baseBiz.getCount(t1,t2,param);
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("total",total);
		map.put("rows",list);
		String jsonString = JSON.toJSONString(map);
		this.Write(jsonString);
	}
	public void add(){
		try {
			baseBiz.add(t);
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
			baseBiz.delete(id);
			this.ajaxReturn(true, "删除成功");
		} catch (Exception e) {
			// TODO: handle exception
			this.ajaxReturn(false, "删除失败");
		}
	}
	//获取单列部门信息
	public void get(){
		T t = baseBiz.get(id);
		String jsonString = JSON.toJSONString(t);
		String afterJsonString = mapData(jsonString, "t");
		this.Write(afterJsonString);
		
	}
	//给jsonString加前缀
	public String mapData(String jsonString,String prefix){
		Map<String,Object> map = JSON.parseObject(jsonString);
		Map<String,Object> dataMap=new HashMap<String, Object>();
		for(String key:map.keySet()){
			dataMap.put(prefix+"."+key, map.get(key));
		}
		return JSON.toJSONString(dataMap);
	}
	//修改部门
	public void update(){
		try {
			baseBiz.update(t);
			ajaxReturn(true, "修改成功");
		} catch (Exception e) {
			// TODO: handle exception
			ajaxReturn(false, "修改失败");
		}
		
	}
}
