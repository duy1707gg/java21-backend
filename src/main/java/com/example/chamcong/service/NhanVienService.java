package com.example.chamcong.service;

import com.example.chamcong.entity.NhanVien;
import com.example.chamcong.entity.User;
import com.example.chamcong.repository.NhanVienRepository;
import com.example.chamcong.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NhanVienService {

    @Autowired
    private NhanVienRepository nhanVienRepository;

    @Autowired
    private UserRepository userRepository;

    public List<NhanVien> findAll() {
        return nhanVienRepository.findAll();
    }

    public NhanVien save(NhanVien nhanVien) {
        return nhanVienRepository.save(nhanVien);
    }

    public NhanVien findById(Long id) {
        return nhanVienRepository.findById(id).orElse(null);
    }

    public NhanVien findByUsername(String username) {
        return nhanVienRepository.findByUser_Username(username).orElse(null);
    }

    public void deleteById(Long id) {
        nhanVienRepository.deleteById(id);
    }

    public NhanVien findByUserId(Long userId) {
        return nhanVienRepository.findByUserId(userId).orElse(null);
    }

    public NhanVien findByUserUsername(String username) {
        return nhanVienRepository.findByUser_Username(username).orElse(null);
    }

    public boolean linkUserToNhanVien(NhanVien nhanVien, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng: " + username));
        nhanVien.setUser(user);
        nhanVienRepository.save(nhanVien);
        return true; // ✅ trả về true khi thành công
    }
}
