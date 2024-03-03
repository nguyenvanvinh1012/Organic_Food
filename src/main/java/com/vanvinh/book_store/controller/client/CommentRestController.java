package com.vanvinh.book_store.controller.client;

import com.vanvinh.book_store.daos.CommentDTO;
import com.vanvinh.book_store.entity.Blog;
import com.vanvinh.book_store.entity.Comment;
import com.vanvinh.book_store.entity.User;
import com.vanvinh.book_store.services.BlogService;
import com.vanvinh.book_store.services.CommentService;
import com.vanvinh.book_store.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class CommentRestController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    @GetMapping("/getComments")
    public List<CommentDTO> getCommentFromBlogID(@RequestParam("id") Long id){
        List<Comment> comments = commentService.getAllCommentsByBlogId(id);
        List<CommentDTO> commentDTOS = convertToDTO(comments);
        return commentDTOS;
    }
    @GetMapping("/postComment")
    public void postComment(@RequestParam("commentText") String commentText, @RequestParam("userID") Long userID,
                            @RequestParam("blogID") Long blogID){
        User user = userService.getUserByUserID(userID);
        Blog blog = blogService.getBlogById(blogID);
        commentService.addCommentForBlog(user,blog,commentText);
    }
    @GetMapping("/getTotalComment")
    public int getTotalComment(@RequestParam("id") Long blogID){
        Blog blog = blogService.getBlogById(blogID);
        return blog.getTotalComments();
    }
    private List<CommentDTO> convertToDTO(List<Comment> comments) {
        List<CommentDTO> commentDTOS = new ArrayList<>();
        for (Comment comment : comments) {
            CommentDTO commentDTO = new CommentDTO();
            commentDTO.setId(comment.getId());
            commentDTO.setComment_text(comment.getComment_text());
            commentDTO.setDate(comment.getTimestamp());
            commentDTO.setUser_img(comment.getUser().getImg());
            commentDTO.setUser_name(comment.getUser().getFull_name());
            commentDTOS.add(commentDTO);
        }
        return commentDTOS;
    }
}
