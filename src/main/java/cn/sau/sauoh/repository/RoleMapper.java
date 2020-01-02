package cn.sau.sauoh.repository;

import cn.sau.sauoh.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author nullptr
 * @email justitacsl@outlook.com
 * @date 2019-12-25 19:33:28
 */
@Mapper
@Repository
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 查询指定 ID 的用户的所有身份
     * @param userId 用户 ID
     * @return 保存用户所有身份的 List
     */
    List<Role> selectAllByUserId(Integer userId);

    /**
     * 查询指定value对应的id值
     * @param value value
     * @return value对应的id值
     */
    Integer selectRoleIdByValue(String value);
}
