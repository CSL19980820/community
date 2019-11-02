package csl.individual.community.login.service.impl;

import com.alibaba.fastjson.JSONObject;
import csl.individual.community.common.service.BaseService;
import csl.individual.community.common.utils.DateUtils;
import csl.individual.community.login.entity.LogEntity;
import csl.individual.community.login.mapper.LogCodeMapper;
import csl.individual.community.login.service.ILogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Calendar;

/**
 * @Author Administrator
 * @Date 2019/11/1 14:41
 */
@Service
public  class LogServiceImpl extends BaseService<LogEntity> implements ILogService {

    @Autowired
    private LogCodeMapper logCodeMapper;
    @Override
    public void alterLog() {
        logCodeMapper.alterLog();
    }

    @Override
    public JSONObject findCode(String code, String email) {
        Example example = new Example(LogEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("code",code);
        criteria.andEqualTo("registerEmail",email);
        Long gmtCreate_30 = DateUtils.getTimeByMinute(Calendar.getInstance(),-30);
        criteria.andGreaterThanOrEqualTo("gmtCreate",gmtCreate_30);
        Integer count = super.selectCountByExample(example);
        JSONObject json = new JSONObject();
        if(count == null || count == 0){
            json.put("valid",false);
        }else{
            json.put("valid",true);
        }
        return json;
    }
}
