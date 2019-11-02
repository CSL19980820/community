package csl.individual.community.postText.service.impl;

import com.alibaba.fastjson.JSONObject;
import csl.individual.community.common.service.BaseService;
import csl.individual.community.login.providerUtil.UserUtil;
import csl.individual.community.postText.model.PostText;
import csl.individual.community.postText.service.PublishService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 帖子业务逻辑层
 */
@Service
@Transactional
public class PublishServiceImpl extends BaseService<PostText> implements PublishService  {

    @Override
    public Integer create(PostText postText)
    {

        postText.setCreator(UserUtil.user.getId());
        postText.setGmtCreate(System.currentTimeMillis());
        postText.setGmtModified(postText.getGmtCreate());
        postText.setAlterCreatorTime(postText.getGmtCreate());
        postText.setAlterCreator(UserUtil.user.getId());
        return super.insert(postText);
    }
    @Override
    public JSONObject findTitle(String title, String type) {
        PostText postText = new PostText();
        postText.setTitle(title);
        postText.setType(Integer.parseInt(type));
        Integer count = super.selectCount(postText);
        JSONObject json = new JSONObject();
        if(count == null || count == 0){
            json.put("valid",true);
        }else{
            json.put("valid",false);
        }
        return json;
    }
}
