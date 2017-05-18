package co.edu.udea.quejatec.Clases;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Jesus Gomez on 04/05/2017.
 */

public class MiFirebaseInstanceIdService extends FirebaseInstanceIdService{
    public static final String TAG = "NOTICIAS";

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        //toca guardar este tokenn
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.i(TAG, "Token: "+token);
    }
}

