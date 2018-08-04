package com.example.realgodjj.rxjavademo.Adapter;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.realgodjj.rxjavademo.R;
import com.example.realgodjj.rxjavademo.utils.SharedPreferencesUtil;
import com.example.realgodjj.rxjavademo.utils.TimePlan;

import java.util.List;

public class MyRecycleViewAdapter extends RecyclerView.Adapter<MyRecycleViewAdapter.ViewHolder> {
    private List<TimePlan> timePlanList;

    public MyRecycleViewAdapter(List<TimePlan> timePlanList) {
        this.timePlanList = timePlanList;
    }

    @NonNull
    @Override
    public MyRecycleViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.time_plan_list, parent, false);
        MyRecycleViewAdapter.ViewHolder viewHolder = new MyRecycleViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecycleViewAdapter.ViewHolder holder, int position) {
        holder.tvPlanTitle.setText(timePlanList.get(position).getTitle());
        holder.tvPlanLocation.setText(timePlanList.get(position).getLocation());
        holder.tvPlanContext.setText(timePlanList.get(position).getContext());
    }

    @Override
    public int getItemCount() {
        return timePlanList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @SuppressLint("StaticFieldLeak")
        TextView tvPlanTitle;
        TextView tvPlanContext;
        TextView tvPlanLocation;

        ViewHolder(View itemView) {
            super(itemView);
            tvPlanTitle = itemView.findViewById(R.id.tv_list_title);
            tvPlanLocation = itemView.findViewById(R.id.tv_list_time_location);
            tvPlanContext = itemView.findViewById(R.id.tv_list_context);
        }
    }
}
