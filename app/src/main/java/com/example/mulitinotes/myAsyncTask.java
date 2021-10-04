package com.example.mulitinotes;

import android.os.AsyncTask;
import android.util.JsonReader;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class myAsyncTask extends AsyncTask<Void, Void, Void> {

    private MainActivity ma;

    public myAsyncTask(MainActivity mainActivity) {
        ma = mainActivity;
    }
    @Override
    protected Void doInBackground(Void... voids) {

        try {

            InputStream is = ma.getApplicationContext().openFileInput("note_Saved.json");
            JsonReader jr = new JsonReader(new InputStreamReader(is, "UTF-8"));
            String name;

            jr.beginObject();

            while (jr.hasNext()) {
                name = jr.nextName();
                if (name.equals("notes")) {

                    jr.beginArray();
                    while (jr.hasNext()) {
                        Note_info tempNotes = new Note_info();
                        jr.beginObject();
                        while(jr.hasNext()) {
                            name = jr.nextName();
                            if (name.equals("title")) {
                                tempNotes.setTitle(jr.nextString());
                            } else if (name.equals("date")) {
                                tempNotes.setDate(jr.nextString());
                            } else if (name.equals("note")) {
                                tempNotes.setMessage(jr.nextString());
                            } else {
                                jr.skipValue();
                            }
                        }
                        jr.endObject();
                        ma.getNote_infoList().add(tempNotes);

                    }
                    jr.endArray();
                }
                else{
                    jr.skipValue();
                }

            }
            jr.endObject();

        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error: "+e);
        }

        return null;
    }

    }


