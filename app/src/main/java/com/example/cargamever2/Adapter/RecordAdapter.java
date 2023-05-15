package com.example.cargamever2.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cargamever2.Activity.MainActivity;
import com.example.cargamever2.Interfaces.ListCallBack;
import com.example.cargamever2.Interfaces.MapCallBack;
import com.example.cargamever2.Model.Record;
import com.example.cargamever2.R;

import java.util.ArrayList;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.RecordViewHolder> {

    private  ArrayList<Record> records;
    private Context context;

    private MapCallBack mapCallBack;



    public RecordAdapter(Context context, ArrayList<Record> records, MapCallBack mapCallBack) {
        this.records = records;
        this.context = context;
        this.mapCallBack = mapCallBack;
    }

    @NonNull
    @Override
    public RecordAdapter.RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_item, parent, false);
        RecordViewHolder recordViewHolder = new RecordViewHolder(view);
        return recordViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecordAdapter.RecordViewHolder holder, int position) {

        Record record = getRecord(position);

        Log.d("fdsafdsa",getRecord(position) +"");

        holder.record_LBL_title.setText(records.get(position).toString());
        holder.record_LayOut.setOnClickListener(v -> {
            mapCallBack.sendTheLocation(record.getLatitude(),record.getLongitude());
        });
        Log.d("RecordAdapter", "records latitude: " + record.getLatitude() );

    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    public Record getRecord(int position) {
        return this.records.get(position);
    }



    public class RecordViewHolder extends RecyclerView.ViewHolder {
        TextView record_LBL_title;
        RelativeLayout record_LayOut;

        public RecordViewHolder(View itemView) {
            super(itemView);
            record_LBL_title = itemView.findViewById(R.id.record_LBL_title);
            record_LayOut = itemView.findViewById(R.id.record_LayOut);
        }




    }
}
