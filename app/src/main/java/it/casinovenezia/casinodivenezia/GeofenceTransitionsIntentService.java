package it.casinovenezia.casinodivenezia;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;
import com.google.gson.Gson;

import java.util.*;
import java.util.Map;

/**
 * Created by massimomoro on 27/08/15.
 */
public class GeofenceTransitionsIntentService extends IntentService  {
    private final String TAG = GeofenceTransitionsIntentService.class.getName();
    private SharedPreferences prefs;
    private Gson gson;

    public GeofenceTransitionsIntentService() {
        super("GeofenceTransitionsIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        SharedPreferences settings = getSharedPreferences(Constants.PREFS_NAME, 0);
        final SharedPreferences.Editor editor = settings.edit();

        GeofencingEvent event = GeofencingEvent.fromIntent(intent);
        if (event.hasError()) {
            String errorMessage = GeofenceErrorMessages.getErrorString(this,
                    event.getErrorCode());
            Log.e(TAG, errorMessage);
            return;
        }
        // Get the transition type.
        int transition = event.getGeofenceTransition();
        if (transition == Geofence.GEOFENCE_TRANSITION_ENTER) {
            List<String> geofenceIds = new ArrayList<>();
            for (Geofence geofence : event.getTriggeringGeofences()) {
                geofenceIds.add(geofence.getRequestId());
            }

            if (transition == Geofence.GEOFENCE_TRANSITION_ENTER ) {
                if (settings.getInt("notification", 0) < 3) {
                    if (settings.getInt("notification", 0) == 0) {
                        editor.putInt("notification", 1);
                        editor.commit();
                        onEnteredGeofences(geofenceIds);
                    } else {
                        editor.putInt("notification", settings.getInt("notification", 0) + 1);
                        editor.commit();
                        onEnteredGeofences(geofenceIds);
                    }
                }

            }
        }


    }

    private void onEnteredGeofences(List<String> geofenceIds) {
        for (String geofenceId : geofenceIds) {

            // Set the notification text and send the notification
            String contextText = this.getResources().getString(R.string.Notification_Text);

            NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
            Intent intent = new Intent(this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingNotificationIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            Notification notification = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.leo)
                   // .setContentTitle(this.getResources().getString(R.string.Notification_Title))
                    .setContentText(contextText)
                    .setContentIntent(pendingNotificationIntent)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(contextText))
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setAutoCancel(true)
                    //.setSound(alarmSound)
                    .build();
            notification.sound = Uri.parse("android.resource://"
                    + getPackageName() + "/" + R.raw.coins4);
            notificationManager.notify(0, notification);

        }
    }

    private void onError(int i) {
        Log.e(TAG, "Geofencing Error: " + i);
    }
}