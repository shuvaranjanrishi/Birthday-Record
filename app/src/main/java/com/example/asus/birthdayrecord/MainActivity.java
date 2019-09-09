package com.example.asus.birthdayrecord;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    MyDBFunctions myDBFunctions;
    private Button saveBtn,showBtn,deleteBtn;
    private EditText idEditText,nameEditText,genderEditText,birthdayEditText;
    MediaPlayer mp1,mp2,mp3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDBFunctions = new MyDBFunctions(this);
        myDBFunctions.getWritableDatabase();

        idEditText = (EditText) findViewById(R.id.idEditTextId);
        nameEditText = (EditText) findViewById(R.id.nameEditTextId);
        genderEditText = (EditText) findViewById(R.id.genderEdtitTextId);
        birthdayEditText = (EditText) findViewById(R.id.birthEditTextId);

        saveBtn = (Button) findViewById(R.id.saveButtonId);
        showBtn = (Button) findViewById(R.id.showBirthdayButtonId);
        deleteBtn = (Button) findViewById(R.id.deleteBirthdayButtonId);

        mp1 = MediaPlayer.create(MainActivity.this,R.raw.button_click_sound);
        mp2 = MediaPlayer.create(MainActivity.this,R.raw.go_button_sound);
        mp3 = MediaPlayer.create(MainActivity.this,R.raw.wrong_alrert_button_sound);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String id = idEditText.getText().toString();
                String name = nameEditText.getText().toString();
                String gender = genderEditText.getText().toString();
                String day = birthdayEditText.getText().toString();

                if(name.isEmpty() || gender.isEmpty() || day.isEmpty()){
                    mp3.start();
                    Toast.makeText(getApplicationContext(),"Field must not be empty !!", Toast.LENGTH_LONG).show();
                }else{
                    long rowId =  myDBFunctions.insertData(id,name,gender,day);

                    if(rowId > 0){
                        mp1.start();
                        Toast.makeText(getApplicationContext(),"Row "+rowId+" is successfully inserted", Toast.LENGTH_LONG).show();
                        clearField();
                        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(birthdayEditText.getWindowToken(),0);
                    }else{
                        mp3.start();
                        Toast.makeText(getApplicationContext(),"Data is not inserted", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        showBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ListViewActivity.class);
                startActivity(intent);
                mp2.start();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = idEditText.getText().toString();

                Integer deleteRows = myDBFunctions.deleteData(id);
                if(deleteRows > 0){
                    mp1.start();
                    Toast.makeText(getApplication(),"Data successfully deleted", Toast.LENGTH_LONG).show();
                    clearField();
                }
                else {
                    mp3.start();
                    Toast.makeText(getApplication(),"Data is not deleted!\nPlease select id to delete", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void clearField(){
        idEditText.setText("");
        nameEditText.setText("");
        genderEditText.setText("");
        birthdayEditText.setText("");
    }
}
