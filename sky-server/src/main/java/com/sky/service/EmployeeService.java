package com.sky.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

public interface EmployeeService extends IService<Employee> {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    void save(EmployeeDTO employeeDTO);


    PageResult pageshow(EmployeePageQueryDTO employeePageQueryDTO);

    void changestatus(Integer status,Long id);

    /**
     * 根据id查雇员
     * @param id
     * @return
     */
    Employee selectemployee(Long id);

    /**
     * 修改员工信息
     * @param employee
     */
    void editemployee(Employee employee);
}
