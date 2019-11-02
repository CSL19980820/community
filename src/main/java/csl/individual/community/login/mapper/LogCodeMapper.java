package csl.individual.community.login.mapper;

import csl.individual.community.common.mappers.BaseMapperInfo;
import csl.individual.community.login.entity.LogEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author Administrator
 * @Date 2019/11/1 14:40
 */
@Mapper
public interface LogCodeMapper extends BaseMapperInfo<LogEntity> {
    @Insert("alter table log_register AUTO_INCREMENT=1")
    void alterLog();
}
