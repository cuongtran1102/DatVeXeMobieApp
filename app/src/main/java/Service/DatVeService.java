package Service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import Pojo.DatVeXeKhach;

public class DatVeService {
    private Context context;
    private DatVeXeKhach db;
    public DatVeService(Context context){
        this.context = context;
        db = new DatVeXeKhach(context);
    }
    public void luuVe(List<Integer> dsGhe, float giaVe, String ngayDatVe, String gioDatVe,
                      int idChuyenXe, int idKhachHang){
        SQLiteDatabase database = db.getWritableDatabase();
        for(int soGhe : dsGhe){
            ContentValues values = new ContentValues();
            values.put("so_ghe", soGhe);
            values.put("gia_ve", giaVe);
            values.put("ngay_dat_ve", ngayDatVe);
            values.put("gio_dat_ve", gioDatVe);
            values.put("id_chuyenxe", idChuyenXe);
            values.put("id_khachhang", idKhachHang);
            database.insert("Ve", null, values);
        }
        database.close();
    }
    public List<Integer> DSGheDaDatCuaCX(int idChuyenXe){
        List<Integer> dsGhe = new ArrayList<>();
        SQLiteDatabase database = db.getReadableDatabase();
        String sql = "select so_ghe from Ve where id_chuyenxe = ?";
        Cursor cursor = database.rawQuery(sql, new String[]{String.valueOf(idChuyenXe)});
        cursor.moveToPosition(-1);
        while (cursor.moveToNext()){
            int soGhe = cursor.getInt(0);
            dsGhe.add(soGhe);
        }
        cursor.close();
        database.close();
        return dsGhe;
    }
}
