package com.ta.debam.deteksiekspresitugasakhirfinal.adaper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ta.debam.deteksiekspresitugasakhirfinal.R;
import com.ta.debam.deteksiekspresitugasakhirfinal.object.History;

import java.util.ArrayList;

/**
 * Created by Debam on 6/20/2016.
 */
public class HistoryAdapter extends ArrayAdapter<History> {

    ArrayList<History> list;
    Context ctx;

    public static class viewHolder{
        TextView judul;
        ImageView eks;
    }

    public HistoryAdapter(Context context, ArrayList<History> listH) {
        super(context, R.layout.item_history, listH );
        list = listH;
        ctx = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        viewHolder v;
        if(convertView == null){
            v = new viewHolder();
            LayoutInflater LI = LayoutInflater.from(getContext());
            convertView = LI.inflate(R.layout.item_history, parent, false);
            v.judul = (TextView) convertView.findViewById(R.id.h_judul);
            v.eks = (ImageView) convertView.findViewById(R.id.h_gambar);
            convertView.setTag(v);
        } else {
            v = (viewHolder) convertView.getTag();
        }

        v.judul.setText(list.get(position).getJudul());
        if (list.get(position).getEkspresi().equalsIgnoreCase("Senang")){
            v.eks.setImageResource(R.drawable.dialogue1);
        } else if (list.get(position).getEkspresi().equalsIgnoreCase("Sedih")){
            v.eks.setImageResource(R.drawable.dialogue3);
        } else if (list.get(position).getEkspresi().equalsIgnoreCase("Netral")){
            v.eks.setImageResource(R.drawable.dialogue2);
        }

        return convertView;
    }
}
