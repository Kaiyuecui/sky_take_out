package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName CategoryMapper
 * @Description TODO
 * @Author lukcy
 * @Date 2024/7/6 11:06
 * @Version 1.0
 */
@Mapper
public interface CategoryMapper {
    void insert(Category category);
    Page<Category> page(CategoryPageQueryDTO categoryPageQueryDTO);

    void editstatus(Category category);

    /**
     * 根据id删除分类
     * @param id
     */
    @Delete("delete from category where id = #{id}")
    void deletebyId(Long id);
}
