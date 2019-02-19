package cn.itcast.erp.action;
import cn.itcast.erp.biz.ISupplierBiz;
import cn.itcast.erp.entity.Supplier;

/**
 * 供应商Action 
 * @author Administrator
 *
 */
public class SupplierAction extends BaseAction<Supplier> {

	private ISupplierBiz supplierBiz;
	
	public void setSupplierBiz(ISupplierBiz supplierBiz) {
		this.supplierBiz = supplierBiz;
		super.setBaseBiz(this.supplierBiz);
	}
	private String q;

	public void setQ(String q) {
		this.q = q;
	}

	@Override
	public void list() {
		if(null==getT1()){
			setT1(new Supplier());
		}
		getT1().setName(q);
		super.list();
	}
	
	
	
}
