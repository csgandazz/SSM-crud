package com.gec.crud.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gec.crud.bean.Employee;
import com.gec.crud.bean.EmployeeExample;
import com.gec.crud.bean.EmployeeExample.Criteria;
import com.gec.crud.bean.Msg;
import com.gec.crud.dao.EmployeeMapper;
/*
 * ���ز�ѯ�Ľ����
 */
@Service
public class EmployeeService {
	
	@Autowired
	EmployeeMapper employeeMapper;
	
	public List<Employee> getAll(){	
		/*
		 * ��ѯԱ������
		 * ��ʾǰ�˱�
		 */
		return employeeMapper.selectByExampleWithDept(null);
	}

	public void saveEmp(Employee employee) {
		/*
		 * ����Ա������
		 * ʵ��Ϊ�������ݲ���������������
		 */
		employeeMapper.insertSelective(employee);  //Ա������Ϊ����id����id�����԰���������
	}

		
	/*
	 * �����û����Ƿ���ԣ����ڣ�
	 * countByExample(example):��������ͳ�Ʒ��������ļ�¼��
	 * 	@param empName
	 * 	@return ture:��ʾ��ǰ�û�����  false:������
	 */
	public boolean checkUser(String empName) {
		//Criteria��EmployeeExample��һ���ڲ���
		EmployeeExample example=new EmployeeExample();
		Criteria criteria = example.createCriteria();
		//criteria�������кܶ෽��������ҵ���Ӧ��ͬ�ķ���
		//EmpName�Ƿ���ڡ�����
		criteria.andEmpNameEqualTo(empName);
		
		long count = employeeMapper.countByExample(example);//���صļ�¼�����Ϊ0�����˺ſ������
		return count==0? true : false;   //count==0
	}

	//����id��ѯ��Ϣ
	public Employee getEmp(Integer id) {
		Employee employee = employeeMapper.selectByPrimaryKey(id);
		return employee;
	}

	//����������ѡ��ĸ���
	public void updateEmp(Employee employee) {
		 employeeMapper.updateByPrimaryKeySelective(employee);	
	}

	//ɾ��Ա������
	//��������ɾ��Ա��
	public void deleteEmp(Integer id) {
		employeeMapper.deleteByPrimaryKey(id);
		
	}
	
	//����ɾ������
	public void deleteBatch(List<Integer> list) {
		
		//�趨�Զ�������
		//���򹤳̵��Զ�����
		EmployeeExample example=new EmployeeExample();
		Criteria criteria = example.createCriteria();
		criteria.andEmpIdIn(list);
		//��������ɾ��
		//delete from xxx where in(1,2,3)
		employeeMapper.deleteByExample(example);
		
	}
}
