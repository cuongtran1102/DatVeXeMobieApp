package Service;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.time.LocalDate;
import java.time.YearMonth;

import Pojo.DatVeXeKhach;

public class ThongKeService {
    private DatVeXeKhach db;

    public ThongKeService(Context context) {
        db = new DatVeXeKhach(context);
    }

    public int countTicketsInMonthYear(int month, int year) {
        SQLiteDatabase database = db.getReadableDatabase();
        String sql = "SELECT COUNT(*) FROM Ve WHERE strftime('%m', ngay_dat_ve) = ? AND strftime('%Y', ngay_dat_ve) = ?";
        Cursor cursor = database.rawQuery(sql, new String[]{String.valueOf(month), String.valueOf(year)});
        cursor.moveToFirst();
        int ticketCount = cursor.getInt(0);
        cursor.close();
        database.close();
        return ticketCount;
    }
}
