package cn.itcast.erp.action;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import cn.itcast.erp.biz.ISupplierBiz;
import cn.itcast.erp.entity.Supplier;
import cn.itcast.erp.exception.ErpException;

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
	//上传相关
	private File file;
	private String fileFileName;
	private String fileContentType;
	
	public void setFile(File file) {
		this.file = file;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
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
	/**
	 * 导出excel文件
	 */
	public void export(){
		String filename="";
		if("1".equals(getT1().getType())){
			filename="供应商.xls";
		}
		if("2".equals(getT1().getType())){
			filename="客户.xls";
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			response.setHeader("Content-Disposition", "attachment;filename="+new String(filename.getBytes(),"ISO-8859-1"));
			supplierBiz.export(response.getOutputStream(), getT1());
		} catch (UnsupportedEncodingException e) {
			// TODO: handle exception
			e.printStackTrace();
		}catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public void doImport(){
		if(!"application/vnd.ms-excel".equals(fileContentType)){
			write(ajaxReturn(false, "上传的文件必须为excel文件"));
			return;
		}
		try {
			supplierBiz.doImport(new FileInputStream(file));
			write(ajaxReturn(true, "上传文件成功"));
		} catch (ErpException e) {
			// TODO: handle exception
			write(ajaxReturn(false, e.getMessage()));
		}catch (Exception e) {
			// TODO: handle exception
			write(ajaxReturn(false, "上传文件失败"));
			e.printStackTrace();
		}
	}
	
	
	
}
