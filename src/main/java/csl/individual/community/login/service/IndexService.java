package csl.individual.community.login.service;

import com.alibaba.fastjson.JSONObject;
import csl.individual.community.common.service.IBaseService;
import csl.individual.community.login.model.User;

/**
 * 登录业务逻辑层
 */
public interface IndexService  extends IBaseService<User> {



    /**
     * 注册
     * @param user
     */
    String  toRegister(User user);

    /**
     *获取Email
     */
    JSONObject findEmail(String email);
}
