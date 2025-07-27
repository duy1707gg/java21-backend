package com.example.chamcong.service;

import com.example.chamcong.entity.CongViec;
import com.example.chamcong.repository.CongViecRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CongViecService {
    @Autowired
    private CongViecRepository congViecRepository;

    public List<CongViec> findAll() {
        return congViecRepository.findAll();
    }

    public CongViec save(CongViec congViec) {
        return congViecRepository.save(congViec);
    }

    public CongViec findById(Long id) {
        return congViecRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        congViecRepository.deleteById(id);
    }
}
