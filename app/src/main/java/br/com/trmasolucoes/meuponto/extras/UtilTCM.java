package br.com.trmasolucoes.meuponto.extras;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class UtilTCM {
    public static boolean verifyConnection(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }
}
