package com.erik_falk.ghplab1b;

import android.app.AlertDialog;
import android.content.Context;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;


public class MainActivity extends ActionBarActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mLight;
    private Sensor mAccelerometer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //reference to sensor service
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);


        //B3 check for and initialize light sensor
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) != null){
            mLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
            Log.i("Lichtsensor", "vorhanden");

        }
        else{
            Log.i("Lichtsensor", "nicht vorhanden");
        }

        //B3 check for and initialize accelerometer
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null){
            mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            Log.i("Beschleunigungssensor", "vorhanden");
        }
        else{
            Log.i("Beschleunigungssensor", "nicht vorhanden");
        }

    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy){


    }

    @Override
    public final void onSensorChanged(SensorEvent event){


        //which sensor changed
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            getAccelerometer(event);
        }

        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            getLight(event);
        }

    }

    //B5 compute accelerometer data
    private void getAccelerometer(SensorEvent event){

       final float[] values = event.values;
       ProgressBar xProgress = (ProgressBar)findViewById(R.id.progressBarX),
                   yProgress = (ProgressBar)findViewById(R.id.progressBarY),
                   zProgress = (ProgressBar)findViewById(R.id.progressBarZ);

       values[0] *=10;
       values[1] *=10;
       values[2] *=10;

       //Output on View
       xProgress.setProgress((int)values[0]);
       yProgress.setProgress((int)values[1]);
       zProgress.setProgress((int)values[2]);

       //Output in Log
       Log.i("X:", String.valueOf(values[0]));
       Log.i("Y:", String.valueOf(values[1]));
       Log.i("Z:", String.valueOf(values[2]));
    }

    //B6 compute light data
    private void getLight(SensorEvent event){

        final float values = event.values[0];

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        Button mButton = (Button)findViewById(R.id.button);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                builder.setTitle("Lichtwert: ");
                builder.setMessage(String.valueOf(values) + " lux");
                AlertDialog dialog = builder.create();
                dialog.show();
                Log.i("Lichtwert", String.valueOf(values));
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        //register light and accelerometer sensorlistener
        mSensorManager.registerListener(this, mLight, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause(){
        super.onPause();
        //unregister light and accelerometer sensorlistener
        mSensorManager.unregisterListener(this, mLight);
        mSensorManager.unregisterListener(this, mAccelerometer);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
