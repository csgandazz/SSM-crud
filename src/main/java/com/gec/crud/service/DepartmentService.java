package com.gec.crud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import com.gec.crud.bean.Department;
import com.gec.crud.dao.DepartmentMapper;

/**
 * @author my_computer
 *	处理相关部门有关联的业务
 */

@Service
public class DepartmentService {
	
		@Autowired
		private DepartmentMapper departmentMapper;
	
		public List<Department> getDepts(){ 
				//查出的所有部门信息
				List<Department> list = departmentMapper.selectByExample(null);//查询部门，不需要条件
				return list;
		}
}
