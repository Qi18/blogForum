package cn.rich.community.controller;

import cn.rich.community.domain.ResponseResult;
import cn.rich.community.entity.*;
import cn.rich.community.service.DiscussPostService;
import cn.rich.community.service.UserService;
import cn.rich.community.util.CommunityConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/discuss")
public class DiscussPostController implements CommunityConstant {

    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private UserService userService;

//    @Autowired
//    private CommentService commentService;
//
//    @Autowired
//    private LikeService likeService;
//
//    @Autowired
//    private EventProducer eventProducer;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult<?> addDiscussPost(String title, String content) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findUserByName(userDetails.getUsername());
        DiscussPost post = new DiscussPost();
        post.setUserId(String.valueOf(user.getId()));
        post.setTitle(title);
        post.setContent(content);
        discussPostService.addDiscussPost(post);

//        // 触发发帖事件
//        Event event = new Event()
//                .setTopic(TOPIC_PUBLISH)
//                .setUserId(user.getId())
//                .setEntityType(ENTITY_TYPE_POST)
//                .setEntityId(post.getId());
//        eventProducer.fireEvent(event);
//
//        // 计算帖子分数
//        String redisKey = RedisKeyUtil.getPostScoreKey();
//        redisTemplate.opsForSet().add(redisKey, post.getId());

        return ResponseResult.success("发布帖子成功");
    }

//    @RequestMapping(path = "/detail/{discussPostId}", method = RequestMethod.GET)
//    public String getDiscussPost(@PathVariable("discussPostId") int discussPostId, int index, int size) {
//        // 帖子
//        DiscussPost post = discussPostService.findDiscussPostById(discussPostId);
//        // 作者
//        User user = userService.findUserById(Integer.parseInt(post.getUserId()));
//        // 点赞数量
////        long likeCount = likeService.findEntityLikeCount(ENTITY_TYPE_POST, discussPostId);
////        // 点赞状态
////        int likeStatus = hostHolder.getUser() == null ? 0 :
////                likeService.findEntityLikeStatus(hostHolder.getUser().getId(), ENTITY_TYPE_POST, discussPostId);
//
//        // 评论: 给帖子的评论
//        // 回复: 给评论的评论
//        // 评论列表
//        List<Comment> commentList = commentService.findCommentsByEntity(
//                ENTITY_TYPE_POST, post.getId(), page.getOffset(), page.getLimit());
//        // 评论VO列表
//        List<Map<String, Object>> commentVoList = new ArrayList<>();
//        if (commentList != null) {
//            for (Comment comment : commentList) {
//                // 评论VO
//                Map<String, Object> commentVo = new HashMap<>();
//                // 评论
//                commentVo.put("comment", comment);
//                // 作者
//                commentVo.put("user", userService.findUserById(comment.getUserId()));
//                // 点赞数量
//                likeCount = likeService.findEntityLikeCount(ENTITY_TYPE_COMMENT, comment.getId());
//                commentVo.put("likeCount", likeCount);
//                // 点赞状态
//                likeStatus = hostHolder.getUser() == null ? 0 :
//                        likeService.findEntityLikeStatus(hostHolder.getUser().getId(), ENTITY_TYPE_COMMENT, comment.getId());
//                commentVo.put("likeStatus", likeStatus);
//
//                // 回复列表
//                List<Comment> replyList = commentService.findCommentsByEntity(
//                        ENTITY_TYPE_COMMENT, comment.getId(), 0, Integer.MAX_VALUE);
//                // 回复VO列表
//                List<Map<String, Object>> replyVoList = new ArrayList<>();
//                if (replyList != null) {
//                    for (Comment reply : replyList) {
//                        Map<String, Object> replyVo = new HashMap<>();
//                        // 回复
//                        replyVo.put("reply", reply);
//                        // 作者
//                        replyVo.put("user", userService.findUserById(reply.getUserId()));
//                        // 回复目标
//                        User target = reply.getTargetId() == 0 ? null : userService.findUserById(reply.getTargetId());
//                        replyVo.put("target", target);
//                        // 点赞数量
//                        likeCount = likeService.findEntityLikeCount(ENTITY_TYPE_COMMENT, reply.getId());
//                        replyVo.put("likeCount", likeCount);
//                        // 点赞状态
//                        likeStatus = hostHolder.getUser() == null ? 0 :
//                                likeService.findEntityLikeStatus(hostHolder.getUser().getId(), ENTITY_TYPE_COMMENT, reply.getId());
//                        replyVo.put("likeStatus", likeStatus);
//
//                        replyVoList.add(replyVo);
//                    }
//                }
//                commentVo.put("replys", replyVoList);
//
//                // 回复数量
//                int replyCount = commentService.findCommentCount(ENTITY_TYPE_COMMENT, comment.getId());
//                commentVo.put("replyCount", replyCount);
//
//                commentVoList.add(commentVo);
//            }
//        }
//
//        return
//    }


    // 删除
    @RequestMapping(path = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult<?> setDelete(int id) {
        DiscussPost discussPost = new DiscussPost();
        discussPost.setStatus(2);
        discussPostService.updateDiscussPost(id, discussPost);

//        // 触发删帖事件
//        Event event = new Event()
//                .setTopic(TOPIC_DELETE)
//                .setUserId(hostHolder.getUser().getId())
//                .setEntityType(ENTITY_TYPE_POST)
//                .setEntityId(id);
//        eventProducer.fireEvent(event);

        return ResponseResult.success("删除成功");
    }

}
