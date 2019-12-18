package com.gec.crud.test;

import java.util.UUID;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gec.crud.bean.Department;
import com.gec.crud.bean.Employee;
import com.gec.crud.dao.DepartmentMapper;
import com.gec.crud.dao.EmployeeMapper;



//ʹ�ô�ע�����ԭ������һ��
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:applicationContext.xml"})
public class EmployeeMapperTest{
	
	@Autowired
	DepartmentMapper departmentMapper;
	
	@Autowired
	EmployeeMapper employeeMapper;
	
	@Autowired
	SqlSession sqlSession;
	
		@Test
		public void testcrud(){
			/*//����springIoc����
			ApplicationContext ioc=new ClassPathXmlApplicationContext("applicationContext.xml"); 
			//�������л�ȡmapper
			DepartmentMapper mapper = ioc.getBean(DepartmentMapper.class);
			*/
			
			//departmentMapper.insert(new Department(null, "���Բ�"));
			//departmentMapper.insertSelective(new Department(001, "������"));
			//������Ա��
			EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
			for(int i=0;i<200;i++) {
				//uuid�����࣬��������idֵ
				String uid=UUID.randomUUID().toString().substring(0,5)+i;
				mapper.insertSelective(new Employee(null, uid, "M", uid+"@qq.com", 1));
			}
			//employeeMapper.insertSelective(null);//��ѡ���ȥ����
		}
}
