package cn.sau.sauoh.web.rest;

import cn.sau.sauoh.entity.MedicineOrder;
import cn.sau.sauoh.entity.Patient;
import cn.sau.sauoh.service.MedicineOrderService;
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
@RequestMapping("/api/medicineorder")
public class MedicineOrderController {
    @Autowired
    private MedicineOrderService medicineOrderService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                  @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                  @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
                  @RequestParam(value = "sortOf", defaultValue = "ASC") String sortOf) {
        Page<MedicineOrder> page = new Page<>(pageNum, pageSize);
        if ("ASC".equals(sortOf)) {
            page.addOrder(OrderItem.asc(sortBy));
        } else {
            page.addOrder(OrderItem.desc(sortBy));
        }
        medicineOrderService.page(page);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id) {
        MedicineOrder medicineOrder = medicineOrderService.getById(id);

        return R.ok().put("patient", medicineOrder);
    }


    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody MedicineOrder medicineOrder) {
        medicineOrderService.save(medicineOrder);

        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody MedicineOrder medicineOrder) {
        medicineOrderService.updateById(medicineOrder);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Integer[] ids) {
        medicineOrderService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
