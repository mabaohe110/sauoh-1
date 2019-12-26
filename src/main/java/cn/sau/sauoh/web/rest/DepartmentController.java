package cn.sau.sauoh.web.rest;

import cn.sau.sauoh.entity.Department;
import cn.sau.sauoh.service.DepartmentService;
import cn.sau.sauoh.utils.R;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;


/**
 * @author nullptr
 * @email justitacsl@outlook.com
 * @date 2019-12-25 19:33:28
 */
@RestController
@RequestMapping("/api/department")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                  @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                  @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
                  @RequestParam(value = "sortOf", defaultValue = "ASC") String sortOf) {
        Page<Department> page = new Page<>(pageNum, pageSize);
        if ("ASC".equals(sortOf)) {
            page.addOrder(OrderItem.asc(sortBy));
        } else {
            page.addOrder(OrderItem.desc(sortBy));
        }
        departmentService.page(page);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id) {
        Department department = departmentService.getById(id);

        return R.ok().put("department", department);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody Department department) {
        departmentService.save(department);

        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody Department department) {
        departmentService.updateById(department);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Integer[] ids) {
        departmentService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
