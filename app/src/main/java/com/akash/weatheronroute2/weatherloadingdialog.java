package com.akash.weatheronroute2;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class weatherloadingdialog {
    Activity activity;
    AlertDialog dialog;




        weatherloadingdialog(Activity myActivity){
            activity = myActivity;

        }

        void startloading(){
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);

            LayoutInflater inflater = activity.getLayoutInflater();

            builder.setView(inflater.inflate(R.layout.custom_dialog,null));
            builder.setCancelable(true);

            dialog = builder.create();
            dialog.show();





        }

        void dismissloading(){
            dialog.dismiss();




        }

}
