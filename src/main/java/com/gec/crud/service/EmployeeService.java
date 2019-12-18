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
 * 返回查询的结果集
 */
@Service
public class EmployeeService {
	
	@Autowired
	EmployeeMapper employeeMapper;
	
	public List<Employee> getAll(){	
		/*
		 * 查询员工数据
		 * 显示前端表单
		 */
		return employeeMapper.selectByExampleWithDept(null);
	}

	public void saveEmp(Employee employee) {
		/*
		 * 保存员工功能
		 * 实现为插入数据操作，按条件插入
		 */
		employeeMapper.insertSelective(employee);  //员工设置为自增id，无id，所以按条件插入
	}

		
	/*
	 * 检验用户名是否可以（存在）
	 * countByExample(example):按照条件统计符合条件的记录数
	 * 	@param empName
	 * 	@return ture:表示当前用户可用  false:不可用
	 */
	public boolean checkUser(String empName) {
		//Criteria是EmployeeExample的一个内部类
		EmployeeExample example=new EmployeeExample();
		Criteria criteria = example.createCriteria();
		//criteria对象里有很多方法，根据业务对应不同的方法
		//EmpName是否等于。。。
		criteria.andEmpNameEqualTo(empName);
		
		long count = employeeMapper.countByExample(example);//返回的记录数如果为0，则账号可以添加
		return count==0? true : false;   //count==0
	}

	//根据id查询信息
	public Employee getEmp(Integer id) {
		Employee employee = employeeMapper.selectByPrimaryKey(id);
		return employee;
	}

	//根据主键有选择的更新
	public void updateEmp(Employee employee) {
		 employeeMapper.updateByPrimaryKeySelective(employee);	
	}

	//删除员工数据
	//根据主键删除员工
	public void deleteEmp(Integer id) {
		employeeMapper.deleteByPrimaryKey(id);
		
	}
	
	//批量删除数据
	public void deleteBatch(List<Integer> list) {
		
		//设定自定义条件
		//逆向工程的自定义类
		EmployeeExample example=new EmployeeExample();
		Criteria criteria = example.createCriteria();
		criteria.andEmpIdIn(list);
		//按照条件删除
		//delete from xxx where in(1,2,3)
		employeeMapper.deleteByExample(example);
		
	}
}
