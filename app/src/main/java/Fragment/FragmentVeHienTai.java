package Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

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
import Pojo.Ve;
import Service.ChuyenXeService;
import Service.XemVeService;

public class FragmentVeHienTai extends Fragment implements XemVeService.OnItemClickListener{
    private RecyclerView recyclerView;
    private List<TuyenDuong> dsXemVe;
    private XemVeService xemVeService;
    private ChuyenXeService chuyenXeService;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ImageButton btnClose;
    private Button HuyVe;
    private ConstraintLayout inputView;
    private TextView txtBenDi, txtBenDen, txtLoaiXe;
    private EditText etGiaVe, etGioXuatPhat;
    private Ve selectedVe;
    private Service.XemVeService XemVeService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ve_hien_tai,
                container, false);
        chuyenXeService = new ChuyenXeService(getContext());
        dsXemVe = new ArrayList<>();
        dsXemVe = chuyenXeService.getListTuyenDuong();
        recyclerView = rootView.findViewById(R.id.recyclerView_VeHienTai);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        dsXemVe = (List<TuyenDuong>) new XemVeService(dsXemVe);
        recyclerView.setAdapter(XemVeService);

        swipeRefreshLayout = rootView.findViewById(R.id.refreshLayout_VeHienTai);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                dsXemVe = chuyenXeService.getListTuyenDuong();
                XemVeService = new XemVeService(dsXemVe);
                XemVeService.set(FragmentVeHienTai.this);
                recyclerView.setAdapter(xemVeService);

                swipeRefreshLayout.setRefreshing(false);
            }
        });
        btnClose = rootView.findViewById(R.id.btnClose_ManageTD);
        inputView = rootView.findViewById(R.id.inPutView_VeHienTai);
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

        HuyVe = rootView.findViewById(R.id.btnDeleteTD);
        HuyVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Confirmation");
                builder.setMessage("Xác nhận Hủy Vé");
                builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.setNegativeButton("Xác nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        XemVeService.deleteVeByIDTXemVe(selectedVe.getId());
                        XemVeService.deleteDanhGiaByIDXemVe(selectedVe.getId());
                        XemVeService.deleteChuyenXeByIDXemVe(selectedVe.getId());
                        XemVeService.deleteXemVe(selectedVe.getId());

                        inputView.setVisibility(View.GONE);
                        dsXemVe = chuyenXeService.getListTuyenDuong();
                        XemVeService = new XemVeService(dsXemVe);
                        XemVeService.set(FragmentVeHienTai.this);
                        recyclerView.setAdapter(XemVeService);
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
    public void onItemClick(Ve ve) {
        swipeRefreshLayout.setVisibility(View.GONE);
        inputView.setVisibility(View.VISIBLE);

        txtBenDi.setText(ve.getDiaDiemDi());
        txtBenDen.setText(Ve.getDiaDiemDen());
        txtLoaiXe.setText(String.valueOf(ve.getLoaiXe()));
        NumberFormat format = NumberFormat.getCurrencyInstance(new
                Locale("vi", "VN"));
        String fomatted = format.format(ve.getGiaNiemYet());
        etGiaVe.setText(fomatted);
        etGioXuatPhat.setText(ve.getGioXuatPhat().toString());

        selectedVe = ve;
    }
}
