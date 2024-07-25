package cn.rich.community.service.impl;

import cn.rich.community.entity.DiscussPost;
import cn.rich.community.mapper.DiscussPostMapper;
import cn.rich.community.service.DiscussPostService;
import cn.rich.community.util.SensitiveFilter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author rich
 * @since 2024-07-22
 */
@Service
@Slf4j
public class DiscussPostServiceImpl extends ServiceImpl<DiscussPostMapper, DiscussPost> implements DiscussPostService {

    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Autowired
    private SensitiveFilter sensitiveFilter;


    @Override
    public List<DiscussPost> findDiscussPostsByUserId(int userId, int index, int size) {
        log.debug("load post list from DB.");
        QueryWrapper<DiscussPost> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        return discussPostMapper.selectPage(new Page<>(index, size), wrapper).getRecords();
    }

    @Override
    public Long findDiscussPostRowsByUserId(int userId) {
        log.debug("load post rows from DB.");
        return discussPostMapper.selectCount(new QueryWrapper<DiscussPost>().eq("user_id", userId));
    }

    @Override
    public void addDiscussPost(DiscussPost post) {
        if (post == null) throw new IllegalArgumentException("参数不能为空!");
//        // 转义HTML标记
//        post.setTitle(HtmlUtils.htmlEscape(post.getTitle()));
//        post.setContent(HtmlUtils.htmlEscape(post.getContent()));
        // 过滤敏感词
        post.setTitle(sensitiveFilter.filter(post.getTitle()));
        post.setContent(sensitiveFilter.filter(post.getContent()));
        discussPostMapper.insert(post);
    }

    @Override
    public DiscussPost findDiscussPostById(int id) {
        return discussPostMapper.selectById(id);
    }

    @Override
    public void updateDiscussPost(int id, DiscussPost discussPost) {
        UpdateWrapper<DiscussPost> updateWrapper = new UpdateWrapper<>();
        updateWrapper.gt("id", id);
        discussPostMapper.update(discussPost, updateWrapper);
    }

    @Override
    public void updateCommentCount(int id, int commentCount) {
        this.updateDiscussPost(id, new DiscussPost().setCommentCount(commentCount));
    }

}

