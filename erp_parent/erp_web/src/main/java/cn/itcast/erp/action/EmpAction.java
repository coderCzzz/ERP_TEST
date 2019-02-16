package cn.itcast.erp.action;
import cn.itcast.erp.biz.IEmpBiz;
import cn.itcast.erp.entity.Emp;
import cn.itcast.erp.exception.ErpException;

/**
 * 员工Action 
 * @author Administrator
 *
 */
public class EmpAction extends BaseAction<Emp> {

	private IEmpBiz empBiz;
	public void setEmpBiz(IEmpBiz empBiz) {
		this.empBiz = empBiz;
		super.setBaseBiz(this.empBiz);
	}
	/**
	 * 修改密码
	 */
	private String newPwd;
	private String oldPwd;
	public String getNewPwd() {
		return newPwd;
	}
	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}
	public String getOldPwd() {
		return oldPwd;
	}
	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}
	public void updatePwd(){
		Emp loginUser = getLoginUser();
		String rtn=null;
		if(null==loginUser){
		 rtn = ajaxReturn(false, "亲，您还没登录");
			return;
		}
		try {
			this.empBiz.updatePwd(loginUser.getUuid(), oldPwd, newPwd);
			rtn=ajaxReturn(true, "密码修改成功");
		} catch (ErpException e) {
			// TODO: handle exception
			rtn=ajaxReturn(false,e.getMessage() );
		}catch (Exception e) {
			// TODO: handle exception
			rtn=ajaxReturn(false, "修改密码失败");
		}
		write(rtn);
	}
	public void updatePwd_reset(){
		String rtn=null;
		try {
			this.empBiz.updatePwd_reset(getId(), newPwd);
			rtn=ajaxReturn(true, "重置密码成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			rtn=ajaxReturn(false, "重置密码失败");
		}
		write(rtn);
	}
	
}
