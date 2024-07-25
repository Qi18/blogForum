//package cn.rich.community.service.impl;
//
//import cn.rich.community.entity.Message;
//import cn.rich.community.mapper.MessageMapper;
//import cn.rich.community.service.MessageService;
//import cn.rich.community.util.SensitiveFilter;
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.util.HtmlUtils;
//
//import java.util.List;
//
///**
// * <p>
// *  服务实现类
// * </p>
// *
// * @author rich
// * @since 2024-07-25
// */
//@Service
//public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {
//
//
//    @Autowired
//    private MessageMapper messageMapper;
//
//    @Autowired
//    private SensitiveFilter sensitiveFilter;
//
//    @Override
//    public List<Message> findReceiveLetterByUser(int userId, int index, int size) {
//        QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("to_id", userId);
//        return messageMapper.selectPage(new Page<>(index, size), queryWrapper).getRecords();
//    }
//
//    public int findConversationCount(int userId) {
//        return messageMapper.selectConversationCount(userId);
//    }
//
//    public List<Message> findLetters(String conversationId, int offset, int limit) {
//        return messageMapper.selectLetters(conversationId, offset, limit);
//    }
//
//    public int findLetterCount(String conversationId) {
//        return messageMapper.selectLetterCount(conversationId);
//    }
//
//    public int findLetterUnreadCount(int userId, String conversationId) {
//        return messageMapper.selectLetterUnreadCount(userId, conversationId);
//    }
//
//    public int addMessage(Message message) {
//        message.setContent(HtmlUtils.htmlEscape(message.getContent()));
//        message.setContent(sensitiveFilter.filter(message.getContent()));
//        return messageMapper.insertMessage(message);
//    }
//
//    public int readMessage(List<Integer> ids) {
//        return messageMapper.updateStatus(ids, 1);
//    }
//
//    public Message findLatestNotice(int userId, String topic) {
//        return messageMapper.selectLatestNotice(userId, topic);
//    }
//
//    public int findNoticeCount(int userId, String topic) {
//        return messageMapper.selectNoticeCount(userId, topic);
//    }
//
//    public int findNoticeUnreadCount(int userId, String topic) {
//        return messageMapper.selectNoticeUnreadCount(userId, topic);
//    }
//
//    public List<Message> findNotices(int userId, String topic, int offset, int limit) {
//        return messageMapper.selectNotices(userId, topic, offset, limit);
//    }
//
//}
//
