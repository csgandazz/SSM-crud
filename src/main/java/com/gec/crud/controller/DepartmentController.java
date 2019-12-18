package com.gec.crud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
/*
 * ������ز��ŵ�����
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
	 * �������еĲ�����Ϣ��json����
	 * 	����json���ݵķ��ض���װ��Msg�����
	 */
	
	@RequestMapping("/depts")
	@ResponseBody
	public Msg savedept() {//1.���ش���dept���ݵĶ���  2.��װ��Msg����
		
		List<Department> list = departmentService.getDepts();
		
		return Msg.success().add("depts",list);
	}
}
