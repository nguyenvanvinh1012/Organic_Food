package com.vanvinh.book_store.services;

import com.vanvinh.book_store.entity.CustomUserDetail;
import com.vanvinh.book_store.entity.User;
import com.vanvinh.book_store.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private IUserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null)
            throw new UsernameNotFoundException("User not found");
        String fullName = user.getFull_name();
        return new CustomUserDetail(user, userRepository, fullName);
    }
}
