package com.sky.controller.admin;

/**
 * @ClassName CategoryController
 * @Description TODO
 * @Author lukcy
 * @Date 2024/7/6 11:00
 * @Version 1.0
 */

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/category")
@Slf4j
@Api(tags = "分类相关接口")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    /**
     * 新增分类
     * @param categoryDTO
     * @return
     */
    @PostMapping()
    public Result add(@RequestBody CategoryDTO categoryDTO){
        categoryService.add(categoryDTO);
        return Result.success();
    }

    /**
     * 菜品分页管理
     * @param categoryPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    public Result<PageResult> page(CategoryPageQueryDTO categoryPageQueryDTO){
       PageResult pageResult= categoryService.pageinfo(categoryPageQueryDTO);
       return Result.success(pageResult);
    }
    /**
     * 禁用启用
     */
    @PostMapping("/status/{status}")
    public Result stoporstart(@PathVariable Integer status,Long id){
        categoryService.editstatus(status,id);
        return Result.success();
    }

    /**
     * 根据分类id删除分类
     * @param id
     * @return
     */
    @DeleteMapping
    private Result delete(Long id){
categoryService.delete(id);
return Result.success();
    }

    @PutMapping
    public Result edit(@RequestBody CategoryDTO categoryDTO){
        categoryService.edit(categoryDTO);
        return Result.success();
    }

}
