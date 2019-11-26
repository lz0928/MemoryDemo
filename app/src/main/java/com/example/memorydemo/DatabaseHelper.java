package com.example.memorydemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 单例模式好处：
 * 将对象写成static，避免内存频繁实例化，因此对象在静态内存区只有一份。直接使用getInstance()取得对象。
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static DatabaseHelper helper;
    private Context context;

    private final static String DB_NAME = "my_info";
    private final static int VERSION = 1;


    private DatabaseHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    /**
     * 将 context.getApplicationContext() 赋值给 mContext，此时单例引用的对象是 Application，
     *
     * 而 Application 的生命周期本来就跟应用程序是一样的，也就不存在内存泄露。
     *
     * @param context
     * @return
     */
    //添加 synchronized关键字，使得getInstance()是一个同步方法，保证多线程情况下单例对象的唯一性。
    public static synchronized DatabaseHelper getInstance(Context context) {
        if (helper == null) {
            helper = new DatabaseHelper(context);
        }

        return helper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //建表语句直接使用db.execSQL(String sql)方法执行SQL建表语句
        String createTable = "create table user(id integer primary key,name text,age integer);";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static void insertInfo(Context context, InfoBean info) {
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getWritableDatabase();

        ContentValues value = new ContentValues();
        value.put("name", info.getName());
        value.put("age", info.getAge());
        db.insert("user", null, value);
        db.close();
    }

    public static void deleteInfo(Context context, int id) {
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getWritableDatabase();
        db.delete("user", "id=" + id, null);
        db.close();
    }

    public static void updateInfo(Context context, int id, InfoBean info) {
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", info.getName());
        values.put("age", info.getAge());
        db.update("user", values, "id=" + id, null);
        db.close();
    }

    public static InfoBean queryById(Context context, int id) {
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getWritableDatabase();
        Cursor cursor = db.rawQuery("select id, name,age from user where id=?", new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            InfoBean info = new InfoBean();
            int pid = cursor.getInt(cursor.getColumnIndex("id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            int age = cursor.getInt(cursor.getColumnIndex("age"));
            info.setName(name);
            info.setAge(age);
            cursor.close();
            return info;
        }

        cursor.close();
        return null;

    }

    public static List<InfoBean> getAll(Context context) {
        ArrayList<InfoBean> list = new ArrayList<>();
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getWritableDatabase();
        Cursor cursor = db.query("user", new String[]{"id", "name", "age"}, null, null, null, null, null);

        try {
            while (cursor != null && cursor.moveToNext()) {
                InfoBean info = new InfoBean();
                info.setId(cursor.getInt(cursor.getColumnIndex("id")));
                info.setName(cursor.getString(cursor.getColumnIndex("name")));
                info.setAge(cursor.getInt(cursor.getColumnIndex("age")));

                list.add(info);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

}
