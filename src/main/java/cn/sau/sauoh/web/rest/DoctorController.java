package cn.sau.sauoh.web.rest;

import cn.sau.sauoh.entity.Doctor;
import cn.sau.sauoh.service.DoctorService;
import cn.sau.sauoh.utils.Constant;
import cn.sau.sauoh.utils.R;
import cn.sau.sauoh.utils.RRException;
import cn.sau.sauoh.web.vm.DoctorVM;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


/**
 * @author nullptr
 * @email justitacsl@outlook.com
 * @date 2019-12-25 19:33:28
 */
@RestController
@RequestMapping("/api/doctor")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;

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
        Page<Doctor> page = new Page<>(pageNum, pageSize);
        if (Constant.SORTOF_ASC.equalsIgnoreCase(sortOf)) {
            page.addOrder(OrderItem.asc(sortBy));
            doctorService.page(page);
            return R.ok().put("page", page);
        } else if (Constant.SORTOF_DESC.equalsIgnoreCase(sortOf)) {
            page.addOrder(OrderItem.desc(sortBy));
            doctorService.page(page);
            return R.ok().put("page", page);
        }
        throw new RRException("sortOf:alone allow ASC or DESC", HttpServletResponse.SC_BAD_REQUEST);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id) {
        Doctor doctor = doctorService.getById(id);
        return R.ok().put("doctor", doctor);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@Valid @RequestBody Doctor doctor, HttpServletResponse response) {
        doctorService.save(doctor);
        return R.created(response).put("doctor", doctor);
    }

    @PostMapping("/savevm")
    public R save(@Valid @RequestBody DoctorVM vm, HttpServletResponse response) {
        doctorService.save(vm);
        return R.created(response).put("doctorvm", vm);
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@Valid @RequestBody Doctor doctor, HttpServletResponse response) {
        doctorService.updateById(doctor);
        return R.noContent(response);
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public R delete(@PathVariable Integer id, HttpServletResponse response) {
        doctorService.removeById(id);
        return R.noContent(response);
    }

}
