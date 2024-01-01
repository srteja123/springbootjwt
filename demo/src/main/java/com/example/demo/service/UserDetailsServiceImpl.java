package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.UserInfo;
import com.example.demo.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  UserRepository userRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException {
    UserInfo user = userRepository.findByEmailId(emailId);

     // Assuming UserDetailsImpl implements UserDetails
        UserDetails userDetails = UserDetailsImpl.build(user);

        // Cast UserDetailsImpl to UserDetails
        return (UserDetails) userDetails;
  }
}
