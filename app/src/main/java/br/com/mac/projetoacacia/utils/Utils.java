package br.com.mac.projetoacacia.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.Toast;

import br.com.mac.projetoacacia.ui.activity.LoginActivity;

public class Utils {
    public static boolean isConnected(final Context context){
        final ConnectivityManager conmag;
        conmag = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        conmag.getActiveNetworkInfo();

        if(conmag.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected()){
            return true;
        }

        if(conmag.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected()){
            return true;
        }

        return false;
    }

    public static void showToast(final Context context, final String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}