package Pojo;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatVeXeKhach extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "datvexekhach.db";
    private static final int DATABASE_VERSION = 1;
    public DatVeXeKhach(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUser = "CREATE TABLE User (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "user_name TEXT," +
                "password TEXT," +
                "user_role INTEGER," +
                "name TEXT," +
                "phone TEXT," +
                "ngay_sinh TEXT" +
                ")";
        db.execSQL(createUser);

        String createTuyenDuong = "CREATE TABLE TuyenDuong (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "ten_tuyen_duong TEXT," +
                "dia_diem_di TEXT," +
                "dia_diem_den TEXT," +
                "loai_xe INT," +
                "gia_niem_yet REAL," +
                "gio_xuat_phat TEXT" +
                ")";
        db.execSQL(createTuyenDuong);

        String createChuyenXe = "CREATE TABLE ChuyenXe(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ngay_di TEXT," +
                "ma_tuyen_duong INTEGER," +
                "FOREIGN KEY (ma_tuyen_duong) REFERENCES TuyenDuong(id)" +
                ")";
        db.execSQL(createChuyenXe);


        String createVe = "CREATE TABLE Ve (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "so_ghe INTEGER," +
                "gia_ve REAL," +
                "ngay_dat_ve TEXT," +
                "gio_dat_ve TEXT," +
                "id_chuyenxe INTEGER," +
                "id_khachhang INTEGER," +
                "FOREIGN KEY (id_chuyenxe) REFERENCES ChuyenXe(id)," +
                "FOREIGN KEY (id_khachhang) REFERENCES User(id)" +
                ")";
        db.execSQL(createVe);

        String createDanhGia = "CREATE TABLE DanhGia (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "rating INTEGER," +
                "id_khachhang INTEGER, " +
                "id_chuyenxe INTEGER," +
                "FOREIGN KEY (id_khachhang) REFERENCES User(id)," +
                "FOREIGN KEY (id_chuyenxe) REFERENCES ChuyenXe(id)" +
                ")";
        db.execSQL(createDanhGia);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

