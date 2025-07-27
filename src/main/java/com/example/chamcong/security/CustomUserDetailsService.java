package com.example.chamcong.security;

import com.example.chamcong.entity.NhanVien;
import com.example.chamcong.entity.User;
import com.example.chamcong.repository.NhanVienRepository;
import com.example.chamcong.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final NhanVienRepository nhanVienRepository;

    public CustomUserDetailsService(NhanVienRepository nhanVienRepository) {
        this.nhanVienRepository = nhanVienRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        NhanVien nhanVien = nhanVienRepository.findByUser_Username(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        User user = nhanVien.getUser();
        if (user == null) {
            throw new UsernameNotFoundException("User entity not found for NhanVien: " + username);
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),  // lấy username từ User
                user.getPassword(),  // lấy password từ User
                new ArrayList<>()    // quyền (roles) nếu có, hoặc lấy từ user.getAuthorities()
        );
    }
}

