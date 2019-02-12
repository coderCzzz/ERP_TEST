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
public void list(){
	List<Dep> list=depBiz.getList();
	String jsonString = JSON.toJSONString(list);
	HttpServletResponse response = ServletActionContext.getResponse();
	response.setContentType("text/html;charset=utf-8");
	try {
		response.getWriter().write(jsonString);
	} catch (Exception e) {
		// TODO: handle exception
	}	
}
}
