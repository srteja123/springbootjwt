package com.example.demo.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.entity.UserInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserDetailsImpl implements UserDetails {
  private static final long serialVersionUID = 1L;

  private Long id;

  private String email;

  @JsonIgnore
  private String password;
 
  private Long loginCounter;

  private String accountStatus;

  private Collection<? extends GrantedAuthority> authorities;

  

  public UserDetailsImpl(Long id, String email, String password, Long loginCounter, String accountStatus,
      Collection<? extends GrantedAuthority> authorities) {
    this.id = id;
    this.email = email;
    this.password = password;
    this.loginCounter = loginCounter;
    this.accountStatus = accountStatus;
    this.authorities = authorities;
  }

  public static UserDetailsImpl build(UserInfo user) {
    GrantedAuthority grantAuth =new SimpleGrantedAuthority(user.getUserType());
    List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
    authorities.add(grantAuth);

    return new UserDetailsImpl(
        user.getUserId(),  
        user.getEmailId(),
        user.getPassword(), 
        user.getLoginCounter(),
        user.getAccountStatus(),
        authorities);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  public Long getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  public Long getLoginCounter() {
    return loginCounter;
  }

  public void setLoginCounter(Long loginCounter) {
    this.loginCounter = loginCounter;
  }

  @Override
  public String getPassword() {
    return password;
  }


  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    UserDetailsImpl user = (UserDetailsImpl) o;
    return Objects.equals(id, user.id);
  }

@Override
public String getUsername() {
    // TODO Auto-generated method stub
    return email;
}

public String getAccountStatus() {
  return accountStatus;
}

public void setAccountStatus(String accountStatus) {
  this.accountStatus = accountStatus;
}

}
