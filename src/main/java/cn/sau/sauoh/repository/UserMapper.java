package cn.sau.sauoh.repository;

import cn.sau.sauoh.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 
 * 
 * @author nullptr
 * @email justitacsl@outlook.com
 * @date 2019-12-25 19:33:28
 */
@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {

    /**
     * 检查指定的 checkCode 是否存在
     * @param checkCode 校验码
     * @return true表示存在，false表示不存在
     */
    boolean checkCodeExist(String checkCode);

    /**
     * 找到 checkCode 对应的user
     * @param checkCode 校验码
     * @return 输入的校验码对应的 User对象
     */
    User selectByCheckCode(String checkCode);

    User selectByUsername(String username);

    User selectByEmail(String email);
}
