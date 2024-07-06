package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Api(tags = "员工相关接口")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    @ApiOperation("用户登录")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);
        //给前端展示的VO模型
        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @ApiOperation("用户退出")
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success();
    }

    /**
     * 新增员工
     * @param employeeDTO
     * @return
     */
    @PostMapping
    @ApiOperation("新增员工")
    public Result save(@RequestBody  EmployeeDTO employeeDTO){
        employeeService.save(employeeDTO);
        return Result.success();
    }

    /**employeePageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("员工分页")
    public Result<PageResult> page(EmployeePageQueryDTO employeePageQueryDTO){
        PageResult pageResult=employeeService.pageshow(employeePageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     *禁用
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation("禁用启用员工")
    public Result editstatus( @PathVariable Integer status,Long id){
        employeeService.changestatus(status,id);
    return Result.success();}

    /**
     * 回显
     * @param id
     * @return
     */
  @GetMapping("{id}")
  @ApiOperation("根据id查雇员")
    public Result<Employee> select(@PathVariable("id") Long id){
        Employee employee=employeeService.selectemployee(id);
        return Result.success(employee);
  }
    /**
     * 编辑后保存雇员信息
     * @param employee
     * @return
     */
    @PutMapping()
    @ApiOperation("编辑后保存雇员信息")
    public Result save(@RequestBody Employee employee){
        employeeService.editemployee(employee);
        return Result.success();
    }

}
