package cn.sau.sauoh.web.rest;

import cn.sau.sauoh.entity.Medicine;
import cn.sau.sauoh.service.MedicineService;
import cn.sau.sauoh.utils.Constant;
import cn.sau.sauoh.utils.R;
import cn.sau.sauoh.utils.RRException;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;


/**
 * 药品 API
 */
@RestController
@RequestMapping("/api/medicine")
public class MedicineController {
    @Autowired
    private MedicineService medicineService;

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
        Page<Medicine> page = new Page<>(pageNum, pageSize);
        if (Constant.SORTOF_ASC.equalsIgnoreCase(sortOf)) {
            page.addOrder(OrderItem.asc(sortBy));
        } else if (Constant.SORTOF_DESC.equalsIgnoreCase(sortOf)) {
            page.addOrder(OrderItem.desc(sortBy));
        }
        medicineService.page(page);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id) {
        Medicine medicine = medicineService.getById(id);
        if (medicine != null) {
            return R.ok().put("medicine", medicine);
        }
        throw RRException.notFound("id不存在");
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@Valid @RequestBody Medicine medicine, HttpServletResponse response) {
        if (medicine.getId() != null) {
            throw RRException.badRequest("插入时不能指明Id");
        }
        if (medicineService.save(medicine)) {
            return R.created(response).put("medicine", medicine);
        }
        throw RRException.serverError();
    }

    /**
     * 批量保存
     */
    @PostMapping("/batch/save")
    public R saveBatch(@Valid @RequestBody List<Medicine> medicineList, HttpServletResponse response) {
        medicineList.forEach(medicine -> {
            if (medicine.getId() != null) {
                throw RRException.badRequest("插入时不能指明Id");
            }
        });
        if (medicineService.saveBatch(medicineList)) {
            return R.noContent(response);
        }
        throw RRException.serverError();
    }


    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@Valid @RequestBody Medicine medicine, HttpServletResponse response) {
        if (medicine.getId() == null) {
            throw RRException.badRequest("修改时必须指明Id");
        }
        if (medicineService.updateById(medicine)) {
            return R.noContent(response);
        }
        throw RRException.serverError();
    }

    /**
     * 批量修改
     */
    @PutMapping("/batch/update")
    public R update(@Valid @RequestBody List<Medicine> medicineList, HttpServletResponse response) {
        medicineList.forEach(medicine -> {
            if (medicine.getId() == null) {
                throw RRException.badRequest("修改时必须指明Id");
            }
        });
        if (medicineService.updateBatchById(medicineList)) {
            return R.noContent(response);
        }
        throw RRException.serverError();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public R delete(@PathVariable Integer id, HttpServletResponse response) {
        if (medicineService.removeById(id)) {
            return R.noContent(response);
        }
        throw RRException.serverError();
    }

    /**
     * 批量删除
     */
    @PostMapping("/batch/delete")
    public R delete(@RequestBody Integer[] ids, HttpServletResponse response) {
        if (medicineService.removeByIds(Arrays.asList(ids))) {
            return R.noContent(response);
        }
        throw RRException.serverError();
    }
}
