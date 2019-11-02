package csl.individual.community.common.mappers;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.common.*;

import java.util.List;
import java.util.Map;

/**
 * @Author Administrator
 * @Date 2019/11/1 9:39
 */
public interface BaseMapperInfo<T> extends Mapper<T>, MySqlMapper<T>, ConditionMapper<T>, IdsMapper<T> {
    /**
     * * 单表分页查询 * * @param object * @param offset * @param limit * @return
     */
    @SelectProvider(type = BaseMapperProvider.class, method = "dynamicSQL")
    List selectPage(@Param("entity") T object, @Param("offset") int offset, @Param("limit") int limit);
    public List<Map<String,Object>> selectList(Map map);
}
