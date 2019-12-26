package cn.sau.sauoh.web.rest;

import cn.sau.sauoh.entity.UserRole;
import cn.sau.sauoh.service.UserRoleService;
import cn.sau.sauoh.utils.R;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;


/**
 * @author nullptr
 * @email justitacsl@outlook.com
 * @date 2019-12-25 19:33:27
 */
@RestController
@RequestMapping("/api/userrole")
public class UserRoleController {
    @Autowired
    private UserRoleService userRoleService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                  @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                  @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
                  @RequestParam(value = "sortOf", defaultValue = "ASC") String sortOf) {
        Page<UserRole> page = new Page<>(pageNum, pageSize);
        if ("ASC".equals(sortOf)) {
            page.addOrder(OrderItem.asc(sortBy));
        } else {
            page.addOrder(OrderItem.desc(sortBy));
        }
        userRoleService.page(page);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{userId}")
    public R info(@PathVariable("userId") Integer userId) {
        UserRole userRole = userRoleService.getById(userId);

        return R.ok().put("userRole", userRole);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody UserRole userRole) {
        userRoleService.save(userRole);

        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody UserRole userRole) {
        userRoleService.updateById(userRole);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Integer[] userIds) {
        userRoleService.removeByIds(Arrays.asList(userIds));

        return R.ok();
    }

}
