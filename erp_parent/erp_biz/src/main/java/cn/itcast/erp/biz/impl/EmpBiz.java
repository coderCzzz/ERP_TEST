package cn.itcast.erp.biz.impl;
import org.apache.shiro.crypto.hash.Md5Hash;

import cn.itcast.erp.biz.IEmpBiz;
import cn.itcast.erp.dao.IEmpDao;
import cn.itcast.erp.entity.Emp;
import cn.itcast.erp.exception.ErpException;
/**
 * 员工业务逻辑类
 * @author Administrator
 *
 */
public class EmpBiz extends BaseBiz<Emp> implements IEmpBiz {
	/**
	 * 散列次数
	 */
	private int hashIterations=2;
	private IEmpDao empDao;
	
	public void setEmpDao(IEmpDao empDao) {
		this.empDao = empDao;
		setBaseDao(empDao);
	}
	/**
	 * 根据用户名和密码查询登录用户信息
	 */
	@Override
	public Emp findByUsernameAndPwd(String username, String pwd) {
		// TODO Auto-generated method stub
		String encrypted = encrypt(pwd, username);
		System.err.println(encrypted);
		return empDao.findByUsernameAndPwd(username, encrypted);
	}
	/**
	 * MD5加密
	 */
	private String encrypt(String src,String salt){
		Md5Hash md5=new Md5Hash(src, salt, hashIterations);
		return md5.toString();
	}
	/**
	 * 添加
	 */
	@Override
	public void add(Emp emp) {
		String salt=emp.getUsername();
		String encrypt = encrypt(emp.getUsername(), salt);
		emp.setPwd(encrypt);
		empDao.add(emp);
	}
	/**
	 * 修改密码
	 */
	@Override
	public void updatePwd(Long id, String oldPwd, String newPwd)throws ErpException {
		// TODO Auto-generated method stub
		Emp emp = this.empDao.get(id);
		String encryptOldPwd = encrypt(oldPwd, emp.getUsername());
		if(!encryptOldPwd.equals(emp.getPwd())){
			throw new ErpException("原密码不正确");
		}
		String encryptNewPwd=encrypt(newPwd, emp.getUsername());
		empDao.updatePwd(id, encryptNewPwd);
	}
	/**
	 * 重置密码
	 */
	@Override
	public void updatePwd_reset(Long uuid, String newPwd) {
		// TODO Auto-generated method stub
		Emp emp = this.empDao.get(uuid);
		emp.setPwd(encrypt(newPwd, emp.getUsername()));
	}
	
}
