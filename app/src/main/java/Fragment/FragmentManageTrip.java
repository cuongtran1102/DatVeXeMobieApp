package Fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.tvc.myapplication.R;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Pojo.ChuyenXe;
import Service.CarAdapter;
import Service.ChuyenXeService;

public class FragmentManageTrip extends Fragment implements CarAdapter.OnItemClickListener{
    private RecyclerView recyclerView;
    private ChuyenXeService chuyenXeService;
    private List<ChuyenXe> dsChuyenXe;
    private CarAdapter carAdapter;
    private SwipeRefreshLayout refreshLayout;
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
                chuyenXeService.deleteVe(chuyenXe.getIdChuyenXe());
                chuyenXeService.deleteDanhGia(chuyenXe.getIdChuyenXe());
                chuyenXeService.deleteChuyenXe(chuyenXe.getIdChuyenXe());

                dsChuyenXe = getCarListFromDatabase();
                carAdapter = new CarAdapter(dsChuyenXe);
                carAdapter.setOnItemClickListener(FragmentManageTrip.this);
                recyclerView.setAdapter(carAdapter);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void refreshData(){
        dsChuyenXe = getCarListFromDatabase();
        carAdapter = new CarAdapter(dsChuyenXe);
        carAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(carAdapter);

        refreshLayout.setRefreshing(false);
    }
}
