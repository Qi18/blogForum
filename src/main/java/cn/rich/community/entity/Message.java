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
@TableName("message")
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("from_id")
    private Integer fromId;

    @TableField("to_id")
    private Integer toId;

    @TableField("conversation_id")
    private String conversationId;

    @TableField("content")
    private String content;

    /**
     * 0-未读;1-已读;2-删除;
     */
    @TableField("status")
    private Integer status;

    @TableField("gmt_create")
    private Date gmtCreate;

    @TableField("gmt_update")
    private Date gmtUpdate;
}
