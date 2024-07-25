package cn.rich.community.service.impl;

import cn.rich.community.entity.Comment;
import cn.rich.community.mapper.CommentMapper;
import cn.rich.community.service.CommentService;
import cn.rich.community.service.DiscussPostService;
import cn.rich.community.util.SensitiveFilter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

import static cn.rich.community.util.CommunityConstant.ENTITY_TYPE_POST;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author rich
 * @since 2024-07-25
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Autowired
    private DiscussPostService discussPostService;

    @Override
    public List<Comment> findCommentsByEntity(int entityType, int entityId, int index, int size) {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("entity_type", entityType)
                .eq("entity_id", entityId);
        return commentMapper.selectPage(new Page<>(index, size), queryWrapper).getRecords();
    }

    @Override
    public Long countCommentByEntity(int entityType, int entityId) {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("entity_type", entityType)
                .eq("entity_id", entityId);
        return commentMapper.selectCount(queryWrapper);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void addComment(Comment comment) {
        if (comment == null) {
            throw new IllegalArgumentException("参数不能为空!");
        }

        // 添加评论
        comment.setContent(sensitiveFilter.filter(comment.getContent()));
        commentMapper.insert(comment);

        // 更新帖子评论数量
        if (comment.getEntityType() == ENTITY_TYPE_POST) {
            long count = this.countCommentByEntity(comment.getEntityType(), comment.getEntityId());
            discussPostService.updateCommentCount(comment.getEntityId(), (int) count);
        }
    }

    @Override
    public Comment findCommentById(int id) {
        return commentMapper.selectById(id);
    }

}

