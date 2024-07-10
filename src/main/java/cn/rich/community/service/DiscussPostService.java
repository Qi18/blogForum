//package cn.rich.community.service;
//
//import cn.rich.community.dao.DiscussPostMapper;
//import cn.rich.community.entity.DiscussPost;
//import cn.rich.community.util.SensitiveFilter;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.util.HtmlUtils;
//
//import javax.annotation.PostConstruct;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//@Service
//public class DiscussPostService {
//
//    private static final Logger logger = LoggerFactory.getLogger(DiscussPostService.class);
//
//    @Autowired
//    private DiscussPostMapper discussPostMapper;
//
//    @Autowired
//    private SensitiveFilter sensitiveFilter;
//
//    @Value("${caffeine.posts.max-size}")
//    private int maxSize;
//
//    @Value("${caffeine.posts.expire-seconds}")
//    private int expireSeconds;
//
//    // Caffeine核心接口: Cache, LoadingCache, AsyncLoadingCache
//
//    // 帖子列表缓存
////    private LoadingCache<String, List<DiscussPost>> postListCache;
////
////    // 帖子总数缓存
////    private LoadingCache<Integer, Integer> postRowsCache;
//
//    @PostConstruct
//    public void init() {
//        // 初始化帖子列表缓存
//
//        // 初始化帖子总数缓存
//
//    }
//
//    public List<DiscussPost> findDiscussPosts(int userId, int offset, int limit, int orderMode) {
////        if (userId == 0 && orderMode == 1) {
////            return postListCache.get(offset + ":" + limit);
////        }
//
//        logger.debug("load post list from DB.");
//        return discussPostMapper.selectDiscussPosts(userId, offset, limit, orderMode);
//    }
//
//    public int findDiscussPostRows(int userId) {
////        if (userId == 0) {
////            return postRowsCache.get(userId);
////        }
//
//        logger.debug("load post rows from DB.");
//        return discussPostMapper.selectDiscussPostRows(userId);
//    }
//
//    public int addDiscussPost(DiscussPost post) {
//        if (post == null) {
//            throw new IllegalArgumentException("参数不能为空!");
//        }
//
//        // 转义HTML标记
//        post.setTitle(HtmlUtils.htmlEscape(post.getTitle()));
//        post.setContent(HtmlUtils.htmlEscape(post.getContent()));
//        // 过滤敏感词
//        post.setTitle(sensitiveFilter.filter(post.getTitle()));
//        post.setContent(sensitiveFilter.filter(post.getContent()));
//
//        return discussPostMapper.insertDiscussPost(post);
//    }
//
//    public DiscussPost findDiscussPostById(int id) {
//        return discussPostMapper.selectDiscussPostById(id);
//    }
//
//    public int updateCommentCount(int id, int commentCount) {
//        return discussPostMapper.updateCommentCount(id, commentCount);
//    }
//
//    public int updateType(int id, int type) {
//        return discussPostMapper.updateType(id, type);
//    }
//
//    public int updateStatus(int id, int status) {
//        return discussPostMapper.updateStatus(id, status);
//    }
//
//    public int updateScore(int id, double score) {
//        return discussPostMapper.updateScore(id, score);
//    }
//
//}
