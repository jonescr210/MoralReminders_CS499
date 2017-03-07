package cs499_uab_capstone_project.moralreminders;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;

import static android.R.attr.data;
import static android.R.attr.version;

/**
 * Created by Chris on 2/2/2017.
 */



public class Moral_Database extends SQLiteOpenHelper implements Serializable {

    public static final String DATABASE_NAME = "Moral_database";
    public static String DATABASE_FILE_PATH = "raw\\data.csv";
    private static InputStream data_stream;

    public Moral_Database(Context context) {
        super(context, DATABASE_NAME, null, 1);
        Resources res = context.getResources();
        //data_stream = res.openRawResource(R.raw.data);
    }

    @Override
    public void onCreate(SQLiteDatabase myDatabase) {
        myDatabase.execSQL("CREATE TABLE IF NOT EXISTS Happy(Message TEXT, Author TEXT)");
        myDatabase.execSQL("CREATE TABLE IF NOT EXISTS Sad(Message TEXT, Author TEXT)");
        myDatabase.execSQL("CREATE TABLE IF NOT EXISTS Angry(Message TEXT, Author TEXT)");
        myDatabase.execSQL("CREATE TABLE IF NOT EXISTS Faith(Message TEXT, Author TEXT)");
        myDatabase.execSQL("CREATE TABLE IF NOT EXISTS Lonely(Message TEXT, Author TEXT)");
        myDatabase.execSQL("CREATE TABLE IF NOT EXISTS Love(Message TEXT, Author TEXT)");

        myDatabase.execSQL("INSERT INTO Happy VALUES('Always be Happy!', 'Me')");
        myDatabase.execSQL("INSERT INTO Sad VALUES('Never be Sad!', 'You')");
        myDatabase.execSQL("INSERT INTO Angry VALUES('Angry is no good!', 'Some guy')");

//        BufferedReader reader;
//        try {
//            reader = new BufferedReader(new InputStreamReader(data_stream));
//            String line = "";
//            while ((line = reader.readLine()) != null) {
//                String[] messages = line.split("::");
//                System.out.println(messages);
//                myDatabase.execSQL("INSERT INTO Happy VALUES('" + messages[0] + "', '" + messages[1] + "')");
//            }
//        } catch (FileNotFoundException e) {
//            System.out.println("File not found!");
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }


    public String[] getMessage(String mood) {
        SQLiteDatabase myDatabase = this.getReadableDatabase();
        String[] message = new String[2];
        Cursor result = null;

        if (myDatabase != null) {
            result = myDatabase.rawQuery("Select * from " + mood + " ORDER BY RANDOM() LIMIT 1", null);
            result.moveToFirst();
            message[0] = result.getString(0);
            message[1] = result.getString(1);
        } else {
            message[0] = "Error!";
            message[1] = "Something broke!";
        }
        return message;
    }

    public void updateDatabase(String mood, String message, String author){
        SQLiteDatabase myDatabase = this.getWritableDatabase();
        if (myDatabase != null){
            myDatabase.execSQL("INSERT OR REPLACE into " + mood + " VALUES('" + message +
            "', '" + author + "')");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
