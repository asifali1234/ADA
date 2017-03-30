package com.genesis.team8.ada;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.test.mock.MockPackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.firebase.client.Firebase;
import com.genesis.team8.ada.AmbulanceOnWayActivity.AmbulanceOnWay;
import com.genesis.team8.ada.AnalyzeActivity.Analyze;
import com.genesis.team8.ada.getContactActivity.GetContacts;
import com.genesis.team8.ada.mapLocationsActivity.ImportantLocations;
import com.genesis.team8.ada.newsActivity.NewsActivity;
import com.genesis.team8.ada.service.AccidentService;
import com.genesis.team8.ada.service.GPSTracker;
import com.genesis.team8.ada.service.MyAlarmReceiver;
import com.genesis.team8.ada.volley.AppController;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by asif ali on 14/01/17.
 */

public class MainActivity extends ActionBarActivity implements SensorEventListener  {
    TextView tv;

    public static int notificationFlag = 0;
    SensorManager sensorManager;
    private static final int REQUEST_CODE_PERMISSION = 2;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    static int[] imageResources = new int[]{
            R.drawable.aaa,
            R.drawable.aaa,
            R.drawable.aaa,

            R.drawable.aaa


    };
    static int[] Strings = new int[]{
            R.string.hiiiiii,
            R.string.cont,
            R.string.Near,

            R.string.news



    };
    GPSTracker gps;
    static int imageResourceIndex = 0;
    static int str = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(notificationFlag ==1)
        {
            //close service

            notificationFlag=0;
        }
       /* MessageDigest md = null;
        try {
            PackageInfo info = getApplicationContext().getPackageManager().getPackageInfo(
                    getApplicationContext().getPackageName(),
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
        Log.i("SecretKey = ", Base64.encodeToString(md.digest(), Base64.DEFAULT));
*/


        try {
            if (ActivityCompat.checkSelfPermission(this, mPermission)
                    != MockPackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{mPermission},
                        REQUEST_CODE_PERMISSION);

                // If any permission above not allowed by user, this condition will
                //execute every time, else your else part will work
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
scheduleAlarm();

        bmb();


    }





    public void but( )
    {


        final String URL = "http://192.168.1.56:3000/accident";
// Post params to be sent to the server
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("lat", "10.1001010");
        params.put("long", "74.0923020");

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

    public void show(String mUserId)
    {
        Toast.makeText(this, mUserId,Toast.LENGTH_LONG).show();

    }

    public void soshelpp(View view)
    {
        gps = new GPSTracker(MainActivity.this);

        // check if GPS enabled
        if (gps.canGetLocation()) {

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
           /* Toast.makeText(getApplicationContext(), "Your Location is - \nLat: "
                    + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();*/
           // sendNotification(Double.toString((latitude)));

            SmsManager sms = SmsManager.getDefault();
            String phoneNumber="8594014280";
            String lat= Double.toString(latitude);
            String lng= Double.toString(longitude);

            String message="Help Me, I Have Met With An Accident http://maps.google.com/?q="+lat+","+lng;
           sms.sendTextMessage(phoneNumber, null, message, null, null);
            String no="no";


            System.out.println("message is being sent");

            SharedPreferences details = PreferenceManager.getDefaultSharedPreferences(this);


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

            String y= "t";
            Firebase.setAndroidContext(this);
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





            sensorManager.unregisterListener(this);
          Intent in = new Intent(this, AmbulanceOnWay.class);
            startActivity(in);
    /*
                    Toast.makeText(getApplicationContext(), "Your Location is - \nLat: "
                            + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();*/
        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }


    }


    public void g(View view)

    {
        gps = new GPSTracker(MainActivity.this);

        // check if GPS enabled
        if (gps.canGetLocation()) {

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            String lat= Double.toString(latitude);
            String lngg= Double.toString(longitude);
            String y= "t";
            Firebase.setAndroidContext(this);
            Firebase ref = new Firebase("https://ad-a-bc752.firebaseio.com/PreviousLocation/");

            //Getting values to store

            //Creating Person object
            location person = new location();

            //Adding values
            person.setYes(y);
            person.setLat(lat);
            person.setLng(lngg);
            //Storing values to firebase
            ref.push().setValue(person);
        }
        else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
        Intent in = new Intent(this, AmbulanceOnWay.class);
        startActivity(in);

    }
    public void bmb()
    {
        ActionBar mActionBar = getSupportActionBar();
        assert mActionBar != null;
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);

        View actionBar = mInflater.inflate(R.layout.custombar, null);
        TextView mTitleTextView = (TextView) actionBar.findViewById(R.id.title_text);
        mTitleTextView.setText(R.string.app_name);
        mActionBar.setCustomView(actionBar);
        mActionBar.setDisplayShowCustomEnabled(true);
        ((Toolbar) actionBar.getParent()).setContentInsetsAbsolute(0, 0);

        BoomMenuButton bmb = (BoomMenuButton) actionBar.findViewById(R.id.bmb);
        bmb.setButtonEnum(ButtonEnum.Ham);
        bmb.setPiecePlaceEnum(PiecePlaceEnum.HAM_4);
        bmb.setButtonPlaceEnum(ButtonPlaceEnum.HAM_4);
        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++) {
            HamButton.Builder builder = new HamButton.Builder()
                    .normalTextRes(getString())
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {


                            if (index == 0) {
                                start(index);

                            }
                            if (index == 1) {
                                con(index);
                            }
                            if (index == 2) {
                                map(index);

                            }
                            if (index == 3) {
                                news(index);
                            }
                            if (index == 4) {
                                news(index);

                            }
                        }
                    })

                    .normalImageRes(getImageResource());
            bmb.addBuilder(builder);
        }
    }
    // Setup a recurring alarm every half hour
    public void scheduleAlarm() {
        // Construct an intent that will execute the AlarmReceiver
        Intent intent = new Intent(getApplicationContext(), MyAlarmReceiver.class);
        // Create a PendingIntent to be triggered when the alarm goes off
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, MyAlarmReceiver.REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // Setup periodic alarm every 5 seconds
        long firstMillis = System.currentTimeMillis(); // alarm is set right away
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        // First parameter is the type: ELAPSED_REALTIME, ELAPSED_REALTIME_WAKEUP, RTC_WAKEUP
        // Interval can be INTERVAL_FIFTEEN_MINUTES, INTERVAL_HALF_HOUR, INTERVAL_HOUR, INTERVAL_DAY
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis,
                1, pIntent);

    }
    public static int getString() {
        if (str >= Strings.length) str = 0;
        return Strings[str++];
    }
    public static int getImageResource() {
        if (imageResourceIndex >= imageResources.length) imageResourceIndex = 0;
        return imageResources[imageResourceIndex++];
    }
    public void start(int pos)
    {
       Intent in = new Intent(this, Analyze.class);
        startActivity(in);
    }
    public void map2(int pos)
    {
        Intent in = new Intent(this, safe.class);
        startActivity(in);
    }
    public void con(int pos)
    {
        Intent in = new Intent(this, GetContacts.class);
        startActivity(in);
    }
    public void map(int pos)
    {
        Intent in = new Intent(this, ImportantLocations.class);
        startActivity(in);
    }
    public void news(int pos)
    {
        Intent in = new Intent(this, NewsActivity.class);
        startActivity(in);
    }
    public void police(View view)
    {String a1="100";
        startActivity( new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + a1)));
    }
    public void ambulance(View view)
    {String a1="108";
        startActivity( new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + a1)));
    }
    public void fire(View view)
    {String a1="101";
        startActivity( new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + a1)));
    }
    public void traffic(View view)
    {String a1="1099";
        startActivity( new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + a1)));
    }
    public void child(View view)
    {String a1="1098";
        startActivity( new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + a1)));
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
     //   tv.setText(Float.toString(gForce));

    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
