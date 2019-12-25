package cn.sau.sauoh.service;

import cn.sau.sauoh.entity.User;

/**
 * 登陆（实际由 Spring Security 接管）、注册逻辑
 *
 * @author nullptr
 * @date 2019/12/23 16:44
 */
public interface AuthService {

    /**
     * 检查指定的字段处于何种状态（一般用户用户名和邮箱的检查）
     *
     * @param fieldName 字段名
     * @param field 字段值
     * @return 字段处于何种状态的描述，包括：noExist不存在、registered已注册（有数据且check_code为空）、
     * registering 注册中（有数据、check_code不为空且create_time离当前时间不超过半个小时）、
     * overtime 注册过期（有数据、check_code不为空但create_time离当前时间超过了半个小时)
     */
    String fieldStatus(String fieldName, String field);

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
     * 检查邮箱地址的相关过程，返回的结果表示处理后的结果
     *
     * @param checkCode checkCode
     * @return noExist 表示checkCode不存在，未执行任何处理过程；checked 表示处理结束；overtime 表示超过验证时间
     */
    String checkEmailAddressProcess(String checkCode);

    /**
     * 删除指定用户
     * @param user 指定用户
     */
    void deleteUser(User user);
}
