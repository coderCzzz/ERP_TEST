package cn.itcast.erp.biz;

import java.util.List;

import cn.itcast.erp.entity.Dep;

public interface IDepBiz {
	//��ѯȫ���б�
List<Dep> getList();
//������ѯ
List<Dep> getList(Dep dep1);
}
