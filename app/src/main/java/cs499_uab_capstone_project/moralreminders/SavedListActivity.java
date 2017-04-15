package cs499_uab_capstone_project.moralreminders;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class SavedListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_list2);

        final TextView savedText = (TextView) findViewById(R.id.saved_Text);

        Bundle bundle = this.getIntent().getExtras();
        final Moral_Database helper = new Moral_Database(this);
        String[] message = new String[0];
        if (bundle != null)
            message = bundle.getStringArray("message");
        String total = "";
        for (int i = 0; i < message.length; i++){
            String author = message[i];
            i = i + 1;
            String quote = message[i];
            total = quote + " - " + author + "\n\n" + total;
        }
        savedText.setText(total);
        savedText.setMovementMethod(new ScrollingMovementMethod());
    }
}
