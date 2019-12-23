package cn.sau.sauoh.service;

import cn.sau.sauoh.entity.User;

/**
 * @author nullptr
 * @date 2019/12/23 16:44
 */
public interface UserService {

    /**
     * 检查指定的邮箱处于何种状态
     *
     * @param username 用户名
     * @return 邮箱处于何种状态的描述，包括：noExist不存在、registered已注册（有数据且check_code为空）、
     * registering 注册中（有数据、check_code不为空且create_time离当前时间不超过半个小时）、
     * overtime 注册过期（有数据、check_code不为空但create_time离当前时间超过了半个小时)
     */
    String usernameAvailable(String username);

    /**
     * 处理创建账户的过程
     *
     * @param input 前端传递过来的User数据(经过数据验证)
     * @return 存入数据库后的User数据
     */
    User userRegisterProcess(User input);

    /**
     * 检查 checkCode 是否可用，即：如果已存在不可用，不存在才可用。
     *
     * @param checkCode checkCode
     * @return true 表示可用、fasle 表示不可用
     */
    boolean checkCodeAvailable(String checkCode);

    /**
     * 检查邮箱地址的相关过程，返回的结果表示是否处理成功
     *
     * @param checkCode checkCode
     * @return true表示处理成功，false表示处理失败（可能的原因有：checkCode不存在或验证时间已过）
     */
    boolean checkEmailAddressProcess(String checkCode);

    /**
     * 删除指定用户
     * @param user 指定用户
     */
    void deleteUser(User user);
}
