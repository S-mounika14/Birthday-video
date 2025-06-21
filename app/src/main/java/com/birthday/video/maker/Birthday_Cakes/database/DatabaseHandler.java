package com.birthday.video.maker.Birthday_Cakes.database;

import static java.lang.Integer.valueOf;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.birthday.video.maker.stickerviewnew.TextInfo;

import java.util.ArrayList;

//import com.apptrends.calligraphynameart.calligraphy.name.art.photo.editor.stickerviewfortext.TextSticker;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String BG_ALPHA = "BG_ALPHA";
    private static final String BG_COLOR = "BG_COLOR";
    private static final String BG_DRAWABLE = "BG_DRAWABLE";
    private static final String COLORTYPE = "COLORTYPE";
    private static final String CREATE_TABLE_TEMPLATES = "CREATE TABLE TEMPLATES(TEMPLATE_ID INTEGER PRIMARY KEY,THUMB_URI TEXT,FRAME_NAME TEXT,RATIO TEXT,PROFILE_TYPE TEXT,SEEK_VALUE TEXT,TYPE TEXT,TEMP_PATH TEXT,TEMP_COLOR TEXT,OVERLAY_NAME TEXT,OVERLAY_OPACITY TEXT,OVERLAY_BLUR TEXT)";
    private static final String CREATE_TABLE_TEXT_INFO = "CREATE TABLE TEXT_INFO(TEXT_ID INTEGER PRIMARY KEY,TEMPLATE_ID TEXT,TEXT TEXT,FONT_NAME TEXT,TEXT_COLOR TEXT,TEXT_ALPHA TEXT,SHADOW_COLOR TEXT,SHADOW_PROG TEXT,SHADOW_X TEXT,SHADOW_Y TEXT,BG_DRAWABLE TEXT,BG_COLOR TEXT,BG_ALPHA TEXT,POS_X TEXT,POS_Y TEXT,WIDHT TEXT,HEIGHT TEXT,ROTATION TEXT,TYPE TEXT,ORDER_ TEXT,XROTATEPROG TEXT,YROTATEPROG TEXT,ZROTATEPROG TEXT,CURVEPROG TEXT,FIELD_ONE TEXT,FIELD_TWO TEXT,FIELD_THREE TEXT,FIELD_FOUR TEXT)";
    private static final String CURVEPROG = "CURVEPROG";
    private static final String DATABASE_NAME = "NAMEMAKER_DB";
    private static final String FIELD_FOUR = "FIELD_FOUR";
    private static final String FIELD_ONE = "FIELD_ONE";
    private static final String FIELD_THREE = "FIELD_THREE";
    private static final String FIELD_TWO = "FIELD_TWO";
    private static final String FONT_NAME = "FONT_NAME";
    private static final String FRAME_NAME = "FRAME_NAME";
    private static final String HEIGHT = "HEIGHT";
    private static final String ORDER = "ORDER_";
    private static final String OVERLAY_BLUR = "OVERLAY_BLUR";
    private static final String OVERLAY_NAME = "OVERLAY_NAME";
    private static final String OVERLAY_OPACITY = "OVERLAY_OPACITY";
    private static final String POS_X = "POS_X";
    private static final String POS_Y = "POS_Y";
    private static final String PROFILE_TYPE = "PROFILE_TYPE";
    private static final String RATIO = "RATIO";
    private static final String ROTATION = "ROTATION";
    private static final String SEEK_VALUE = "SEEK_VALUE";
    private static final String SHADOW_COLOR = "SHADOW_COLOR";
    private static final String SHADOW_PROG = "SHADOW_PROG";
    private static final String TEMPLATES = "TEMPLATES";
    private static final String TEMPLATE_ID = "TEMPLATE_ID";

    private static final String TEMP_COLOR = "TEMP_COLOR";
    private static final String TEMP_PATH = "TEMP_PATH";
    private static final String TEXT = "TEXT";

    private static final String SHADOW_X = "SHADOW_X";
    private static final String SHADOW_Y = "SHADOW_Y";
    private static final String TEXT_ALPHA = "TEXT_ALPHA";
    private static final String TEXT_COLOR = "TEXT_COLOR";
    private static final String TEXT_INFO = "TEXT_INFO";


    private static final String THUMB_URI = "THUMB_URI";
    private static final String TYPE = "TYPE";
    private static final String WIDHT = "WIDHT";
    private static final String XROTATEPROG = "XROTATEPROG";
    private static final String YROTATEPROG = "YROTATEPROG";
    private static final String Y_ROTATION = "Y_ROTATION";
    private static final String ZROTATEPROG = "ZROTATEPROG";


    private DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public static DatabaseHandler getDbHandler(Context context) {
        return new DatabaseHandler(context);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TEMPLATES);
        db.execSQL(CREATE_TABLE_TEXT_INFO);
        Log.i("testing", "Database Created");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS TEMPLATES");
        db.execSQL("DROP TABLE IF EXISTS TEXT_INFO");
        onCreate(db);
    }

    public long insertTemplateRow(TemplateInfo tInfo) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(THUMB_URI, tInfo.getTHUMB_URI());
        values.put(FRAME_NAME, tInfo.getFRAME_NAME());
        values.put(RATIO, tInfo.getRATIO());
        values.put(PROFILE_TYPE, tInfo.getPROFILE_TYPE());
        values.put(SEEK_VALUE, tInfo.getSEEK_VALUE());
        values.put(TYPE, tInfo.getTYPE());
        values.put(TEMP_PATH, tInfo.getTEMP_PATH());
        values.put(TEMP_COLOR, tInfo.getTEMPCOLOR());
        values.put(OVERLAY_NAME, tInfo.getOVERLAY_NAME());
        values.put(OVERLAY_OPACITY, tInfo.getOVERLAY_OPACITY());
        values.put(OVERLAY_BLUR, tInfo.getOVERLAY_BLUR());
        Log.i("testing", "Before insertion ");
        long insert = db.insert(TEMPLATES, null, values);
        Log.i("testing", "ID " + insert);
        Log.i("testing", "Framepath " + tInfo.getFRAME_NAME());
        Log.i("testing", "Thumb Path " + tInfo.getTHUMB_URI());
        db.close();
        return insert;
    }

    public void insertTextInfoRow(TextInfo tInfo) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TEMPLATE_ID, valueOf(tInfo.getTEMPLATE_ID()));
        values.put(TEXT, String.valueOf(tInfo.getTEXT()));
        values.put(FONT_NAME, tInfo.getFONT_NAME());
        values.put(TEXT_COLOR, valueOf(tInfo.getTEXT_COLOR()));
        values.put(TEXT_ALPHA, valueOf(tInfo.getTEXT_ALPHA()));
        values.put(SHADOW_COLOR, valueOf(tInfo.getSHADOW_COLOR()));
        values.put(SHADOW_PROG, valueOf(tInfo.getSHADOW_PROG()));
        values.put(BG_DRAWABLE, tInfo.getBG_DRAWABLE());
        values.put(BG_COLOR, valueOf(tInfo.getBG_COLOR()));
        values.put(BG_ALPHA, valueOf(tInfo.getBG_ALPHA()));
        values.put(POS_X, tInfo.getPOS_X());
        values.put(POS_Y, tInfo.getPOS_Y());
        values.put(SHADOW_X, tInfo.getSHADOW_X());
        values.put(SHADOW_Y, tInfo.getSHADOW_X());
        values.put(WIDHT, valueOf(tInfo.getWIDTH()));
        values.put(HEIGHT, valueOf(tInfo.getHEIGHT()));
        values.put(ROTATION, tInfo.getROTATION());
        values.put(TYPE, tInfo.getTYPE());
        values.put(ORDER, valueOf(tInfo.getORDER()));
        values.put(XROTATEPROG, valueOf((int) tInfo.getXRotateProg()));
        values.put(YROTATEPROG, valueOf((int) tInfo.getYRotateProg()));
        values.put(ZROTATEPROG, valueOf((int) tInfo.getZRotateProg()));
        values.put(CURVEPROG, valueOf(tInfo.getCurveRotateProg()));
        values.put(FIELD_ONE, valueOf(tInfo.getFIELD_ONE()));
        values.put(FIELD_TWO, tInfo.getFIELD_TWO());
        values.put(FIELD_THREE, tInfo.getFIELD_THREE());
        values.put(FIELD_FOUR, tInfo.getFIELD_FOUR());
        Log.i("testing", "Before TEXT insertion ");
        Log.i("testing", "TEXT ID " + db.insert(TEXT_INFO, null, values));
        db.close();
    }

    public ArrayList<TemplateInfo> getTemplateList(String type) {
        ArrayList templateList = new ArrayList();
        String query = "SELECT  * FROM TEMPLATES WHERE TYPE='" + type + "' ORDER BY " + TEMPLATE_ID + " ASC;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor == null || cursor.getCount() <= 0 || !cursor.moveToFirst()) {
            db.close();
            Log.e("templateList size is", "" + templateList.size());
        } else {
            do {
                TemplateInfo values = new TemplateInfo();
                values.setTEMPLATE_ID(cursor.getInt(0));
                values.setTHUMB_URI(cursor.getString(1));
                values.setFRAME_NAME(cursor.getString(2));
                values.setRATIO(cursor.getString(3));
                values.setPROFILE_TYPE(cursor.getString(4));
                values.setSEEK_VALUE(cursor.getString(5));
                values.setTYPE(cursor.getString(6));
                values.setTEMP_PATH(cursor.getString(7));
                values.setTEMPCOLOR(cursor.getString(8));
                values.setOVERLAY_NAME(cursor.getString(9));
                values.setOVERLAY_OPACITY(cursor.getInt(10));
                values.setOVERLAY_BLUR(cursor.getInt(11));
                templateList.add(values);
            } while (cursor.moveToNext());
            db.close();
            Log.e("templateList size is", "" + templateList.size());
        }
        return templateList;
    }



  /*  public ArrayList<TextInfo> getTextInfoList(int templateID) {
        ArrayList textInfoArrayList = new ArrayList();
        String query = "SELECT * FROM TEXT_INFO WHERE TEMPLATE_ID='" + templateID + "'";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor == null || cursor.getCount() <= 0 || !cursor.moveToFirst()) {
            db.close();
        }
        else {
            do {
                TextInfo values = new TextInfo();
                values.setTEXT_ID(cursor.getInt(0));
                values.setTEMPLATE_ID(cursor.getInt(1));
                values.setTEXT(cursor.getString(2));
                values.setFONT_NAME(cursor.getString(3));
                values.setTEXT_COLOR(cursor.getInt(4));
                values.setTEXT_ALPHA(cursor.getInt(5));
                values.setSHADOW_COLOR(cursor.getInt(6));
                values.setSHADOW_PROG(cursor.getInt(7));
                values.setSHADOW_X(cursor.getInt(8));
                values.setSHADOW_Y(cursor.getInt(9));
                values.setBG_DRAWABLE(cursor.getString(10));
                values.setBG_COLOR(cursor.getInt(11));
                values.setBG_ALPHA(cursor.getInt(12));
                values.setPOS_X(cursor.getFloat(13));
                values.setPOS_Y(cursor.getFloat(14));
                values.setWIDTH(cursor.getInt(15));
                values.setHEIGHT(cursor.getInt(16));
                values.setROTATION(cursor.getFloat(17));
                values.setTYPE(cursor.getString(18));
                values.setORDER(cursor.getInt(19));
                values.setXRotateProg(cursor.getInt(20));
                values.setYRotateProg(cursor.getInt(21));
                values.setZRotateProg(cursor.getInt(22));
                values.setCurveRotateProg(cursor.getInt(23));
                values.setFIELD_ONE(cursor.getInt(24));
                values.setFIELD_TWO(cursor.getString(25));
                values.setFIELD_THREE(cursor.getString(26));
                values.setFIELD_FOUR(cursor.getString(27));
                textInfoArrayList.add(values);
            }
            while (cursor.moveToNext());
            db.close();
        }
        return textInfoArrayList;
    }
*/

    public ArrayList<TextInfo> getTextInfoList(int templateID) {
        ArrayList<TextInfo> textInfoArrayList = new ArrayList<>(); // Added generic type for clarity
        String query = "SELECT * FROM TEXT_INFO WHERE TEMPLATE_ID='" + templateID + "'";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // Log the initial query and cursor status
        Log.i("Database", "Executing query: " + query);
        if (cursor == null || cursor.getCount() <= 0 || !cursor.moveToFirst()) {
            Log.w("Database", "No text info found for templateID: " + templateID +
                    ", Cursor is " + (cursor == null ? "null" : "empty, count: " + cursor.getCount()));
            db.close();
        } else {
            Log.d("Database", "Found " + cursor.getCount() + " text info entries for templateID: " + templateID);
            do {
                TextInfo values = new TextInfo();
                values.setTEXT_ID(cursor.getInt(0));
                values.setTEMPLATE_ID(cursor.getInt(1));
                values.setTEXT(cursor.getString(2));
                values.setFONT_NAME(cursor.getString(3));
                values.setTEXT_COLOR(cursor.getInt(4));
                values.setTEXT_ALPHA(cursor.getInt(5));
                values.setSHADOW_COLOR(cursor.getInt(6));
                values.setSHADOW_PROG(cursor.getInt(7));
                values.setSHADOW_X(cursor.getInt(8));
                values.setSHADOW_Y(cursor.getInt(9));
                values.setBG_DRAWABLE(cursor.getString(10));
                values.setBG_COLOR(cursor.getInt(11));
                values.setBG_ALPHA(cursor.getInt(12));
                values.setPOS_X(cursor.getFloat(13));
                values.setPOS_Y(cursor.getFloat(14));
                values.setWIDTH(cursor.getInt(15));
                values.setHEIGHT(cursor.getInt(16));
                values.setROTATION(cursor.getFloat(17));
                values.setTYPE(cursor.getString(18));
                values.setORDER(cursor.getInt(19));
                values.setXRotateProg(cursor.getInt(20));
                values.setYRotateProg(cursor.getInt(21));
                values.setZRotateProg(cursor.getInt(22));
                values.setCurveRotateProg(cursor.getInt(23));
                values.setFIELD_ONE(cursor.getInt(24));
                values.setFIELD_TWO(cursor.getString(25));
                values.setFIELD_THREE(cursor.getString(26));
                values.setFIELD_FOUR(cursor.getString(27));
                textInfoArrayList.add(values);

                // Log details of each TextInfo object
                Log.d("Database", "TextInfo Entry #" + textInfoArrayList.size() + ": " +
                        "TEXT_ID=" + values.getTEXT_ID() +
                        ", TEMPLATE_ID=" + values.getTEMPLATE_ID() +
                        ", TEXT=" + values.getTEXT() +
                        ", FONT_NAME=" + values.getFONT_NAME() +
                        ", TEXT_COLOR=" + values.getTEXT_COLOR() +
                        ", POS_X=" + values.getPOS_X() +
                        ", POS_Y=" + values.getPOS_Y() +
                        ", WIDTH=" + values.getWIDTH() +
                        ", HEIGHT=" + values.getHEIGHT());
            } while (cursor.moveToNext());

            // Log final size of the list
            Log.d("Database", "Total TextInfo entries retrieved: " + textInfoArrayList.size());
            db.close();
        }
        return textInfoArrayList;
    }
    public void resetDatabase() {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("DROP TABLE IF EXISTS " + "TEMPLATES");
        database.execSQL("DROP TABLE IF EXISTS " + "TEXT_INFO");
        database.execSQL(CREATE_TABLE_TEMPLATES);
        database.execSQL(CREATE_TABLE_TEXT_INFO);
        database.close();
    }

}
