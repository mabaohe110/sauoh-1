package cn.sau.sauoh.web.rest;

import cn.sau.sauoh.entity.MedicalRecord;
import cn.sau.sauoh.service.MedicalRecordService;
import cn.sau.sauoh.utils.R;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;


/**
 * 问诊记录 api
 */
@RestController
@RequestMapping("/api/medicalrecord")
public class MedicalRecordController {
    @Autowired
    private MedicalRecordService medicalRecordService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                  @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                  @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
                  @RequestParam(value = "sortOf", defaultValue = "ASC") String sortOf) {
        Page<MedicalRecord> page = new Page<>(pageNum, pageSize);
        if ("ASC".equals(sortOf)) {
            page.addOrder(OrderItem.asc(sortBy));
        } else {
            page.addOrder(OrderItem.desc(sortBy));
        }
        medicalRecordService.page(page);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id) {
        MedicalRecord medicalRecord = medicalRecordService.getById(id);

        return R.ok().put("medicalRecord", medicalRecord);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody MedicalRecord medicalRecord) {
        medicalRecordService.save(medicalRecord);

        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody MedicalRecord medicalRecord) {
        medicalRecordService.updateById(medicalRecord);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Integer[] ids) {
        medicalRecordService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
