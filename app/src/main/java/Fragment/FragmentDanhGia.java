package Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tvc.myapplication.R;


public class FragmentDanhGia extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_danh_gia, container, false);

        // Lấy ID của vé đã chọn từ Intent
        Bundle bundle = getArguments();
        if (bundle != null) {
            int selectedVeId = bundle.getInt("selectedVeId", -1); // -1 là giá trị mặc định nếu không có dữ liệu
            // Do something with the selectedVeId, such as displaying the review form
        }

        // ... (phần code khác)
        return rootView;
    }
}











