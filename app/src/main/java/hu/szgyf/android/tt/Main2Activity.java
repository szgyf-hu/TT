package hu.szgyf.android.tt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ArrayList<ScoreListItem> al=
                new ScoreListDBHelper(this).getAllScoreListItems();

        CustomArrayAdapter caa = new CustomArrayAdapter(this);
        caa.addAll(al);

        ListView lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(caa);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                ScoreListItem sli=new ScoreListDBHelper(Main2Activity.this).getScoreListItemById((int)id);

                Toast.makeText(Main2Activity.this,sli.toString(),Toast.LENGTH_LONG)
                        .show();
            }
        });

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
