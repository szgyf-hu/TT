package hu.szgyf.android.tt;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.zip.Inflater;

public class CustomArrayAdapter extends ArrayAdapter {

    public CustomArrayAdapter(@NonNull Context context) {
        super(context,0);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater i =
                (LayoutInflater) getContext().
                        getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View v = i.inflate(R.layout.scorelistitemlayout,null);

        return v;
    }
}
