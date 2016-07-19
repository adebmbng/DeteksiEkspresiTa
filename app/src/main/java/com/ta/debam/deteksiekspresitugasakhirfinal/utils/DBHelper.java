package com.ta.debam.deteksiekspresitugasakhirfinal.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.ta.debam.deteksiekspresitugasakhirfinal.object.Ekspresi;
import com.ta.debam.deteksiekspresitugasakhirfinal.object.History;

import java.util.ArrayList;

/**
 * Created by Debam on 6/20/2016.
 */
public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, Constant.DB.DB_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL(
//                "CREATE TABLE `ekspresi` (" +
//                        "`_id`  int(11) NOT NULL," +
//                        "`name`  varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ," +
//                        "`smiling`  varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ," +
//                        "`righteye`  varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ," +
//                        "`lefteye`  varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ," +
//                        "`lips`  longtext CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL ," +
//                        "PRIMARY KEY (`_id`)" +
//                        ")"
//        );
        db.execSQL(
                "CREATE TABLE history (" +
                        "songtitle text," +
                        "ekspresi text" +
                        ")"
        );
    }

    public void insert2db(ArrayList<Ekspresi> listEkspresi){
        SQLiteDatabase db = this.getWritableDatabase();
        for(Ekspresi ee: listEkspresi ){
            ContentValues contentValues = new ContentValues();
            contentValues.put("_id",ee.getId());
            contentValues.put("name",ee.getNama());
            contentValues.put("smiling",ee.getBibir());
            contentValues.put("righteye",ee.getKanan());
            contentValues.put("lefteye",ee.getKiri());
            contentValues.put("lips",ee.getBibirPixel().toString());
        }
    }

    public void insert2history(String tit, String eks){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.w("SIZE", DatabaseUtils.queryNumEntries(db,"history")+"");

        ContentValues contentValues = new ContentValues();
        contentValues.put("songtitle", tit);
        contentValues.put("ekspresi",eks);
        db.insert("history", null, contentValues);

        Log.w("SIZE", DatabaseUtils.queryNumEntries(db, "history") + "");
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
    }

    public ArrayList<History> getHistory(){
        ArrayList<History> listH = new ArrayList<History>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cs = db.rawQuery("select * from history",null);

        cs.moveToFirst();

        while (cs.isAfterLast() == false){
            History h = new History();
            h.setJudul(cs.getString(cs.getColumnIndex("songtitle")));
            h.setEkspresi(cs.getString(cs.getColumnIndex("ekspresi")));

            listH.add(h);
            cs.moveToNext();
        }
        return listH;
    }
}
