package Fragment;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.tvc.myapplication.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import Pojo.TuyenDuong;
import Service.ChuyenXeService;
import Service.TuyenDuongAdapter;

public class FragmentManageTuyenDuong extends Fragment implements TuyenDuongAdapter.OnItemClickListener{
    private RecyclerView recyclerView;
    private List<TuyenDuong> dsTuyenDuong;
    private TuyenDuongAdapter tuyenDuongAdapter;
    private ChuyenXeService chuyenXeService;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ImageButton btnClose;
    private Button btnDeleteTD;
    private ConstraintLayout inputView;
    private TextView txtBenDi, txtBenDen, txtLoaiXe;
    private EditText etGiaVe, etGioXuatPhat;
    private TuyenDuong selectedTuyenDuong;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_manage_tuyenduong,
                container, false);
        chuyenXeService = new ChuyenXeService(getContext());
        dsTuyenDuong = new ArrayList<>();
        dsTuyenDuong = chuyenXeService.getListTuyenDuong();
        recyclerView = rootView.findViewById(R.id.recyclerView_ManageTuyenDuong);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        tuyenDuongAdapter = new TuyenDuongAdapter(dsTuyenDuong);
        tuyenDuongAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(tuyenDuongAdapter);

        swipeRefreshLayout = rootView.findViewById(R.id.refreshLayout_ManageTuyenDuong);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                dsTuyenDuong = chuyenXeService.getListTuyenDuong();
                tuyenDuongAdapter = new TuyenDuongAdapter(dsTuyenDuong);
                tuyenDuongAdapter.setOnItemClickListener(FragmentManageTuyenDuong.this);
                recyclerView.setAdapter(tuyenDuongAdapter);

                swipeRefreshLayout.setRefreshing(false);
            }
        });
        btnClose = rootView.findViewById(R.id.btnClose_ManageTD);
        inputView = rootView.findViewById(R.id.inPutView_ManageTuyenDuong);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputView.setVisibility(View.GONE);
                swipeRefreshLayout.setVisibility(View.VISIBLE);
            }
        });

        txtBenDi = rootView.findViewById(R.id.txtBenDi_eidtTD);
        txtBenDen = rootView.findViewById(R.id.txtBenDen_editTD);
        txtLoaiXe = rootView.findViewById(R.id.txtLoaiXe_editTD);
        etGiaVe = rootView.findViewById(R.id.etGiaVe_editTD);
        etGioXuatPhat = rootView.findViewById(R.id.etGioDi_editTD);

        btnDeleteTD = rootView.findViewById(R.id.btnDeleteTD);
        btnDeleteTD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Confirmation");
                builder.setMessage("Xác nhận xóa tuyến đường");
                builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.setNegativeButton("Xác nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        chuyenXeService.deleteVeByIDTuyenDuong(selectedTuyenDuong.getId());
                        chuyenXeService.deleteDanhGiaByIDTuyenDuong(selectedTuyenDuong.getId());
                        chuyenXeService.deleteChuyenXeByIDTuyenDuong(selectedTuyenDuong.getId());
                        chuyenXeService.deleteTuyenDuong(selectedTuyenDuong.getId());

                        inputView.setVisibility(View.GONE);
                        dsTuyenDuong = chuyenXeService.getListTuyenDuong();
                        tuyenDuongAdapter = new TuyenDuongAdapter(dsTuyenDuong);
                        tuyenDuongAdapter.setOnItemClickListener(FragmentManageTuyenDuong.this);
                        recyclerView.setAdapter(tuyenDuongAdapter);
                        swipeRefreshLayout.setVisibility(View.VISIBLE);

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        return rootView;
    }

    @Override
    public void onItemClick(TuyenDuong tuyenDuong) {
        swipeRefreshLayout.setVisibility(View.GONE);
        inputView.setVisibility(View.VISIBLE);

        txtBenDi.setText(tuyenDuong.getDiaDiemDi());
        txtBenDen.setText(tuyenDuong.getDiaDiemDen());
        txtLoaiXe.setText(String.valueOf(tuyenDuong.getLoaiXe()));
        NumberFormat format = NumberFormat.getCurrencyInstance(new
                Locale("vi", "VN"));
        String fomatted = format.format(tuyenDuong.getGiaNiemYet());
        etGiaVe.setText(fomatted);
        etGioXuatPhat.setText(tuyenDuong.getGioXuatPhat().toString());

        selectedTuyenDuong = tuyenDuong;
    }
}
