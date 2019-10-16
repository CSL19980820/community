package csl.individual.community.mapper;

import csl.individual.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("select t.* from User t where t.account_Id = #{accountId}")
    User find(User user);
    @Insert("insert into user(ACCOUNT_ID,NAME,TOKEN,GMT_CREATE,GMT_MODIFIED) values(#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified})")
    void insert(User user);
}
