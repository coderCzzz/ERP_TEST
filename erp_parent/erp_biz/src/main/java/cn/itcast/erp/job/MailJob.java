package cn.itcast.erp.job;

import java.util.List;

import cn.itcast.erp.biz.IStoredetailBiz;
import cn.itcast.erp.entity.Storealert;

public class MailJob {
/**
 * 后台定时检测库存预警
 * 如果存在库存预警，则发预警邮件给相关工作人员
 */
	/**
	 * 商品库存业务
	 */
	private IStoredetailBiz storedetailBiz;
	public void setStoredetailBiz(IStoredetailBiz storedetailBiz) {
		this.storedetailBiz = storedetailBiz;
	}
	public void sendStorealertMail(){
		//查询是否存在库存预警
		List<Storealert> storealertList = storedetailBiz.getStorealertList();
		if(storealertList.size()>0){
			try {
				//调用业务发送预警邮件
				storedetailBiz.sendStoreAlertMail();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
}
