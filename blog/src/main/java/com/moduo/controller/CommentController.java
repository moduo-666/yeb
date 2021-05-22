package com.moduo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.moduo.pojo.Comment;
import com.moduo.pojo.User;
import com.moduo.service.IBlogService;
import com.moduo.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Wu zicong
 * @since 2021-04-29
 */
@Controller
public class CommentController {
    @Autowired
    private ICommentService commentService;
    @Autowired
    private IBlogService blogService;
    @Value("${comment.avatar}")
    private String avatar;
    @GetMapping("/comments/{blogId}")
    public String comment(@PathVariable Long blogId, Model model){
        List<Comment> comments = commentService.listCommentByBlogId(blogId);
        model.addAttribute("comments",comments);
        return "blog :: commentList";
    }
    @PostMapping("/comments")
    public String post(Comment comment , HttpSession session){
        Long blogId = comment.getBlog().getId();
        comment.setBlog(blogService.getById(blogId));
        User user = (User)session.getAttribute("user");
        if(null!=user){
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
//            comment.setNickname(user.getNickname());
        }else{
            comment.setAdminComment(false);
            comment.setAvatar(avatar);
        }
        comment.setBlogId(blogId);
        commentService.saveComment(comment);
        return "redirect:/comments/" + blogId;
    }

}
