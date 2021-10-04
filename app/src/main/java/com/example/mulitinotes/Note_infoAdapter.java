package com.example.mulitinotes;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;
import java.util.List;

public class Note_infoAdapter extends RecyclerView.Adapter<MyViewHolder> {


    private static final String TAG = "Note_infoAdapter";

    private List<Note_info> noteList;
    private MainActivity mainAct;

    public Note_infoAdapter(List<Note_info> note_info_List, MainActivity ma) {
        this.noteList = note_info_List;
        mainAct = ma;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: MAKING NEW MyViewHolder");

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notes_layout_row, parent, false);

        itemView.setOnClickListener(mainAct);
        itemView.setOnLongClickListener(mainAct);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Note_info Note = noteList.get(position);
        holder.itemView.setLongClickable(true);

        holder.title.setText(Note.getTitle());

        holder.dateTime.setText(new Date().toString());

        if(Note.getMessage().length() > 80){
            String showNote = Note.getMessage().substring(0, 79);
            showNote = showNote.concat("...");
            holder.message.setText(showNote);
        }
        else {
            holder.message.setText(Note.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        mainAct.getSupportActionBar().setTitle("Multi Notes " + "(" + noteList.size()+")");
        return noteList.size();
    }
}