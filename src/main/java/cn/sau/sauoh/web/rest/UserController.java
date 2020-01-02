package cn.sau.sauoh.web.rest;

import cn.sau.sauoh.entity.User;
import cn.sau.sauoh.service.UserService;
import cn.sau.sauoh.utils.R;
import cn.sau.sauoh.utils.RRException;
import cn.sau.sauoh.web.vm.UserVM;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

/**
 * 账户相关的 restcontroller，使用 UserVM（user+role）
 */
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                  @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                  @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
                  @RequestParam(value = "sortOf", defaultValue = "ASC") String sortOf) {
        Page<User> page = new Page<>(pageNum, pageSize);
        if ("ASC".equals(sortOf)) {
            page.addOrder(OrderItem.asc(sortBy));
        } else {
            page.addOrder(OrderItem.desc(sortBy));
        }
        userService.page(page);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id) {
        UserVM vm = userService.getById(id);
        return R.ok().put("uservm", vm);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@Valid @RequestBody UserVM vm, HttpServletResponse response) {
        userService.save(vm);
        return R.created(response).put("uservm", vm);
    }

    /**
     * 批量保存
     */
    @PostMapping("/batchsave")
    public R save(@RequestBody String reqBody, HttpServletResponse response){
        List<UserVM> vmList = JSONObject.parseArray(reqBody, UserVM.class);
        if(userService.saveBatch(vmList)){
            return R.created(response).put("insertSize", vmList.size());
        }
        throw RRException.serverError();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@Valid @RequestBody UserVM vm, HttpServletResponse response) {
        if (userService.updateById(vm)) {
            return R.noContent(response);
        }
        throw RRException.serverError();
    }

    /**
     * 批量修改
     */
    @PutMapping("/batchupdate")
    public R update(@RequestBody String reqBody, HttpServletResponse response) {
        List<UserVM> vmList = JSONObject.parseArray(reqBody, UserVM.class);
        if (userService.updateBatchById(vmList)) {
            return R.noContent(response);
        }
        throw RRException.serverError();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public R delete(@PathVariable Integer id, HttpServletResponse response) {
        userService.removeById(id);
        return R.noContent(response);
    }

    /**
     * 批量删除
     */
    @PostMapping("/batchdelete")
    public R delete(@RequestBody Integer[] ids, HttpServletResponse response) {
        userService.removeByIds(Arrays.asList(ids));
        return R.noContent(response);
    }
}
