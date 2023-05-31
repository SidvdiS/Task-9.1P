package com.example.task91p.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.task91p.model.Advert;
import com.example.task91p.util.AdvertUtil;

import java.util.ArrayList;

public class AdvertDatabaseHelper extends SQLiteOpenHelper {
    public AdvertDatabaseHelper(@Nullable Context context) {
        super(context, AdvertUtil.DATABASE_NAME, null, AdvertUtil.DATABASE_VERSION);
    }

    private final String CREATE_TABLE = "CREATE TABLE " + AdvertUtil.TABLE_NAME + "(" + AdvertUtil.ADVERT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
            + AdvertUtil.ADVERT_TYPE+" TEXT , " + AdvertUtil.ADVERT_NAME+" TEXT , " + AdvertUtil.ADVERT_DESC+" TEXT , "
            + AdvertUtil.DATE+" TEXT , " + AdvertUtil.LATITUDE+" TEXT , " + AdvertUtil.LONGITUDE+" TEXT)";

    private final String DROP_TABLE = "DROP TABLE IF EXISTS " + AdvertUtil.TABLE_NAME;

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DROP_TABLE);
        onCreate(sqLiteDatabase);
    }

    public long insertAdvert(Advert advert) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(AdvertUtil.ADVERT_TYPE, advert.getType());
        contentValues.put(AdvertUtil.ADVERT_NAME, advert.getName());
        contentValues.put(AdvertUtil.ADVERT_DESC, advert.getDescription());
        contentValues.put(AdvertUtil.DATE, advert.getDate());
        contentValues.put(AdvertUtil.LATITUDE, advert.getLatitude());
        contentValues.put(AdvertUtil.LONGITUDE, advert.getLongitude());
        long insert = db.insert(AdvertUtil.TABLE_NAME,null, contentValues);
        db.close();
        return insert;
    }

    public ArrayList<Advert> getAllAdverts(){
        SQLiteDatabase db =this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ AdvertUtil.TABLE_NAME, null);
        ArrayList<Advert> adverts = new ArrayList<>();
        while(cursor.moveToNext()){
            Advert advert = new Advert();
            advert.setAdvertID(cursor.getInt(0));
            advert.setType(cursor.getString(1));
            advert.setName(cursor.getString(2));
            advert.setDescription(cursor.getString(3));
            advert.setDate(cursor.getString(4));
            advert.setLatitude(cursor.getString(5));
            advert.setLongitude(cursor.getString(6));
            adverts.add(advert);
        }
        cursor.close();
        db.close();
        return adverts;
    }

    public void deleteAdvert(int advertID){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(AdvertUtil.TABLE_NAME, AdvertUtil.ADVERT_ID + "=?", new String[]{String.valueOf(advertID)});
    }

    public Advert getAdvertDetails(int advertID){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + AdvertUtil.TABLE_NAME + " WHERE " + AdvertUtil.ADVERT_ID + "=" +advertID, null);
        Advert advert = new Advert();
        while(cursor.moveToNext()){
            advert.setAdvertID(cursor.getInt(0));
            advert.setType(cursor.getString(1));
            advert.setName(cursor.getString(2));
            advert.setDescription(cursor.getString(3));
            advert.setDate(cursor.getString(4));
            advert.setLatitude(cursor.getString(5));
            advert.setLongitude(cursor.getString(6));
        }
        db.close();
        return advert;
    }
}
