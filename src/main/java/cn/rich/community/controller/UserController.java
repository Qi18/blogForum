package cn.rich.community.controller;

import cn.rich.community.entity.User;
import cn.rich.community.service.UserService;
import cn.rich.community.util.CommunityConstant;
import cn.rich.community.util.HostHolder;
//import cn.rich.community.service.FollowService;
//import cn.rich.community.service.LikeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/user")
@Slf4j
public class UserController implements CommunityConstant {

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Autowired
    private UserService userService;

    @Autowired
    private HostHolder hostHolder;

//    @Autowired
//    private LikeService likeService;
//
//    @Autowired
//    private FollowService followService;


    // 更新头像路径
//    @RequestMapping(path = "/header/url", method = RequestMethod.POST)
//    @ResponseBody
//    public String updateHeaderUrl(String fileName) {
//        if (StringUtils.isBlank(fileName)) {
//            return CommunityUtil.getJSONString(1, "文件名不能为空!");
//        }
//
//        String url = headerBucketUrl + "/" + fileName;
//        userService.updateHeader(hostHolder.getUser().getId(), url);
//
//        return CommunityUtil.getJSONString(0);
//    }


    // 废弃
//    @RequestMapping(path = "/header/{fileName}", method = RequestMethod.GET)
//    public void getHeader(@PathVariable("fileName") String fileName, HttpServletResponse response) {
//        // 服务器存放路径
//        fileName = uploadPath + "/" + fileName;
//        // 文件后缀
//        String suffix = fileName.substring(fileName.lastIndexOf("."));
//        // 响应图片
//        response.setContentType("image/" + suffix);
//        try (
//                FileInputStream fis = new FileInputStream(fileName);
//                OutputStream os = response.getOutputStream();
//        ) {
//            byte[] buffer = new byte[1024];
//            int b = 0;
//            while ((b = fis.read(buffer)) != -1) {
//                os.write(buffer, 0, b);
//            }
//        } catch (IOException e) {
//            logger.error("读取头像失败: " + e.getMessage());
//        }
//    }

    // 个人主页
    @RequestMapping(path = "/profile/{userId}", method = RequestMethod.GET)
    public String getProfilePage(@PathVariable("userId") int userId, Model model) {
        User user = userService.findUserById(userId);
        if (user == null) {
            throw new RuntimeException("该用户不存在!");
        }

//        // 用户
//        model.addAttribute("user", user);
//        // 点赞数量
//        int likeCount = likeService.findUserLikeCount(userId);
//        model.addAttribute("likeCount", likeCount);
//
//        // 关注数量
//        long followeeCount = followService.findFolloweeCount(userId, ENTITY_TYPE_USER);
//        model.addAttribute("followeeCount", followeeCount);
//        // 粉丝数量
//        long followerCount = followService.findFollowerCount(ENTITY_TYPE_USER, userId);
//        model.addAttribute("followerCount", followerCount);
//        // 是否已关注
//        boolean hasFollowed = false;
//        if (hostHolder.getUser() != null) {
//            hasFollowed = followService.hasFollowed(hostHolder.getUser().getId(), ENTITY_TYPE_USER, userId);
//        }
//        model.addAttribute("hasFollowed", hasFollowed);

        return "/site/profile";
    }

}
