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
    private OnRecycleViewItemListener onRecycleViewItemListener;

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
    public void onBindViewHolder(@NonNull final MyRecycleViewAdapter.ViewHolder holder, int position) {
        //实现设定事件的显示
        holder.tvPlanTheme.setText(timePlanList.get(position).getTheme());
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String allDay = "全天";
        holder.tvPlanTime.setText(timePlanList.get(position).isAllDay() ?
                simpleDateFormat2.format(timePlanList.get(position).getDate()) + allDay :
                simpleDateFormat1.format(timePlanList.get(position).getStartDateTime()) + "\n"
                + simpleDateFormat1.format(timePlanList.get(position).getEndDateTime()));
        holder.tvPlanContext.setText(timePlanList.get(position).getContext());
        holder.tvPlanLocation.setText(timePlanList.get(position).getLocation());

        //实现事项的点击事件
        if (onRecycleViewItemListener != null) {

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition();
                    onRecycleViewItemListener.onItemClick(holder.itemView, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return timePlanList.size();
    }

    public TimePlan getItem(int position) {
        return timePlanList.get(position);
    }

    public void setOnRecycleViewItemListener(OnRecycleViewItemListener onRecycleViewItemListener) {
        this.onRecycleViewItemListener = onRecycleViewItemListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @SuppressLint("StaticFieldLeak")
        TextView tvPlanTheme;
        TextView tvPlanContext;
        TextView tvPlanTime;
        TextView tvPlanLocation;

        ViewHolder(View itemView) {
            super(itemView);
            tvPlanTheme = itemView.findViewById(R.id.tv_list_title);
            tvPlanTime = itemView.findViewById(R.id.tv_list_time);
            tvPlanContext = itemView.findViewById(R.id.tv_list_context);
            tvPlanLocation = itemView.findViewById(R.id.tv_list_location);
        }
    }

    public interface OnRecycleViewItemListener {
        void onItemClick(View view, int position);
    }
}
