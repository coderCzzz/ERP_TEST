package cn.itcast.erp.biz;
import java.io.OutputStream;
import java.util.List;

import cn.itcast.erp.entity.Orders;
import cn.itcast.redsun.ws.Waybilldetail;
/**
 * 订单业务逻辑层接口
 * @author Administrator
 *
 */
public interface IOrdersBiz extends IBaseBiz<Orders>{
/**
 * 采购订单审核	
 */
void doCheck(Long uuid,Long empUuid);
void doStart(Long uuid,Long empUuid);
/**
 * 导出订单明细
 */
public void exportById(OutputStream os,Long uuid)throws Exception;
List<Waybilldetail> waybilldetailList(Long sn);
}

