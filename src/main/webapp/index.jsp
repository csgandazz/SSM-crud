<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>员工列表</title>
<%
	pageContext.setAttribute("APP_PATH", request.getContextPath());
%>

<script type="text/javascript"
	src="${APP_PATH}/static/js/jquery-1.12.4.min.js"></script>
<link
	href="${APP_PATH}/static/bootstrap-3.3.7-dist/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="${APP_PATH}/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
</head>
<body>
<!-- Modal  动态模态框-->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Modal title</h4>
      </div>
      <div class="modal-body">
      		<!-- table -->
      			<form class="form-horizontal">
				  <div class="form-group">
				    <label  class="col-sm-2 control-label">empName</label>
				    <div class="col-sm-10">
				    		<!-- 数据看<input>框 -->
				      <input type="text" class="form-control"  name="empName"  id="empName_add_input" placeholder="用户名">
				      <!-- bootstrap 校验框 -->
				      <span id="helpBlock2" class="help-block"></span>
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <label for="inputPassword3" class="col-sm-2 control-label">email</label>
				    <div class="col-sm-10">
				      <input type="text" class="form-control"  name="email"  id="email_add_input" placeholder="email@qq.com">
				      <!-- span作用是bootstrap的显示标签 -->
				       <span id="helpBlock2" class="help-block"></span>
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <label for="inputPassword3" class="col-sm-2 control-label">gender</label>
				    <div class="col-sm-10">
				    	<label class="radio-inline">
					  <input type="radio" name="gender"  id="gender1_add_input" value="M" checked="checked"> 男
					</label>
					<label class="radio-inline">
					  <input type="radio"  name="gender"  id="gender2_add_input" value="F">女
				</label>	
 				</div>
			</div>		
					    
				     <div class="form-group">
					    <label for="inputEmail3" class="col-sm-2 control-label">deptName</label>
					    <div class="col-sm-4">
					      	<select class="form-control" name="dId" id="dept_add_select">
							 	
							</select>
					    </div>
					  </div>	 			  
</form>

      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal" id="emp_close_btn">Close</button>
        <button type="button" class="btn btn-primary" id="emp_save_btn">Save </button>
      </div>
    </div>
  </div>
</div>

<!-- 修改模态框 -->
<div class="modal fade" id="empUpdateModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">员工修改</h4>
      </div>
      <div class="modal-body">
        <form class="form-horizontal">
		  <div class="form-group">
		    <label class="col-sm-2 control-label">empName</label>
		    <div class="col-sm-10">
		      	<p class="form-control-static" id="empName_update_static"></p>
		    </div>
		  </div>
		  <div class="form-group">
		    <label class="col-sm-2 control-label">email</label>
		    <div class="col-sm-10">
		      <input type="text" name="email" class="form-control" id="email_update_input" placeholder="email@atguigu.com">
		      <span class="help-block"></span>
		    </div>
		  </div>
		  <div class="form-group">
		    <label class="col-sm-2 control-label">gender</label>
		    <div class="col-sm-10">
		      <label class="radio-inline">
				  <input type="radio" name="gender" id="gender1_update_input" value="M" checked="checked"> 男
				</label>
				<label class="radio-inline">
				  <input type="radio" name="gender" id="gender2_update_input" value="F"> 女
				</label>
		    </div>
		  </div>
		  <div class="form-group">
		    <label class="col-sm-2 control-label">deptName</label>
		    <div class="col-sm-4">
		    	<!-- 部门提交部门id即可 -->
		      <select class="form-control" name="dId">
		      </select>
		    </div>
		  </div>
		</form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button type="button" class="btn btn-primary" id="emp_update_btn">更新</button>
      </div>
    </div>
  </div>
</div>

	<!-- 搭建显示页面 -->
	<div class="container">
		<!-- 标题 -->
		<div class="row">
			<div class="col-md-12">
				<h1>SSM-CRUD</h1>
			</div>
		</div>
		<!-- 按钮 -->
		<div class="row">
			<div class="col-md-4 col-md-offset-9">
				<button class="btn btn-primary" id="emp_add_modal">新增</button>
				<button class="btn btn-danger" id="emp_del_btn">删除</button>
			</div>
		</div>
		<!-- 显示表格数据 -->
		<div class="row">
			<div class="col-md-12">
				<table class="table table-striped" id="emps_table">
				<thead>
					<tr>
						<th><input type="checkbox" id="check_all"></th>
						<th>#</th>
						<th>empName</th>
						<th>gender</th>
						<th>email</th>
						<th>deptName</th>
						<th>   部门</th>
						<th>        操作</th>
					</tr>
				</thead>
				<tbody>
				
				</tbody>	
					
				</table>
				
			</div>
		</div>

		<div class="row">
			<div class="col-md-6"  id="page_info_area"></div>
			<!-- 分页条信息 -->
			<div class="col-md-6" id="page_nav_area"></div>
		</div>
		
	</div>
<script type="text/javascript">
	var totalRecord,currentNum; //总页码数，全局变量，关联保存表单返回最后一页有关
	
	//页面加载完成以后，发送一个ajax请求，获取分页数据
	$(function(){
		to_page(1);
	}); //function()

	function to_page(pn){
		$.ajax({
			url:"${APP_PATH}/emps",
			data:"pn="+pn,
			type:"GET",
			success:function(result){
				//console.log(result);
				//result已经是json数据了
				//解析json数据，并显示员工，分页信息
				//调用函数1，使显示员工数据
				//调用函数2，显示分页总信息
				//调用函数3，显示分页
				build_emp_table(result);
				build_page_nav(result);
				build_page_info(result);
			}
			}); //ajax
	}
	
	function build_emp_table(result){
		
		var emps=result.extend.pageInfo.list;  //json数据就是ajax的回调函数result
		//必须之前的清空数据，接分页的ajax请求
		$("#emps_table tbody").empty();
		$.each(emps,function(index,items){
			//alert(items.empName);
			
			var checkbox=$("<th><input type='checkbox' class='check_item'/></th>");
			var empid=$("<th></th>").append(items.empId);
			var empname=$("<th></th>").append(items.empName);
			var gender=$("<th></th>").append(items.gender== "M"?"男":"女");
			var emai=$("<th></th>").append(items.email);
			var dId=$("<th></th>").append(items.dId);
			var department=$("<th></th>").append(items.department.deptName);
			
			//添加按钮
			var button_edit=$("<button></button>").addClass("btn btn-primary btn-sm edit_btn").append($("<span></span>"))
			.addClass("glyphicon glyphicon-pencil").append("编辑");
			//每一个按钮后面添加一个属性（放id值得属性）
			button_edit.attr("edit-id",items.empId);
			var button_delete=$("<button></button>").addClass("btn btn-danger btn-sm delete_btn").append($("<span></span>"))
			.addClass("glyphicon glyphicon-trash").append("删除");
			//添加存放id的属性
			button_delete.attr("del-id",items.empId);
			var btn=$("<td></td>").append(button_edit).append(" ").append(button_delete);
			
			//$()jquery元素选择器
			//添加table员工数据
			$("<tr></tr>").append(checkbox)
			.append(empid)
			.append(empname)
			.append(gender)
			.append(emai)
			.append(dId)
			.append(department)
			.append(btn)
			.appendTo("#emps_table tbody");			
		});

	};
	
	//解析显示分页信息
	function build_page_nav(result){
			var pageinfo=result.extend.pageInfo;
			//清空数据
			$("#page_info_area").empty();
			
			$("#page_info_area").append("当前第"+pageinfo.pageNum+"页，总页数: "+pageinfo.pages+"页，总记录数:"
					+pageinfo.total+"条");			
			totalRecord=pageinfo.pages;
			currentNum=pageinfo.pageNum;
	};
	
	function build_page_info(result){ //根据pageinfo对象里面的pagenum和navigatepageNums这两个参数，每一次都需要渲染遍历一遍
		//清空数据
		$("#page_nav_area").empty();
		
		var ul=$("<ul></ul>").addClass("pagination");  
		var firstPageLi=$("<li></li>").append($("<a></a>").append("首页").attr("href","#"));
		var prePageLi=$("<li></li>").append($("<a></a>").append("&laquo;"));  //前进标签
		if(result.extend.pageInfo.hasPreviousPage==false){ //是否第一页判断
			firstPageLi.addClass("disabled");
			prePageLi.addClass("disabled");
		}
		
		//首页，前进页添加点击事件
		firstPageLi.click(function(){
			to_page(1);
		});
		
		//翻页添加点击事件
		prePageLi.click(function(){
			to_page(result.extend.pageInfo.pageNum-1);
		});
		var lastPageLi=$("<li></li>").append($("<a></a>").append("尾页").attr("href","#"));
		var nextPageLi=$("<li></li>").append($("<a></a>").append("&raquo;")); //尾页
		if(result.extend.pageInfo.hasNextPage==false){//是否最后一页
			lastPageLi.addClass("disabled");
			nextPageLi.addClass("disabled");
		}
		
		lastPageLi.click(function(){
			to_page(result.extend.pageInfo.pages);
		});
		
		nextPageLi.click(function(){
			to_page(result.extend.pageInfo.pageNum+1);
		});
		ul.append(firstPageLi).append(prePageLi); //吧第一页和上一页插入到ul标签里
		
		$.each(result.extend.pageInfo.navigatepageNums,function(index,item){//遍历页码导航栏+逻辑+点击事件
						//遍历流程，每一个页面遍历一次
						//点击事件发送ajax请求
				var numLi=$("<li></li>").append($("<a></a>").append(item));
				if(result.extend.pageInfo.pageNum==item){
					numLi.addClass("active");
				}
				
				numLi.click(
						function()
						{
							to_page(item);  //添加练级事件给每个<li>标签，result.extend.pageInfo.navigatepageNums
													//必须清空数据，在ajax回调函数处清空
						});
				
				ul.append(numLi); //插入到ul里，执行插入操作
		});
		
		ul.append(nextPageLi).append(lastPageLi); //插入操作
		var navEle=$("<nav></nav>").append(ul); 
		navEle.appendTo("#page_nav_area");
	};
	
	//新的模块：点击模态框
	//新增添加点击事件
	$("#emp_add_modal").click(function(){
			//重置表单
			$("#myModal form")[0].reset();
			$("#myModal form").find("*").removeClass("has-success has-error");//删除所有
			$("#myModal form").find(".help-block").text("");
			//发送ajax（获取部门信息的请求）
			getDepts("#dept_add_select");
			$('#myModal').modal({
				//使弹出模态框
				backdrop:"static"
				})
	});
		
		//关闭时触发清空事件
		$("#emp_close_btn").click(function(){
			//清空class元素
			show_validate_msg("#empName_add_input", "", "");
			show_validate_msg("#email_add_input", "", "");
			$("#empName_add_input").val("");
			$("#email_add_input").val("");
		});
			
		
		//从数据库里查出部门
		function getDepts(ele){
				$.ajax({
					url:"${APP_PATH}/depts",
					type:"GET",
					success:function(result){
						$(ele).empty();
						//动态显示部门信息
						$.each(result.extend.depts,function(index,items){
								var optionEle=$("<option></option>").append(items.deptName).attr("value",items.deptId);
								optionEle.appendTo(ele);
						});
					}
				});
			};
		
			//校验表单数据
			function validate_add_form(){
				//1、拿到要校验的数据，使用正则表达式
				var empName = $("#empName_add_input").val();
				var regName = /(^[a-zA-Z0-9_-]{6,16}$)|(^[\u2E80-\u9FFF]{2,5})/;
				if(!regName.test(empName)){
					//alert("用户名可以是2-5位中文或者6-16位英文和数字的组合");
					show_validate_msg("#empName_add_input", "error", "用户名可以是2-5位中文或者6-16位英文和数字的组合");
					return false;
				}else{
					show_validate_msg("#empName_add_input", "success", "");
				};
				//2、校验邮箱信息
				var email = $("#email_add_input").val();
				var regEmail = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
				if(!regEmail.test(email)){
					//alert("邮箱格式不正确");
					//应该清空这个元素之前的样式
					show_validate_msg("#email_add_input", "error", "邮箱格式不正确");
					/* $("#email_add_input").parent().addClass("has-error");
					$("#email_add_input").next("span").text("邮箱格式不正确"); */
					return false;
				}else{
					show_validate_msg("#email_add_input", "success", "");
				}
				return true;
			}
			
			//显示校验结果的提示信息
			function show_validate_msg(ele,status,msg){
				//清除元素的校验状态
				$(ele).parent().removeClass("has-success has-error");
				$(ele).next("span").text("");
				if("success"==status){
					$(ele).parent().addClass("has-success");
					$(ele).next("span").text(msg);
				}else if("error" == status){
					$(ele).parent().addClass("has-error");
					$(ele).next("span").text(msg);
				}
			}
			
			//校验用户名是否可用
			$("#empName_add_input").change(function(){
				//发送ajax请求校验用户名是否可用
				var empName = this.value;
				$.ajax({
					url:"${APP_PATH}/checkUser",
					data:"empName="+empName,
					type:"POST",
					success:function(result){
						if(result.code==100){
							show_validate_msg("#empName_add_input","success","用户名可用");
							//添加<input>标签属性验证
							$("#emp_save_btn").attr("ajax-va","success");
						}else{
							show_validate_msg("#empName_add_input","error",result.extend.va_msg);
							$("#emp_save_btn").attr("ajax-va","error");
						}
					}
				});
			});	
			
			//“保存按钮”添加点击事件(校验)
			//保存时触发校验
			$("#emp_save_btn").click(function(){
				//用户名不过已重复则上锁
				if($(this).attr("ajax-va")=="error"){
					return false;
				}	
				//校验用户名是否合法
				if(!validate_add_form()){
					return false;
					}	 
				$.ajax({
					url:"${APP_PATH}/emps",
					type:"POST",
					data:$("#myModal form").serialize(),
					success:function(result){
						//JSR303校验后的新步骤
						//判断返回的回调函数是否成功校验
						if(result.code==100){
							alert("添加成功");//此处不需要去验证是否成功
							$('#myModal').modal('hide');
							to_page(totalRecord);
						}else {
							//显示错误信息
							//有哪个显示那个
							if(undefined != result.extend.errorFields.email){
								//显示邮箱错误信息
								show_validate_msg("#email_add_input", "error", result.extend.errorFields.email);
							}
							if(undefined != result.extend.errorFields.empName){
								//显示员工名字的错误信息
								show_validate_msg("#empName_add_input", "error", result.extend.errorFields.empName);
							}
						}			
					}
				});
									
			});			
		
			//因为“添加”是在ajax请求后添加的按钮，所以点击事件是在绑定之前的函数，会失效
			//可以在创建按钮的时候去绑定点击事件
			//所有修改按钮绑定点击事件
			$(document).on("click",".edit_btn",function(){
				//往模态框里
				getDepts("#empUpdateModal select");
				//获取员工的信息
				getEmps($(this).attr("edit-id"));
				//使更新按钮带有id的属性
				$("#emp_update_btn").attr("edit-id",$(this).attr("edit-id"));
				//使弹出模态框
				$('#empUpdateModal').modal({
					backdrop:"static"
					})
			});
			
			//获取员工信息
			function getEmps(id){
				$.ajax({
					url:"${APP_PATH}/emp/"+id,
					type:"GET",
					success:function(result){
					console.log(result);
					var empData=result.extend.emp;
					$("#empName_update_static").text(empData.empName);
					$("#email_update_input").val(empData.email);
					$("#empUpdateModal input[name=gender]").val([empData.gender]);
					$("#empUpdateModal select").val([empData.dId]);
					}
				});
			}
			
			//修改按钮绑定点击事件
			$("#emp_update_btn").click(function(){
					//前后端校验
					//邮箱是否合法
				var email = $("#email_update_input").val();
				var regEmail = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
				if(!regEmail.test(email)){
					//alert("邮箱格式不正确");
					//应该清空这个元素之前的样式
					show_validate_msg("#email_update_input", "error", "邮箱格式不正确");
					/* $("#email_add_input").parent().addClass("has-error");
					$("#email_add_input").next("span").text("邮箱格式不正确"); */
					return false;
				}else{
					show_validate_msg("#email_update_input", "success", "");
				}
				
				//发送ajax请求
				$.ajax({
					url:"${APP_PATH}/emp/"+$(this).attr("edit-id"),
					type:"PUT",
					data:$("#empUpdateModal form").serialize(),
					success:function(result){
						//console.log(result.Msg);
						alert("添加成功！")
						//关闭模态框
						$("#empUpdateModal").modal("hide");
						//回到本页面
						to_page(currentNum);
					}
				});
			});
			
			//单个删除
			$(document).on("click",".delete_btn",function(){
				//获取empName
				//当前剑点击按钮的父节点tr，再找th第二个元素
				empName=$(this).parents("tr").find("th:eq(2)").text();
				empId=$(this).attr("del-id");
				if(confirm("是否删除【"+empName+"】的用户吗？")){
					//确认删除ajax请求
					$.ajax({
						url:"${APP_PATH}/emp/"+empId,
						type:"DELETE",
						success:function(result){
							alert(result.msg);
							//回到本页面
							to_page(currentNum);
						}
					})
				}
		
			});
			
			//全选功能
			$("#check_all").click(function(){
				//console.log("1");
				//一点击复选框其他全部选中
				//prop("checked")方法为复选框的状态
				$(".check_item").prop("checked",$(this).prop("checked"));
			});
			
			//check_item
			$(document).on("click",".check_item",function(){
				//判断当前选择中的元素是否5个
				if(!$(this).prop("check")==true){
					$("#check_all").prop("checked",false);
				}
			});
			
			//批量删除点击事件
			$("#emp_del_btn").click(function(){
				empName="";
				empId="";
				//遍历每一个选中的元素
				$.each($(".check_item:checked"),function(){
					//用this表示迭代,使用parents（）找到empName
					empName+=$(this).parents("tr").find("th:eq(2)").text()+",";		
					//为什么在迭代里面解析id？ 因为这里是遍历，吧每一个id都组装在一起
					//组装员工id，添加“-”
					empId+=$(this).parents("tr").find("th:eq(1)").text()+"-";		
				});
				//去除多余的逗号
				empName=empName.substring(0,empName.length-1);
				//291-292-293
				empId=empId.substring(0,empId.length-1);
				//console.log(empId);
				if(confirm("是否删除用户名为【"+empName+"】的员工数据？")){
					//发送ajax请求
					$.ajax({
						url:"${APP_PATH}/emp/"+empId,
						type:"DELETE",
						success:function(result){
							alert(result.msg);
							to_page(currentNum);
						}
		
					});
				}
			});
</script>
</body>
</html>
</html>