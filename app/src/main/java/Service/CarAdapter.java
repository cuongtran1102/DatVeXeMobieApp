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

import Pojo.ChuyenXe;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {

    private List<ChuyenXe> dsChuyenXe;
    //khởi tạo biến interface click
    private OnItemClickListener onItemClickListener;


    public CarAdapter(List<ChuyenXe> dsChuyenXe) {
        this.dsChuyenXe = dsChuyenXe;
    }

    //setter cho biến click
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chuyenxe, parent, false);
        return new CarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        ChuyenXe chuyenXe = dsChuyenXe.get(position);
        holder.tvTripName.setText(chuyenXe.getTenChuyenXe());
        holder.tvTripDate.setText(chuyenXe.getNgayDi().toString());
        holder.tvTripTime.setText(chuyenXe.getGioXuatPhat().toString());
        NumberFormat format = NumberFormat.getCurrencyInstance(new
                Locale("vi", "VN"));
        String formattedGiaVe = format.format(chuyenXe.getGiaVe());
        holder.tvTripPrice.setText(formattedGiaVe);

        //sự kiện onclick cho carview
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(chuyenXe);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dsChuyenXe.size();
    }

    public static class CarViewHolder extends RecyclerView.ViewHolder {

        TextView tvTripName;
        TextView tvTripDate;
        TextView tvTripTime;
        TextView tvTripPrice;

        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTripName = itemView.findViewById(R.id.tvTripName);
            tvTripDate = itemView.findViewById(R.id.tvTripDate);
            tvTripTime = itemView.findViewById(R.id.tvTripTime);
            tvTripPrice = itemView.findViewById(R.id.tvTicketPrice);
        }
    }
    //interface tạo sự kiện click cho carview sẽ chuyển sang một activity khác
    //chứa thông tin chuyến xe của carview đó
    public interface OnItemClickListener{
        void onItemClick(ChuyenXe chuyenXe);
    }
}
