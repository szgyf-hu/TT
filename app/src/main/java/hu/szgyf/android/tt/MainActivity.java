package hu.szgyf.android.tt;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar().hide();

        Intent i = getIntent();
        Toast.makeText(this,
                i.getStringExtra("text"),
                Toast.LENGTH_LONG).show();

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

        if (savedInstanceState == null) {
            column = i.getIntExtra("xc",4);
            row = i.getIntExtra("yc",4);
        }

        rl.post(new Runnable() {
            @Override
            public void run() {
                newGame();
            }
        });
    }

    int column;
    int row;

    ImageView ivs[];
    int values[];
    Bitmap bmps[];

    long newGameTimestamp;

    void newGame() {

        if (values == null) {
            // nincs visszaállított állapot
            values = new int[column * row];
            randomGen();
        }

        RelativeLayout rl = (RelativeLayout) findViewById(R.id.rl);
        rl.removeAllViews();

        ivs = new ImageView[column * row];
        bmps = new Bitmap[column * row];

        Drawable d = getResources().getDrawable(R.drawable.hatterkep);
        rl.setBackground(d);

        int tiles = (int) (
                ((rl.getWidth() < rl.getHeight())
                        ?
                        (rl.getWidth())
                        :
                        (rl.getHeight())) * 0.9
                        /
                        ((column < row) ? (row) : (column))
        );

        int ox = (rl.getWidth() - tiles * column) / 2;
        int oy = (rl.getHeight() - tiles * row) / 2;

        int tmp = 0;

        for (int y = 0; y < row; y++)
            for (int x = 0; x < column; x++) {
                ImageView iv = new ImageView(MainActivity.this);
                ivs[tmp] = iv;
                //iv.setBackgroundColor(Color.CYAN);
                iv.setId(tmp++);
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        click(v.getId());
                    }
                });

                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(tiles, tiles);
                lp.topMargin = oy + tiles * y;
                lp.leftMargin = ox + tiles * x;
                rl.addView(iv, lp);
            }

        BitmapDrawable bd = (BitmapDrawable) getResources().getDrawable(R.drawable.monguz);
        Bitmap bmp = bd.getBitmap();

        int bmpw = bmp.getWidth() / column;
        int bmph = bmp.getHeight() / row;

        for (int i = 0; i < column * row; i++) {

            pos p = id2xy(i);
            bmps[i] = Bitmap.createBitmap(bmp,
                    p.x * bmpw,
                    p.y * bmph,
                    bmpw,
                    bmph);
        }

        //bmp.recycle();

        refreshUi();

        newGameTimestamp = System.currentTimeMillis();
    }

    void refreshUi() {

        for (int i = 0; i < column * row; i++) {
            ivs[i].setImageBitmap(bmps[values[i]]);
            ivs[i].setScaleType(ImageView.ScaleType.FIT_XY);

            ivs[i].setVisibility(
                    values[i] == (column * row - 1)
                            &&
                            isGameOver() == false
                            ?
                            (View.INVISIBLE)
                            :
                            (View.VISIBLE)
            );
        }
    }

    void randomGen() {
        for (int i = 0; i < column * row; i++)
            values[i] = i;

        values[column * row - 1] = column * row - 2;
        values[column * row - 2] = column * row - 1;

        /*
        Random r = new Random();

        for (int i = 0; i < column * row; i++) {

            boolean oke;

            do {
                oke = true;
                values[i] = r.nextInt(column * row);
                for (int j = 0; (j < i) && (oke == true); j++) {
                    if (values[i] == values[j])
                        oke = false;
                }
            } while (oke != true);
        }
*/
    }

    int xy2id(int x, int y) {
        return y * column + x;
    }

    class pos {
        public int x;
        public int y;
    }

    pos id2xy(int id) {
        pos p = new pos();

        p.x = id % column;
        p.y = id / column;

        return p;
    }

    void click(int id) {

        if (isGameOver())
            return;

        pos p = id2xy(id);
        int x = p.x;
        int y = p.y;

        // Bal szomszéd vizsgálata

        if (x > 0)
            if (values[xy2id(x - 1, y)] == (column * row - 1)) {
                values[xy2id(x - 1, y)] = values[id];
                values[id] = (column * row - 1);
                refreshUi();
            }

        // jobb szomszéd

        if (x < (column - 1))
            if (values[xy2id(x + 1, y)] == (column * row - 1)) {
                values[xy2id(x + 1, y)] = values[id];
                values[id] = (column * row - 1);
                refreshUi();
            }

        // felső szomszéd

        if (y > 0)
            if (values[xy2id(x, y - 1)] == (column * row - 1)) {
                values[xy2id(x, y - 1)] = values[id];
                values[id] = (column * row - 1);
                refreshUi();
            }

        // alsó szomszéd

        if (y < (row - 1))
            if (values[xy2id(x, y + 1)] == (column * row - 1)) {
                values[xy2id(x, y + 1)] = values[id];
                values[id] = (column * row - 1);
                refreshUi();
            }

        if (isGameOver()) {

            int sec = (int) (System.currentTimeMillis() - newGameTimestamp) / 1000;

            Toast.makeText(this, "Gém óvör! sec:" + sec, Toast.LENGTH_LONG).show();

            new ScoreListDBHelper(this).
                    saveScoreListItem(
                            new ScoreListItem("vasmacska", sec)
                    );

            MediaPlayer mp = MediaPlayer.create(this, R.raw.applause2);
            mp.start();
        }
    }

    boolean isGameOver() {

        for (int i = 0; i < column * row; i++)
            if (values[i] != i)
                return false;

        return true;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("column", column);
        outState.putInt("row", row);
        outState.putIntArray("values", values);
        outState.putLong("newGameTimestamp", newGameTimestamp);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        column = savedInstanceState.getInt("column");
        row = savedInstanceState.getInt("row");
        values = savedInstanceState.getIntArray("values");
        newGameTimestamp = savedInstanceState.getLong("newGameTimestamp");
    }
}

