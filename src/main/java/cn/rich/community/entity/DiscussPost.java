package cn.rich.community.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author rich
 * @since 2024-07-25
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("discuss_post")
public class DiscussPost implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("user_id")
    private String userId;

    @TableField("title")
    private String title;

    @TableField("content")
    private String content;

    /**
     * 0-普通; 1-置顶;
     */
    @TableField("type")
    private Integer type;

    /**
     * 0-正常; 1-精华; 2-拉黑;
     */
    @TableField("status")
    private Integer status;

    /**
     * 评论数
     */
    @TableField("comment_count")
    private Integer commentCount;

    @TableField("score")
    private Double score;

    @TableField("gmt_create")
    private Date gmtCreate;

    @TableField("gmt_update")
    private Date gmtUpdate;
}
