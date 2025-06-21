package com.birthday.video.maker.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.view.Window;
import android.widget.TextView;

import com.birthday.video.maker.R;

public class ProgressBuilder {
    private final Context context;
    private Dialog dialog;
    private TextView loading_txt;
    private String text = "Please wait...";

    public ProgressBuilder(Context context) {
        this.context = context;


    }

    public void showProgressDialog() {
        try {
            dialog = new Dialog(context,R.style.Custom_Dialog);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(context.getResources().getDrawable(R.color.transparent));
            dialog.setContentView(R.layout.dialog_loading_lyt);
            loading_txt  = (TextView)dialog.findViewById(R.id.loading_dialog_txt);
            setDialogText(this.text);
            dialog.setCancelable(false);
            dialog.show();
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    public TextView getTextView()
    {
        return (TextView)dialog.findViewById(R.id.loading_dialog_txt);
    }

    public void setDialogText(String text){
        try {
            this.loading_txt.setText(text);
            this.text = text;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Dialog getDialog()
    {
        return dialog;
    }

    public void dismissProgressDialog() {
        try {
            if (dialog!=null&&dialog.isShowing())
            dialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
