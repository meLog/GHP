package com.erik_falk.ghplab1c;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    ProgressBar pBar;
    TextView tView1, tView2;

    //C4 Async Task
    public void runAsync(View view){
        new AsyncCounter().execute("");
    }

    private class AsyncCounter extends AsyncTask<String, Integer, String> {

        //work for the background,
        protected String doInBackground(String... params){
            for(int i = 1; i <=10; i++){
               tonsOfWork();
               //for UI update publish it!
               publishProgress(i);
            }
        return "Done";
        }

        //shows infos during the work
        protected void onProgressUpdate(Integer... progress){
            tView2.setText(String.valueOf(progress[0]));
        }

        //shows infos when work is done
        protected void onPostExecute(String result){
            tView2.setText(result);
        }
    }


    //C3 mit Thread und post method
    public void startProgress(View view){

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= pBar.getMax(); i++){
                   final int value = i;
                    tonsOfWork();
                    //handler for sending messages to the UI
                    pBar.post(new Runnable() {
                        @Override
                        public void run() {
                            tView1.setText(String.valueOf(value)+"%");
                            pBar.setProgress(value);
                        }
                    });
                }
            }
        };
        new Thread(runnable).start();
    }

    /*
    //C2 ohne Thread
    public void startProgress(View view){
        pBar.setMax(1000000000);
        for (int i = 0; i<= pBar.getMax(); i++){
            pBar.setProgress(i);
            tView1.setText(String.valueOf(i*100/pBar.getMax())+"%");
        }
    }
    */

    /*
    android.view.ViewRootImpl$CalledFromWrongThreadException:
    Only the original thread that created a view hierarchy can touch its views.
     //C3 mit Thread
     public void startProgress(View view){

        Runnable runnable = new Runnable()
            @Override
            public void run() {
                for (int i = 0; i <= pBar.getMax(); i++){

                    final int value = i;
                    tonsOfWork();
                    tView.setText(String.valueOf(value));
                    pBar.setProgress(value);
                }
            }
        };
        new Thread(runnable).start();
    }*/

    //tons of work
    private void tonsOfWork(){
       try {
           Thread.sleep(1000);
       } catch (InterruptedException e){
           e.printStackTrace();
       }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pBar = (ProgressBar)findViewById(R.id.progressBar);
        tView1 = (TextView)findViewById(R.id.textView1);
        tView2 = (TextView)findViewById(R.id.textView2);
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

package com.example.rfa.labor1_3;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    //eigener Code

    ProgressBar bar;
    TextView text;

    //Teil 1 ProgressBar aufladen

    //ohne Threads
    /*
    public void startCount(View view){
        bar.setMax(99999);
        for (int i = 0; i<= bar.getMax(); i++){
            bar.setProgress(i);
            text.setText(String.valueOf(i*100/bar.getMax())+"%");
        }
    }
    */
     /*
    android.view.ViewRootImpl$CalledFromWrongThreadException:
    Only the original thread that created a view hierarchy can touch its views.
     //mit Thread
     public void startCount(View view){
        Runnable runnable = new Runnable()
            @Override
            public void run() {
                for (int i = 0; i <= bar.getMax(); i++){
                    final int value = i;
                    overload();
                    text.setText(String.valueOf(value));
                    bar.setProgress(value);
                }
            }
        };
        new Thread(runnable).start();
    }
    */
    //mit Threads und Post
    public void startCount(View view){

        Runnable run = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= bar.getMax(); i++){
                    final int value = i;
                    overload();
                    bar.post(new Runnable() {
                        @Override
                        public void run() {
                            text.setText(String.valueOf(value)+"%");
                            bar.setProgress(value);
                        }
                    });
                }
            }
        };
        new Thread(run).start();
    }

    //Teil 2 Asynchrones Arbeiten

    public void startWork(View view){
        new AsyncCounter().execute("");
    }

    private class AsyncCounter extends AsyncTask<String, Integer, String> {

        //work for the background,
        protected String doInBackground(String... params){
            for(int i = 1; i <=10; i++){
                overload();
                //Updating der UI
                publishProgress(i);
            }
            return "Done";
        }

        //während er arbeitet
        protected void onProgressUpdate(Integer... progress){
            text.setText(String.valueOf(progress[0]));
        }

        //wenn er fertig ist
        protected void onPostExecute(String result){
            text.setText(result);
        }
    }


    //Overload
    private void overload(){
        try{
            Thread.sleep(500);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //eigener Code
        bar = (ProgressBar)findViewById(R.id.progressBar);
        text = (TextView)findViewById(R.id.textView);
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
