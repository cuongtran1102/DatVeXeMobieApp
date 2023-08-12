package Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tvc.myapplication.QLChuyenXeActivity;
import com.tvc.myapplication.QLTuyenDuongActivity;
import com.tvc.myapplication.R;

public class FragmentChuyenXe extends Fragment {
    private Button btnQLChuyenXe, btnQLTuyenDuong;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_quan_ly_chuyen_xe, container, false);
        btnQLChuyenXe = rootView.findViewById(R.id.btnQLChuyenXe);
        btnQLTuyenDuong = rootView.findViewById(R.id.btnQLTuyenDuong);
        btnQLChuyenXe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), QLChuyenXeActivity.class);
                startActivity(intent);
            }
        });

        btnQLTuyenDuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), QLTuyenDuongActivity.class);
                startActivity(intent);
            }
        });
        return rootView;
    }
}
