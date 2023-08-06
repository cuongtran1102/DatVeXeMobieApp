package Pojo;
import java.time.LocalDate;
import java.time.LocalTime;

public class ChuyenXe {
    private int id;
    private int soLuongGhe;
    private int idTuyenDuong;
    private int idChuyenXe;
    private String tenChuyenXe;
    private String benDen;
    private String benDi;
    private LocalDate ngayDi;
    private LocalTime gioXuatPhat;
    private float giaVe;

    public ChuyenXe(){

    }
    public ChuyenXe(int idTuyenDuong, int idChuyenXe, String tenChuyenXe, String benDen, String benDi, LocalDate ngayDi,
                    LocalTime gioXuatPhat, float giaVe, int soLuongGhe){
        this.idTuyenDuong = idTuyenDuong;
        this.idChuyenXe = idChuyenXe;
        this.tenChuyenXe = tenChuyenXe;
        this.benDen = benDen;
        this.benDi = benDi;
        this.ngayDi = ngayDi;
        this.gioXuatPhat = gioXuatPhat;
        this.giaVe = giaVe;
        this.soLuongGhe = soLuongGhe;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSoLuongGhe() {
        return soLuongGhe;
    }

    public void setSoLuongGhe(int soLuongGhe) {
        this.soLuongGhe = soLuongGhe;
    }

    public int getIdTuyenDuong() {
        return idTuyenDuong;
    }

    public void setIdTuyenDuong(int idTuyenDuong) {
        this.idTuyenDuong = idTuyenDuong;
    }

    public String getTenChuyenXe() {
        return tenChuyenXe;
    }

    public void setTenChuyenXe(String tenChuyenXe) {
        this.tenChuyenXe = tenChuyenXe;
    }

    public String getBenDen() {
        return benDen;
    }

    public void setBenDen(String benDen) {
        this.benDen = benDen;
    }

    public String getBenDi() {
        return benDi;
    }

    public void setBenDi(String benDi) {
        this.benDi = benDi;
    }

    public LocalDate getNgayDi() {
        return ngayDi;
    }

    public void setNgayDi(LocalDate ngayDi) {
        this.ngayDi = ngayDi;
    }

    public LocalTime getGioXuatPhat() {
        return gioXuatPhat;
    }

    public void setGioXuatPhat(LocalTime gioXuatPhat) {
        this.gioXuatPhat = gioXuatPhat;
    }

    public float getGiaVe() {
        return giaVe;
    }

    public void setGiaVe(float giaVe) {
        this.giaVe = giaVe;
    }

    public int getIdChuyenXe() {
        return idChuyenXe;
    }

    public void setIdChuyenXe(int idChuyenXe) {
        this.idChuyenXe = idChuyenXe;
    }
}
