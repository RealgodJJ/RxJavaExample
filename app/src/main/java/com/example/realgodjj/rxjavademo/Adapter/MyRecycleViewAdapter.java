package com.example.realgodjj.rxjavademo.Adapter;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.realgodjj.rxjavademo.R;
import com.example.realgodjj.rxjavademo.utils.TimePlan;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

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
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        String allDay = "全天";
        holder.tvPlanTime.setText(timePlanList.get(position).isAllDay() ? allDay :
                simpleDateFormat.format(timePlanList.get(position).getStartDateTime()) + "\n"
                + simpleDateFormat.format(timePlanList.get(position).getEndDateTime()));
        holder.tvPlanContext.setText(timePlanList.get(position).getContext());
        holder.tvPlanLocation.setText(timePlanList.get(position).getLocation());
    }

    @Override
    public int getItemCount() {
        return timePlanList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @SuppressLint("StaticFieldLeak")
        TextView tvPlanTitle;
        TextView tvPlanContext;
        TextView tvPlanTime;
        TextView tvPlanLocation;

        ViewHolder(View itemView) {
            super(itemView);
            tvPlanTitle = itemView.findViewById(R.id.tv_list_title);
            tvPlanTime = itemView.findViewById(R.id.tv_list_time);
            tvPlanContext = itemView.findViewById(R.id.tv_list_context);
            tvPlanLocation = itemView.findViewById(R.id.tv_list_location);
        }
    }
}
