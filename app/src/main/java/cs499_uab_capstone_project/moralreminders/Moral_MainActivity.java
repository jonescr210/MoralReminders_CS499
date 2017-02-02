package cs499_uab_capstone_project.moralreminders;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Moral_MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moral__main);

        final Moral_Database moralDatabase = new Moral_Database(this);

        final Button happyButton = (Button) findViewById(R.id.happyButton);
        final Button sadButton = (Button) findViewById(R.id.sadButton);
        final Button angryButton = (Button) findViewById(R.id.angryButton);

        final TextView messageText = (TextView) findViewById(R.id.moral_message);

        happyButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String message = moralDatabase.getMessage("Happy");
                messageText.setText(message);
            }
        });
        sadButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String message = moralDatabase.getMessage("Sad");
                messageText.setText(message);
            }
        });
        angryButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String message = moralDatabase.getMessage("Angry");
                messageText.setText(message);
            }
        });
    }
}
