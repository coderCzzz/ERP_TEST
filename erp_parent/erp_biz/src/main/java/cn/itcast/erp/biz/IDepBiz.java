package cn.itcast.erp.biz;

import java.util.List;

import cn.itcast.erp.entity.Dep;

public interface IDepBiz {
	//��ѯȫ���б�
List<Dep> getList();
//������ѯ
List<Dep> getList(Dep dep1,Dep dep2,Object param,int firstResult,int maxResults);
long getCount(Dep dep1,Dep dep2,Object param);
void add(Dep dep);
void delete(Long uuid);
}
