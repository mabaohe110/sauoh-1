package cn.sau.sauoh.web.rest;

import cn.sau.sauoh.entity.Department;
import cn.sau.sauoh.service.DepartmentService;
import cn.sau.sauoh.utils.Constant;
import cn.sau.sauoh.utils.R;
import cn.sau.sauoh.utils.RRException;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


/**
 * 科室API
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
        if ((!Constant.SORTOF_ASC.equalsIgnoreCase(sortOf))) {
            if ((!Constant.SORTOF_DESC.equalsIgnoreCase(sortOf))) {
                throw new RRException("sortOf allow ASC or DESC", HttpServletResponse.SC_BAD_REQUEST);
            }
        }
        Page<Department> page = new Page<>(pageNum, pageSize);
        if (Constant.SORTOF_ASC.equalsIgnoreCase(sortOf)) {
            page.addOrder(OrderItem.asc(sortBy));
            departmentService.page(page);
            return R.ok().put("page", page);
        } else if (Constant.SORTOF_DESC.equalsIgnoreCase(sortOf)) {
            page.addOrder(OrderItem.desc(sortBy));
            departmentService.page(page);
            return R.ok().put("page", page);
        }
        throw new RRException("sortOf:alone allow ASC or DESC", HttpServletResponse.SC_BAD_REQUEST);
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
    public R save(@Valid @RequestBody Department department, HttpServletResponse response) {
        departmentService.save(department);
        return R.created(response).put("department", department);
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@Valid @RequestBody Department department, HttpServletResponse response) {
        departmentService.updateById(department);
        return R.noContent(response);
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public R delete(@PathVariable Integer id, HttpServletResponse response) {
        departmentService.removeById(id);
        return R.noContent(response);
    }

}
