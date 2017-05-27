package co.edu.udea.quejatec.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import co.edu.udea.quejatec.Activities.MainActivity;
import co.edu.udea.quejatec.R;

/**
 * Created by AW 13 on 15/05/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public MyFirebaseMessagingService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Map<String, String> mensaje = remoteMessage.getData();
        String msj = mensaje.get("body");
        ShowNotification(msj);
        Log.e("mensaje",""+ mensaje);

    }

    private void ShowNotification(String mensaje) {
        Log.d("custom notification", mensaje);
        Intent i = new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_UPDATE_CURRENT);
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setAutoCancel(true).setContentTitle("QuejatecNotifications")
                .setSound(sound)
                .setContentText(mensaje).setVibrate(new long[]{1000,1000,1000,1000,1000,1000,1000,1000,1000})
                .setSmallIcon(R.drawable.logo_notif).setLargeIcon(BitmapFactory.decodeResource(
                        getResources(), R.drawable.logo_proyecto)).setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        manager.notify(0,builder.build());
    }
}
