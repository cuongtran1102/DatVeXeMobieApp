package Service;

import android.content.Context;
import android.database.Cursor;
import android.content.ContentValues;

import Pojo.DatVeXeKhach;
import android.database.sqlite.SQLiteDatabase;

public class UserService {
    private Context context;
    private DatVeXeKhach db;
    public UserService(){};
    public UserService(Context context){
        this.context = context;
        db = new DatVeXeKhach(context);
    }
    public boolean checkLogin(String userName, String passWord){
        SQLiteDatabase database = db.getReadableDatabase();
        String sql = "select * from User where user_name = ? and password = ?";
        Cursor cursor = database.rawQuery(sql, new String[]{userName, passWord});
        boolean isExistAccount = cursor.moveToFirst();
        cursor.close();
        return isExistAccount;
    }
    public boolean checkUserRole(String userName){
        SQLiteDatabase database = db.getReadableDatabase();
        String sql = "select user_role from User where user_name = ?";
        Cursor cursor = database.rawQuery(sql, new String[]{userName});
        cursor.moveToPosition(-1);
        while (cursor.moveToNext()){
            if(cursor.getInt(0) == 0)
                return true;
        }
        return false;
    }

    public boolean checkUserName(String userName){
        SQLiteDatabase database = db.getReadableDatabase();
        String sql = "select * from User where user_name = ?";
        Cursor cursor = database.rawQuery(sql, new String[]{userName});
        cursor.moveToPosition(-1);
        while(cursor.moveToNext()){
            if(cursor.getString(1).equals(userName))
                return false;
        }
        return true;
    }
    public void insertUser(int user_role, String userName, String passWord, String name,
                           String phone, String ngaySinh){
        SQLiteDatabase database = db.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_name", userName);
        values.put("password", passWord);
        values.put("user_role", user_role);
        values.put("name", name);
        values.put("phone", phone);
        values.put("ngay_sinh", ngaySinh);
        database.insert("User", null, values);
        database.close();
    }
    public int getIDByUserName(String userName){
        int id = 0;
        SQLiteDatabase database = db.getReadableDatabase();
        String sql = "select id from User where user_name = ?";
        Cursor cursor = database.rawQuery(sql, new String[]{userName});
        cursor.moveToPosition(-1);
        while (cursor.moveToNext()){
            id = cursor.getInt(0);
        }
        cursor.close();
        database.close();
        return id;
    }
}
