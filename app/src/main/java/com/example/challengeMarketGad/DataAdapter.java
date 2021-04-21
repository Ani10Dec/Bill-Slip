package com.example.challengeMarketGad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private List<DataEntity> dataEntityList;
    private Context context;

    public DataAdapter(List<DataEntity> dataEntityList, Context context) {
        this.dataEntityList = dataEntityList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataEntity current = dataEntityList.get(position);
        holder.count.setText(String.valueOf(current.data_id + 1));
        holder.amount.setText(current.amount);
        Picasso.get().load(current.bitmap).rotate(90).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return dataEntityList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView count, amount;
        private ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            count = itemView.findViewById(R.id.count);
            amount = itemView.findViewById(R.id.amount);
            imageView = itemView.findViewById(R.id.image);
        }
    }
}
