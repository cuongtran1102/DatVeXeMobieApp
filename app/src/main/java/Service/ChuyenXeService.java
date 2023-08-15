package Service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import Pojo.ChuyenXe;
import Pojo.DatVeXeKhach;
import Pojo.TuyenDuong;

public class ChuyenXeService {
    private Context context;
    private DatVeXeKhach db;
    public ChuyenXeService(Context context){
        this.context = context;
        db = new DatVeXeKhach(context);
    }
    public void insertTuyenDuong(String tenTuyenDuong, String diaDiemDi, String diaDiemDen
    , int loaiXe, float giaNiemYet, String gioXuatPhat){
        SQLiteDatabase database = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ten_tuyen_duong", tenTuyenDuong);
        values.put("dia_diem_di", diaDiemDi);
        values.put("dia_diem_den", diaDiemDen);
        values.put("loai_xe", loaiXe);
        values.put("gia_niem_yet", giaNiemYet);
        values.put("gio_xuat_phat", gioXuatPhat);
        database.insert("TuyenDuong", null, values);
        database.close();
    }
    public boolean checkTuyenDuong(String benDi, String benDen, int loaiXe){
        SQLiteDatabase database = db.getReadableDatabase();
        String sql = "select dia_diem_di, dia_diem_den, " +
                "loai_xe from TuyenDuong where dia_diem_di = ?" +
                "and dia_diem_den = ? and loai_xe = ?";
        Cursor cursor = database.rawQuery(sql, new String[]{benDi, benDen,
                String.valueOf(loaiXe)});
        cursor.moveToPosition(-1);
        while(cursor.moveToNext()){
            if(cursor.getString(0).equals(benDi) && cursor.getString(1).equals(benDen)
            && cursor.getInt(2) == loaiXe){
                return false;
            }
        }
        return true;
    }

    public void insertChuyenXe(String ngayDi, int maTuyenDuong){
        SQLiteDatabase database = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ngay_di", ngayDi);
        values.put("ma_tuyen_duong", maTuyenDuong);
        database.insert("ChuyenXe", null, values);
        database.close();
    }
    public List<ChuyenXe> getListChuyenXe(String diaDiemDi, String diaDiemDen,
                                          String ngayDi){
        SQLiteDatabase database = db.getReadableDatabase();
        List<ChuyenXe> listChuyenXe = new ArrayList<>();
        String sql = "select t.id, c.id, t.ten_tuyen_duong," +
                " t.dia_diem_di, t.dia_diem_den, c.ngay_di, t.gio_xuat_phat," +
                " t.gia_niem_yet, t.loai_xe from TuyenDuong t, ChuyenXe c" +
                " where t.id = c.ma_tuyen_duong and t.dia_diem_di = ? " +
                "and t.dia_diem_den = ? and c.ngay_di = ?";
        Cursor cursor = database.rawQuery(sql, new String[]{diaDiemDi, diaDiemDen, ngayDi});
        cursor.moveToPosition(-1);
        while (cursor.moveToNext()){
            int idTuyenDuong = cursor.getInt(0);
            int idChuyenXe = cursor.getInt(1);
            String tenChuyenXe = cursor.getString(2);
            String noiDi = cursor.getString(3);
            String noiDen = cursor.getString(4);
            String ngayKhoiHanh = cursor.getString(5);
            String gioXuatPhat = cursor.getString(6);
            float giaNiemYet = cursor.getFloat(7);
            int loaiXe = cursor.getInt(8);
            ChuyenXe c = new ChuyenXe(idTuyenDuong, idChuyenXe, tenChuyenXe, noiDen,
                    noiDi, LocalDate.parse(ngayKhoiHanh, DateTimeFormatter.ISO_LOCAL_DATE),
                    LocalTime.parse(gioXuatPhat, DateTimeFormatter.ISO_LOCAL_TIME),
                    giaNiemYet, loaiXe);
            listChuyenXe.add(c);
        }
        cursor.close();
        database.close();
        return listChuyenXe;
    }
    public List<ChuyenXe> getListChuyenXe(){
        SQLiteDatabase database = db.getReadableDatabase();
        List<ChuyenXe> listChuyenXe = new ArrayList<>();
        String sql = "select t.id, c.id, t.ten_tuyen_duong," +
                "t.dia_diem_di, t.dia_diem_den, c.ngay_di, t.gio_xuat_phat," +
                 "t.gia_niem_yet, t.loai_xe from TuyenDuong t, ChuyenXe c" +
                " where t.id = c.ma_tuyen_duong";
        Cursor cursor = database.rawQuery(sql, null);
        cursor.moveToPosition(-1);
        while (cursor.moveToNext()){
            int idTuyenDuong = cursor.getInt(0);
            int idChuyenXe = cursor.getInt(1);
            String tenChuyenXe = cursor.getString(2);
            String noiDi = cursor.getString(3);
            String noiDen = cursor.getString(4);
            String ngayKhoiHanh = cursor.getString(5);
            String gioXuatPhat = cursor.getString(6);
            float giaNiemYet = cursor.getFloat(7);
            int loaiXe = cursor.getInt(8);
            ChuyenXe c = new ChuyenXe(idTuyenDuong, idChuyenXe, tenChuyenXe, noiDen,
                    noiDi, LocalDate.parse(ngayKhoiHanh, DateTimeFormatter.ISO_LOCAL_DATE),
                    LocalTime.parse(gioXuatPhat, DateTimeFormatter.ISO_LOCAL_TIME),
                    giaNiemYet, loaiXe);
            listChuyenXe.add(c);
        }
        cursor.close();
        database.close();
        return listChuyenXe;
    }
    public void deleteChuyenXeByIDTuyenDuong(int idTuyenDuong){
        SQLiteDatabase database = db.getReadableDatabase();
        String sql = "select id from ChuyenXe where ma_tuyen_duong = ?";
        Cursor cursor = database.rawQuery(sql,
                new String[]{String.valueOf(idTuyenDuong)});
        cursor.moveToPosition(-1);
        while(cursor.moveToNext()){
            deleteChuyenXe(cursor.getInt(0));
        }
        cursor.close();
        database.close();
    }
    public void deleteVeByIDTuyenDuong(int idTuyenDuong){
        SQLiteDatabase database = db.getReadableDatabase();
        String sql = "select id from ChuyenXe where ma_tuyen_duong = ?";
        Cursor cursor = database.rawQuery(sql,
                new String[]{String.valueOf(idTuyenDuong)});
        cursor.moveToPosition(-1);
        while(cursor.moveToNext()){
            deleteVe(cursor.getInt(0));
        }
        cursor.close();
        database.close();
    }

    public void deleteDanhGiaByIDTuyenDuong(int idTuyenDuong){
        SQLiteDatabase database = db.getReadableDatabase();
        String sql = "select id from ChuyenXe where ma_tuyen_duong = ?";
        Cursor cursor = database.rawQuery(sql,
                new String[]{String.valueOf(idTuyenDuong)});
        cursor.moveToPosition(-1);
        while(cursor.moveToNext()){
            deleteDanhGia(cursor.getInt(0));
        }
        cursor.close();
        database.close();
    }
    public List<TuyenDuong> getListTuyenDuong(){
        SQLiteDatabase database = db.getReadableDatabase();
        List<TuyenDuong> dsTuyenDuong = new ArrayList<>();
        String sql = "select * from TuyenDuong";
        Cursor cursor = database.rawQuery(sql, null);
        cursor.moveToPosition(-1);
        while(cursor.moveToNext()){
            int id = cursor.getInt(0);
            String tenTuyenDuong = cursor.getString(1);
            String diaDiemDi = cursor.getString(2);
            String diaDiemDen = cursor.getString(3);
            int loaiXe = cursor.getInt(4);
            float giaNiemYet = cursor.getFloat(5);
            LocalTime gioXuatPhat = LocalTime.parse(cursor.getString(6),
                    DateTimeFormatter.ISO_LOCAL_TIME);
            TuyenDuong tuyenDuong = new TuyenDuong(id, tenTuyenDuong, diaDiemDi, diaDiemDen,
                    loaiXe, giaNiemYet, gioXuatPhat);
            dsTuyenDuong.add(tuyenDuong);
        }
        cursor.close();
        database.close();
        return dsTuyenDuong;
    }
    public List<TuyenDuong> getListTuyenDuong(String benDi, String benDen){
        SQLiteDatabase database = db.getReadableDatabase();
        List<TuyenDuong> dsTuyenDuong = new ArrayList<>();
        String sql = "select * from TuyenDuong where dia_diem_di = ? and dia_diem_den = ?";
        Cursor cursor = database.rawQuery(sql, new String[]{benDi, benDen});
        cursor.moveToPosition(-1);
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String tenTuyenDuong = cursor.getString(1);
            String diaDiemDi = cursor.getString(2);
            String diaDiemDen = cursor.getString(3);
            int loaiXe = cursor.getInt(4);
            float giaNiemYet = cursor.getFloat(5);
            LocalTime gioXuatPhat = LocalTime.parse(cursor.getString(6),
                    DateTimeFormatter.ISO_LOCAL_TIME);
            TuyenDuong tuyenDuong = new TuyenDuong(id, tenTuyenDuong, diaDiemDi, diaDiemDen,
                    loaiXe, giaNiemYet, gioXuatPhat);
            dsTuyenDuong.add(tuyenDuong);
        }
        cursor.close();
        database.close();
        return dsTuyenDuong;
    }
    public boolean checkChuyenXe(String ngayKhoiHanh, int idTuyenDuong){
        SQLiteDatabase database = db.getReadableDatabase();
        String sql = "select * from ChuyenXe where ngay_di = ? " +
                "and ma_tuyen_duong = ?";
        Cursor cursor = database.rawQuery(sql, new String[]{ngayKhoiHanh,
                String.valueOf(idTuyenDuong)});
        cursor.moveToPosition(-1);
        while (cursor.moveToNext()){
            if(cursor.getString(1).equals(ngayKhoiHanh) &&
            cursor.getInt(2) == idTuyenDuong){
                return false;
            }
        }
        return true;
    }
    public void deleteChuyenXe(int id){
        SQLiteDatabase database = db.getWritableDatabase();
        database.delete("ChuyenXe", "id = ?",
                new String[]{String.valueOf(id)});
        database.close();
    }

    public void deleteVe(int id_chuyenxe){
        SQLiteDatabase database = db.getWritableDatabase();
        database.delete("Ve", "id_chuyenxe = ?",
                new String[]{String.valueOf(id_chuyenxe)});
        database.close();;
    }
    public void deleteDanhGia(int id_chuyenxe){
        SQLiteDatabase database = db.getWritableDatabase();
        database.delete("DanhGia", "id_chuyenxe = ?",
                new String[]{String.valueOf(id_chuyenxe)});
        database.close();
    }
    public void deleteTuyenDuong(int id){
        SQLiteDatabase database = db.getWritableDatabase();
        database.delete("TuyenDuong", "id = ?",
                new String[]{String.valueOf(id)});
        database.close();
    }


}
