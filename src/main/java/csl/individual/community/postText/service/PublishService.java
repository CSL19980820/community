package csl.individual.community.postText.service;

import com.alibaba.fastjson.JSONObject;
import csl.individual.community.common.service.IBaseService;
import csl.individual.community.postText.model.PostText;

public interface PublishService extends IBaseService<PostText> {
    /**
     * 创建帖子
     * @param postText
     * @return
     */
    Integer create(PostText postText);

    /**
     * 标题验证
     * @param title
     * @param type
     * @return
     */
    JSONObject findTitle(String title, String type);
}
