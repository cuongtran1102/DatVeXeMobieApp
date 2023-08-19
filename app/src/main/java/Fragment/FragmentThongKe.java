package Fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import Service.ThongKeService;
import com.tvc.myapplication.R;

import java.time.LocalDate;
import java.util.Calendar;

public class FragmentThongKe extends Fragment {
    private TextView textViewTicketCount;
    private ThongKeService thongKeService;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thong_ke, container, false);
        textViewTicketCount = view.findViewById(R.id.textViewTicketCount);
        thongKeService = new ThongKeService(getContext());

        // Lấy tháng và năm hiện tại
        LocalDate currentDate = LocalDate.now();
        int currentMonth = currentDate.getMonthValue();
        int currentYear = currentDate.getYear();

        // Thực hiện thống kê
        int ticketCount = thongKeService.countTicketsInMonthYear(currentMonth, currentYear);

        // Hiển thị kết quả
        textViewTicketCount.setText(String.valueOf(ticketCount));

        return view;
    }
}

