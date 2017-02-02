package cs499_uab_capstone_project.moralreminders;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.R.attr.version;

/**
 * Created by Chris on 2/2/2017.
 */



public class Moral_Database extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "Moral_database";

    public Moral_Database(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase myDatabase) {
        myDatabase.execSQL("CREATE TABLE IF NOT EXISTS Happy(Message TEXT, Author TEXT)");
        myDatabase.execSQL("CREATE TABLE IF NOT EXISTS Sad(Message TEXT, Author TEXT)");
        myDatabase.execSQL("CREATE TABLE IF NOT EXISTS Angry(Message TEXT, Author TEXT)");

        myDatabase.execSQL("INSERT INTO Happy VALUES('Always be Happy!', 'Me')");
        myDatabase.execSQL("INSERT INTO Sad VALUES('Never be Sad!', 'You')");
        myDatabase.execSQL("INSERT INTO Angry VALUES('Angry is no good!', 'Some guy')");
    }


    public String getMessage(String mood){
        SQLiteDatabase myDatabase = this.getReadableDatabase();
        if (myDatabase != null) {
            String message = "";
            Cursor result = myDatabase.rawQuery("Select * from " + mood, null);
            result.moveToFirst();
            message = result.getString(0) + " - " + result.getString(1);
            return message;
        }
        else {
            return "Error! Database not found";
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
