package com.example.joes.timetable;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Joes on 17/3/2018.
 */

public class TimeTableAdapter extends RecyclerView.Adapter<TimeTableAdapter.TimeTableHolder> {

    private Context context;
    private List<Timetable> timetableList;


    public TimeTableAdapter(Context context, List<Timetable> timetableList) {
        this.context = context;
        this.timetableList = timetableList;
    }

    @NonNull
    @Override
    public TimeTableHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.timetable_list, parent, false);
        return new TimeTableHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeTableHolder holder, int position) {
        for (int i = 0; i < Utils.timetableList.size(); i++) {
            Log.i("TAG", "Module: " + Utils.timetableList.get(i).getModule());
            Log.i("TAG", "Classroom: " + Utils.timetableList.get(i).getLocation());
            Log.i("TAG", "Start Time: " + Utils.timetableList.get(i).getStartTime());
            Log.i("TAG", "End Time: " + Utils.timetableList.get(i).getEndTime());
            Log.i("TAG", "Date: " + Utils.timetableList.get(i).getDate());
        }
        Timetable timetable = timetableList.get(position);

        holder.DateTextView.setText(timetable.getDate());
        holder.TitleTextView.setText(timetable.getModule());
        holder.RoomTextView.setText(timetable.getLocation());
        holder.LocationTextView.setText(timetable.getLocation());
    }

    @Override
    public int getItemCount() {
        return timetableList.size();
    }

    class TimeTableHolder extends RecyclerView.ViewHolder {

        TextView DateTextView, TitleTextView, RoomTextView, LocationTextView;

        public TimeTableHolder(View itemView) {
            super(itemView);

            DateTextView = itemView.findViewById(R.id.date);
            TitleTextView = itemView.findViewById(R.id.course_title);
            RoomTextView = itemView.findViewById(R.id.course_room);
            LocationTextView = itemView.findViewById(R.id.course_locaton);
        }
    }

}
