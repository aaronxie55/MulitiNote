package com.example.mulitinotes;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;



public class MyViewHolder extends RecyclerView.ViewHolder {

    public TextView title;
    public TextView message;
    public TextView dateTime;

    public MyViewHolder(View view) {
        super(view);
        title = view.findViewById(R.id.title_view);
        message = view.findViewById(R.id.note_view);
        dateTime = view.findViewById(R.id.date_view);
    }



}
