var method='';
	$(function(){
		//表格初始化
		$('#grid').datagrid({
			url:name+'_getList',
			columns:columns,
			singleSelect:true,
			pagination:true,
			 toolbar:[{
					text:'新增',
					iconCls: 'icon-add',
					handler: function(){
						method="add";
						$('#editDlg').dialog('open');
						}
			   }]
		});
		//条件查询
		$('#btnSearch').bind('click',function(){
			var formdata=$('#searchForm').serializeJSON();
			$('#grid').datagrid('load',formdata);
		});
		$('#editDlg').dialog({    
		    title: "部门编辑",    
		    width: 400,    
		    height: 200,    
		    closed: true,       
		    modal: true   
		});
		//保存
		$('#btnSave').bind('click',function(){
			var formdata=$('#editForm').serializeJSON();
			$.ajax({
				url:name+'_'+method,
				data:formdata,
				datatype:'json',
				type:'post',
				success:function(rtn){
					$.messager.alert("提示",rtn.message,'info',function(){
						//成功的话关闭窗口
						$('#editDlg').dialog('close');
						//刷新表单数据
						$('#grid').datagrid('reload');
					});
				}
			});
		});
	});
	//删除
	function del(uuid){
		$.messager.confirm("确认","确认要删除吗？",function(yes){
			if(yes){
				$.ajax({
					url: name+'_delete?id=' + uuid,
					dataType: 'json',
					type: 'post',
					success:function(rtn){
						$.messager.alert("提示",rtn.message,'info',function(){
							//刷新表格数据
							$('#grid').datagrid('reload');
						});
					}
				});
			}
		});
	}
	/* 修改部门  */
	function edit(uuid){
		method="update";
		$('#editDlg').dialog('open');
		$('#editForm').form('clear');
		$('#editForm').form('load',name+'_get?id='+uuid);
	}