package Pojo;

import java.time.LocalTime;

public class TuyenDuong {
    private int id;
    private int loaiXe;
    private float giaNiemYet;
    private String tenTuyenDuong;
    private String diaDiemDi;
    private String diaDiemDen;
    private LocalTime gioXuatPhat;

    public TuyenDuong(int id, String tenTuyenDuong, String diaDiemDi, String diaDiemDen,
                      int loaiXe, float giaNiemYet, LocalTime gioXuatPhat){
        this.id = id;
        this.tenTuyenDuong = tenTuyenDuong;
        this.diaDiemDi = diaDiemDi;
        this.diaDiemDen = diaDiemDen;
        this.loaiXe = loaiXe;
        this.giaNiemYet = giaNiemYet;
        this.gioXuatPhat = gioXuatPhat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLoaiXe() {
        return loaiXe;
    }

    public void setLoaiXe(int loaiXe) {
        this.loaiXe = loaiXe;
    }

    public float getGiaNiemYet() {
        return giaNiemYet;
    }

    public void setGiaNiemYet(float giaNiemYet) {
        this.giaNiemYet = giaNiemYet;
    }

    public String getTenTuyenDuong() {
        return tenTuyenDuong;
    }

    public void setTenTuyenDuong(String tenTuyenDuong) {
        this.tenTuyenDuong = tenTuyenDuong;
    }

    public String getDiaDiemDi() {
        return diaDiemDi;
    }

    public void setDiaDiemDi(String diaDiemDi) {
        this.diaDiemDi = diaDiemDi;
    }

    public String getDiaDiemDen() {
        return diaDiemDen;
    }

    public void setDiaDiemDen(String diaDiemDen) {
        this.diaDiemDen = diaDiemDen;
    }

    public LocalTime getGioXuatPhat() {
        return gioXuatPhat;
    }

    public void setGioXuatPhat(LocalTime gioXuatPhat) {
        this.gioXuatPhat = gioXuatPhat;
    }
}
