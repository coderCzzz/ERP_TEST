package cn.itcast.erp.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.opensymphony.xwork2.ActionContext;

import cn.itcast.erp.biz.IBaseBiz;
import cn.itcast.erp.entity.Emp;

/**
 * 基本Action
 * @author Administrator
 *
 */
public class BaseAction<T> {
	
	private IBaseBiz<T> baseBiz;
	
	public void setBaseBiz(IBaseBiz<T> baseBiz) {
		this.baseBiz = baseBiz;
	}
	
	private T t1;

	public T getT1() {
		return t1;
	}
	public void setT1(T t1) {
		this.t1 = t1;
	}

	private T t2;
	
	public T getT2() {
		return t2;
	}
	public void setT2(T t2) {
		this.t2 = t2;
	}

	private Object param;
		
	public Object getParam() {
		return param;
	}
	public void setParam(Object param) {
		this.param = param;
	}
	
	/**
	 * 查询全部列表
	 */
	public void list(){
		
		List<T> list = baseBiz.getList(t1,t2,param);
		String jsonString = JSON.toJSONString(list);			
		write(jsonString);
	}
	
	private int page;//页码
	private int rows;//每页记录数

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
	
	/**
	 * 查询全部列表
	 */
	public void listByPage(){		
		int firstResult=(page-1)*rows;
		List<T> list = baseBiz.getListByPage(t1,t2, param, firstResult, rows);
		long count = baseBiz.getCount(t1, t2, param);		
			Map<String, Object> map=new HashMap<String, Object>();
		map.put("rows", list);
		map.put("total", count);		
		String jsonString = JSON.toJSONString(map,SerializerFeature.DisableCircularReferenceDetect);	
		write(jsonString);
	}
	
	/**
	 * 输出字符串
	 * @param jsonString
	 */
	public void write(String jsonString){

		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		try {
			response.getWriter().write(jsonString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	private T t;		
	public T getT() {
		return t;
	}
	public void setT(T t) {
		this.t = t;
	}
	
	/**
	 * 增加
	 */
	public void add(){
		
		try {
			baseBiz.add(t);
			write(ajaxReturn(true, "增加成功"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			write(ajaxReturn(true, "增加失败"));
		}
	}
		
	/**
	 * 修改
	 */
	public void update(){
		
		try {
			baseBiz.update(t);
			write(ajaxReturn(true, "修改成功"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			write(ajaxReturn(true, "修改失败"));
		}
	}
	
	
	/**
	 * 标准结构返回体
	 * @param success
	 * @param message
	 * @return
	 */
	public String ajaxReturn(boolean success,String message){
		
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("success", success);
		map.put("message", message);
		return JSON.toJSONString(map);		
	}
	
	private Long id;		
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * 删除
	 */
	public void delete(){
		
		try {
			baseBiz.delete(id);
			write(ajaxReturn(true, "删除成功"));
		} catch (Exception e) {
			e.printStackTrace();
			write(ajaxReturn(true, "删除失败"));
		}
	}
	
	/**
	 * 获取实体
	 */
	public void get(){
		T t3 = (T) baseBiz.get(id);
		String jsonString = JSON.toJSONStringWithDateFormat(t3, "yyyy-MM-dd");
		write(mapJson(jsonString, "t"));
	}
	
	/**
	 * 自定添加前缀
	 * @param jsonString
	 * @param prefix
	 * @return
	 */
	private String mapJson(String jsonString,String prefix){
	
		Map<String,Object> map=JSON.parseObject(jsonString);
		Map<String,Object> newmap=new HashMap<String, Object>();
		for(String key:map.keySet()){
			if(map.get(key) instanceof Map){
				Map<String,Object> m2=(Map<String,Object>)map.get(key);
				for(String key2:m2.keySet()){
					newmap.put(prefix+'.'+key+'.'+key2, m2.get(key2));
				}
			}else{
				newmap.put(prefix+"."+key, map.get(key));				
			}
			
		}
		return  JSON.toJSONString(newmap);		
	}
	public Emp getLoginUser(){
		return (Emp) ActionContext.getContext().getSession().get("loginUser");
	}
	
}
