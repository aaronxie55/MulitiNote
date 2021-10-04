package com.example.mulitinotes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class CreateNote extends AppCompatActivity {
    private EditText title;
    private EditText note;

    private Note_info n = new Note_info();
    private Menu oMenu;
    private String ptitle = null;
    private String pnote = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        title = findViewById(R.id.create_title);
        note = findViewById(R.id.create_note);

        Intent intent = getIntent();
        if(intent.hasExtra("Title"))
        {
            ptitle = intent.getStringExtra("Title");
            title.setText(ptitle);
        }
        else if (intent.hasExtra("Note")) {
            pnote = intent.getStringExtra("Note");
            note.setText(pnote);
        }

        note.setMovementMethod(new ScrollingMovementMethod());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu2, menu);
        oMenu = menu;
        return true;
    }


    @Override
    public void onBackPressed() {


        if(title.getText().toString().isEmpty()) {
            Toast.makeText(CreateNote.this, "Un-titled activity was not saved!", Toast.LENGTH_SHORT).show();
            super.onBackPressed();
        }else if(ptitle.isEmpty()&&pnote.isEmpty()){
            super.onBackPressed();
        }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage("Your note is not saved! \nSave note '" + title.getText() + "'");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    noteSave();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    finish();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.save_note:
                noteSave();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    public void noteSave(){
        Note_info inote = new Note_info();
        inote.setMessage(note.getText().toString());
        inote.setTitle(title.getText().toString());
        inote.setDate(Calendar.getInstance().getTime().toString());
        Intent intent = new Intent();
        intent.putExtra("eNote",inote);
        if(title.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Un-titled activity was not saved!", Toast.LENGTH_SHORT).show();
            intent.putExtra("Status","Stay");
        }
        else if(ptitle.isEmpty()&&pnote.isEmpty()){
            intent.putExtra("Status","New");
        }
        else if(ptitle.equals(title.getText().toString())&& pnote.equals(note.getText().toString()))
        {
            intent.putExtra("Status","Stay");
        }
        else{
            intent.putExtra("Status","Change");
        }
        setResult(RESULT_OK,intent);
        finish();
    }

}
