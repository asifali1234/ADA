package com.genesis.team8.ada.service;

import android.Manifest;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsManager;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.genesis.team8.ada.location;
import com.genesis.team8.ada.safe;
import com.genesis.team8.ada.safefactor;
import com.genesis.team8.ada.volley.AppController;
import com.genesis.team8.ada.MainActivity;
import com.genesis.team8.ada.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Random;

/**
 * Created by asif ali on 14/01/17.
 */

public class AccidentService extends IntentService implements SensorEventListener {

    private static final int REQUEST_CODE_PERMISSION = 2;

    String a;

    SensorManager sensorManager;
    GPSTracker gps;
    private NotificationManager mNotificationManager;
    public static final int NOTIFICATION_ID = 1;

    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;



    public AccidentService() {
        super("AccidentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        // Do the task here
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        Firebase.setAndroidContext(AccidentService.this);
        but();


    }

    public void but( )
    {

        gps = new GPSTracker(AccidentService.this);

        //String url="https://ad-a-bc752.firebaseio.com/safezone/";
        //final Firebase ref = new Firebase(url);

        System.out.println("service running...............................................");

        /*ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {

                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    safefactor user = userSnapshot.getValue(safefactor.class);

                    a = user.getSafe();


                }
                if (a.equalsIgnoreCase("1")) {
                    System.out.println("Accident................................Prone Area............");
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });*/
        Random rand;
        //int randomNum = 1 + (int)(Math.random() * ((1) + 1));

        int randomNum=1;

        if(randomNum == 1) {
            System.out.println("222222222222222222222222222222222222222222222222222222222222222222222222222");

            send2("hi");
        }
        else
            System.out.println("111111111111111111111111111111111111111111111111111111111111111");


        final String URL = "http://192.168.1.56:3000/scriptMachineLearning";
        System.out.println("Adfffffffffffffffffffffffffffffffffff");
        // Post params to be sent to the server
        double latitude = gps.getLatitude();
        double longitude = gps.getLongitude();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("lat", Double.toString(latitude));
        params.put("long", Double.toString(longitude));

        JsonObjectRequest request_json = new JsonObjectRequest(URL, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(AccidentService.this, "ddd", Toast.LENGTH_LONG).show();
                        System.out.println("Adfffffffffffffffffffffffffffffffffff");
                        try {
                            //Process os success response

                            String lat = response.getString("value");
                            Toast.makeText(AccidentService.this, lat, Toast.LENGTH_LONG).show();
                            if(lat.equals("1"))
                            {
                                send2("hi");
                            }
                        }
                        catch (Exception e)
                        {
                            Toast.makeText(AccidentService.this, "error", Toast.LENGTH_LONG).show();
                            System.out.println(e.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
            }
        });

        // add the request object to the queue to be executed
        AppController.getInstance().addToRequestQueue(request_json);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        float gX = x / 9.8f;
        float gY = y / 9.8f;
        float gZ = z / 9.8f;

        float gForce = (float) Math.sqrt(gX * gX + gY * gY + gZ * gZ);

        if (gForce>3)
        {
            sensorManager.unregisterListener(AccidentService.this);

            System.out.println("geforceeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
            final Context c = this;
            gps = new GPSTracker(AccidentService.this);


            if (gps.canGetLocation()) {
                // Toast.makeText(AccidentService.this, "C'Mom no hands!", Toast.LENGTH_SHORT).show();
                MainActivity.notificationFlag =1;

                sendNotification("Should We Send Help??");

                new CountDownTimer(20000, 1000) {

                    public void onTick(long millisUntilFinished) {
                    }

                    public void onFinish() {

                        double latitude = gps.getLatitude();
                        double longitude = gps.getLongitude();

                        if(MainActivity.notificationFlag==1) {

                            Toast.makeText(getApplicationContext(), "Help is Being Sent To Your Location\nLat: "
                                    + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();



                            String phoneNumber = "8594014280";
                            String lat = Double.toString(latitude);
                            String lng = Double.toString(longitude);

                            String y= "t";

                            System.out.println("1111111111111111");
                            System.out.println("1111111111111111");
                            System.out.println("1111111111111111");
                            System.out.println("1111111111111111");
                            System.out.println("1111111111111111");
                            System.out.println("1111111111111111");
                            System.out.println("1111111111111111");
                            System.out.println("1111111111111111");
                            System.out.println("1111111111111111");



                            Firebase ref = new Firebase("https://ad-a-bc752.firebaseio.com/PreviousLocation/");

                            //Getting values to store

                            //Creating Person object
                            location person = new location();

                            //Adding values
                            person.setYes(y);
                            person.setLat(lat);
                            person.setLng(lng);
                            //Storing values to firebase
                            ref.push().setValue(person);


                            String no="no";


                            System.out.println("message is being sent");

                            SharedPreferences details = PreferenceManager.getDefaultSharedPreferences(AccidentService.this);
                            SmsManager sms = SmsManager.getDefault();

                            if (!no.equals(details.getString("number1", "no")))
                                sms.sendTextMessage(details.getString("number1", "no"), null, "Help me.I am at: http://maps.google.com/?q=" + lat + "," + lng, null, null);
                            if (!no.equals(details.getString("number2", "no")))
                                sms.sendTextMessage(details.getString("number2", "no"), null, "Help me.I am at: http://maps.google.com/?q=" + lat + "," + lng, null, null);
                            if (!no.equals(details.getString("number3", "no")))
                                sms.sendTextMessage(details.getString("number3", "no"), null, "Help me.I am at: http://maps.google.com/?q=" + lat + "," + lng, null, null);
                            if (!no.equals(details.getString("number4", "no")))
                                sms.sendTextMessage(details.getString("number4", "no"), null, "Help me.I am at: http://maps.google.com/?q=" + lat + "," + lng, null, null);
                            if (!no.equals(details.getString("number5", "no")))
                                sms.sendTextMessage(details.getString("number5", "no"), null, "Help me.I am at: http://maps.google.com/?q=" + lat + "," + lng, null, null);



                            String message = "http://maps.google.com/?q=" + lat + "," + lng;
                              sms.sendTextMessage(phoneNumber, null, message, null, null);

                            System.out.println("2222222222222222");
                            System.out.println("2222222222222222");
                            System.out.println("2222222222222222");
                            System.out.println("2222222222222222");
                            System.out.println("2222222222222222");
                            System.out.println("2222222222222222");



                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "You Canceled ", Toast.LENGTH_LONG).show();
                        }
                    }

                }.start();

            } else {
                // can't get location
                // GPS or Network is not enabled
                // Ask user to enable GPS/network in settings
                gps.showSettingsAlert();
            }

            System.out.println("force" + gForce);
        }

    }
    public void but(String lat,String lng)
    {



            final String URL = "http://192.168.1.38:3000/accident";
            // Post params to be sent to the server
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("lat", lat);
            params.put("long", lng);

            JsonObjectRequest request_json = new JsonObjectRequest(URL, new JSONObject(params),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                //Process os success response
                            }
                            catch (Exception e)
                            {

                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.e("Error: ", error.getMessage());
                }
            });

            // add the request object to the queue to be executed
            AppController.getInstance().addToRequestQueue(request_json);
        }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    private void sendNotification(String msg) {
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setContentTitle("Are You OK?")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(msg))
                        .setContentText(msg)
                                .setSound(Uri.parse("android.resource://"
                                       + getApplicationContext().getPackageName() + "/" + R.raw.s))
                                        //  .setSound(R.raq)
                        .setSmallIcon(R.drawable.pin);



        AudioManager am;
        am= (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);
        am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        int volume = am.getStreamVolume(AudioManager.STREAM_ALARM);
        am.setStreamVolume(AudioManager.STREAM_ALARM, 3,AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

    private void send2(String msg) {

        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setContentTitle("Please Be Carefull!")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(""))
                        .setContentText("You Are In An Accident Prone Area.")
                                    .setSound(Uri.parse("android.resource://"
                                         + getApplicationContext().getPackageName() + "/" + R.raw.aaaaaaaaa))
                                  //.setSound(R.raq)
                        .setSmallIcon(R.drawable.caracc);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

}
