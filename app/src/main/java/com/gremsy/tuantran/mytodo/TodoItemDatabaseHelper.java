package com.gremsy.tuantran.mytodo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Administrator on 2/26/2016.
 */
public class TodoItemDatabaseHelper extends SQLiteOpenHelper {
    // Database Info
    private static final String DATABASE_NAME = "todoDatabase";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_NAME = "todoTable";

    // Table Columns
    private static final String ITEM_INDEX = "id";
    private static final String ITEM_ACTIVITY = "Activity";
    private static final String ITEM_PRIORITY = "Priority";
    public TodoItemDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                "(" +
                ITEM_INDEX + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                ITEM_ACTIVITY + " TEXT,"+
                ITEM_PRIORITY + " TEXT"+
                ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }

    // Insert item
    public boolean insertItem( String itemContent, String priority){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEM_ACTIVITY, itemContent);
        contentValues.put(ITEM_PRIORITY, priority);
        db.insert(TABLE_NAME, null, contentValues);
        return true;
    }

    // Delete item
    public int deleteItem(TodoTask item){
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME, ITEM_ACTIVITY + " =? ", new String[]{item.task});
        return result;
    }

    // Update item
    public boolean updateItem(int id, String content, String priority){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEM_ACTIVITY, content);
        contentValues.put(ITEM_PRIORITY, priority);
        db.update(TABLE_NAME, contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    // Get all items
    public ArrayList<TodoTask> getAllItems(){
        ArrayList<TodoTask> array_list = new ArrayList<TodoTask>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from " + TABLE_NAME, null);
        res.moveToFirst();
        while(!res.isAfterLast()){
//            array_list.add(res.getString(res.getColumnIndex(ITEM_ACTIVITY)));
            String task = res.getString(res.getColumnIndex(ITEM_ACTIVITY));
            String priority = res.getString(res.getColumnIndex(ITEM_PRIORITY));
            array_list.add(new TodoTask(task,priority));
            res.moveToNext();
        }
        return array_list;
    }

    public int getItemIndex(String activity){
        Integer index = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select " + ITEM_INDEX + " from " + TABLE_NAME+ " where "+ ITEM_ACTIVITY + " =? ", new String[]{activity});
        if(res.getCount()>0){
            res.moveToFirst();
            index = res.getInt(res.getColumnIndex(ITEM_INDEX));
        }
        return index;
    }

}
