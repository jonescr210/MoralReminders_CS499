package cs499_uab_capstone_project.moralreminders;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

import cs499_uab_capstone_project.moralreminders.service.manager.InspiratorScheduleManager;

public class Moral_MainActivity extends AppCompatActivity {

    private String version = "";
    private InspiratorScheduleManager mScheduleManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moral__main);

        final Moral_Database moralDatabase = new Moral_Database(this);

        final Button happyButton = (Button) findViewById(R.id.happyButton);
        final Button sadButton = (Button) findViewById(R.id.sadButton);
        final Button angryButton = (Button) findViewById(R.id.angryButton);
        final Button loveButton = (Button) findViewById(R.id.loveButton);
        final Button lonelyButton = (Button) findViewById(R.id.lonelyButton);
        final Button faithButton = (Button) findViewById(R.id.faithButton);
        final Button updateButton = (Button) findViewById(R.id.update_button);

        final TextView versionNumber = (TextView) findViewById(R.id.version_number);
        versionNumber.setText(getVersionText(moralDatabase));

        mScheduleManager = new InspiratorScheduleManager(this);
        mScheduleManager.bindService();

        final Intent newIntent = new Intent(this, MessageActivity.class);

        happyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String[] message = moralDatabase.getMessage("Happy");
                Bundle b = new Bundle();
                b.putSerializable("message", message);
                newIntent.putExtras(b);
                startActivity(newIntent);
            }
        });
        sadButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String message[] = moralDatabase.getMessage("Sad");
                Bundle b = new Bundle();
                b.putSerializable("message", message);
                newIntent.putExtras(b);
                startActivity(newIntent);
            }
        });
        angryButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String message[] = moralDatabase.getMessage("Angry");
                Bundle b = new Bundle();
                b.putSerializable("message", message);
                newIntent.putExtras(b);
                startActivity(newIntent);            }
        });
        loveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String message[] = moralDatabase.getMessage("Love");
                Bundle b = new Bundle();
                b.putSerializable("message", message);
                newIntent.putExtras(b);
                startActivity(newIntent);            }
        });
        lonelyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String message[] = moralDatabase.getMessage("Lonely");
                Bundle b = new Bundle();
                b.putSerializable("message", message);
                newIntent.putExtras(b);
                startActivity(newIntent);            }
        });
        faithButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String message[] = moralDatabase.getMessage("Faith");
                Bundle b = new Bundle();
                b.putSerializable("message", message);
                newIntent.putExtras(b);
                startActivity(newIntent);            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int time =  calendar.get(Calendar.SECOND) + 10; //TODO - For testing only, delete this.
//                int time =  calendar.get(Calendar.HOUR_OF_DAY) + 8; //TODO - uncomment this
                calendar.set(Calendar.MINUTE, time);
                mScheduleManager.setAlarmForNotification(calendar);
                getWebMessages(moralDatabase);
            }
        });
    }
    public String getVersionText(Moral_Database db){
        try {
            String filePath = "versionNumber.txt";
            InputStream readVersion = openFileInput(filePath);
            if (readVersion != null) {
                InputStreamReader versionReader = new InputStreamReader(readVersion);
                BufferedReader br = new BufferedReader(versionReader);
                version = br.readLine();
            }
        } catch (FileNotFoundException e) {
            //Toast.makeText(getApplicationContext(), "Unable to determine version number - updating list!", Toast.LENGTH_LONG);
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return version;
    }

    public void getWebMessages(Moral_Database db){
        Message_Parser webParser = new Message_Parser(db);
        webParser.execute("https://www.dropbox.com/s/g7e2tm73zhmb9lh/data.txt?raw=1");
    }

    private class Message_Parser extends AsyncTask<String, String, String> {

        ProgressDialog pd;
        Moral_Database db;

        public Message_Parser(Moral_Database database){
            db = database;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(Moral_MainActivity.this);
            pd.setMessage("Retrieving new messages, please wait...");
            pd.setCancelable(false);
            pd.show();
        }

        protected  void onPostExecute(String result){
            TextView versionNumber = (TextView) findViewById(R.id.version_number);
            getVersionText(db);
            versionNumber.setText(version);
            super.onPostExecute(result);
            if (pd.isShowing()){
                pd.dismiss();
            }
            //Toast.makeText(getApplicationContext(), "Done!", Toast.LENGTH_SHORT);
        }

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader;

            try
            {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                String line = reader.readLine();
                FileOutputStream outputStream;
                if (!(line.equals(getVersionText(db))))
                {
                    try  {
                        outputStream = openFileOutput("versionNumber.txt", Context.MODE_PRIVATE);
                        outputStream.write(line.getBytes());
                        outputStream.close();
                    } catch (Exception e){
                        e.printStackTrace();
                    }

                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                        String[] values = line.split("::");
                        for (int i = 0; i < values.length; i++){
                            if (values[i].contains("'")){
                                System.out.println("ADSFASDFASDFA");
                                values[i] = values[i].replace("'", "''");
                            }
                        }
                        System.out.println(values[0] + " " + values[1] + " " + values[2]);
                        db.updateDatabase(values[2], values[0], values[1]);
                    }

                }
            } catch (MalformedURLException e) {
                    e.printStackTrace();
            } catch (IOException e) {
                    e.printStackTrace();
            }
            return null;
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        mScheduleManager.unBindService();
    }
}
