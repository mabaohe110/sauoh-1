package cn.sau.sauoh.web.rest;

import cn.sau.sauoh.entity.Doctor;
import cn.sau.sauoh.service.DoctorService;
import cn.sau.sauoh.utils.Constant;
import cn.sau.sauoh.utils.R;
import cn.sau.sauoh.utils.RRException;
import cn.sau.sauoh.web.vm.DoctorRecordVM;
import cn.sau.sauoh.web.vm.DoctorVM;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;


/**
 * Doctor RESTful api
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
                throw RRException.badRequest("sortOf allow ASC or DESC");
            }
        }
        Page<Doctor> page = new Page<>(pageNum, pageSize);
        if (Constant.SORTOF_ASC.equalsIgnoreCase(sortOf)) {
            page.addOrder(OrderItem.asc(sortBy));
        } else if (Constant.SORTOF_DESC.equalsIgnoreCase(sortOf)) {
            page.addOrder(OrderItem.desc(sortBy));
        }
        doctorService.page(page);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id) {
        Doctor doctor = doctorService.getById(id);
        if (doctor == null) {
            throw RRException.notFound(Constant.ERROR_MSG_ID_NOT_EXIST);
        }
        return R.ok().put("doctor", doctor);
    }

    /**
     * 一个提供给患者的，查看医生信息的controller，包括问诊次数和好评率
     */
    @GetMapping("/find/{id}")
    public R find(@PathVariable("id") Integer id) {
        DoctorRecordVM vm = doctorService.getVmById(id);
        if (vm == null) {
            throw RRException.notFound(Constant.ERROR_MSG_ID_NOT_EXIST);
        }
        return R.ok().put("doctorInfo", vm);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@Valid @RequestBody Doctor doctor, HttpServletResponse response) {
        if (doctor.getId() != null) {
            throw RRException.badRequest(Constant.ERROR_MSG_ID_NOT_NEED);
        }
        if (doctorService.save(doctor)) {
            return R.created(response).put("doctor", doctor);
        }
        throw RRException.serverError();
    }

    /**
     * 批量保存
     */
    @PostMapping("/batch/save")
    public R saveBatch(@Valid @RequestBody List<Doctor> doctorList, HttpServletResponse response) {
        doctorList.forEach(doctor -> {
            if (doctor.getId() != null) {
                throw RRException.badRequest(Constant.ERROR_MSG_ID_NOT_NEED);
            }
        });
        if (doctorService.saveBatch(doctorList)) {
            return R.noContent(response);
        }
        throw RRException.serverError();
    }

    /**
     * 直接保存
     */
    @PostMapping("/savevm")
    public R saveVm(@Valid @RequestBody DoctorVM vm, HttpServletResponse response) {
        if (vm.getUserId() != null || vm.getDoctorId() != null) {
            throw RRException.badRequest(Constant.ERROR_MSG_ID_NOT_NEED);
        }
        if (doctorService.saveVm(vm)) {
            return R.noContent(response);
        }
        throw RRException.serverError();
    }

    /**
     * 直接批量保存
     */
    @PostMapping("/batch/savevm")
    public R saveVmBatch(@Valid @RequestBody List<DoctorVM> vmList, HttpServletResponse response) {
        vmList.forEach(vm -> {
            if (vm.getUserId() != null || vm.getDoctorId() != null) {
                throw RRException.badRequest(Constant.ERROR_MSG_ID_NOT_NEED);
            }
        });
        if (doctorService.saveVmBatch(vmList)) {
            return R.noContent(response);
        }
        throw RRException.serverError();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@Valid @RequestBody Doctor doctor, HttpServletResponse response) {
        if (doctor.getId() == null) {
            throw RRException.badRequest(Constant.ERROR_MSG_ID_NEED);
        }
        if (doctorService.updateById(doctor)) {
            return R.noContent(response);
        }
        throw RRException.serverError();
    }

    /**
     * 批量修改
     */
    @PutMapping("/batch/update")
    public R updateBatch(@Valid @RequestBody List<Doctor> doctorList, HttpServletResponse response) {
        doctorList.forEach(doctor -> {
            if (doctor.getId() == null) {
                throw RRException.badRequest(Constant.ERROR_MSG_ID_NEED);
            }
        });
        if (doctorService.updateBatchById(doctorList)) {
            return R.noContent(response);
        }
        throw RRException.serverError();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public R delete(@PathVariable Integer id, HttpServletResponse response) {
        if (doctorService.removeById(id)) {
            return R.noContent(response);
        }
        throw RRException.serverError();
    }

    /**
     * 批量删除
     */
    @PostMapping("/batch/delete")
    public R deleteBatch(@RequestBody Integer[] ids, HttpServletResponse response) {
        if (doctorService.removeByIds(Arrays.asList(ids))) {
            return R.noContent(response);
        }
        throw RRException.serverError();
    }
}
