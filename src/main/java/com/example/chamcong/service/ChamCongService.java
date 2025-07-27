package com.example.chamcong.service;

import com.example.chamcong.entity.ChamCong;
import com.example.chamcong.repository.ChamCongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChamCongService {
    @Autowired
    private ChamCongRepository chamCongRepository;

    public List<ChamCong> findAll() {
        return chamCongRepository.findAll();
    }

    public List<ChamCong> findByNhanVienId(Long nhanVienId) {
        return chamCongRepository.findByNhanVienId(nhanVienId);
    }

    public ChamCong save(ChamCong chamCong) {
        return chamCongRepository.save(chamCong);
    }

    public ChamCong findById(Long id) {
        return chamCongRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        chamCongRepository.deleteById(id);
    }
}
