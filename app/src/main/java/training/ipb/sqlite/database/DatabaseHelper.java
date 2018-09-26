package training.ipb.sqlite.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME =  "db_note";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL(DatabaseNote.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DatabaseNote.TABLE_NAME);

        onCreate(sqLiteDatabase);
    }

    public long insertNote(String note){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(DatabaseNote.NOTE, note);

        long id = sqLiteDatabase.insert(DatabaseNote._ID, null, values);

        sqLiteDatabase.close();

        return id;
    }

    public DatabaseNote getNote(long id){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(DatabaseNote.TABLE_NAME,
                new String[] {DatabaseNote._ID, DatabaseNote.NOTE, DatabaseNote.TIME},
                    DatabaseNote._ID + "=?",
                new String[] {String.valueOf(id)},null,null,null,null);

        if (cursor != null){
            cursor.moveToFirst();
        }

        DatabaseNote note = new DatabaseNote(
                cursor.getInt(cursor.getColumnIndex(DatabaseNote._ID)),
                cursor.getString(cursor.getColumnIndex(DatabaseNote.NOTE)),
                cursor.getString(cursor.getColumnIndex(DatabaseNote.TIME)));

        cursor.close();

        return note;

    }

    public List<DatabaseNote> getAllNotes(){
        List<DatabaseNote> notes = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + DatabaseNote.TABLE_NAME + " ORDER BY "+
                DatabaseNote.TIME + "DESC ";
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery,null);

        if (cursor.moveToFirst()){
            do {
                DatabaseNote note = new DatabaseNote();
                note.setId(cursor.getInt(cursor.getColumnIndex(DatabaseNote._ID)));
                note.setNote(cursor.getString(cursor.getColumnIndex(DatabaseNote.NOTE)));
                note.setTime(cursor.getString(cursor.getColumnIndex(DatabaseNote.TIME)));

                notes.add(note);
            } while (cursor.moveToNext());
        }
        sqLiteDatabase.close();

        return notes;
    }

    public int getNotesCount(){

        String countQuery = "SELECT * FROM " + DatabaseNote.TABLE_NAME;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(countQuery,null);

        int count = cursor.getCount();
        cursor.close();

        return count;
    }

    public int updateNote(DatabaseNote note){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseNote.NOTE,note.getNote());

        return sqLiteDatabase.update(DatabaseNote.NOTE,values,DatabaseNote._ID + " =? ",
                new String[] {String.valueOf(note.getId())});
    }

    public void deleteNote(DatabaseNote note){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        sqLiteDatabase.delete(DatabaseNote.NOTE,DatabaseNote._ID + " =? ",
                new String[]{String.valueOf(note.getId())});
        sqLiteDatabase.close();
    }


}
