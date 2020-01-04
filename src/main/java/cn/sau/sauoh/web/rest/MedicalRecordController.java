package cn.sau.sauoh.web.rest;

import cn.sau.sauoh.entity.MedicalRecord;
import cn.sau.sauoh.service.MedicalRecordService;
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
 * 问诊记录 api
 */
@RestController
@RequestMapping("/api/mr")
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
        if ((!Constant.SORTOF_ASC.equalsIgnoreCase(sortOf))) {
            if ((!Constant.SORTOF_DESC.equalsIgnoreCase(sortOf))) {
                throw RRException.badRequest("sortOf allow ASC or DESC");
            }
        }
        Page<MedicalRecord> page = new Page<>(pageNum, pageSize);
        if (Constant.SORTOF_ASC.equalsIgnoreCase(sortOf)) {
            page.addOrder(OrderItem.asc(sortBy));
        } else if (Constant.SORTOF_DESC.equalsIgnoreCase(sortOf)) {
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
        if (medicalRecord != null) {
            return R.ok().put("medicalRecord", medicalRecord);
        }
        throw RRException.notFound(Constant.ERROR_MSG_ID_NOT_EXIST);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@Valid @RequestBody MedicalRecord medicalRecord, HttpServletResponse response) {
        if (medicalRecord.getId() != null) {
            throw RRException.badRequest(Constant.ERROR_MSG_ID_NOT_NEED);
        }
        if (medicalRecordService.save(medicalRecord)) {
            return R.created(response).put("medicalRecord", medicalRecord);
        }
        throw RRException.serverError();
    }

    /**
     * 批量保存
     */
    @PostMapping("/batch/save")
    public R saveBatch(@Valid @RequestBody List<MedicalRecord> medicalRecordList, HttpServletResponse response) {
        medicalRecordList.forEach(medicalRecord -> {
            if (medicalRecord.getId() != null) {
                throw RRException.badRequest(Constant.ERROR_MSG_ID_NOT_NEED);
            }
        });
        if (medicalRecordService.saveBatch(medicalRecordList)) {
            return R.noContent(response);
        }
        throw RRException.serverError();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@Valid @RequestBody MedicalRecord medicalRecord) {
        if (medicalRecord.getId() == null) {
            throw RRException.badRequest(Constant.ERROR_MSG_ID_NEED);
        }
        if (medicalRecordService.updateById(medicalRecord)) {
            return R.ok().put("medicalRecord", medicalRecord);
        }
        throw RRException.serverError();
    }

    /**
     * 批量修改
     */
    @PutMapping("/batch/update")
    public R updateBatch(@Valid @RequestBody List<MedicalRecord> medicalRecordList, HttpServletResponse response) {
        medicalRecordList.forEach(medicalRecord -> {
            if (medicalRecord.getId() == null) {
                throw RRException.badRequest(Constant.ERROR_MSG_ID_NEED);
            }
        });
        if (medicalRecordService.updateBatchById(medicalRecordList)) {
            return R.noContent(response);
        }
        throw RRException.serverError();
    }


    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public R delete(@PathVariable Integer id, HttpServletResponse response) {
        if (medicalRecordService.removeById(id)) {
            return R.noContent(response);
        }
        throw RRException.serverError();
    }

    /**
     * 批量删除
     */
    @PostMapping("/batch/delete")
    public R deleteBatch(@RequestBody Integer[] ids, HttpServletResponse response) {
        if (medicalRecordService.removeByIds(Arrays.asList(ids))) {
            return R.noContent(response);
        }
        throw RRException.serverError();
    }

}
