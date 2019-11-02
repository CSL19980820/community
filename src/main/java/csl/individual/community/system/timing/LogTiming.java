package csl.individual.community.system.timing;

import csl.individual.community.common.utils.DateUtils;
import csl.individual.community.login.entity.LogEntity;
import csl.individual.community.login.service.ILogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author Administrator
 * @Date 2019/11/2 11:58
 */
@Component
public class LogTiming {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private static final Logger logger = LoggerFactory.getLogger(LogTiming.class);

    @Autowired
    private ILogService logService ;
    @Scheduled(fixedRate = 1000)
    public void ss() {
        try {

        }catch (Exception e){
            logger.error(""+e);
        }

    }

    //每天00：30执行清除12点以前的日志
    @Scheduled(cron = "0 30 00 ? * *")
    public void destructionLog() {
        try {
            Long startTime = System.currentTimeMillis();
            logger.info("日志定时清除任务启动:"+ dateFormat.format(new Date()));
            Long deleteTime = DateUtils.initDateByDay(-12,0,0);
            Example example = new Example(LogEntity.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andLessThanOrEqualTo("gmtCreate",deleteTime);
            criteria.orIsNull("gmtCreate");
            logService.deleteByExample(example);
            Long endTime = System.currentTimeMillis();
            logger.info("日志定时清除任务执行完毕,耗时:" + (endTime - startTime) + "ms");
        }catch (Exception e){
            logger.error("执行日志定时任务时出错。"+ dateFormat.format(new Date()));
        }
    }

    //每天00：35查询日志表是否数据为空,如果数据为空重置表初始Id
    @Scheduled(cron = "0 35 00 ? * *")
    public void alterLogStartId() {
        try {
            Long startTime = System.currentTimeMillis();
            Long endTime = 0l;
            logger.info("日志ID重置任务启动:"+ dateFormat.format(new Date()));
            LogEntity logEntity = new LogEntity();
            Integer num = logService.selectCount(logEntity);
            if(num == 0){
                logService.alterLog();
                endTime = System.currentTimeMillis();
                logger.info("日志ID重置任务执行完毕,耗时:" + (endTime - startTime) + "ms");
            }else{
                logger.error("日志未被完全清除");
            }
        }catch (Exception e){
            logger.error("执行日志ID重置任务时出错 :"+ dateFormat.format(new Date()));
        }
    }
}
