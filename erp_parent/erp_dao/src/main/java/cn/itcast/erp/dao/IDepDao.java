package cn.itcast.erp.dao;
import java.util.List;
import cn.itcast.erp.entity.Dep;
public interface IDepDao {
List<Dep> getList();
//��ҳ�����
List<Dep> getList(Dep dep1,Dep dep2,Object param,int firstResult,int maxResults);
//�����ܼ�¼��
long getCount(Dep dep1,Dep dep2,Object param);
//��������
void add(Dep dep);
//ɾ������
void delete(Long uuid);
}
