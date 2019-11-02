package csl.individual.community.login.service.impl;

import com.alibaba.fastjson.JSONObject;
import csl.individual.community.common.service.BaseService;
import csl.individual.community.login.model.User;
import csl.individual.community.login.providerUtil.UserUtil;
import csl.individual.community.login.service.IndexService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class IndexServiceImpl extends BaseService<User> implements IndexService {

    @Override
    public String  toRegister(User user) {
        user.setGmtCreate(System.currentTimeMillis());
        user.setGmtModified(user.getGmtCreate());
        String accountId = UserUtil.getUUId();
        user.setAccountId(accountId);
        user.setSource(UserUtil.userLogin);
        user.setPassword(UserUtil.putMd5Password(user.getPassword()));
        if(super.insert(user) > 0){
            return accountId;
        }else {
            return "";
        }
    }

    @Override
    public JSONObject findEmail(String email) {
        JSONObject json = new JSONObject();
        if(UserUtil.isNull(email)){
            json.put("valid",false);
            return json;
        }
        User user = new User();
        user.setEmail(email);
        Integer count = super.selectCount(user);
        if(count == null || count == 0){
            json.put("valid",true);
        }else{
            json.put("valid",false);
        }
        return json;
    }
}
