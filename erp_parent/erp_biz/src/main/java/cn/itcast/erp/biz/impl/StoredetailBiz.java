package cn.itcast.erp.biz.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.itcast.erp.biz.IStoredetailBiz;
import cn.itcast.erp.dao.IBaseDao;
import cn.itcast.erp.dao.IGoodsDao;
import cn.itcast.erp.dao.IStoreDao;
import cn.itcast.erp.dao.IStoredetailDao;
import cn.itcast.erp.entity.Storedetail;
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
}
