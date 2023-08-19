package Service;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import Fragment.FragmentLichSuVe;
import Fragment.FragmentVeHienTai;
import Pojo.DatVeXeKhach;
import Pojo.TuyenDuong;
import Pojo.Ve;

public class XemVeService extends RecyclerView.Adapter {
    private Context context;
    private DatVeXeKhach db;
    private char[] id_chuyenxe;

    public XemVeService(Context context) {
        this.context = context;
        db = new DatVeXeKhach(context);
    }

    public XemVeService(List<TuyenDuong> dsXemVe) {
    }

    public static void setOnItemClickListener(FragmentLichSuVe fragmentLichSuVe) {
    }

    public static List<TuyenDuong> getListLichSuVe() {
        return null;
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



    public void set(FragmentVeHienTai fragmentVeHienTai) {
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void deleteVeByIDTXemVe(int id) {
        SQLiteDatabase database = db.getWritableDatabase();
        database.delete("Ve", "id_chuyenxe = ?",
                new String[]{String.valueOf(id_chuyenxe)});
        database.close();;

    }

    public void deleteDanhGiaByIDXemVe(int id) {
        SQLiteDatabase database = db.getWritableDatabase();
        database.delete("DanhGia", "id_chuyenxe = ?",
                new String[]{String.valueOf(id_chuyenxe)});
        database.close();
    }

    public void deleteXemVe(int id) {

    }

    public void deleteChuyenXeByIDXemVe(int id) {
        SQLiteDatabase database = db.getWritableDatabase();
        database.delete("ChuyenXe", "id = ?",
                new String[]{String.valueOf(id)});
        database.close();
    }

    public void set(FragmentLichSuVe fragmentLichSuVe) {
    }

    public interface OnItemClickListener {
        void onItemClick(Ve ve);
    }
}













