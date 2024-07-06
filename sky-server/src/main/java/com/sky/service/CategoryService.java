package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.result.PageResult;
import org.springframework.stereotype.Service;

/**
 * @ClassName CategoryService
 * @Description TODO
 * @Author lukcy
 * @Date 2024/7/6 11:06
 * @Version 1.0
 */
@Service
public interface CategoryService {
    public void add(CategoryDTO categoryDTO);

    PageResult pageinfo(CategoryPageQueryDTO categoryPageQueryDTO);

    void editstatus(Integer status, Long id);

    void delete(Long id);

    void edit(CategoryDTO categoryDTO);
}
