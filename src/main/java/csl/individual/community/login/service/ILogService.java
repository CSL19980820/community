package csl.individual.community.login.service;

import com.alibaba.fastjson.JSONObject;
import csl.individual.community.common.service.IBaseService;
import csl.individual.community.login.entity.LogEntity;

/**
 * @Author Administrator
 * @Date 2019/11/1 14:41
 */
public interface ILogService extends IBaseService<LogEntity> {
    /**
     * 修改日志表ID
     */
    void alterLog();

    JSONObject findCode(String code, String email);
}
