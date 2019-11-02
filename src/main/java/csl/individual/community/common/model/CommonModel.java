package csl.individual.community.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * 一部分公共属性
 */
@Data
@AllArgsConstructor // 全参构造方法
@NoArgsConstructor // 无参构造方法
@Accessors(chain = true) // 链式编程写法
public class CommonModel implements Serializable {
    /**
     * Id
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    /**
     * 创建者
     */
    @Column(name = "creator")
    protected Long creator;

    /**
     * 创建时间戳
     */
    @Column(name = "gmt_Create")
    protected Long gmtCreate;


    /**
     * 创建时间戳
     */
    @Column(name = "gmt_Modified")
    protected Long gmtModified;


    /**
     * 最后修改者
     */
    @Column(name = "alterCreator")
    protected Long alterCreator;


    /**
     * 最后修改时间
     */
    @Column(name = "alterCreatorTime")
    protected Long alterCreatorTime;

}
