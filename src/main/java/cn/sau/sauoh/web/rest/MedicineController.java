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


/**
 * @author nullptr
 * @email justitacsl@outlook.com
 * @date 2019-12-25 19:33:28
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
                throw new RRException("sortOf allow ASC or DESC", HttpServletResponse.SC_BAD_REQUEST);
            }
        }
        Page<Medicine> page = new Page<>(pageNum, pageSize);
        if (Constant.SORTOF_ASC.equalsIgnoreCase(sortOf)) {
            page.addOrder(OrderItem.asc(sortBy));
            medicineService.page(page);
            return R.ok().put("page", page);
        } else if (Constant.SORTOF_DESC.equalsIgnoreCase(sortOf)) {
            page.addOrder(OrderItem.desc(sortBy));
            medicineService.page(page);
            return R.ok().put("page", page);
        }
        throw new RRException("sortOf:alone allow ASC or DESC", HttpServletResponse.SC_BAD_REQUEST);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id) {
        Medicine medicine = medicineService.getById(id);
        return R.ok().put("medicine", medicine);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody Medicine medicine, HttpServletResponse response) {
        medicineService.save(medicine);
        return R.created(response).put("medicine", medicine);
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody Medicine medicine, HttpServletResponse response) {
        medicineService.updateById(medicine);
        return R.noContent(response);
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public R delete(@PathVariable Integer id, HttpServletResponse response) {
        medicineService.removeById(id);
        return R.noContent(response);
    }

}
