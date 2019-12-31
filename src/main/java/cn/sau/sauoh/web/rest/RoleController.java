package cn.sau.sauoh.web.rest;

import cn.sau.sauoh.entity.Role;
import cn.sau.sauoh.service.RoleService;
import cn.sau.sauoh.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 身份
 */
@Slf4j
@RestController
@RequestMapping("/api/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    /**
     * 列表
     */
    @GetMapping("/all")
    public R list() {
        return R.ok().put("roles", roleService.list());
    }

    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id) {
        Role role = roleService.getById(id);
        return R.ok().put("role", role);
    }
}
