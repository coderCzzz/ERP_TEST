//保存提交的方法名称 
var method="";
//查询条件
var listParam="";
//保存附带条件
var saveParam="";
$(function(){
	//加载表格数据
	$('#grid').datagrid({
		url:name + '_listByPage' + listParam,
		columns:columns,
		singleSelect: true,
		pagination: true,
		toolbar: [{
			text: '新增',
			iconCls: 'icon-add',
			handler: function(){
				//设置保存按钮提交的方法为add
				method = "add";
				//关闭编辑窗口
				$('#editDlg').dialog('open');
			}
		},'-',{
			text: '导出',
			iconCls: 'icon-excel',
			handler: function(){
				var formData = $('#searchForm').serializeJSON();
				//下载文件
				$.download(name + "_export" + listParam,formData);
			}
		},'-',{
			text: '导入',
			iconCls: 'icon-save',
			handler: function(){
				$('#importDlg').dialog('open');
			}
		}]
	});
	
	//条件查询
	$('#btnSearch').bind('click',function(){
		var formdata= $('#searchForm').serializeJSON();
		$('#grid').datagrid('load',formdata);		
	});
	
	//保存
	$('#btnSave').bind('click',function(){
		var isValid=$('#editForm').form('validate');
		if(isValid==false){
			return;
		}
		var formdata= $('#editForm').serializeJSON();	
		$.ajax({
			url:name+'_'+method+saveParam,
			data:formdata,
			dataType:'json',
			type:'post',
			success:function(value){
				
				if(value.success){
					$('#editWindow').window('close');
					$('#grid').datagrid('reload');
				}
				$.messager.alert('提示',value.message);				
			}
			
		});
		
		
	});
	//判断是否有导入的功能
	var importForm = document.getElementById('importForm');
	if(importForm){
		$('#importDlg').dialog({
			title:'导入数据',
			width:330,
			height:106,
			modal:true,
			closed:true,
			buttons:[
			    {
			    	text: '导入',
			    	handler:function(){
			    		$.ajax({
			    			url: name + '_doImport',
			    			data:new FormData($('#importForm')[0]),
			    			type:'post',
			    			processData:false,
			    			contentType:false,
			    			dataType:'json',
			    			success:function(rtn){
			    				$.messager.alert('提示',rtn.message,'info',function(){
			    					if(rtn.success){
			    						$('#importDlg').dialog('close');
			    						$('#importForm').form('clear');
			    						$('#grid').datagrid('reload');
			    					}
			    				});
			    			}
			    		});
			    	}
			    }
			]
		});
	}
	
});

/**
 * 删除 
 */
function dele(id){
	
	$.messager.confirm('提示','确定要删除此记录吗？',function(r){
		if(r)
		{
			$.ajax({
				url:name+'_delete.action?id='+id,
				dataType:'json',
				success:function(value){
					if(value.success){
						$('#grid').datagrid('reload');
					}
					$.messager.alert('提示',value.message);
				}
			});		
		}	
	});	
}

/**
 * 编辑
 */
function edit(id){
	
	$('#editWindow').window('open');
	$('#editForm').form('clear');
	$('#editForm').form('load',name+'_get.action?id='+id);	
	method="update";
}
