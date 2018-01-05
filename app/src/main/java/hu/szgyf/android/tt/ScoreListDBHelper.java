package hu.szgyf.android.tt;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class ScoreListDBHelper extends SQLiteOpenHelper {

    public ScoreListDBHelper(Context context) {
        super(context, "adatbazisnev", null, 12);
    }

    @Override
    public void onCreate(SQLiteDatabase krucifix) {
        krucifix.execSQL("" +
                "CREATE TABLE scorelist (" +
                "   id INTEGER PRIMARY KEY," +
                "   name TEXT," +
                "   score INTEGER" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS scorelist");
        onCreate(sqLiteDatabase);
    }

    // CRUD = Create + Read + Update + Delete

    public void saveScoreListItem(ScoreListItem scl) {

        if (scl.getId() != null)
        {
            // Update
            SQLiteDatabase sld = this.getWritableDatabase();

            ContentValues cv = new ContentValues();
            cv.put("id", scl.getId());
            cv.put("name", scl.getName());
            cv.put("score", scl.getScore());

            sld.update("scorelist",
                    cv,
                    "id = ?",
                    new String[] {scl.getId().toString(scl.getId())} );
        }
        else
        {
            // Insert (Create)
            SQLiteDatabase sld = this.getWritableDatabase();

            ContentValues cv = new ContentValues();
            cv.put("name", scl.getName());
            cv.put("score", scl.getScore());

            sld.insert("scorelist", null, cv );
        }
    }

    public ArrayList<ScoreListItem> getAllScoreListItems(){
        // READ
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor c =db.rawQuery("SELECT * FROM scorelist ORDER BY score ASC",null);

        ArrayList<ScoreListItem> out = new ArrayList<ScoreListItem>();

         if (c.moveToFirst())
             do {
                out.add(
                        new ScoreListItem(
                        c.getInt(c.getColumnIndex("id")),
                        c.getString(c.getColumnIndex("name")),
                        c.getInt(c.getColumnIndex("score"))
                        )
                );
             }while(c.moveToNext());

         return out;
    }

    public ScoreListItem getScoreListItemById(int Id){
        // READ
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor c =db.rawQuery(
                "SELECT * FROM scorelist WHERE id = ?",
                new String[] {""+Id}
                );

        if (c.moveToFirst())
                return
                        new ScoreListItem(
                                c.getInt(c.getColumnIndex("id")),
                                c.getString(c.getColumnIndex("name")),
                                c.getInt(c.getColumnIndex("score"))
                        );
        return null;
    }

    public void delScoreListItem(ScoreListItem sli)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(
                "scorelist",
                "id=?",
                new String[] {sli.getId().toString()}
                );
    }
}
