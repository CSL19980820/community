package csl.individual.community.login.entity;

import csl.individual.community.common.model.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @Author Administrator
 * @Date 2019/11/1 9:24
 */
@Data
@AllArgsConstructor // 全参构造方法
@NoArgsConstructor
@Accessors(chain = true)
@Table(name="log_register")
public class LogEntity extends BaseModel {

    /**
     * ip地址
     */
    @Column(name = "register_Ip")
    private String registerIp;
    /**
     * 注册Email
     */
    @Column(name = "register_Email")
    private String registerEmail;
    /**
     * 临时code
     */
    @Column(name = "code")
    private String code;

    /**
     * 日志类型
     */
    @Column(name = "type")
    private Integer type;
}
