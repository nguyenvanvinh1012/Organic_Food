package com.vanvinh.book_store.controller.client;

import com.vanvinh.book_store.daos.CommentDTO;
import com.vanvinh.book_store.entity.Comment;
import com.vanvinh.book_store.entity.Product;
import com.vanvinh.book_store.entity.User;
import com.vanvinh.book_store.services.CommentService;
import com.vanvinh.book_store.services.ProductService;
import com.vanvinh.book_store.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@CrossOrigin(origins = "*")
public class CommentRestController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
//    @GetMapping("/comment")
//    public CommentDTO writeComment(@RequestParam("text") String text, @RequestParam("rating") int rating,
//                                @RequestParam("productID") long productID){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName();
//        User user = userService.getUserbyUserName(username);
//        Product product = productService.getProductById(productID);
//        CommentDTO commentDTO = convertToDTO(commentService.addComment(user, product,text,rating));
//        return commentDTO;
//    }
    public CommentDTO convertToDTO(Comment comment){
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setComment_text(comment.getComment_text());
        commentDTO.setRating(comment.getRating_value());
        commentDTO.setDate(comment.getTimestamp());
        commentDTO.setUser_name(comment.getUser().getFull_name());
        commentDTO.setUser_img(comment.getUser().getImg());
        return  commentDTO;
    }
}
