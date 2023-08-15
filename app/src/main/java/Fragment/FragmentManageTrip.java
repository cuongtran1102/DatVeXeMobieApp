package Fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import Pojo.ChuyenXe;
import Service.CarAdapter;
import Service.ChuyenXeService;

public class FragmentManageTrip extends Fragment implements CarAdapter.OnItemClickListener{
    private RecyclerView recyclerView;
    private ChuyenXeService chuyenXeService;
    private List<ChuyenXe> dsChuyenXe;
    private CarAdapter carAdapter;
    private SwipeRefreshLayout refreshLayout;
    private ConstraintLayout inputView;
    private ImageButton btnClose;
    private Button btnHuy;
    private TextView txtBenDi, txtBenDEn, txtNgayDi, txtGioDi, txtGiaVe, txtSLGhe;
    private ChuyenXe selectedChuyenXe;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_managetrip,
                container, false);

        recyclerView = rootView.findViewById(R.id.recyclerView_ManageTrip);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        chuyenXeService = new ChuyenXeService(getContext());
        dsChuyenXe = new ArrayList<>();
        dsChuyenXe = getCarListFromDatabase();
        carAdapter = new CarAdapter(dsChuyenXe);
        carAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(carAdapter);

        refreshLayout = rootView.findViewById(R.id.refreshLayout_ManageTrip);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });

        txtBenDi = rootView.findViewById(R.id.txtBenDi_manageCX);
        txtBenDEn = rootView.findViewById(R.id.txtBenDen_manageCX);
        txtNgayDi = rootView.findViewById(R.id.txtNgayDi_manageCX);
        txtGioDi = rootView.findViewById(R.id.txtGioDi_manageCX);
        txtGiaVe = rootView.findViewById(R.id.txtGiaVe_manageCX);
        txtSLGhe = rootView.findViewById(R.id.txtSoLuongGhe_manageCX);

        btnClose = rootView.findViewById(R.id.btnClose_ManageCX);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputView.setVisibility(View.GONE);
                refreshLayout.setVisibility(View.VISIBLE);
            }
        });
        btnHuy = rootView.findViewById(R.id.btnHuy_manageCX);
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Confirmation");
                builder.setMessage("Bạn có muốn HỦY chuyến xe");

                builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        chuyenXeService.deleteVe(selectedChuyenXe.getIdChuyenXe());
                        chuyenXeService.deleteDanhGia(selectedChuyenXe.getIdChuyenXe());
                        chuyenXeService.deleteChuyenXe(selectedChuyenXe.getIdChuyenXe());

                        inputView.setVisibility(View.GONE);
                        dsChuyenXe = getCarListFromDatabase();
                        carAdapter = new CarAdapter(dsChuyenXe);
                        carAdapter.setOnItemClickListener(FragmentManageTrip.this);
                        recyclerView.setAdapter(carAdapter);
                        refreshLayout.setVisibility(View.VISIBLE);
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        inputView = rootView.findViewById(R.id.inputView_manageChuyenXe);
        return rootView;
    }

    private List<ChuyenXe> getCarListFromDatabase() {
        LocalDate currentDate = LocalDate.now();
        List<ChuyenXe> listChuyenXe = chuyenXeService.getListChuyenXe();
        List<ChuyenXe> resultListCX = new ArrayList<>();
        for (ChuyenXe chuyenXe : listChuyenXe) {
            LocalDate ngayDi = chuyenXe.getNgayDi();
            if (currentDate.compareTo(ngayDi) < 0) {
                resultListCX.add(chuyenXe);
            }
        }
        return resultListCX;
    }

    @Override
    public void onItemClick(ChuyenXe chuyenXe) {
        this.selectedChuyenXe = chuyenXe;
        inputView.setVisibility(View.VISIBLE);
        refreshLayout.setVisibility(View.GONE);

        txtBenDi.setText(chuyenXe.getBenDi());
        txtBenDEn.setText(chuyenXe.getBenDen());
        txtNgayDi.setText(chuyenXe.getNgayDi().toString());
        txtGioDi.setText(chuyenXe.getGioXuatPhat().toString());
        txtSLGhe.setText(String.valueOf(chuyenXe.getSoLuongGhe()));
        NumberFormat format = NumberFormat.getCurrencyInstance(new
                Locale("vi", "VN"));
        String fomatted = format.format(chuyenXe.getGiaVe());
        txtGiaVe.setText(fomatted);
    }

    private void refreshData(){
        dsChuyenXe = getCarListFromDatabase();
        carAdapter = new CarAdapter(dsChuyenXe);
        carAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(carAdapter);

        refreshLayout.setRefreshing(false);
    }
}
