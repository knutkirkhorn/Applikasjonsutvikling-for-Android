package me.kirkhorn.knut.assignment_7;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Knut on 11.11.2017.
 */

public class DatabaseManager extends SQLiteOpenHelper {
    static final String TABLE_AUTHOR = "author";
    static final String TABLE_TITLE = "book";
    static final String TABLE_AUTHOR_BOOK = "author_book";
    static final String KEY_ROWID = "_id";
    static final String KEY_NAME = "name";
    static final String KEY_TITLE="title";
    static final String KEY_AUTHOR="author_id";
    static final String KEY_BOOK="book_id";

    static final String DATABASE_NAME = "BooksDatabase";
    static final int DATABASE_VERSION = 1;

    static final String DATABASE_CREATE1 = "create table " + TABLE_AUTHOR
            + " ("+KEY_ROWID+" integer primary key autoincrement, "
            + KEY_NAME +" text unique not null);";
    static final String DATABASE_CREATE2 = "create table " + TABLE_TITLE
            + " ("+KEY_ROWID+" integer primary key autoincrement, "
            + KEY_TITLE +" text unique not null);";
    static final String DATABASE_CREATE3 = "create table " + TABLE_AUTHOR_BOOK
            + " ("+KEY_ROWID+" integer primary key autoincrement, "
            + KEY_BOOK+" numeric, "
            + KEY_AUTHOR+" numeric,"
            + "FOREIGN KEY("+KEY_AUTHOR+") REFERENCES " + TABLE_AUTHOR + "("+KEY_ROWID+"), "
            + "FOREIGN KEY("+KEY_BOOK+") REFERENCES "+ TABLE_TITLE +"("+KEY_ROWID+")"
            + ");";

    private final String TAG = "DatabaseManager";

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i(TAG, "onCreate");
        sqLiteDatabase.execSQL(DATABASE_CREATE1);
        sqLiteDatabase.execSQL(DATABASE_CREATE2);
        sqLiteDatabase.execSQL(DATABASE_CREATE3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.i(TAG, "onUpgrade");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_AUTHOR_BOOK);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TITLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_AUTHOR);
        onCreate(sqLiteDatabase);
    }

    public void clean() {
        SQLiteDatabase database = this.getWritableDatabase();
        onUpgrade(database, 0, 0);
        database.close();
    }

    private long insertAuthor(String name, SQLiteDatabase database) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME, name);
        return database.insert(TABLE_AUTHOR, null, contentValues);
    }

    private long insertBook(String book, SQLiteDatabase database) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_TITLE, book);
        return database.insert(TABLE_TITLE, null, contentValues);
    }

    private long insertAuthor_book(long authorId, long titleId, SQLiteDatabase database) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_AUTHOR, authorId);
        contentValues.put(KEY_BOOK, titleId);
        return database.insert(TABLE_AUTHOR_BOOK, null, contentValues);
    }

    public long insert(String author, String book) {
        SQLiteDatabase database = this.getWritableDatabase();

        String[] columns = new String[]{KEY_ROWID, KEY_NAME};
        Cursor cursor = database.query(true, TABLE_AUTHOR, columns, KEY_NAME + "='" + author + "'", null, null, null, null, null);
        long authorId;
        if (cursor == null || cursor.getCount() == 0) {
            authorId = insertAuthor(author, database);
        } else {
            cursor.moveToFirst();
            authorId = cursor.getLong(0);
        }

        columns[1] = KEY_TITLE;
        cursor = database.query(TABLE_TITLE, new String[]{KEY_ROWID, KEY_TITLE}, KEY_TITLE + "='" + book + "'", null, null, null, null, null);

        long titleId;
        if (cursor == null || cursor.getCount() == 0) {
            titleId = insertBook(book, database);
        } else {
            cursor.moveToFirst();
            titleId = cursor.getLong(0);
        }

        long id = insertAuthor_book(authorId, titleId, database);
        database.close();
        return id;
    }

    public ArrayList<String> getAllAuthors() {
        SQLiteDatabase database = this.getReadableDatabase();

        String[] columns = new String[]{KEY_ROWID, KEY_NAME};
        Cursor cursor = database.query(TABLE_AUTHOR, columns, null, null, null, null, null, null);
        ArrayList<String> authors = new ArrayList<String>();

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                authors.add(cursor.getString(1));
                cursor.moveToNext();
            }
        }
        database.close();
        return authors;
    }

    public ArrayList<String> getAllBooks() {
        SQLiteDatabase database = this.getReadableDatabase();

        String[] columns = new String[]{KEY_ROWID, KEY_TITLE};
        Cursor cursor = database.query(TABLE_TITLE, columns, null, null, null, null, null, null);
        ArrayList<String> authors = new ArrayList<String>();

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                authors.add(cursor.getString(1));
                cursor.moveToNext();
            }
        }
        database.close();
        return authors;
    }
}
