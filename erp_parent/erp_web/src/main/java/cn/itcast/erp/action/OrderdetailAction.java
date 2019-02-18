package cn.itcast.erp.action;
import cn.itcast.erp.biz.IOrderdetailBiz;
import cn.itcast.erp.entity.Emp;
import cn.itcast.erp.entity.Orderdetail;
import cn.itcast.erp.exception.ErpException;

/**
 * 订单明细Action 
 * @author Administrator
 *
 */
public class OrderdetailAction extends BaseAction<Orderdetail> {

	private IOrderdetailBiz orderdetailBiz;
	
	public void setOrderdetailBiz(IOrderdetailBiz orderdetailBiz) {
		this.orderdetailBiz = orderdetailBiz;
		super.setBaseBiz(this.orderdetailBiz);
	}
	//仓库编号
	private Long storeuuid;

	public Long getStoreuuid() {
		return storeuuid;
	}
	public void setStoreuuid(Long storeuuid) {
		this.storeuuid = storeuuid;
	}
	public void doInStore(){
		Emp loginUser = getLoginUser();
		if(null==loginUser){
			write(ajaxReturn(false, "请您登录"));
			return;
		}
		try {
			orderdetailBiz.doInStore(getId(), storeuuid, loginUser.getUuid());
			write(ajaxReturn(true, "入库成功"));
		} catch (ErpException e) {
			// TODO: handle exception
			write(ajaxReturn(false, e.getMessage()));;
		}catch(Exception e){
			e.printStackTrace();
			write(ajaxReturn(false, "入库失败！"));;
		}
	}
	
	
}
