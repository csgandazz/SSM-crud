package com.gec.crud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
/*
 * 处理相关部门的请求
 */
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gec.crud.bean.Department;
import com.gec.crud.bean.Msg;
import com.gec.crud.service.DepartmentService;


@Controller
public class DepartmentController { 
	
	@Autowired
	private  DepartmentService departmentService;
	
	/*
	 * 返回所有的部门信息的json数据
	 * 	所有json数据的返回都包装成Msg这个类
	 */
	
	@RequestMapping("/depts")
	@ResponseBody
	public Msg savedept() {//1.返回带有dept数据的对象  2.封装在Msg上面
		
		List<Department> list = departmentService.getDepts();
		
		return Msg.success().add("depts",list);
	}
}
