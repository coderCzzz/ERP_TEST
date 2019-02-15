[TOC]

# 知识点总结

### day02

#### (一)easyUI

1. datagrid属性和方法

- pagination开启分页栏。并会给后台带page和row两个参数。后台的返回格式有要求{total：total，rows：[]}
- getData方法：获取表格数据{total：10，rows：[]}
- toolbar：加添加之类的按钮的
- reload：刷新数据，通常用于增删改后刷新表格数据
- 列属性：formatter 比如把列转换为相应的<a >

2. messager消息窗口

- alert：消息提醒，参数：1 窗口标题 2：提示信息
- confirm：询问对话框 参数：1 窗口标题  2：提示信息  3：回调函数  

3. form表单

- load:加载数据，通常用于修改数据前读取记录信息
- clear：清空表单数据，通常用于增加数据前清空表单数据

#### (二)json&js

- `JSON.stringfy`：将表单对象转化为json字符串

  fastjson：`JSON.toJsonString`：把对象转化为json字符串

  `JSON.praseObject`:把json字符串转化为map

#### (三)hibernate

1. 离线QBC查询：不需要session就可以创建

2. 分页查询

3. 投影查询

4. 序列生成策略的设置
### day03
#### (一)easyui
1. 格式化器：`formatter:function(value,row,index)`  
`row`表示当前列的对象数据，value代表返回的字段值
2. 日期输入控件`class="easy-databox"`
3. 下拉框`class="easyui-combobox" data-options="
url:'',valueField:'提交的key'，textField：'下拉显示的key'"`
4. 验证控件：`class="easyui-validatebox" data-options="required:true"`  
`validType`:验证类型  
`missMessage`:文本框未填写时的提示信息
`invalidMessage`：验证失败出现的信息
5. form的validate方法：校验表单的所有控件是否均已通过校验
6. `numberbox`:数字框
#### (二)fastJSON
- 对json字符串进行日期格式化
`JSON.toJSONStringWithDateFormat(obj,"yyyy-MM-dd")`
#### (三)hibernate
1. 多对一关联的配置
`<many-to-one></many-to-one>`
2. 删除一的一方，导致多的一方的列表无法显示  
解决方案：1.添加外键约束 2.建立级联删除

   

   

   

   











