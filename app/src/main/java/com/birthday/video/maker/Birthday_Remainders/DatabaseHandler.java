package com.birthday.video.maker.Birthday_Remainders;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "birthdayManager";
    public static final int DATABASE_VERSION = 1;
    public static final String KEY_BIRTHDAY = "birthday";
    public static final String KEY_ID = "_id";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_NAME = "name";
    public static final String TABLE_USERS = "users";
    public static final String PREF_NAME = "BirthdayApp";
    private static int PRIVATE_MODE;
    private static SharedPreferences prefs;
    private String colname;

    static class AgeComparator implements Comparator<User> {
        AgeComparator() {
        }

        public int compare(User p1, User p2) {
            Calendar dob = Calendar.getInstance();
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(p1.get_birthday());
            dob.setTime(cal.getTime());
            Calendar today2 = Calendar.getInstance();
            Calendar next = Calendar.getInstance();
            next.set(today2.get(Calendar.YEAR), dob.get(Calendar.MONTH), dob.get(Calendar.DAY_OF_MONTH));
            if (next.getTimeInMillis() < today2.getTimeInMillis()) {
                next.set(today2.get(Calendar.YEAR) + Calendar.YEAR, dob.get(Calendar.MONTH), dob.get(Calendar.DAY_OF_MONTH));
            }
            Date startdate = today2.getTime();
            Date enddate = next.getTime();
            long elapsedDays = (enddate.getTime() - startdate.getTime()) / 86400000;
            Calendar dob2 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal2.setTimeInMillis(p2.get_birthday());
            dob2.setTime(cal2.getTime());
            Calendar today22 = Calendar.getInstance();
            Calendar next2 = Calendar.getInstance();
            next2.set(today22.get(Calendar.YEAR), dob2.get(Calendar.MONTH), dob2.get(Calendar.DAY_OF_MONTH));
            if (next2.getTimeInMillis() < today22.getTimeInMillis()) {
                next2.set(today22.get(Calendar.YEAR) + Calendar.YEAR, dob2.get(Calendar.MONTH), dob2.get(Calendar.DAY_OF_MONTH));
            }
            Date startdate2 = today22.getTime();
            Date enddate2 = next2.getTime();
            long elapsedDays2 = (enddate2.getTime() - startdate2.getTime()) / 86400000;
            if (elapsedDays == elapsedDays2) {
                return 0;
            }
            if (elapsedDays > elapsedDays2) {
                return Calendar.YEAR;
            }
            return -1;
        }
    }

     static class  NameComparator implements Comparator<User> {

        NameComparator()
        {
        }
         @Override
         public int compare(User lhs, User rhs) {
              return lhs.get_name().compareToIgnoreCase(rhs.get_name());
         }
     }

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users(_id integer primary key autoincrement, name TEXT,birthday INTEGER,image TEXT)");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        onCreate(db);
    }

    public void addUser(User user) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(KEY_NAME, user.get_name());
        value.put(KEY_BIRTHDAY, user.get_birthday());
        value.put(KEY_IMAGE, user.get_image());
        db.insert(TABLE_USERS, null, value);
        db.close();
    }

    public User getUser(int id) {
        SQLiteDatabase db = getReadableDatabase();
        String str = TABLE_USERS;
        String[] strArr = new String[]{KEY_ID, KEY_NAME, KEY_BIRTHDAY, KEY_IMAGE};
        String[] strArr2 = new String[DATABASE_VERSION];
        strArr2[0] = String.valueOf(id);
        Cursor cursor = db.query(str, strArr, "_id=?", strArr2, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return new User(Integer.parseInt(cursor.getString(0)), cursor.getString(DATABASE_VERSION), Long.parseLong(cursor.getString(2)), cursor.getString(3));
    }

    public List<User> getAllUsers(Context c) {
        List<User> usersList = new ArrayList();
        Cursor cursor = getWritableDatabase().rawQuery("SELECT * FROM users", null);
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.set_id(Integer.parseInt(cursor.getString(0)));
                user.set_name(cursor.getString(DATABASE_VERSION));
                user.set_birthday(Long.parseLong(cursor.getString(2)));
                user.set_image(cursor.getString(3));
                usersList.add(user);
            } while (cursor.moveToNext());
        }
        prefs = c.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
         int dayofnotification = prefs.getInt(BirthdayRemainderSettings.KEY_ORDER, 0);
         int dayofnotification1 = prefs.getInt(BirthdayRemainderSettings.KEY_ORDER, 1);
        if(dayofnotification==0)
        {
            Collections.sort(usersList, new AgeComparator());
        }else if(dayofnotification1==1)
        {

            Collections.sort(usersList, new NameComparator());
        }

        return usersList;
    }
    public int rowsCount() {

        int count = 0;
        String countQuery = "SELECT  * FROM users" ;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        if (cursor != null && !cursor.isClosed()) {
            count = cursor.getCount();
            cursor.close();
        }
        return count;
    }

    public String getname()
    {
        SQLiteDatabase sqldb = this.getReadableDatabase();
        try {
            Cursor c = sqldb.query(TABLE_USERS, null, null, null, null, null, null);
            if (c != null) {
                int num = c.getColumnCount();
                for (int i = 0; i < num; ++i) {
                     colname = c.getColumnName(i);

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return colname;
    }

    public String retriveDetails(int uid) {
        SQLiteDatabase sqldb = this.getReadableDatabase();
        String amnt = "";
        Cursor c = sqldb.rawQuery("select " + KEY_NAME + " from " + TABLE_USERS + " where _id ='" + uid + "'", null);
        if (c != null && (c.moveToNext())) {
            if (c.getCount() > 0) {
                amnt = c.getString(0);
            }
        }
        c.close();
        return amnt;
    }

    public boolean hasObject(String id) {
        SQLiteDatabase db = getWritableDatabase();
        String selectString = "SELECT * FROM " + TABLE_USERS + " WHERE " + KEY_NAME + " =?";

        // Add the String you are searching by here.
        // Put it in an array to avoid an unrecognized token error
        Cursor cursor = db.rawQuery(selectString, new String[]{id});

        boolean hasObject = false;
        if(cursor.moveToFirst()){
            hasObject = true;

            //region if you had multiple records to check for, use this region.

            int count = 0;
            while(cursor.moveToNext()){
                count++;
            }
            //here, count is records found

            //endregion

        }

        cursor.close();          // Dont forget to close your cursor
        db.close();              //AND your Database!
        return hasObject;
    }

    public int updateUser(User user) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.get_name());
        values.put(KEY_BIRTHDAY, Long.valueOf(user.get_birthday()));
        values.put(KEY_IMAGE, user.get_image());
        String[] strArr = new String[DATABASE_VERSION];
        strArr[0] = String.valueOf(user.get_id());
        return db.update(TABLE_USERS, values, "_id = ?", strArr);
    }

    public void deleteUser(User user) {
        SQLiteDatabase db = getWritableDatabase();
        String[] strArr = new String[DATABASE_VERSION];
        strArr[0] = String.valueOf(user.get_id());
        db.delete(TABLE_USERS, "_id = ?", strArr);
        db.close();
    }

}
