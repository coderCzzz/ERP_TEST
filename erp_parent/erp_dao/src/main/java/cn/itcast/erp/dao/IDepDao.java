package cn.itcast.erp.dao;
import java.util.List;
import cn.itcast.erp.entity.Dep;
public interface IDepDao {
List<Dep> getList();
//��ҳ�����
List<Dep> getList(Dep dep1,int firstResult,int maxResults);
//�����ܼ�¼��
long getCount(Dep dep1);
}
