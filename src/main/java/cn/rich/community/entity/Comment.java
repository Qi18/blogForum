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
@TableName("comment")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 评论人id
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 评论的实体类型
     */
    @TableField("entity_type")
    private Integer entityType;

    /**
     * 评论的实体id
     */
    @TableField("entity_id")
    private Integer entityId;

    /**
     * 位置
     */
    @TableField("target_id")
    private Integer targetId;

    /**
     * 评论内容
     */
    @TableField("content")
    private String content;

    /**
     * 0-正常; 1-删除;
     */
    @TableField("status")
    private Integer status;

    @TableField("gmt_create")
    private Date gmtCreate;

    @TableField("gmt_update")
    private Date gmtUpdate;
}
