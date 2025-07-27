package com.example.chamcong.security;

import com.example.chamcong.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Nếu bạn có role thì thêm vào đây. Giả sử bạn để trống danh sách quyền
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return user.getPassword();  // Lấy mật khẩu từ entity
    }

    @Override
    public String getUsername() {
        return user.getUsername();  // Lấy username từ entity
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;  // Có thể điều chỉnh theo logic của bạn
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;  // Có thể điều chỉnh theo logic của bạn
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // Có thể điều chỉnh theo logic của bạn
    }

    @Override
    public boolean isEnabled() {
        return true;  // Có thể điều chỉnh theo logic của bạn
    }

    public User getUser() {
        return user;
    }
}
