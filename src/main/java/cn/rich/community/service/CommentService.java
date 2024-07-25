package cn.rich.community.service;

import cn.rich.community.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author rich
 * @since 2024-07-25
 */
public interface CommentService extends IService<Comment> {

    List<Comment> findCommentsByEntity(int entityType, int entityId, int index, int size);

    Long countCommentByEntity(int entityType, int entityId);

    void addComment(Comment comment);

    Comment findCommentById(int id);
}
