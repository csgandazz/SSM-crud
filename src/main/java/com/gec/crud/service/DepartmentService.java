package com.gec.crud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import com.gec.crud.bean.Department;
import com.gec.crud.dao.DepartmentMapper;

/**
 * @author my_computer
 *	������ز����й�����ҵ��
 */

@Service
public class DepartmentService {
	
		@Autowired
		private DepartmentMapper departmentMapper;
	
		public List<Department> getDepts(){ 
				//��������в�����Ϣ
				List<Department> list = departmentMapper.selectByExample(null);//��ѯ���ţ�����Ҫ����
				return list;
		}
}
