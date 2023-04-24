package com.example.assingment_coffee;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LoginDB extends SQLiteOpenHelper {

    public static final String DBNAME = "User.db";

    public LoginDB(Context context){
        super(context,"User.db",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase UserDB) {
        UserDB.execSQL("create Table users(username Text primary key, phone_number TEXT, email Text, password Text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase UserDB, int i, int i1) {
        UserDB.execSQL("drop Table if exists users");
    }

    public Boolean insertData(String username, String password,String email, String phone_number){
        SQLiteDatabase UserDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("email", email);
        contentValues.put("phone_number", phone_number);

        long result = UserDB.insert("users",null,contentValues);
        if(result == -1) return false;
        else return true;
    }

    public Boolean checkusername(String username){
        SQLiteDatabase UserDB = this.getWritableDatabase();
        Cursor cursor = UserDB.rawQuery("Select * from users where username = ?", new String[] {username});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public Boolean checkusernamepassword(String username, String password){
        SQLiteDatabase UserDB = this.getWritableDatabase();
        Cursor cursor = UserDB.rawQuery("Select * from users where username = ? and password = ?",new String[] {username,password});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public Boolean checkusernameemail(String username, String email){
        SQLiteDatabase UserDB = this.getWritableDatabase();
        Cursor cursor = UserDB.rawQuery("Select * from users where username = ? and email = ?", new String[] {username,email});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public Boolean updatepassword(String username, String email, String password){
       SQLiteDatabase UserDB = this.getWritableDatabase();
       ContentValues contentValues = new ContentValues();
       contentValues.put("password", password);
       Cursor cursor = UserDB.rawQuery("Select * from users where username = ? and email = ?", new String[] {username, email});
       if(cursor.getCount()>0){
           long result = UserDB.update("users", contentValues,"username = ? and email = ? ",new String[] {username,email});
           if(result==-1){
               return false;
           }else{
               return true;
           }
       }else {
           return false;
       }
    }
}
