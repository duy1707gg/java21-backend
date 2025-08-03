package com.example.chamcong.repository;

import com.example.chamcong.entity.ChamCong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChamCongRepository extends JpaRepository<ChamCong, Long> {
    List<ChamCong> findByNhanVienId(Long nhanVienId);
    List<ChamCong> findByNhanVienUserId(Long userId);

    @Query("SELECT c FROM ChamCong c WHERE c.user.id = :userId")
    List<ChamCong> findByUserId(Long userId);
}
