package cn.sau.sauoh.repository;

import cn.sau.sauoh.entity.UserRole;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleMapper {
    int deleteByUser(Integer id);

    int insert(UserRole record);

    List<UserRole> selectAllByUser(Integer id);

    List<UserRole> selectAll();

    int deleteByPrimaryKey(int user_id, int role_id);
}