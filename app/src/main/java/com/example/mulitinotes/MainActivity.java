package com.example.mulitinotes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.JsonWriter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, View.OnLongClickListener {


    private List<Note_info> note_infoList = new ArrayList<>();
    private RecyclerView recyclerView;

    private Note_infoAdapter nAdapter;

    private MyViewHolder mvh;

    private static final int REQUEST_CODE = 123;

    private int index;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyc);

        recyclerView.setAdapter(nAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        nAdapter = new Note_infoAdapter(note_infoList, this);


        new myAsyncTask(this).execute();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.create_button:
                Intent intent1 = new Intent(this, CreateNote.class);
                startActivityForResult(intent1, REQUEST_CODE);
                return true;
            case R.id.info_button:
                Intent intent = new Intent(this, AboutPage.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume(){
        note_infoList.size();
        super.onResume();
        nAdapter.notifyDataSetChanged();
    }
    @Override
    protected void onPause(){
        saveNotes();
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        index = recyclerView.getChildLayoutPosition(v);
        Intent intent = new Intent(MainActivity.this, CreateNote.class);
        intent.putExtra("Title", note_infoList.get(index).getTitle());
        intent.putExtra("Note", note_infoList.get(index).getMessage());
        intent.putExtra("Date", note_infoList.get(index).getDate());
        startActivityForResult(intent, REQUEST_CODE);

    }

    @Override
    public boolean onLongClick(View v) {

        index = recyclerView.getChildLayoutPosition(v);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);


        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                note_infoList.remove(index);
                nAdapter.notifyDataSetChanged();
                index = -1;
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                index = -1;
            }
        });

        builder.setMessage("Delete Note" + "'" + note_infoList.get(index).getTitle() + "'?");


        AlertDialog dialog = builder.create();
        dialog.show();

        return false;
    }

    private void saveNotes(){
        try
        {
            FileOutputStream fos =getApplicationContext().openFileOutput("Note_Saved.json", Context.MODE_PRIVATE);

            JsonWriter jw = new JsonWriter(new OutputStreamWriter(fos, "UTF-8"));
            jw.setIndent(" ");
            jw.beginObject();
            jw.name("multinotes");
            jwArray(jw);
            jw.endObject();
            jw.close();

        }
        catch (Exception e)
        {
            e.getStackTrace();
        }
    }

    @Override
    protected void onActivityResult(
            int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {

            if (resultCode == RESULT_OK) {
                Note_info enote = (Note_info) data.getExtras().getSerializable("enote");
                String status = data.getStringExtra("Status");
                if(status.equals("Stay"))
                {}
                else if (status.equals("New"))
                {
                    note_infoList.add(0,enote);
                }
                else if (status.equals("Change")) {
                    note_infoList.remove(index);
                    note_infoList.add(0, enote);
                }
            }
        }
    }

    public List<Note_info> getNote_infoList()
    {
        return note_infoList;
    }

    public void jwObject(JsonWriter jw, Note_info note_info) throws IOException
    {
        jw.beginObject();
        jw.name("title").value(note_info.getTitle());
        jw.name("date").value(note_info.getDate());
        jw.name("message").value(note_info.getMessage());
        jw.endObject();
    }

    public void jwArray(JsonWriter jw) throws IOException{
        jw.beginArray();
        for (Note_info note_info : note_infoList)
        {
            jwObject(jw, note_info);
        }
        jw.endArray();
    }
}
