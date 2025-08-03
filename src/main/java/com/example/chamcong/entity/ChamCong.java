package com.example.chamcong.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDate;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
public class ChamCong {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cong_viec_id", nullable = false)
    private CongViec congViec;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "nhan_vien_id")
    private NhanVien nhanVien; // Có thể null nếu là cá nhân tự chấm công

    private LocalDate ngayCham;

    private String trangThai; // DI_LAM, NGHI_CO_LY_DO, NGHI_KHONG_LY_DO

    private String ghiChu;

    @Column(name = "tien_cong")
    private Double tienCong;

    public ChamCong() {}

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public CongViec getCongViec() { return congViec; }

    public void setCongViec(CongViec congViec) { this.congViec = congViec; }

    public NhanVien getNhanVien() { return nhanVien; }

    public void setNhanVien(NhanVien nhanVien) { this.nhanVien = nhanVien; }

    public LocalDate getNgayCham() { return ngayCham; }

    public void setNgayCham(LocalDate ngayCham) { this.ngayCham = ngayCham; }

    public String getTrangThai() { return trangThai; }

    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }

    public String getGhiChu() { return ghiChu; }

    public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }

    public Double getTienCong() { return tienCong; }
    public void setTienCong(Double tienCong) { this.tienCong = tienCong;}

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
