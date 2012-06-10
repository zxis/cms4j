package cn.edu.sdufe.cms.common.service.account;

import cn.edu.sdufe.cms.common.dao.account.UserMapper;
import cn.edu.sdufe.cms.common.entity.account.User;
import cn.edu.sdufe.cms.common.service.GenericManager;

import java.util.List;
import java.util.Map;

/**
 * 类功能
 * <p/>
 * User: baitao.jibt@gmail.com
 * Date: 12-8-12
 * Time: 下午4:07
 */
public interface UserManager extends GenericManager<User, Long> {

    /**
     * 获取所有用户
     *
     * @return
     */
    List<User> getAll();

    /**
     * 通过邮箱获取用户
     *
     * @param email
     * @return
     */
    User findUserByEmail(String email);

    /**
     * 获取用户数量
     *
     * @return
     */
    long count();

    /**
     * 任务删除用户
     *
     * @return
     */
    long deleteByTask() throws InterruptedException;

    /**
     * 更新用户
     *
     * @param id
     * @param column
     */
    long update(Long id, String column);

    /**
     * 重置密码
     *
     * @param id
     */
    void repass(Long id);

    /**
     * 批量审核用户
     *
     * @param ids
     */
    void batchAudit(String[] ids);

    /**
     * 批量改变用户的删除标志
     *
     * @param ids
     */
    void batchDelete(String[] ids);

    /**
     * 按照parameters搜索用户
     *
     * @param parameters
     * @return
     */
    List<User> search(Map<String, Object> parameters, int offset, int limit);

    void setUserMapper(UserMapper userMapper);

}
