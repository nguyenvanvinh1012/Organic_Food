package com.vanvinh.book_store.services;

import com.vanvinh.book_store.entity.Category;
import com.vanvinh.book_store.entity.Comment;
import com.vanvinh.book_store.entity.Product;
import com.vanvinh.book_store.entity.User;
import com.vanvinh.book_store.repository.ICategoryRepository;
import com.vanvinh.book_store.repository.ICommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    private ICommentRepository commentRepository;
    public List<Comment> getAllComments(){
        return commentRepository.findAll();
    }
    public Optional<Comment> getCommentById(Long id){
        return commentRepository.findById(id);
    }
    public void addComment(User user, Product product, String text, int rate){
        Date date = new Date();
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setProduct(product);
        comment.setComment_text(text);
        comment.setRating_value(rate);
        comment.setTimestamp(date);
        commentRepository.save(comment);
}
    public void deleteCommentById(Long id){
        commentRepository.deleteById(id);
    }
}
