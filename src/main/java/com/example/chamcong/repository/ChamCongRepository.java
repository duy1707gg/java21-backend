package com.example.chamcong.repository;

import com.example.chamcong.entity.ChamCong;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChamCongRepository extends JpaRepository<ChamCong, Long> {
    List<ChamCong> findByNhanVienId(Long nhanVienId);
    List<ChamCong> findByNhanVienUserId(Long userId);
}
