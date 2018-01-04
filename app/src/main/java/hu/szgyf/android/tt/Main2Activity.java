package hu.szgyf.android.tt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ((Button) findViewById(R.id.startgame)).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Main2Activity.this,
                                MainActivity.class);
                        i.putExtra("text", "helloka");
                        i.putExtra("xc",
                                Integer.parseInt(
                                        ((TextView) findViewById(R.id.xtext)).getText().toString()
                                )
                        );
                        i.putExtra("yc",
                                Integer.parseInt(
                                        ((TextView) findViewById(R.id.ytext)).getText().toString()
                                ));
                        startActivity(i);
                    }
                });
    }
}
