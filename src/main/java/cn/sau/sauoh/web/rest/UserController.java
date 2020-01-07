package cn.sau.sauoh.web.rest;

import cn.sau.sauoh.entity.User;
import cn.sau.sauoh.security.utils.CurrentUser;
import cn.sau.sauoh.service.UserService;
import cn.sau.sauoh.utils.Constant;
import cn.sau.sauoh.utils.R;
import cn.sau.sauoh.utils.RRException;
import cn.sau.sauoh.web.vm.UserVM;
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

    private CurrentUser currentUser;
    private UserService userService;

    @Autowired
    public void setCurrentUser(CurrentUser currentUser) {
        this.currentUser = currentUser;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

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
        Page<User> page = new Page<>(pageNum, pageSize);
        if (Constant.SORTOF_ASC.equalsIgnoreCase(sortOf)) {
            page.addOrder(OrderItem.asc(sortBy));
        } else if (Constant.SORTOF_DESC.equalsIgnoreCase(sortOf)) {
            page.addOrder(OrderItem.desc(sortBy));
        }
        userService.page(page);
        return R.ok().put("page", page);
    }

    /**
     * 当前登陆用户
     */
    @GetMapping("/info/me")
    public R infoMe() {
        String username = currentUser.getCurrentUser().getUsername();
        R r = R.ok();
        r.putAll(userService.getInfoByUsername(username));
        return r;
    }

    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id) {
        UserVM vm = userService.getById(id);
        if (vm == null) {
            throw RRException.notFound(Constant.ERROR_MSG_ID_NOT_EXIST);
        }
        return R.ok().put("uservm", vm);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@Valid @RequestBody UserVM vm, HttpServletResponse response) {
        if (vm.getId() != null) {
            throw RRException.badRequest(Constant.ERROR_MSG_ID_NOT_NEED);
        }
        if (userService.saveVm(vm)) {
            return R.created(response).put("uservm", vm);
        }
        throw RRException.serverError();
    }

    /**
     * 批量保存
     */
    @PostMapping("/batch/save")
    public R saveBatch(@RequestBody List<UserVM> vmList, HttpServletResponse response) {
        vmList.forEach(vm -> {
            if (vm.getId() != null) {
                throw RRException.badRequest(Constant.ERROR_MSG_ID_NOT_NEED);
            }
        });
        if (userService.saveVmBatch(vmList)) {
            return R.noContent(response);
        }
        throw RRException.serverError();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@Valid @RequestBody UserVM vm, HttpServletResponse response) {
        if (vm.getId() == null) {
            throw RRException.badRequest(Constant.ERROR_MSG_ID_NEED);
        }
        if (userService.updateById(vm)) {
            return R.noContent(response);
        }
        throw RRException.serverError();
    }

    /**
     * 批量修改
     */
    @PutMapping("/batch/update")
    public R updateBatch(@RequestBody List<UserVM> vmList, HttpServletResponse response) {
        vmList.forEach(vm -> {
            if (vm.getId() == null) {
                throw RRException.badRequest(Constant.ERROR_MSG_ID_NEED);
            }
        });
        if (userService.updateBatchById(vmList)) {
            return R.noContent(response);
        }
        throw RRException.serverError();
    }

    /**
     * 删除
     * user_role、patient、doctor 表中的 user_id 外键设置了 cascade 属性，会同步delete和update
     * 即：删除一个 user 时，user对应的 roles和患者（医生）的对应数据也会删除
     */
    @DeleteMapping("/delete/{id}")
    public R delete(@PathVariable Integer id, HttpServletResponse response) {
        if (userService.removeById(id)) {
            return R.noContent(response);
        }
        throw RRException.serverError();
    }

    /**
     * 批量删除
     */
    @PostMapping("/batch/delete")
    public R delete(@RequestBody Integer[] ids, HttpServletResponse response) {
        if (userService.removeByIds(Arrays.asList(ids))) {
            return R.noContent(response);
        }
        throw RRException.serverError();
    }
}
