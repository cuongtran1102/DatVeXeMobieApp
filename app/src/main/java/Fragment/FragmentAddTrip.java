package Fragment;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.tvc.myapplication.R;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import Pojo.TuyenDuong;
import Service.ChuyenXeService;
import Service.TuyenDuongAdapter;

public class FragmentAddTrip extends Fragment implements TuyenDuongAdapter.OnItemClickListener{
    private RecyclerView recyclerView;
    private List<TuyenDuong> dsTuyenDuong;
    private TuyenDuongAdapter tuyenDuongAdapter;
    private ChuyenXeService chuyenXeService;
    private EditText etBenDi, etBenDen, etChonNgay_Add;
    private Button btnTraCuu, btnThemChuyenXe;
    private TextView txtTenCX, txtBenDi, txtBenDen, txtGiaVe, txtSLGhe, txtGioDi;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ConstraintLayout inputView;
    private ImageButton btnClose;
    private Calendar calendar;
    private TuyenDuong tuyenDuong_add;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_addtrip,
                container, false);
        chuyenXeService = new ChuyenXeService(getContext());
        inputView = rootView.findViewById(R.id.inputView);

        txtTenCX = rootView.findViewById(R.id.txtTenCX_add);
        txtBenDi = rootView.findViewById(R.id.txtBenDi_add);
        txtBenDen = rootView.findViewById(R.id.txtBenDen_add);
        txtGiaVe = rootView.findViewById(R.id.txtGiaVe_add);
        txtSLGhe = rootView.findViewById(R.id.txtSLGhe_add);
        txtGioDi = rootView.findViewById(R.id.txtGioDi_add);

        etChonNgay_Add = rootView.findViewById(R.id.etChonNgay);

        btnThemChuyenXe = rootView.findViewById(R.id.btnThemChuyenXe);
        btnClose = rootView.findViewById(R.id.btnClose_addCX);
        recyclerView = rootView.findViewById(R.id.recyclerView_AddTRip);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        swipeRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });

        dsTuyenDuong = chuyenXeService.getListTuyenDuong();
        tuyenDuongAdapter = new TuyenDuongAdapter(dsTuyenDuong);
        tuyenDuongAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(tuyenDuongAdapter);

        etBenDi = rootView.findViewById(R.id.etBenDi_AddTrip);
        etBenDen = rootView.findViewById(R.id.etBenDen_AddTrip);
        btnTraCuu = rootView.findViewById(R.id.btnTraCuu_AddTRip);
        btnTraCuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Thông báo");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                if(etBenDi.getText().toString().isEmpty() ||
                        etBenDen.getText().toString().isEmpty()){
                    builder.setMessage("Hãy nhập đủ thông tin chuyến xe cần tra cứu");
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                else{
                    dsTuyenDuong = chuyenXeService.getListTuyenDuong(etBenDi.getText().toString().strip(),
                            etBenDen.getText().toString().strip());
                    if(dsTuyenDuong.isEmpty()){
                        builder.setMessage("Không tìm thấy tuyến đường");
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                    else {
                        tuyenDuongAdapter = new TuyenDuongAdapter(dsTuyenDuong);
                        tuyenDuongAdapter.setOnItemClickListener(FragmentAddTrip.this);
                        recyclerView.setAdapter(tuyenDuongAdapter);
                    }
                }
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputView.setVisibility(View.GONE);
                swipeRefreshLayout.setVisibility(View.VISIBLE);
                etBenDen.setVisibility(View.VISIBLE);
                etBenDi.setVisibility(View.VISIBLE);
                btnTraCuu.setVisibility(View.VISIBLE);
            }
        });

        calendar = Calendar.getInstance();

        etChonNgay_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        btnThemChuyenXe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Thông báo");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                if(etChonNgay_Add.getText().toString().isEmpty()){
                    builder.setMessage("Hãy chọn ngày khởi hành");
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                else if(chuyenXeService.checkChuyenXe(etChonNgay_Add.getText().toString(),
                        tuyenDuong_add.getId()) == false){
                    builder.setMessage("Đã tồn tại chuyến xe này trong CSDL");
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                else{
                    chuyenXeService.insertChuyenXe(etChonNgay_Add.getText().toString(),
                            tuyenDuong_add.getId());
                    etChonNgay_Add.setText("");
                    builder.setMessage("Thêm chuyến xe thành công");
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        });
        return rootView;
    }

    @Override
    public void onItemClick(TuyenDuong tuyenDuong) {
        this.tuyenDuong_add = tuyenDuong;
        swipeRefreshLayout.setVisibility(View.GONE);
        etBenDen.setVisibility(View.GONE);
        etBenDi.setVisibility(View.GONE);
        btnTraCuu.setVisibility(View.GONE);
        inputView.setVisibility(View.VISIBLE);

        txtTenCX.setText(tuyenDuong.getTenTuyenDuong());
        txtBenDi.setText(tuyenDuong.getDiaDiemDi());
        txtBenDen.setText(tuyenDuong.getDiaDiemDen());

        NumberFormat format = NumberFormat.getCurrencyInstance(new
                Locale("vi", "VN"));
        String fomatted = format.format(tuyenDuong.getGiaNiemYet());
        txtGiaVe.setText(fomatted);
        txtSLGhe.setText(String.valueOf(tuyenDuong.getLoaiXe()));
        txtGioDi.setText(tuyenDuong.getGioXuatPhat().toString());
    }

    private void refreshData() {
        dsTuyenDuong = chuyenXeService.getListTuyenDuong();
        tuyenDuongAdapter = new TuyenDuongAdapter(dsTuyenDuong);
        tuyenDuongAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(tuyenDuongAdapter);

        // Kết thúc trạng thái làm mới
        swipeRefreshLayout.setRefreshing(false);
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        // Set the minimum date to tomorrow
        Calendar minDateCalendar = Calendar.getInstance();
        minDateCalendar.add(Calendar.DAY_OF_MONTH, 1);
        datePickerDialog.getDatePicker().setMinDate(minDateCalendar.getTimeInMillis());

        datePickerDialog.show();
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "yyyy-MM-dd";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
            etChonNgay_Add.setText(sdf.format(calendar.getTime()));
        }
    };
}
