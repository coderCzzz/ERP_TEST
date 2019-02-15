package cn.itcast.erp.biz.impl;
import cn.itcast.erp.biz.IEmpBiz;
import cn.itcast.erp.dao.IEmpDao;
import cn.itcast.erp.entity.Emp;
/**
 * 员工业务逻辑类
 * @author Administrator
 *
 */
public class EmpBiz extends BaseBiz<Emp> implements IEmpBiz {

	private IEmpDao empDao;
	
	public void setEmpDao(IEmpDao empDao) {
		this.empDao = empDao;
		setBaseDao(empDao);
	}

	@Override
	public Emp findByUsernameAndPwd(String username, String pwd) {
		// TODO Auto-generated method stub
		return empDao.findByUsernameAndPwd(username, pwd);
	}

	
}
