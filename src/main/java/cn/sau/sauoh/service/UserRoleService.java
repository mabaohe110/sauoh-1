package cn.sau.sauoh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.sau.sauoh.entity.UserRole;

import java.util.List;

/**
 * 
 *
 * @author nullptr
 * @email justitacsl@outlook.com
 * @date 2019-12-25 19:33:27
 */
public interface UserRoleService extends IService<UserRole> {

    List<UserRole> getAllByUserId(Integer userId);
}

