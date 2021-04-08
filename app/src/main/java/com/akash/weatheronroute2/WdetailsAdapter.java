package com.akash.weatheronroute2;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class WdetailsAdapter extends RecyclerView.Adapter<WdetailsAdapter.MyViewHolder> {

    Context context;
    ArrayList<WeatherObject> weatherObjectArrayList;

    public WdetailsAdapter(Context ct, ArrayList<WeatherObject> weatherObjectArrayList){
        context = ct;
        this.weatherObjectArrayList = weatherObjectArrayList;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.w_adapter,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.Celcius.setText(weatherObjectArrayList.get(position).GetTemprature());
        holder.Distance.setText(weatherObjectArrayList.get(position).GetDistance());
        holder.Time.setText(weatherObjectArrayList.get(position).GetTime());
        holder.Wicon.setImageResource(weatherObjectArrayList.get(position).GetImageID());
        holder.Cityname.setText(weatherObjectArrayList.get(position).GetCityname());



    }

    @Override
    public int getItemCount() {

        return weatherObjectArrayList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Celcius,Distance,Time,Cityname;

        ImageView Wicon;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Celcius = itemView.findViewById(R.id.Celciusview);
            Distance = itemView.findViewById(R.id.Distance);
            Time = itemView.findViewById(R.id.timeview);
            Wicon = itemView.findViewById(R.id.Wicon);
            Cityname = itemView.findViewById(R.id.textView4);


        }
    }
}
