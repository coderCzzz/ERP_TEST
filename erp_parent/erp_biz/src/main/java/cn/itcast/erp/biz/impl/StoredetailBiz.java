package cn.itcast.erp.biz.impl;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import cn.itcast.erp.biz.IStoredetailBiz;
import cn.itcast.erp.dao.IBaseDao;
import cn.itcast.erp.dao.IGoodsDao;
import cn.itcast.erp.dao.IStoreDao;
import cn.itcast.erp.dao.IStoredetailDao;
import cn.itcast.erp.dao.impl.StoredetailDao;
import cn.itcast.erp.entity.Storealert;
import cn.itcast.erp.entity.Storedetail;
import cn.itcast.erp.exception.ErpException;
import cn.itcast.erp.util.MailUtil;
/**
 * 仓库库存业务逻辑类
 * @author Administrator
 *
 */
public class StoredetailBiz extends BaseBiz<Storedetail> implements IStoredetailBiz {

	private IStoredetailDao storedetailDao;
	//商品
	private IGoodsDao goodsDao;
	//仓库
	private IStoreDao storeDao;
	//邮件预警
	private MailUtil mailUtil;
	private String to;
	private String subject;
	private String text;
	
	public void setMailUtil(MailUtil mailUtil) {
		this.mailUtil = mailUtil;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public void setText(String text) {
		this.text = text;
	}
	public IGoodsDao getGoodsDao() {
		return goodsDao;
	}
	public void setGoodsDao(IGoodsDao goodsDao) {
		this.goodsDao = goodsDao;
	}
	public IStoreDao getStoreDao() {
		return storeDao;
	}
	public void setStoreDao(IStoreDao storeDao) {
		this.storeDao = storeDao;
	}
	public void setStoredetailDao(IStoredetailDao storedetailDao) {
		this.storedetailDao = storedetailDao;
		setBaseDao(storedetailDao);
	}
	//重写分页
	@Override
	public List<Storedetail> getListByPage(Storedetail t1, Storedetail t2, Object param, int firstResult,
			int maxResults) {
		// TODO Auto-generated method stub
		List<Storedetail> list = super.getListByPage(t1, t2, param, firstResult, maxResults);
		Map<Long,String> goodsNameMap=new HashMap<Long,String>();
		Map<Long,String> storeNameMap=new HashMap<Long,String>();
		for(Storedetail sd:list){
			sd.setGoodsName(getGoodsName(sd.getGoodsuuid(), goodsNameMap,goodsDao));
			sd.setStoreName(getStoreName(sd.getStoreuuid(), storeNameMap,storeDao));
		}
		return list;
	}
	@Override
	public List<Storealert> getStorealertList() {
		// TODO Auto-generated method stub
		return storedetailDao.getStorealertList();
	}
	@Override
	public void sendStoreAlertMail() throws MessagingException {
		//1.查看是否需要预警的商品
		List<Storealert> storealertList = storedetailDao.getStorealertList();
		int cnt=storealertList==null?0:storealertList.size();
		if(cnt>0){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			mailUtil.sendMail(to, subject.replace("[time]", sdf.format(new Date())), text.replace("[count]", String.valueOf(cnt)));
		}else{
			throw new ErpException("没有要预警的商品！");
		}
		
	}	
}
