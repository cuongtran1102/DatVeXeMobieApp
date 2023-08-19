package Pojo;

public class Ve {
        private int id;
        private int soGhe;
        private float giaVe;
        private String ngayDatVe;
        private String gioDatVe;
        private int idChuyenXe;
        private int idKhachHang;

        public Ve(int id, int soGhe, float giaVe, String ngayDatVe, String gioDatVe, int idChuyenXe, int idKhachHang) {
            this.id = id;
            this.soGhe = soGhe;
            this.giaVe = giaVe;
            this.ngayDatVe = ngayDatVe;
            this.gioDatVe = gioDatVe;
            this.idChuyenXe = idChuyenXe;
            this.idKhachHang = idKhachHang;
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
    }


