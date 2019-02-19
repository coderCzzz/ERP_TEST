package cn.itcast.erp.biz.impl;

import java.util.List;
import java.util.Map;

import cn.itcast.erp.dao.IBaseDao;
import cn.itcast.erp.dao.IEmpDao;
import cn.itcast.erp.dao.IGoodsDao;
import cn.itcast.erp.dao.IStoreDao;
import cn.itcast.erp.dao.ISupplierDao;
/**
 * 基本业务逻辑类
 * @author Administrator
 *
 * @param <T>
 */
public class BaseBiz<T> {

	private IBaseDao<T> baseDao;
	
	public void setBaseDao(IBaseDao baseDao) {
		this.baseDao = baseDao;
	}

	/**
	 * 条件查询
	 */
	public List<T> getList(T t1,T t2,Object param) {
		return baseDao.getList(t1,t2,param);
	}

	/**
	 * 分页条件查询
	 */
	public List<T> getListByPage(T t1, T t2, Object param, int firstResult, int maxResults) {
		
		return baseDao.getListByPage(t1, t2, param, firstResult, maxResults);
	}

	/**
	 * 统计记录个数
	 */
	public long getCount(T t1, T t2, Object param) {
		
		return baseDao.getCount(t1, t2, param);
	}

	/**
	 * 增加
	 */
	public void add(T t) {
		baseDao.add(t);		
	}
	
	/**
	 * 修改
	 */
	public void update(T t) {
		baseDao.update(t);		
	}

	/**
	 * 删除
	 */
	public void delete(Long id) {
		baseDao.delete(id);		
	}

	/**
	 * 查询实体
	 */
	public T get(Long id) {		
		return (T) baseDao.get(id);
	}
	public T get(String id){
		return (T)baseDao.get(id);
	}
	public String getGoodsName(Long uuid,Map<Long,String> goodsNameMap,IGoodsDao goodsDao){
		if(null==uuid){
			return null;
		}
		String goodsName = goodsNameMap.get(uuid);
		if(null==goodsName){
			goodsName=goodsDao.get(uuid).getName();
			goodsNameMap.put(uuid, goodsName);
		}
		return goodsName;
	}
	public String getStoreName(Long uuid,Map<Long,String> storeNameMap,IStoreDao storeDao){
		if(null==uuid){
			return null;
		}
		String storeName = storeNameMap.get(uuid);
		if(null==storeName){
			storeName=storeDao.get(uuid).getName();
			storeNameMap.put(uuid, storeName);
		}
		return storeName;
	}
	public String getEmpName(Long uuid,Map<Long,String> empNameMap,IEmpDao empDao){
		if(null==uuid){
			return null;
		}
		String empName=empNameMap.get(uuid);
		if(null==empName){
			//如果没有在缓存中找到，则调用dao查询
			empName=empDao.get(uuid).getName();
			empNameMap.put(uuid, empName);
		}
		return empName;
	}
	public String getSupplierName(Long uuid,Map<Long,String> supplierNameMap,ISupplierDao supplierDao){
		if(null==uuid){
			return null;
		}
		String supplierName = supplierNameMap.get(uuid);
		if(null==supplierName){
			supplierName=supplierDao.get(uuid).getName();
			supplierNameMap.put(uuid, supplierName);
		}
		return supplierName;
	}
	
}
