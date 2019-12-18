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



//使用此注解就像原生开发一样
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
			/*//创建springIoc容器
			ApplicationContext ioc=new ClassPathXmlApplicationContext("applicationContext.xml"); 
			//从容器中获取mapper
			DepartmentMapper mapper = ioc.getBean(DepartmentMapper.class);
			*/
			
			//departmentMapper.insert(new Department(null, "测试部"));
			//departmentMapper.insertSelective(new Department(001, "开发部"));
			//插入多个员工
			EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
			for(int i=0;i<200;i++) {
				//uuid工具类，随意生成id值
				String uid=UUID.randomUUID().toString().substring(0,5)+i;
				mapper.insertSelective(new Employee(null, uid, "M", uid+"@qq.com", 1));
			}
			//employeeMapper.insertSelective(null);//有选择的去插入
		}
}
