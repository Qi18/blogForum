package cn.rich.community.service;

import cn.rich.community.entity.DiscussPost;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author rich
 * @since 2024-07-22
 */
public interface DiscussPostService extends IService<DiscussPost> {

    List<DiscussPost> findDiscussPostsByUserId(int userId, int index, int size);

    Long findDiscussPostRowsByUserId(int userId);

    void addDiscussPost(DiscussPost post);

    DiscussPost findDiscussPostById(int id);

    void updateDiscussPost(int id, DiscussPost discussPost);

    void updateCommentCount(int id, int commentCount);

}
