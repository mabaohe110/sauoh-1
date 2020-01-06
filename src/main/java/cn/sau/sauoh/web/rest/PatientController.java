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
import java.util.Arrays;
import java.util.List;


/**
 * 患者
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
                throw RRException.badRequest("sortOf allow ASC or DESC");
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
        if (patient != null) {
            return R.ok().put("patient", patient);
        }
        throw RRException.notFound(Constant.ERROR_MSG_ID_NOT_EXIST);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@Valid @RequestBody Patient patient, HttpServletResponse response) {
        if (patient.getId() != null) {
            throw RRException.badRequest(Constant.ERROR_MSG_ID_NOT_NEED);
        }
        if (patientService.save(patient)) {
            return R.created(response).put("patient", patient);
        }
        throw RRException.serverError();
    }

    /**
     * 批量保存
     */
    @PostMapping("/batch/save")
    public R saveBatch(@Valid @RequestBody List<Patient> patientList, HttpServletResponse response) {
        patientList.forEach(patient -> {
            if (patient.getId() != null) {
                throw RRException.badRequest(Constant.ERROR_MSG_ID_NOT_NEED);
            }
        });
        if (patientService.saveBatch(patientList)) {
            return R.noContent(response);
        }
        throw RRException.serverError();
    }

    /**
     * 直接保存
     */
    @PostMapping("/savevm")
    public R saveVm(@Valid @RequestBody PatientVM vm, HttpServletResponse response) {
        if (vm.getUserId() != null || vm.getPatientId() != null) {
            throw RRException.badRequest(Constant.ERROR_MSG_ID_NOT_NEED);
        }
        if (patientService.saveVm(vm)) {
            return R.created(response).put("patientvm", vm);
        }
        throw RRException.serverError();
    }

    /**
     * 批量直接保存
     */
    @PostMapping("/batch/savevm")
    public R saveVmBatch(@Valid @RequestBody List<PatientVM> vmList, HttpServletResponse response) {
        vmList.forEach(vm -> {
            if (vm.getUserId() != null || vm.getPatientId() != null) {
                throw RRException.badRequest(Constant.ERROR_MSG_ID_NOT_NEED);
            }
        });
        if (patientService.saveVmBatch(vmList)) {
            return R.created(response).put("patientvm", vmList);
        }
        throw RRException.serverError();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@Valid @RequestBody Patient patient, HttpServletResponse response) {
        if (patient.getId() == null) {
            throw RRException.badRequest(Constant.ERROR_MSG_ID_NEED);
        }
        if (patientService.updateById(patient)) {
            return R.noContent(response);
        }
        throw RRException.serverError();
    }

    /**
     * 批量修改
     */
    @PutMapping("/batch/update")
    public R updateBatch(@Valid @RequestBody List<Patient> patientList, HttpServletResponse response) {
        patientList.forEach(patient -> {
            if (patient.getId() == null) {
                throw RRException.badRequest(Constant.ERROR_MSG_ID_NEED);
            }
        });
        if (patientService.updateBatchById(patientList)) {
            return R.noContent(response);
        }
        throw RRException.serverError();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public R delete(@PathVariable Integer id, HttpServletResponse response) {
        if (patientService.removeById(id)) {
            return R.noContent(response);
        }
        throw RRException.serverError();
    }

    /**
     * 批量删除
     */
    @PostMapping("/batch/delete")
    public R deleteBatch(@RequestBody Integer[] ids, HttpServletResponse response) {
        if (patientService.removeByIds(Arrays.asList(ids))) {
            return R.noContent(response);
        }
        throw RRException.serverError();
    }
}
