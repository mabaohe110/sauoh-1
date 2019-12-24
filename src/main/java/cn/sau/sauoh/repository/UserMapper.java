package cn.sau.sauoh.repository;

import cn.sau.sauoh.entity.Role;
import cn.sau.sauoh.entity.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    /**
     * insert data to user table
     * @param record data
     * @return 数据库受影响行数，应该为 1
     */
    int insert(User record);

    User selectByPrimaryKey(Integer id);

    User selectByUsername(String username);

    User selectByEmail(String email);

    List<User> selectAll();

    int updateByPrimaryKey(User record);

    /**
     * insert data to user table(return primary key)
     * @param record data
     * @return 数据库中改变的行数
     */
    int insertUser(User record);

    /**
     * 检查指定的 checkCode 是否存在
     * @param checkCode 校验码
     * @return true表示存在，false表示不存在
     */
    boolean checkCodeExist(String checkCode);

    List<User> selectCityAdmin();

    User selectByCheckCode(String checkCode);
}