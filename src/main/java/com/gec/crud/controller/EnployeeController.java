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
 * ����Ա��crud����
 * @author my_computer
 *	 
 */
@Controller
public class EnployeeController {
	

	@Autowired
	EmployeeService employeeService;
	private List<FieldError> fieldErrors; //������Ϣ
	
	
	/**
	 * Ա������
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
	 * ����û��Ƿ����
	 * �������ݼ��Ƿ���ڸ��û�
	 * 
	 */
	@ResponseBody
	@RequestMapping("/checkUser")
	public Msg checkUser(@RequestParam("empName")String empName){		
			String regx="(^[a-zA-Z0-9_-]{6,16}$)|(^[\u2E80-\u9FFF]{2,5})";
			if(!empName.matches(regx)) {
				//System.out.println("1");
				return Msg.fail().add("va_msg", "�û���������6-16λӢ�Ļ�2-5λ�������!!");
			}
			boolean b=employeeService.checkUser(empName);
			if(b) {
				return Msg.success();	
			}else {
				return Msg.fail().add("va_msg", "�û���������");
			}
			
	}
	
	
	/**
	 * Ա������(����)
	 * 
	 * 	1.֧��JSR303У��
	 * 
	 * @param  @RequestParam("empName")String empName �����н��ܵ�����
	 * @param  BindingResult result  ��װУ��������鿴У����״̬
	 * 
	 */

	@RequestMapping(value="/emps",method = RequestMethod.POST)
	@ResponseBody
	public Msg saveEmp(@Valid Employee employee ,BindingResult result){ 
			
			Map<String , Object> map=new HashMap<String, Object>();
			//���У��
			if(result.hasErrors()) {
				//У��ʧ�ܣ���ģ̬�򷵻�У��ʧ��
				 List<FieldError> list = result.getFieldErrors();
				 for(FieldError errors:list) {
//					 System.out.println("������ֶ���"+errors.getField());
//					 System.out.println("������Ϣ"+errors.getDefaultMessage());
					 //��Ԫ���ݷŽ�HashMap��
					 map.put(errors.getField(), errors.getDefaultMessage());			
				 }
				return Msg.fail().add("errorFields", map);
			}else {
				//���ݻ��û����ظ�У��
				//ҳ���ύ��key value��employee����һ�£����Զ���װ	
				//���ó־ò㣬���ݿ�ִ�в���
				employeeService.saveEmp(employee); 
			}
			return Msg.success();	
	}
	
	
	
	@ResponseBody
	@RequestMapping("/emps")
	/**
	 * �÷�������json����String��
	 */
	public Msg getEmpsWithJson(@RequestParam(value = "pn",defaultValue = "1")Integer pn) {
			//String orderBy="emp_id asc";
			PageHelper.startPage(pn, 5);
			//PageHelper.orderBy(orderBy);
			PageHelper.orderBy("emp_id asc");
			List<Employee> emps=employeeService.getAll(); //��ѯԱ������������
			PageInfo page = new PageInfo(emps,5); //��װ��ҳ������࣬������ʾ5ҳ
			return new Msg().success().add("pageInfo", page);
	}
	
	/*
	 *  ��ȡԱ����Ϣ�����޸Ŀ�
	 */
	@RequestMapping(value="/emp/{id}",method = RequestMethod.GET)
	@ResponseBody
	public Msg getEmps(@PathVariable("id")Integer id) {
		Employee employee=employeeService.getEmp(id);
		return Msg.success().add("emp", employee);
	}
	
	/**
	 * ɾ��Ա��
	 * �����������һ
	 * requestParam:
	 * �����3-1-1
	 * ������1
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
			//������ʱ��תΪInteger���ٵ���
			Integer id=Integer.parseInt(ids);
			employeeService.deleteEmp(id);
		}
		return Msg.success();
	}
	
	//@RequestMapping("/emps")
	/**
	 * ��ѯԱ�����ݣ���ҳ��ѯ��
	 * @return
	 * �÷�����������������Ŀ�ܹ���һ�����������->������
	 */
	public String getEmps(@RequestParam(value = "pn",defaultValue = "1")Integer pn,Model model) {
			PageHelper.startPage(pn, 5);
			List<Employee> emps=employeeService.getAll(); //��ѯԱ������������
			PageInfo pageInfo = new PageInfo(emps,5); //��װ��ҳ������࣬������ʾ5ҳ
			model.addAttribute(pageInfo); //���뵽model��������
			return "list";  //���صġ�list����ҳ�棨ǰ�ˣ�����������ͼ��������ƴ��
		
	}
	
}
