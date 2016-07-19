package com.ta.debam.deteksiekspresitugasakhirfinal.adaper;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ta.debam.deteksiekspresitugasakhirfinal.R;

/**
 * Created by Debam on 5/8/2016.
 */
public class DialogAdapter {
    private Dialog dialog;

    public void showDialog(Activity activity,  String ttl, String msg, int img, int color){
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_layout);

        LinearLayout header = (LinearLayout) dialog.findViewById(R.id.headerdialog);

        ImageView iv = (ImageView) dialog.findViewById(R.id.img_dialog);
        iv.setImageResource(img);

        iv.setBackgroundColor(color);
        header.setBackgroundColor(color);

        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        text.setText(msg);

        TextView title = (TextView) dialog.findViewById(R.id.text_dialog_tittle);
        title.setText(ttl);

        ImageView cls = (ImageView) dialog.findViewById(R.id.imageView_close);
        cls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

}
