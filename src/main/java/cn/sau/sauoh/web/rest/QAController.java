package cn.sau.sauoh.web.rest;

import cn.sau.sauoh.entity.QA;
import cn.sau.sauoh.service.QAService;
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
 * @author nullptr
 * @date 2020/1/6 23:19
 */
@RestController
@RequestMapping("/api/qa")
public class QAController {
    @Autowired
    private QAService qaService;

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
        Page<QA> page = new Page<>(pageNum, pageSize);
        if (Constant.SORTOF_ASC.equalsIgnoreCase(sortOf)) {
            page.addOrder(OrderItem.asc(sortBy));
        } else if (Constant.SORTOF_DESC.equalsIgnoreCase(sortOf)) {
            page.addOrder(OrderItem.desc(sortBy));
        }
        qaService.page(page);
        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id) {
        QA qa = qaService.getById(id);
        if (qa != null) {
            return R.ok().put("medicalRecord", qa);
        }
        throw RRException.notFound(Constant.ERROR_MSG_ID_NOT_EXIST);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@Valid @RequestBody QA qa, HttpServletResponse response) {
        if (qa.getId() != null) {
            throw RRException.badRequest(Constant.ERROR_MSG_ID_NOT_NEED);
        }
        if (qaService.save(qa)) {
            return R.created(response).put("qa", qa);
        }
        throw RRException.serverError();
    }

    /**
     * 批量保存
     */
    @PostMapping("/batch/save")
    public R saveBatch(@Valid @RequestBody List<QA> qaList, HttpServletResponse response) {
        qaList.forEach(qa -> {
            if (qa.getId() != null) {
                throw RRException.badRequest(Constant.ERROR_MSG_ID_NOT_NEED);
            }
        });
        if (qaService.saveBatch(qaList)) {
            return R.noContent(response);
        }
        throw RRException.serverError();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@Valid @RequestBody QA qa) {
        if (qa.getId() == null) {
            throw RRException.badRequest(Constant.ERROR_MSG_ID_NEED);
        }
        if (qaService.updateById(qa)) {
            return R.ok().put("qa", qa);
        }
        throw RRException.serverError();
    }

    /**
     * 批量修改
     */
    @PutMapping("/batch/update")
    public R updateBatch(@Valid @RequestBody List<QA> qaList, HttpServletResponse response) {
        qaList.forEach(qa -> {
            if (qa.getId() == null) {
                throw RRException.badRequest(Constant.ERROR_MSG_ID_NEED);
            }
        });
        if (qaService.updateBatchById(qaList)) {
            return R.noContent(response);
        }
        throw RRException.serverError();
    }


    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public R delete(@PathVariable Integer id, HttpServletResponse response) {
        if (qaService.removeById(id)) {
            return R.noContent(response);
        }
        throw RRException.serverError();
    }

    /**
     * 批量删除
     */
    @PostMapping("/batch/delete")
    public R deleteBatch(@RequestBody Integer[] ids, HttpServletResponse response) {
        if (qaService.removeByIds(Arrays.asList(ids))) {
            return R.noContent(response);
        }
        throw RRException.serverError();
    }
}
