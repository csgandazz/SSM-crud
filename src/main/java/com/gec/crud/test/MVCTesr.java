package com.gec.crud.test;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.portlet.MockActionRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.gec.crud.bean.Employee;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @author my_computer
 *	springmvc�ĵ�Ԫ����ģ��
 *	ģ���������crud����ȷ��
 *	��spring�ĵ�Ԫ���Ի���һ��
 */

//λ��srcĿ¼�£�src/main/webapp/WEB-INF/dispatcherServlet-servlet.xml
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations= {"classpath:applicationContext.xml","file:src/main/webapp/WEB-INF/dispatcherServlet-servlet.xml"})//����Ҫspringmvc�������ļ�
public class MVCTesr {
	
	//����mvc���󣬻�ȡ����������Ҫ��ʼ����init����
	MockMvc mockMvc;
	
	//��ҪaplicationContext.xml
	@Autowired
	WebApplicationContext context;//�����IOC��������
	
	@Before
	public void initMockMvc(){
		mockMvc=MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	//���Է�ҳ�ķ���
	//perform����ģ�ⷢ������������Ҫһ��MockMvcRequestBuilders����url��
	//andreturn()��ȡ����ֵ
	
	@Test
	public void testPage() throws Exception {
		
		//��ȡģ������ķ���ֵ
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/emps").param("pn", "1")).andReturn();
		
		//У�������Ƿ�ɹ����������л����pageinfo
		MockHttpServletRequest request = result.getRequest(); //��ȡ�������
		PageInfo pageInfo = (PageInfo) request.getAttribute("pageInfo");
		System.out.println("��ǰҳ�룺"+pageInfo.getPageNum());
		System.out.println("��ҳ�룺"+pageInfo.getPages());
		System.out.println("�ܼ�¼����"+pageInfo.getTotal());
		System.out.println("��ǰҳ�룺"+pageInfo.getPageNum());
		System.out.println("��ҳ��������ʾҳ����"+pageInfo.getPageNum());
		int[] nums=pageInfo.getNavigatepageNums();
		for(int i : nums) {
			System.out.print(" "+i);
			
		}
		
		//��ȡԱ����Ϣ
		List<Employee> list = pageInfo.getList();
		for (Employee employee : list) {
			System.out.println("ID="+employee.getEmpId()+"  name="+employee.getEmpName());
		}



		
	}
}
