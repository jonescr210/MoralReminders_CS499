package cs499_uab_capstone_project.moralreminders;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        final TextView quoteText = (TextView) findViewById(R.id.quote_text);
        final TextView authorText = (TextView) findViewById(R.id.author_info);
        final Button saveButton = (Button) findViewById(R.id.saveButton);

        Bundle bundle = this.getIntent().getExtras();
        final Moral_Database helper = new Moral_Database(this);
        String[] message = new String[2];
        if (bundle != null)
            message = bundle.getStringArray("message");
        final String quote = message[0];
        final String author = message[1];
        quoteText.setText(quote);
        authorText.setText(author);

        quoteText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    Intent browser = new Intent (Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?espv=2&q=" + quoteText.getText()));
                    startActivity(browser);
                }
            }
        });
        authorText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    Intent browser = new Intent (Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?espv=2&q=" + authorText.getText()));
                    startActivity(browser);
                }
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    helper.saveQuote(quote, author);
                    saveButton.setEnabled(false);
                }
            }
        });
    }
}
