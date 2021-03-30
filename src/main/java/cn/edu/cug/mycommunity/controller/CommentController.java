package cn.edu.cug.mycommunity.controller;

import cn.edu.cug.mycommunity.entity.Comment;
import cn.edu.cug.mycommunity.service.CommentService;
import cn.edu.cug.mycommunity.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

@Controller
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private HostHolder hostHolder;

    @RequestMapping(path = "/add/{discussId}",method = RequestMethod.POST)
    public String addComment(@PathVariable("discussId")int discussId, Comment comment){
        comment.setUserId(hostHolder.getUser().getId());
        comment.setStatus(0);
        comment.setCreateTime(new Date());

        commentService.addComment(comment);
        return "redirect:/discuss/detail/" + discussId;
    }
}
