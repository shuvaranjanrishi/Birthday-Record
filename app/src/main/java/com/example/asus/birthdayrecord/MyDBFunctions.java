package com.example.asus.birthdayrecord;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDBFunctions extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "birthday.db";
    private static final String TABLE_NAME = "birthday_details";

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String GENDER = "gender";
    private static final String BIRTHDAY = "birthday";

    private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+"( "+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+NAME+" TEXT(15), "+GENDER+" INTEGER(10), "+BIRTHDAY+" TEXT(15));";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS "+TABLE_NAME;
    private Context context;

    public MyDBFunctions(Context context) {
        super(context, DATABASE_NAME, null, 3);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            Toast.makeText(context,"onCreate is called", Toast.LENGTH_LONG).show();
            sqLiteDatabase.execSQL(CREATE_TABLE);
        }catch (Exception e){
            Toast.makeText(context,"Exception : "+e, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        try {
            Toast.makeText(context,"onUgrade is called", Toast.LENGTH_LONG).show();
            sqLiteDatabase.execSQL(DROP_TABLE);
            onCreate(sqLiteDatabase);
        }catch (Exception e){
            Toast.makeText(context,"Exception : "+e, Toast.LENGTH_LONG).show();
        }
    }

    public long insertData(String id,String name,String gender,String day){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(ID,id);
        contentValues.put(NAME,name);
        contentValues.put(GENDER,gender);
        contentValues.put(BIRTHDAY,day);

        long rowId = sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        return rowId;
    }

    public String[] showData(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_NAME, null);

        String[] receiveData = new String[cursor.getCount()];
        cursor.moveToFirst();

        if(cursor.moveToFirst()){
            int counter = 0;
            do{
                receiveData[counter] = "Id: "+cursor.getString(cursor.getColumnIndex(ID+""))+"\nName: "+cursor.getString(cursor.getColumnIndex(NAME+""))+"\nGender: "+cursor.getString(cursor.getColumnIndex(GENDER+""))+"\nBirthday: "+cursor.getString(cursor.getColumnIndex(BIRTHDAY+""));
                counter ++;
            }while (cursor.moveToNext());
        }
        return receiveData;
    }

    public Integer deleteData(String id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete(TABLE_NAME, "ID = ?", new String[]{id});
    }
}
