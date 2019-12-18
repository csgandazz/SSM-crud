package com.gec.crud.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gec.crud.bean.Employee;
import com.gec.crud.bean.Msg;
import com.gec.crud.service.EmployeeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 处理员工crud请求
 * @author my_computer
 *	 
 */
@Controller
public class EnployeeController {
	

	@Autowired
	EmployeeService employeeService;
	private List<FieldError> fieldErrors; //错误信息
	
	
	/**
	 * 员工更新
	 * @param employee
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/emp/{empId}",method = RequestMethod.PUT)
	public Msg saveEmp(Employee employee) {
		System.out.println(employee);
		employeeService.updateEmp(employee);
		return Msg.success();
	}
	
	/*	
	 * 检查用户是否可用
	 * 检验数据集是否存在该用户
	 * 
	 */
	@ResponseBody
	@RequestMapping("/checkUser")
	public Msg checkUser(@RequestParam("empName")String empName){		
			String regx="(^[a-zA-Z0-9_-]{6,16}$)|(^[\u2E80-\u9FFF]{2,5})";
			if(!empName.matches(regx)) {
				//System.out.println("1");
				return Msg.fail().add("va_msg", "用户名必须是6-16位英文或2-5位中文组合!!");
			}
			boolean b=employeeService.checkUser(empName);
			if(b) {
				return Msg.success();	
			}else {
				return Msg.fail().add("va_msg", "用户名不可用");
			}
			
	}
	
	
	/**
	 * 员工保存(请求)
	 * 
	 * 	1.支持JSR303校验
	 * 
	 * @param  @RequestParam("empName")String empName 请求中接受的数据
	 * @param  BindingResult result  封装校验额结果，查看校验结果状态
	 * 
	 */

	@RequestMapping(value="/emps",method = RequestMethod.POST)
	@ResponseBody
	public Msg saveEmp(@Valid Employee employee ,BindingResult result){ 
			
			Map<String , Object> map=new HashMap<String, Object>();
			//后端校验
			if(result.hasErrors()) {
				//校验失败，在模态框返回校验失败
				 List<FieldError> list = result.getFieldErrors();
				 for(FieldError errors:list) {
//					 System.out.println("错误的字段名"+errors.getField());
//					 System.out.println("错误信息"+errors.getDefaultMessage());
					 //把元数据放进HashMap里
					 map.put(errors.getField(), errors.getDefaultMessage());			
				 }
				return Msg.fail().add("errorFields", map);
			}else {
				//数据户用户名重复校验
				//页面提交的key value跟employee对象一致，并自动封装	
				//调用持久层，数据库执行操作
				employeeService.saveEmp(employee); 
			}
			return Msg.success();	
	}
	
	
	
	@ResponseBody
	@RequestMapping("/emps")
	/**
	 * 该方法返回json对象（String）
	 */
	public Msg getEmpsWithJson(@RequestParam(value = "pn",defaultValue = "1")Integer pn) {
			//String orderBy="emp_id asc";
			PageHelper.startPage(pn, 5);
			//PageHelper.orderBy(orderBy);
			PageHelper.orderBy("emp_id asc");
			List<Employee> emps=employeeService.getAll(); //查询员工的所有数据
			PageInfo page = new PageInfo(emps,5); //封装分页插件的类，连续显示5页
			return new Msg().success().add("pageInfo", page);
	}
	
	/*
	 *  获取员工信息填入修改框
	 */
	@RequestMapping(value="/emp/{id}",method = RequestMethod.GET)
	@ResponseBody
	public Msg getEmps(@PathVariable("id")Integer id) {
		Employee employee=employeeService.getEmp(id);
		return Msg.success().add("emp", employee);
	}
	
	/**
	 * 删除员工
	 * 单个多个二合一
	 * requestParam:
	 * 多个：3-1-1
	 * 单个：1
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/emp/{ids}",method = RequestMethod.DELETE)
	public Msg deleteEmpById(@PathVariable("ids")String ids) {
		if(ids.contains("-")) {
			List<Integer> list=new ArrayList<Integer>();
			String[] str_ids=ids.split("-");
			for(String id:str_ids) {
				list.add(Integer.parseInt(id));
			}
			employeeService.deleteBatch(list);
		}else {
			//单个的时候，转为Integer，再调用
			Integer id=Integer.parseInt(ids);
			employeeService.deleteEmp(id);
		}
		return Msg.success();
	}
	
	//@RequestMapping("/emps")
	/**
	 * 查询员工数据（分页查询）
	 * @return
	 * 该方法不合适主流的项目架构，一般用于浏览器->服务器
	 */
	public String getEmps(@RequestParam(value = "pn",defaultValue = "1")Integer pn,Model model) {
			PageHelper.startPage(pn, 5);
			List<Employee> emps=employeeService.getAll(); //查询员工的所有数据
			PageInfo pageInfo = new PageInfo(emps,5); //封装分页插件的类，连续显示5页
			model.addAttribute(pageInfo); //传入到model对象域里
			return "list";  //返回的“list”的页面（前端），作用是视图解析器会拼接
		
	}
	
}
