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
@TableName("user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("username")
    private String username;

    @TableField("password")
    private String password;

    @TableField("salt")
    private String salt;

    @TableField("email")
    private String email;

    /**
     * 0-普通用户; 1-管理员;
     */
    @TableField("type")
    private Integer type;

    /**
     * 0-失效; 1-正常;
     */
    @TableField("status")
    private Integer status;

    @TableField("gmt_create")
    private Date gmtCreate;

    @TableField("gmt_update")
    private Date gmtUpdate;
}
