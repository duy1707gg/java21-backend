package com.example.chamcong.repository;

import java.util.Optional;

import com.example.chamcong.entity.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NhanVienRepository extends JpaRepository<NhanVien, Long> {

    Optional<NhanVien> findByUserId(Long userId);
    Optional<NhanVien> findByUser_Username(String username);
    Optional<NhanVien> findByUserUsername(String username);

}
