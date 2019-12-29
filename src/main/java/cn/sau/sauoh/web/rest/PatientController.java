package cn.sau.sauoh.web.rest;

import cn.sau.sauoh.entity.Patient;
import cn.sau.sauoh.service.PatientService;
import cn.sau.sauoh.utils.Constant;
import cn.sau.sauoh.utils.R;
import cn.sau.sauoh.utils.RRException;
import cn.sau.sauoh.web.vm.PatientVM;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


/**
 * @author nullptr
 * @date 2019-12-25 19:33:28
 */
@RestController
@RequestMapping("/api/patient")
public class PatientController {
    @Autowired
    private PatientService patientService;

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
        Page<Patient> page = new Page<>(pageNum, pageSize);
        if (Constant.SORTOF_ASC.equalsIgnoreCase(sortOf)) {
            page.addOrder(OrderItem.asc(sortBy));
        } else if (Constant.SORTOF_DESC.equalsIgnoreCase(sortOf)) {
            page.addOrder(OrderItem.desc(sortBy));
        }
        patientService.page(page);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id) {
        Patient patient = patientService.getById(id);
        return R.ok().put("patient", patient);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@Valid @RequestBody Patient patient, HttpServletResponse response) {
        patientService.save(patient);
        return R.created(response).put("patient", patient);
    }

    @PostMapping("/savevm")
    public R savevm(@Valid @RequestBody PatientVM vm, HttpServletResponse response) {
        patientService.save(vm);
        return R.created(response).put("patientvm", vm);
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@Valid @RequestBody Patient patient, HttpServletResponse response) {
        patientService.updateById(patient);
        return R.noContent(response);
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public R delete(@PathVariable Integer id, HttpServletResponse response) {
        patientService.removeById(id);
        return R.noContent(response);
    }
}
