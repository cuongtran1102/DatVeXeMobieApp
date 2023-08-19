package Service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import Pojo.DatVeXeKhach;
import Pojo.Ve;

public class XemVeService {
    private Context context;
    private DatVeXeKhach db;

    public XemVeService(Context context) {
        this.context = context;
        db = new DatVeXeKhach(context);
    }

    public List<Ve> xemVeDaDat(int idKhachHang) {
        List<Ve> dsVeDaDat = new ArrayList<>();
        SQLiteDatabase database = db.getReadableDatabase();
        String sql = "SELECT * FROM Ve WHERE id_khachhang = ?";
        Cursor cursor = database.rawQuery(sql, new String[]{String.valueOf(idKhachHang)});

        int id = cursor.getInt(0);
        int soGhe = cursor.getInt(2);
        float giaVe = cursor.getFloat(3);
        String ngayDatVe = cursor.getString(4);
        String gioDatVe = cursor.getString(5);
        int idChuyenXe = cursor.getInt(6);
         idKhachHang = cursor.getInt(7);

        Ve ve = new Ve(id, soGhe, giaVe, ngayDatVe, gioDatVe, idChuyenXe, idKhachHang);
        dsVeDaDat.add(ve);


        cursor.close();
        database.close();

        return dsVeDaDat;

    }
}






