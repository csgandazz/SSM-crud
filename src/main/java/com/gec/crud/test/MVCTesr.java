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
 *	springmvc的单元测试模块
 *	模拟请求测试crud的正确性
 *	与spring的单元测试基本一样
 */

//位于src目录下，src/main/webapp/WEB-INF/dispatcherServlet-servlet.xml
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations= {"classpath:applicationContext.xml","file:src/main/webapp/WEB-INF/dispatcherServlet-servlet.xml"})//还需要springmvc的配置文件
public class MVCTesr {
	
	//虚拟mvc请求，获取处理结果（需要初始化（init））
	MockMvc mockMvc;
	
	//需要aplicationContext.xml
	@Autowired
	WebApplicationContext context;//这个是IOC容器本身
	
	@Before
	public void initMockMvc(){
		mockMvc=MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	//测试分页的方法
	//perform方法模拟发送请求，里面需要一个MockMvcRequestBuilders（带url）
	//andreturn()获取返回值
	
	@Test
	public void testPage() throws Exception {
		
		//获取模拟请求的返回值
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/emps").param("pn", "1")).andReturn();
		
		//校验请求是否成功，请求域中会存在pageinfo
		MockHttpServletRequest request = result.getRequest(); //获取请求对象
		PageInfo pageInfo = (PageInfo) request.getAttribute("pageInfo");
		System.out.println("当前页码："+pageInfo.getPageNum());
		System.out.println("总页码："+pageInfo.getPages());
		System.out.println("总记录数："+pageInfo.getTotal());
		System.out.println("当前页码："+pageInfo.getPageNum());
		System.out.println("在页面连续显示页数："+pageInfo.getPageNum());
		int[] nums=pageInfo.getNavigatepageNums();
		for(int i : nums) {
			System.out.print(" "+i);
			
		}
		
		//获取员工信息
		List<Employee> list = pageInfo.getList();
		for (Employee employee : list) {
			System.out.println("ID="+employee.getEmpId()+"  name="+employee.getEmpName());
		}



		
	}
}
