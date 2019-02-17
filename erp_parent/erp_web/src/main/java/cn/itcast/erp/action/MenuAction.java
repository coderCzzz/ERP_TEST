package cn.itcast.erp.action;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionContext;

import cn.itcast.erp.biz.IMenuBiz;
import cn.itcast.erp.entity.Menu;

/**
 * 菜单Action 
 * @author Administrator
 *
 */
public class MenuAction extends BaseAction<Menu> {

	private IMenuBiz menuBiz;
	
	public void setMenuBiz(IMenuBiz menuBiz) {
		this.menuBiz = menuBiz;
		super.setBaseBiz(this.menuBiz);
	}
	public void getMenuTree(){
		//查询顶级菜单
		Menu menu = menuBiz.get("0");
		//转化未为son字符串
		String menuString = JSON.toJSONString(menu);
		write(menuString);
	}	
}
