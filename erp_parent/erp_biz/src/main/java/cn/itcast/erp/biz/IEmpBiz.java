package cn.itcast.erp.biz;
import cn.itcast.erp.entity.Emp;
/**
 * 员工业务逻辑层接口
 * @author Administrator
 *
 */
public interface IEmpBiz extends IBaseBiz<Emp>{
/**
 * 根据用户名和密码查询用户信息	
 */
Emp findByUsernameAndPwd(String username,String pwd);
/**
 * 修改密码
 */
void updatePwd(Long id,String oldPwd,String newPwd);
/**
 * 重置员工密码	
 */
void updatePwd_reset(Long uuid,String newPwd);

}

