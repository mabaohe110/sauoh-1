package cn.sau.sauoh.web.rest;

import cn.sau.sauoh.entity.User;
import cn.sau.sauoh.service.UserService;
import cn.sau.sauoh.utils.R;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;


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
        User user = userService.getById(id);

        return R.ok().put("user", user);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody User user) {
        userService.save(user);

        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody User user) {
        userService.updateById(user);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Integer[] ids) {
        userService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
