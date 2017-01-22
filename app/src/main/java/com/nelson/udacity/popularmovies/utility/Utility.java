package com.nelson.udacity.popularmovies.utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by nelrc on 1/2/2017.
 */

public class Utility {
    private Context context;
    public Utility(Context context){
        this.context = context;
    }

    public boolean isConnectedToInternet() {
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnectedOrConnecting());
    }


}
