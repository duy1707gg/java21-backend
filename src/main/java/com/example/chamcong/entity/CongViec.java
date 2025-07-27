package com.example.chamcong.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDate;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
public class CongViec {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tenCongViec;

    private String hinhThucChamCong; // NGAY, CA, GIO

    private double tienCongMotDonVi; // tiền công một ngày, ca hoặc giờ tùy hình thức

    private LocalDate ngayBatDau;

    private LocalDate ngayChotLuong;

    public CongViec() {}

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getTenCongViec() { return tenCongViec; }

    public void setTenCongViec(String tenCongViec) { this.tenCongViec = tenCongViec; }

    public String getHinhThucChamCong() { return hinhThucChamCong; }

    public void setHinhThucChamCong(String hinhThucChamCong) { this.hinhThucChamCong = hinhThucChamCong; }

    public double getTienCongMotDonVi() { return tienCongMotDonVi; }

    public void setTienCongMotDonVi(double tienCongMotDonVi) { this.tienCongMotDonVi = tienCongMotDonVi; }

    public LocalDate getNgayBatDau() { return ngayBatDau; }

    public void setNgayBatDau(LocalDate ngayBatDau) { this.ngayBatDau = ngayBatDau; }

    public LocalDate getNgayChotLuong() { return ngayChotLuong; }

    public void setNgayChotLuong(LocalDate ngayChotLuong) { this.ngayChotLuong = ngayChotLuong; }
}
