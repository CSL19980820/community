package csl.individual.community.postText.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PublishUtilMapper {

    @Select("select count(*) from postText where title =#{title} and type = #{type}")
    Integer findTitle(@Param("title") String title, @Param("type")String type);
}
