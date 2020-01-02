package cn.sau.sauoh.service;

import cn.sau.sauoh.entity.User;
import cn.sau.sauoh.web.vm.UserVM;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author nullptr
 * @email justitacsl@outlook.com
 * @date 2019-12-25 19:33:28
 */
public interface UserService extends IService<User> {

    UserVM getById(Integer id);

    UserVM save(UserVM vm);

    boolean saveBatch(List<UserVM> vmList);

    boolean updateById(UserVM vm);

    boolean updateBatchById(List<UserVM> vmList);
}

