package csl.individual.community.login.model;

import csl.individual.community.common.model.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Table;

@Data
@AllArgsConstructor // 全参构造方法
@NoArgsConstructor
@Accessors(chain = true)
@Table(name="user")
public class User extends BaseModel {
    protected String name;
    protected String accountId;
    protected String bio;

    /**
     * 登录来源
     */
    public String source;


    /**
     * 邮箱
     */
    public String email;


    /**
     * 密码
     */
    private String password;


    /**
     * 是否验证
     */
    private Integer is_yes;

}
