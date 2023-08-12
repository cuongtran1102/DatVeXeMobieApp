package Service;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tvc.myapplication.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import Pojo.*;

public class TuyenDuongAdapter extends RecyclerView.Adapter<TuyenDuongAdapter.CarViewHolder> {

    private List<TuyenDuong> dsTuyenDuong;

    //khởi tạo biến interface click
    private OnItemClickListener onItemClickListener;


    public TuyenDuongAdapter(List<TuyenDuong> dsTuyenDuong) {
        this.dsTuyenDuong = dsTuyenDuong;
    }

    //setter cho biến click
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tuyenduong, parent, false);
        return new CarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        TuyenDuong tuyenDuong = dsTuyenDuong.get(position);
        holder.tvTenTuyenDuong.setText(tuyenDuong.getTenTuyenDuong());
        holder.tvLoaiXe.setText("Loại xe: "+ String.valueOf(tuyenDuong.getLoaiXe()) + " chỗ");
        holder.tvGioXuatPhat.setText("Giờ xuất phát: "+ tuyenDuong.getGioXuatPhat().toString());
        NumberFormat format = NumberFormat.getCurrencyInstance(new
                Locale("vi", "VN"));
        String formattedGiaVe = format.format(tuyenDuong.getGiaNiemYet());
        holder.tvGiaNiemYet.setText(formattedGiaVe);

        //sự kiện onclick cho carview
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(tuyenDuong);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dsTuyenDuong.size();
    }

    public static class CarViewHolder extends RecyclerView.ViewHolder {

        TextView tvTenTuyenDuong;
        TextView tvLoaiXe;
        TextView tvGioXuatPhat;
        TextView tvGiaNiemYet;

        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenTuyenDuong = itemView.findViewById(R.id.tvTenTuyenDuong);
            tvLoaiXe = itemView.findViewById(R.id.tvLoaiXe);
            tvGioXuatPhat = itemView.findViewById(R.id.tvGioXuatPhat);
            tvGiaNiemYet = itemView.findViewById(R.id.tvGiaNiemYet);
        }
    }
    //interface tạo sự kiện click cho carview sẽ chuyển sang một activity khác
    //chứa thông tin chuyến xe của carview đó
    public interface OnItemClickListener{
        void onItemClick(TuyenDuong tuyenDuong);
    }
}
