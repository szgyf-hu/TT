package hu.szgyf.android.tt;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);

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

                Drawable d =
                        getResources().
                                getDrawable(R.drawable.hatterkep);

                rl.setBackground(d);

                Toast.makeText(MainActivity.this,
                        "" + rl.getWidth() + ";" + rl.getHeight(),
                        Toast.LENGTH_LONG).show();

                int tiles = (int) (
                        ((rl.getWidth() < rl.getHeight())
                                ?
                                (rl.getWidth())
                                :
                                (rl.getHeight())) * 0.9
                                /
                                ((COLUMN < ROW) ? (ROW) : (COLUMN))
                );


                int ox = (rl.getWidth() - tiles * COLUMN) / 2;
                int oy = (rl.getHeight() - tiles * ROW) / 2;

                int tmp = 0;

                for (int y = 0; y < ROW; y++)
                    for (int x = 0; x < COLUMN; x++) {
                        TextView tv = new TextView(MainActivity.this);
                        tvs[tmp] = tv;
                        tv.setText("hello word");
                        tv.setBackgroundColor(Color.CYAN);
                        tv.setId(tmp++);
                        tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                click(v.getId());
                            }
                        });

                        RelativeLayout.LayoutParams lp =
                                new RelativeLayout.LayoutParams(tiles, tiles);
                        lp.topMargin = oy + tiles * y;
                        lp.leftMargin = ox + tiles * x;
                        rl.addView(tv, lp);
                    }

                randomGen();
                refreshUi();
            }
        });
    }

    static final int COLUMN = 3;
    static final int ROW = 3;


    TextView tvs[] = new TextView[COLUMN * ROW];
    int values[] = new int[COLUMN * ROW];

    void refreshUi() {

        for (int i = 0; i < COLUMN * ROW; i++) {

            tvs[i].setText("" + values[i]);
            tvs[i].setVisibility(
                    values[i] == (COLUMN * ROW - 1)
                            ?
                            (View.INVISIBLE)
                            :
                            (View.VISIBLE)
            );
        }
    }

    void randomGen() {

        for (int i = 0; i < COLUMN * ROW; i++)
            values[i] = i;

        values[COLUMN * ROW - 1] = COLUMN * ROW - 2;
        values[COLUMN * ROW - 2] = COLUMN * ROW - 1;

        /*
        Random r = new Random();

        for (int i = 0; i < COLUMN * ROW; i++) {

            boolean oke;

            do {
                oke = true;
                values[i] = r.nextInt(COLUMN * ROW);
                for (int j = 0; (j < i) && (oke == true); j++) {
                    if (values[i] == values[j])
                        oke = false;
                }
            } while (oke != true);
        }
*/
    }

    int xy2id(int x, int y) {
        return y * COLUMN + x;
    }

    class pos {
        public int x;
        public int y;
    }

    pos id2xy(int id) {
        pos p = new pos();

        p.x = id % COLUMN;
        p.y = id / COLUMN;

        return p;
    }

    void click(int id) {
        pos p = id2xy(id);
        int x = p.x;
        int y = p.y;

        // Bal szomszéd vizsgálata

        if (x > 0)
            if (values[xy2id(x - 1, y)] == (COLUMN * ROW - 1)) {
                values[xy2id(x - 1, y)] = values[id];
                values[id] = (COLUMN * ROW - 1);
                refreshUi();
            }

        // jobb szomszéd

        if (x < (COLUMN - 1))
            if (values[xy2id(x + 1, y)] == (COLUMN * ROW - 1)) {
                values[xy2id(x + 1, y)] = values[id];
                values[id] = (COLUMN * ROW - 1);
                refreshUi();
            }

        // felső szomszéd

        if (y > 0)
            if (values[xy2id(x, y - 1)] == (COLUMN * ROW - 1)) {
                values[xy2id(x, y - 1)] = values[id];
                values[id] = (COLUMN * ROW - 1);
                refreshUi();
            }

        // alsó szomszéd

        if (y < (ROW - 1))
            if (values[xy2id(x, y + 1)] == (COLUMN * ROW - 1)) {
                values[xy2id(x, y + 1)] = values[id];
                values[id] = (COLUMN * ROW - 1);
                refreshUi();
            }

        if (checkGameOver()) {
            Toast.makeText(this, "Gém óvör!", Toast.LENGTH_LONG).show();

            MediaPlayer mp = MediaPlayer.create(this, R.raw.applause2);
            mp.start();
        }
    }

    boolean checkGameOver(){

        for (int i=0; i<COLUMN * ROW; i++)
            if(values[i] != i)
                return false;

        return true;
    }
}

