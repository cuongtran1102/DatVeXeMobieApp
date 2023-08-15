package Fragment;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tvc.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import Service.ChuyenXeService;

public class FragmentAddTuyenDuong extends Fragment {
    private EditText etBenDi, etBenDen, etGioKhoiHanh, etGiaVe;
    private Button btnThemTuyenDuong;
    private Spinner spinner;
    private List<String> dsLoaiXe;
    private ChuyenXeService chuyenXeService;
    private int selectedLoaiXe;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_tuyenduong,
                container, false);

        chuyenXeService = new ChuyenXeService(getContext());
        etBenDi = rootView.findViewById(R.id.etBenDi_addTD);
        etBenDen = rootView.findViewById(R.id.etBenDen_addTD);
        etGioKhoiHanh = rootView.findViewById(R.id.etGioXuatPhat_addTD);
        etGiaVe = rootView.findViewById(R.id.etGiaVe_addTD);
        btnThemTuyenDuong = rootView.findViewById(R.id.btnThem_addTD);
        spinner = rootView.findViewById(R.id.spinner_loaixe);
        loadDSLoaiXe();
        ArrayAdapter adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, dsLoaiXe);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedLoaiXe = Integer.parseInt(spinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        etGioKhoiHanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog();
            }
        });

        btnThemTuyenDuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Thông Báo");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                if(etBenDi.getText().toString().isEmpty() ||
                        etBenDen.getText().toString().isEmpty() ||
                        etGiaVe.getText().toString().isEmpty() ||
                        etGioKhoiHanh.getText().toString().isEmpty()){
                    builder.setMessage("Hãy nhập đủ thông tin tuyến đường");
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else{
                    if(chuyenXeService.checkTuyenDuong(etBenDi.getText().toString().strip(),
                            etBenDen.getText().toString().strip(), selectedLoaiXe) == false){
                        builder.setMessage("Tuyến đường đã tồn tại");
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                    else{
                        String tenTuyenDuong = etBenDi.getText().toString()+"-"+etBenDen.getText().toString();
                        chuyenXeService.insertTuyenDuong(tenTuyenDuong, etBenDi.getText().toString().strip(),
                                etBenDen.getText().toString().strip(), selectedLoaiXe,
                                Float.parseFloat(etGiaVe.getText().toString()), etGioKhoiHanh.getText().toString());
                        builder.setMessage("Thêm tuyến đường thành công");
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }
            }
        });

        return rootView;
    }
    private void loadDSLoaiXe(){
        dsLoaiXe = new ArrayList<>();
        dsLoaiXe.add("8");
        dsLoaiXe.add("12");
        dsLoaiXe.add("16");
        dsLoaiXe.add("20");
    }
        private void showTimePickerDialog() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                getContext(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String selectedTime = String.format("%02d:%02d", hourOfDay, minute);
                        etGioKhoiHanh.setText(selectedTime);
                    }
                },
                12, 0, true);

        timePickerDialog.show();
    }
}
