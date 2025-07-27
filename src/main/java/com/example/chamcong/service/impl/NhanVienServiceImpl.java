package com.example.chamcong.service.impl;

import com.example.chamcong.entity.NhanVien;
import com.example.chamcong.entity.User;
import com.example.chamcong.repository.NhanVienRepository;
import com.example.chamcong.repository.UserRepository;
import com.example.chamcong.service.NhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NhanVienServiceImpl extends NhanVienService {

    @Autowired
    private NhanVienRepository nhanVienRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<NhanVien> findAll() {
        return nhanVienRepository.findAll();
    }

    @Override
    public NhanVien findById(Long id) {
        return nhanVienRepository.findById(id).orElse(null);
    }

    @Override
    public NhanVien save(NhanVien nhanVien) {
        return nhanVienRepository.save(nhanVien);
    }

    @Override
    public void deleteById(Long id) {
        nhanVienRepository.deleteById(id);
    }

    @Override
    public NhanVien findByUserUsername(String username) {
        return nhanVienRepository.findByUserUsername(username).orElse(null);
    }


    @Override
    public boolean linkUserToNhanVien(NhanVien nhanVien, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng: " + username));
        nhanVien.setUser(user);
        return false;
    }
}
