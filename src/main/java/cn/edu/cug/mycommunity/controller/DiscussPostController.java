package cn.edu.cug.mycommunity.controller;

import cn.edu.cug.mycommunity.entity.DiscussPost;
import cn.edu.cug.mycommunity.entity.User;
import cn.edu.cug.mycommunity.service.DiscussPostService;
import cn.edu.cug.mycommunity.service.UserService;
import cn.edu.cug.mycommunity.util.CommunityUtil;
import cn.edu.cug.mycommunity.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
@RequestMapping(path = "/discuss")
public class DiscussPostController {
    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private UserService userService;
    /**
     * 添加文章
     * @param title
     * @param content
     * @return
     */
    @RequestMapping(path = "/add" ,method = RequestMethod.POST)
    @ResponseBody
    public String addDiscussPost(String title,String content){
        //获取当前登录对象
        User user = hostHolder.getUser();
        if(user == null){
            return CommunityUtil.getJSONString(403,"还未登陆");
        }
        DiscussPost post = new DiscussPost();
        post.setTitle(title);
        post.setContent(content);
        post.setUserId(user.getId());
        post.setCreateTime(new Date());
        discussPostService.addDiscussPost(post);

        return CommunityUtil.getJSONString(0,"发布成功!");
    }

    @RequestMapping(path = "/detail/{discussPostId}",method = RequestMethod.GET)
    public String getDiscussPost(@PathVariable("discussPostId") int discussPostId, Model model){
        DiscussPost post = discussPostService.findDiscussPostById(discussPostId);
        model.addAttribute("post",post);
        //查询作者
        User user = userService.findUserById(post.getUserId());
        model.addAttribute("user",user);
        return "/site/discuss-detail";
    }

}
