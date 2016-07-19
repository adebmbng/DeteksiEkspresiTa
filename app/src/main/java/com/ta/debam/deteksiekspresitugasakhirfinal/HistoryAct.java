package com.ta.debam.deteksiekspresitugasakhirfinal;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.ta.debam.deteksiekspresitugasakhirfinal.adaper.HistoryAdapter;
import com.ta.debam.deteksiekspresitugasakhirfinal.object.History;
import com.ta.debam.deteksiekspresitugasakhirfinal.utils.DBHelper;

import java.util.ArrayList;

/**
 * Created by Debam on 6/20/2016.
 */
public class HistoryAct extends AppCompatActivity {

    ListView mList;
    DBHelper mydb;

    ArrayList<History> listHistory;

    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_layout);
        ctx = getApplicationContext();

        mList   = (ListView) findViewById(R.id.listHistory);
        mydb    = new DBHelper(ctx);

        listHistory = mydb.getHistory();

        HistoryAdapter HA = new HistoryAdapter(ctx,listHistory);
        mList.setAdapter(HA);


    }
}
