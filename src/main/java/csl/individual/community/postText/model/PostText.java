package csl.individual.community.postText.model;

import csl.individual.community.common.model.CommonModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Table;

/**
 * 帖子
 */
@Data
@AllArgsConstructor // 全参构造方法
@NoArgsConstructor
@Accessors(chain = true)
@Table(name="PostText")
public class PostText extends CommonModel {

    /**
     * 类型
     */
    private Integer type = 0;

    /**
     * 内容
     */
    private String description;


    /**
     * 标题
     */
    private String title;


    private String label;


    //评论数量
    private Integer comment_count;

    //阅读数量
    private Integer view_count;


    //点赞数量
    private Integer like_count;


    //标签
    private String tag;


}
