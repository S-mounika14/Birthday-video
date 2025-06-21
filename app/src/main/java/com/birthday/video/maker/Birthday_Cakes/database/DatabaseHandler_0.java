package com.birthday.video.maker.Birthday_Cakes.database;

import static java.lang.Integer.valueOf;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.birthday.video.maker.Birthday_Video.activity.TextStickerProperties;

import java.util.ArrayList;

public class DatabaseHandler_0 extends SQLiteOpenHelper {
    private static final String CREATE_TABLE_TEMPLATES = "CREATE TABLE TEMPLATES(TEMPLATE_ID INTEGER PRIMARY KEY,THUMB_URI TEXT,FRAME_NAME TEXT,RATIO TEXT,PROFILE_TYPE TEXT,SEEK_VALUE TEXT,TYPE TEXT,TEMP_PATH TEXT,TEMP_COLOR TEXT,OVERLAY_NAME TEXT,OVERLAY_OPACITY TEXT,OVERLAY_BLUR TEXT)";
    private static final String CREATE_TABLE_TEXT_INFO = "CREATE TABLE TEXT_INFO(TEXT_ID INTEGER PRIMARY KEY,TEMPLATE_ID TEXT,newTEXT TEXT,textColor TEXT,rotate TEXT,textWidth TEXT,textHeight TEXT,POS_X TEXT,POS_Y TEXT,FONT_NAME TEXT)";
    private static final String DATABASE_NAME = "NAMEMAKER_DB";
    private static final String FRAME_NAME = "FRAME_NAME";
    private static final String OVERLAY_BLUR = "OVERLAY_BLUR";
    private static final String OVERLAY_NAME = "OVERLAY_NAME";
    private static final String OVERLAY_OPACITY = "OVERLAY_OPACITY";
    private static final String PROFILE_TYPE = "PROFILE_TYPE";
    private static final String RATIO = "RATIO";
    private static final String SEEK_VALUE = "SEEK_VALUE";
    private static final String TEMPLATES = "TEMPLATES";
    private static final String TEMPLATE_ID = "TEMPLATE_ID";

    private static final String TEXT_ID = "TEXT_ID";
    private static final String newTEXT = "newTEXT";
    private static final String textColor = "textColor";
    private static final String POS_X = "POS_X";
    private static final String POS_Y = "POS_Y";
    private static final String FONT_NAME = "FONT_NAME";
    private static final String rotate = "rotate";
    private static final String textHeight = "textHeight";
    private static final String textWidth = "textWidth";
    private static final String TEMP_COLOR = "TEMP_COLOR";
    private static final String TEMP_PATH = "TEMP_PATH";
    private static final String TEXT_INFO = "TEXT_INFO";
    private static final String THUMB_URI = "THUMB_URI";
    private static final String TYPE = "TYPE";
    private DatabaseHandler_0(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public static DatabaseHandler_0 getDbHandler(Context context) {
        return new DatabaseHandler_0(context);
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
    public void insertTextInfo(TextStickerProperties textStickerProperties) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TEMPLATE_ID, valueOf(textStickerProperties.getT_ID()));
        values.put(newTEXT, String.valueOf(textStickerProperties.getNewTEXT()));
        values.put(textColor, valueOf(textStickerProperties.getTextColor()));
        values.put(rotate, textStickerProperties.getRotationAngle());
        values.put(textWidth, valueOf(textStickerProperties.getTextWidth()));
        values.put(textHeight, valueOf(textStickerProperties.getTextHeight()));
        values.put(POS_X, textStickerProperties.getPOS_X());
        values.put(POS_Y, textStickerProperties.getPOS_Y());
        values.put(FONT_NAME, textStickerProperties.getFONT_NAME());
        Log.i("testing", "Before TEXT insertion ");
        Log.i("testing", "TEXT ID " + db.insert(TEXT_INFO, null, values));
        db.close();
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

    public ArrayList<TextStickerProperties> getNEWTextInfoList(int templateID) {
        ArrayList<TextStickerProperties> textInfoArrayList = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = getReadableDatabase();
            String query = "SELECT * FROM TEXT_INFO WHERE TEMPLATE_ID = ?";
            cursor = db.rawQuery(query, new String[]{String.valueOf(templateID)});

            if (cursor != null && cursor.moveToFirst()) {
                int textIdIndex = cursor.getColumnIndex(TEXT_ID);
                int templateIdIndex = cursor.getColumnIndex(TEMPLATE_ID);
                int newTextIndex = cursor.getColumnIndex(newTEXT);
                int textColorIndex = cursor.getColumnIndex(textColor);
                int rotateIndex = cursor.getColumnIndex(rotate);
                int txtWidth = cursor.getColumnIndex(textWidth);
                int txtHeight = cursor.getColumnIndex(textHeight);
                int posX=cursor.getColumnIndex(POS_X);
                int posY=cursor.getColumnIndex(POS_Y);
                int fontName=cursor.getColumnIndex(FONT_NAME);
                do {
                    TextStickerProperties values = new TextStickerProperties();
                    values.setTEXT_ID(textIdIndex >= 0 ? cursor.getInt(textIdIndex) : -1);
                    values.setT_ID(templateIdIndex >= 0 ? cursor.getInt(templateIdIndex) : -1);
                    values.setTextColor(textColorIndex >= 0 ? cursor.getInt(textColorIndex) : -1);
                    values.setNewTEXT(newTextIndex >= 0 ? cursor.getString(newTextIndex) : "N/A");
                    values.setRotationAngle(rotateIndex >= 0 ? cursor.getFloat(rotateIndex) : -1.0f);
                    values.setTextWidth( cursor.getInt(txtWidth));
                    values.setTextHeight(cursor.getInt(txtHeight));
                    values.setPOS_X(cursor.getInt(posX));
                    values.setPOS_Y(cursor.getInt(posY));
                    values.setFONT_NAME(cursor.getString(fontName));
                    textInfoArrayList.add(values);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return textInfoArrayList;
    }

//    public ArrayList<TextStickerProperties> getNEWTextInfoList(int templateID) {
//        ArrayList<TextStickerProperties> textInfoArrayList = new ArrayList<>();
//        String query = "SELECT * FROM TEXT_INFO WHERE TEMPLATE_ID='" + templateID + "'";
//        SQLiteDatabase db = getReadableDatabase();
//        Cursor cursor = db.rawQuery(query, null);
//        if (cursor == null || cursor.getCount() <= 0 || !cursor.moveToFirst()) {
//            db.close();
//        } else {
//            do {
//                TextStickerProperties values = new TextStickerProperties();
//                values.setTEXT_ID(cursor.getInt(0));
//                values.setT_ID(cursor.getInt(1));
//                values.setTextColor(cursor.getInt(2));
//                values.setNewTEXT(cursor.getString(3));
//                values.setRotationAngle(cursor.getFloat(4));
//                textInfoArrayList.add(values);
//            }
//            while (cursor.moveToNext());
//            db.close();
//        }
//        if (cursor != null) {
//            cursor.close();
//        }
//        db.close();
//        return textInfoArrayList;
//    }
    public void resetDatabase() {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("DROP TABLE IF EXISTS " + "TEMPLATES");
        database.execSQL("DROP TABLE IF EXISTS " + "TEXT_INFO");
        database.execSQL(CREATE_TABLE_TEMPLATES);
        database.execSQL(CREATE_TABLE_TEXT_INFO);
        database.close();
    }

    public void checkAndLogData() {
        logTemplatesData();
        logTextInfoData();
    }

    public void logTemplatesData() {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TEMPLATES;
        Cursor cursor = db.rawQuery(query, null);

        try {
            if (cursor != null && cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex(TEMPLATE_ID);
                int thumbUriIndex = cursor.getColumnIndex(THUMB_URI);
                int frameNameIndex = cursor.getColumnIndex(FRAME_NAME);
                int ratioIndex = cursor.getColumnIndex(RATIO);
                int profileTypeIndex = cursor.getColumnIndex(PROFILE_TYPE);
                int seekValueIndex = cursor.getColumnIndex(SEEK_VALUE);
                int typeIndex = cursor.getColumnIndex(TYPE);
                int tempPathIndex = cursor.getColumnIndex(TEMP_PATH);
                int tempColorIndex = cursor.getColumnIndex(TEMP_COLOR);
                int overlayNameIndex = cursor.getColumnIndex(OVERLAY_NAME);
                int overlayOpacityIndex = cursor.getColumnIndex(OVERLAY_OPACITY);
                int overlayBlurIndex = cursor.getColumnIndex(OVERLAY_BLUR);

                do {
                    int templateId = idIndex >= 0 ? cursor.getInt(idIndex) : -1;
                    String thumbUri = thumbUriIndex >= 0 ? cursor.getString(thumbUriIndex) : "N/A";
                    String frameName = frameNameIndex >= 0 ? cursor.getString(frameNameIndex) : "N/A";
                    String ratio = ratioIndex >= 0 ? cursor.getString(ratioIndex) : "N/A";
                    String profileType = profileTypeIndex >= 0 ? cursor.getString(profileTypeIndex) : "N/A";
                    String seekValue = seekValueIndex >= 0 ? cursor.getString(seekValueIndex) : "N/A";
                    String type = typeIndex >= 0 ? cursor.getString(typeIndex) : "N/A";
                    String tempPath = tempPathIndex >= 0 ? cursor.getString(tempPathIndex) : "N/A";
                    String tempColor = tempColorIndex >= 0 ? cursor.getString(tempColorIndex) : "N/A";
                    String overlayName = overlayNameIndex >= 0 ? cursor.getString(overlayNameIndex) : "N/A";
                    int overlayOpacity = overlayOpacityIndex >= 0 ? cursor.getInt(overlayOpacityIndex) : -1;
                    int overlayBlur = overlayBlurIndex >= 0 ? cursor.getInt(overlayBlurIndex) : -1;

                    Log.i("DatabaseHandler", "Template ID: " + templateId +
                            ", Thumb URI: " + thumbUri +
                            ", Frame Name: " + frameName +
                            ", Ratio: " + ratio +
                            ", Profile Type: " + profileType +
                            ", Seek Value: " + seekValue +
                            ", Type: " + type +
                            ", Temp Path: " + tempPath +
                            ", Temp Color: " + tempColor +
                            ", Overlay Name: " + overlayName +
                            ", Overlay Opacity: " + overlayOpacity +
                            ", Overlay Blur: " + overlayBlur);
                } while (cursor.moveToNext());
            } else {
                Log.i("DatabaseHandler", "No data found in TEMPLATES table.");
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
    }

    public void logTextInfoData() {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TEXT_INFO;
        Cursor cursor = db.rawQuery(query, null);

        try {
            if (cursor != null && cursor.moveToFirst()) {
                int textIdIndex = cursor.getColumnIndex(TEXT_ID);
                int templateIdIndex = cursor.getColumnIndex(TEMPLATE_ID);
                int newTextIndex = cursor.getColumnIndex(newTEXT);
                int textColorIndex = cursor.getColumnIndex(textColor);
                int rotateIndex = cursor.getColumnIndex(rotate);

                do {
                    int textId = textIdIndex >= 0 ? cursor.getInt(textIdIndex) : -1;
                    int templateId = templateIdIndex >= 0 ? cursor.getInt(templateIdIndex) : -1;
                    String newText = newTextIndex >= 0 ? cursor.getString(newTextIndex) : "N/A";
                    int textColor = textColorIndex >= 0 ? cursor.getInt(textColorIndex) : -1;
                    float rotate = rotateIndex >= 0 ? cursor.getFloat(rotateIndex) : -1.0f;

                    Log.i("DatabaseHandler", "Text ID: " + textId +
                            ", Template ID: " + templateId +
                            ", New Text: " + newText +
                            ", Text Color: " + textColor +
                            ", Rotation: " + rotate);
                } while (cursor.moveToNext());
            } else {
                Log.i("DatabaseHandler", "No data found in TEXT_INFO table.");
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
    }


}
