package cn.sau.sauoh.web.rest;

import cn.sau.sauoh.entity.MedicineOrder;
import cn.sau.sauoh.service.MedicineOrderService;
import cn.sau.sauoh.utils.Constant;
import cn.sau.sauoh.utils.R;
import cn.sau.sauoh.utils.RRException;
import cn.sau.sauoh.web.vm.MedicineOrderVM;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;


/**
 * 药品订单 API ：因为药品订单的表结构不便于阅读，这个controller中的相关接口都提供的是 MedicineOrderVM
 */
@RestController
@RequestMapping("/api/mo")
public class MedicineOrderController {
    @Autowired
    private MedicineOrderService medicineOrderService;

    /**
     * todo 根据 MedicineVM 提供一个分页查询接口
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
        Page<MedicineOrder> page = new Page<>(pageNum, pageSize);
        if (Constant.SORTOF_ASC.equalsIgnoreCase(sortOf)) {
            page.addOrder(OrderItem.asc(sortBy));
        } else if (Constant.SORTOF_DESC.equalsIgnoreCase(sortOf)) {
            page.addOrder(OrderItem.desc(sortBy));
        }
        medicineOrderService.page(page);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{mrId}")
    public R info(@PathVariable("mrId") Integer mrId) {
        MedicineOrderVM vm = medicineOrderService.getByMedicalRecordId(mrId);
        if (vm != null) {
            return R.ok().put("medicineOrder", vm);
        }
        throw RRException.serverError();
    }


    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@Valid @RequestBody MedicineOrderVM vm, HttpServletResponse response) {
        if (vm.getMedicalRecordId() == null) {
            throw RRException.badRequest(Constant.ERROR_MSG_ID_NEED);
        }
        if (medicineOrderService.saveVm(vm)) {
            return R.created(response).put("medicineOrder", vm);
        }
        throw RRException.serverError();
    }

    /**
     * 批量保存
     */
    @PostMapping("/batch/save")
    public R saveBatch(@Valid @RequestBody List<MedicineOrderVM> vmList, HttpServletResponse response) {
        vmList.forEach(vm -> {
            if (vm.getMedicalRecordId() == null) {
                throw RRException.badRequest(Constant.ERROR_MSG_ID_NEED);
            }
        });
        if (medicineOrderService.saveVmBatch(vmList)) {
            return R.noContent(response);
        }
        throw RRException.serverError();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@Valid @RequestBody MedicineOrderVM vm, HttpServletResponse response) {
        if (vm.getMedicalRecordId() == null) {
            throw RRException.badRequest(Constant.ERROR_MSG_ID_NEED);
        }
        if (medicineOrderService.updateByMrId(vm)) {
            return R.noContent(response);
        }
        throw RRException.serverError();
    }

    /**
     * 批量修改
     */
    @PutMapping("/batch/update")
    public R updateBatch(@Valid @RequestBody List<MedicineOrderVM> vmList, HttpServletResponse response) {
        vmList.forEach(vm -> {
            if (vm.getMedicalRecordId() == null) {
                throw RRException.badRequest(Constant.ERROR_MSG_ID_NEED);
            }
        });
        if (medicineOrderService.updateByMrIdVmBatch(vmList)) {
            return R.noContent(response);
        }
        throw RRException.serverError();
    }


    /**
     * 删除
     */
    @DeleteMapping("/delete/{mrId}")
    public R delete(@PathVariable Integer mrId, HttpServletResponse response) {
        if (medicineOrderService.removeByMrId(mrId)) {
            return R.noContent(response);
        }
        throw RRException.serverError();
    }

    /**
     * 批量删除
     */
    @PostMapping("/batch/delete")
    public R deleteBatch(@RequestBody Integer[] mrIds, HttpServletResponse response) {
        if (medicineOrderService.removeByMrIds(mrIds)) {
            return R.noContent(response);
        }
        throw RRException.serverError();
    }
}
