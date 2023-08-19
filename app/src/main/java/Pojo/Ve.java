package Pojo;

import java.time.LocalTime;

public class Ve {
        private int id;
        private int soGhe;
        private float giaVe;
        private String ngayDatVe;
        private String gioDatVe;
        private int idChuyenXe;
        private int idKhachHang;
    private String diaDiemDi;
    private float giaNiemYet;
    private int loaiXe;
    private String diaDiemDen;
    private LocalTime gioXuatPhat;

    public Ve(int id, int soGhe, float giaVe, String ngayDatVe, String gioDatVe, int idChuyenXe, int idKhachHang) {
            this.id = id;
            this.soGhe = soGhe;
            this.giaVe = giaVe;
            this.ngayDatVe = ngayDatVe;
            this.gioDatVe = gioDatVe;
            this.idChuyenXe = idChuyenXe;
            this.idKhachHang = idKhachHang;
        }

    public static int getDiaDiemDen() {
        return 0;
    }

    public int getId() {
            return id;
        }

        public int getSoGhe() {
            return soGhe;
        }

        public float getGiaVe() {
            return giaVe;
        }

        public String getNgayDatVe() {
            return ngayDatVe;
        }

        public String getGioDatVe() {
            return gioDatVe;
        }

        public int getIdChuyenXe() {
            return idChuyenXe;
        }

        public int getIdKhachHang() {
            return idKhachHang;
        }

        @Override
        public String toString() {
            return "ID: " + id +
                    "\nSố ghế: " + soGhe +
                    "\nGiá vé: " + giaVe +
                    "\nNgày đặt vé: " + ngayDatVe +
                    "\nGiờ đặt vé: " + gioDatVe +
                    "\nID chuyến xe: " + idChuyenXe +
                    "\nID khách hàng: " + idKhachHang;
        }

    public void setDiaDiemDi(String diaDiemDi) {
        this.diaDiemDi = diaDiemDi;
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

    public LocalTime getGioXuatPhat() {
        return gioXuatPhat;
    }

    public void setGioXuatPhat(LocalTime gioXuatPhat) {
        this.gioXuatPhat = gioXuatPhat;
    }

    public String getDiaDiemDi() {
        return diaDiemDi;
    }}

