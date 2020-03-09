package ru.mirea.lab3_1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StudentsAdapter extends RecyclerView.Adapter<StudentsAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Item> list;


    public StudentsAdapter(Context context, ArrayList<Item> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Item currentItem = list.get(position);

        String id = currentItem.getID();
        String fio = currentItem.getFIO();
        String date = currentItem.getDate();

        holder.txt_id.setText(id);
        holder.txt_fio.setText(fio);
        holder.txt_date.setText(date);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txt_id;
        public TextView txt_fio;
        public TextView txt_date;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_id = itemView.findViewById(R.id.text_view_id);
            txt_fio = itemView.findViewById(R.id.text_view_fio);
            txt_date = itemView.findViewById(R.id.text_view_date);
        }
    }
}
