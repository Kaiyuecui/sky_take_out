package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @ClassName CategoryServiceImpl
 * @Description TODO
 * @Author lukcy
 * @Date 2024/7/6 11:06
 * @Version 1.0
 */

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;
    /**
     * 新增分类
     * @param categoryDTO
     */
    @Override
    public void add(CategoryDTO categoryDTO) {

        Category category = Category.builder().name(categoryDTO.getName())
                .sort(categoryDTO.getSort()).
                type(categoryDTO.getType()).
                status(StatusConstant.DISABLE).createTime(LocalDateTime.now()).
                updateTime(LocalDateTime.now()).
                updateUser(BaseContext.getCurrentId()).createUser(BaseContext.getCurrentId()).build();
        categoryMapper.insert(category);

    }

    /**
     * 菜品分类管理
     * @param categoryPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageinfo(CategoryPageQueryDTO categoryPageQueryDTO) {
        PageHelper.startPage(categoryPageQueryDTO.getPage(),categoryPageQueryDTO.getPageSize());
        Page<Category> page=categoryMapper.page(categoryPageQueryDTO);
        long total = page.getTotal();
        List<Category> result = page.getResult();
        return new PageResult(total,result);
    }

    /**
     * 菜品禁用启用
     * @param status
     * @param id
     */
    @Override
    public void editstatus(Integer status, Long id) {
        Category category = Category.builder().status(status).id(id).updateTime(LocalDateTime.now())
                .updateUser(BaseContext.getCurrentId()).build();
        categoryMapper.editstatus(category);

    }

    /**
     * 根据分类id删除分类
     * @param id
     */
    @Override
    public void delete(Long id) {

        //首先查看当前分类下是否有菜品  如果有不能删除
        Integer count = dishMapper.countByCategoryId(id);
        if(count>0){
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_DISH);
        }
        //查看当前分类下是否有套餐 如果有 不能删除
        Integer count1 = setmealMapper.countByCategoryId(id);
        if(count1>0){
            throw new DeletionNotAllowedException((MessageConstant.CATEGORY_BE_RELATED_BY_SETMEAL));
        }
        //删除分类
        categoryMapper.deletebyId(id);
    }

    /**
     * 修改分类信息
     * @param categoryDTO
     */
    @Override
    public void edit(CategoryDTO categoryDTO) {
        Category build = Category.builder().id(categoryDTO.getId()).type(categoryDTO.getType()).sort(categoryDTO.getSort()).name(categoryDTO.getName()).
                updateTime(LocalDateTime.now())
                .updateUser(BaseContext.getCurrentId()).
                build();
        categoryMapper.editstatus(build);
    }
}
