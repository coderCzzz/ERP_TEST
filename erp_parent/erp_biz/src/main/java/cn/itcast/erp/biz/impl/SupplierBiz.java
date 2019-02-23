package cn.itcast.erp.biz.impl;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

import cn.itcast.erp.biz.ISupplierBiz;
import cn.itcast.erp.dao.IOrdersDao;
import cn.itcast.erp.dao.ISupplierDao;
import cn.itcast.erp.entity.Supplier;
import cn.itcast.erp.exception.ErpException;
/**
 * 供应商业务逻辑类
 * @author Administrator
 *
 */
public class SupplierBiz extends BaseBiz<Supplier> implements ISupplierBiz {

	private ISupplierDao supplierDao; 
	public void setSupplierDao(ISupplierDao supplierDao) {
		this.supplierDao = supplierDao;
		setBaseDao(supplierDao);
	}

	@Override
	public void export(OutputStream os, Supplier t1) {
		List<Supplier> supplierList = supplierDao.getList(t1, null, null);
		//创建excel工作簿
		HSSFWorkbook wk=new HSSFWorkbook();
		HSSFSheet sheet=null;
		//根据类型创建相应的工作表
		if("1".equals(t1.getType())){
			sheet=wk.createSheet("供应商");
		}
		if("2".equals(t1.getType())){
			sheet=wk.createSheet("客户");
		}
		//写入表头
		HSSFRow row=sheet.createRow(0);
		String[] headerNames={"名称","地址","联系人","电话","Email"};
		HSSFCell cell=null;
		int[] columnWidth={4000,8000,2000,3000,8000};
		for (int i = 0; i < columnWidth.length; i++) {
			cell=row.createCell(i);
			cell.setCellValue(headerNames[i]);
			sheet.setColumnWidth(i, columnWidth[i]);
		}
		//写入内容
		int i=1;
		for (Supplier supplier : supplierList) {
			row=sheet.createRow(i);
			row.createCell(0).setCellValue(supplier.getName());
			row.createCell(1).setCellValue(supplier.getAddress());
			row.createCell(2).setCellValue(supplier.getContact());
			row.createCell(3).setCellValue(supplier.getTele());
			row.createCell(4).setCellValue(supplier.getEmail());
			i++;
		}
		try {
			wk.write(os);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			try {
				wk.close();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
	}

	@Override
	public void doImport(InputStream is) throws IOException {
		// TODO Auto-generated method stub
		HSSFWorkbook wb=null;
		
		try {
			wb=new HSSFWorkbook(is);
			HSSFSheet sheet = wb.getSheetAt(0);
			String type="";
			if("供应商".equals(sheet.getSheetName())){
				type="1";
			}else if("客户".equals(sheet.getSheetName())){
				type="2";
			}else{
				throw new ErpException("工作表名称不正确");
			}
			//读取数据
			//最后一行
			int lastRowNum = sheet.getLastRowNum();
			Supplier supplier=null;
			for (int i = 0; i < lastRowNum; i++) {
				supplier=new Supplier();
				supplier.setName(sheet.getRow(i).getCell(0).getStringCellValue());
				List<Supplier> list = supplierDao.getList(null, supplier, null);
				if(list.size()>0){
					supplier=list.get(0);
				}
				supplier.setAddress(sheet.getRow(i).getCell(1).getStringCellValue());
				supplier.setContact(sheet.getRow(i).getCell(2).getStringCellValue());
				supplier.setTele(sheet.getRow(i).getCell(3).getStringCellValue());
				supplier.setEmail(sheet.getRow(i).getCell(4).getStringCellValue());
				if(list.size()==0){
					supplier.setType(type);
					supplierDao.add(supplier);
				}
			}
		} finally{
			if(null!=wb){
				try {
					wb.close();
				} catch (IOException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		}
	}

	
	
}
