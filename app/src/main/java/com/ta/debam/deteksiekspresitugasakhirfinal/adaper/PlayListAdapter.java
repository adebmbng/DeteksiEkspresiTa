package com.ta.debam.deteksiekspresitugasakhirfinal.adaper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ta.debam.deteksiekspresitugasakhirfinal.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Debam on 5/3/2016.
 */
public class PlayListAdapter extends ArrayAdapter<String> {

    public static class viewHolder{
        TextView judul_lagu;
    }

    public PlayListAdapter(Context context, ArrayList<String> judul) {
        super(context, R.layout.list_titled, judul);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String judul = getItem(position);

        viewHolder v;
        if(convertView == null){
            v = new viewHolder();
            LayoutInflater LI = LayoutInflater.from(getContext());
            convertView = LI.inflate(R.layout.list_titled, parent, false);
            v.judul_lagu = (TextView) convertView.findViewById(R.id.list_judul);
            convertView.setTag(v);
        } else {
            v = (viewHolder) convertView.getTag();
        }

        v.judul_lagu.setText(judul);

        return convertView;
    }
}
