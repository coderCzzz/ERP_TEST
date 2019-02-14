package cn.itcast.erp.dao;

import java.util.List;

public interface IBaseDao<T> {
	//��ҳ�����
	List<T> getList(T t1,T t2,Object param,int firstResult,int maxResults);
	//�����ܼ�¼��
	long getCount(T t1,T t2,Object param);
	//��������
	void add(T dep);
	//ɾ������
	void delete(Long uuid);
	//��ȡ���в�����Ϣ
	T get(Long uuid);
	//�޸Ĳ���
	void update(T t);
}
