package hu.szgyf.android.tt;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        RelativeLayout rl = (RelativeLayout) findViewById(R.id.rl);
        /*rl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Toast.makeText(MainActivity.this,
                        "touched: " + event.getX() + ";" +event.getY(),
                        Toast.LENGTH_SHORT).show();

                TextView tv = new TextView(MainActivity.this);
                tv.setText("hello word");
                tv.setBackgroundColor(Color.CYAN);
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(200,30);
                lp.topMargin = (int)event.getY();
                lp.leftMargin = (int)event.getX();
                ((RelativeLayout) v).addView(tv, lp);

                return false;
            }
        });*/

        rl.post(new Runnable() {
            @Override
            public void run() {
                RelativeLayout rl = (RelativeLayout) findViewById(R.id.rl);
                Toast.makeText(MainActivity.this,
                        ""+rl.getWidth()+";"+rl.getHeight(),
                        Toast.LENGTH_LONG).show();

                int tilew=rl.getWidth()/3;
                int tileh=rl.getHeight()/3;

                for (int y=0;y<3;y++)
                    for (int x=0;x<3;x++)
                    {
                        TextView tv = new TextView(MainActivity.this);
                        tv.setText("hello word");
                        tv.setBackgroundColor(Color.CYAN);
                        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(tilew,tileh);
                        lp.topMargin = tileh * y;
                        lp.leftMargin = tilew * x;
                        rl.addView(tv, lp);
                    }
            }
        });
    }
}
