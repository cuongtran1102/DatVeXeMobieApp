package Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.tvc.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import Pojo.TuyenDuong;
import Service.ChuyenXeService;
import Service.TuyenDuongAdapter;

public class FragmentManageTuyenDuong extends Fragment implements TuyenDuongAdapter.OnItemClickListener{
    private RecyclerView recyclerView;
    private List<TuyenDuong> dsTuyenDuong;
    private TuyenDuongAdapter tuyenDuongAdapter;
    private ChuyenXeService chuyenXeService;
    private SwipeRefreshLayout swipeRefreshLayout;
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


        return rootView;
    }

    @Override
    public void onItemClick(TuyenDuong tuyenDuong) {

    }
}
