package com.example.simpleasynctaskerick;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Random;

public class SimpleAsyncTask extends AsyncTask <Void, Integer, String>{

    private WeakReference<TextView> mTextView;
    private WeakReference<ProgressBar> mProgressBar;
    private static final int fragmentProgress = 5;

    SimpleAsyncTask (TextView tv, ProgressBar bar){
        mTextView = new WeakReference<>(tv);
        mProgressBar = new WeakReference<>(bar);
    }

    @Override
    protected String doInBackground(Void... voids) {
        // Generate a random number between 0 and 10
        Random random = new Random();
        int number = random.nextInt(11);
        int milli = number * 400;
        int chunkSize = milli / fragmentProgress;

        for (int i=0; i < fragmentProgress; ++i){
            try {
                Thread.sleep(chunkSize);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
            publishProgress(((i + 1) * 100) / fragmentProgress);
        }

        // Return a String result
        return "Awake after sleeping for " + milli + " milliseconds";
    }

    protected void onPostExecute(String result) {
        mTextView.get().setText(result);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        mProgressBar.get().setProgress(values[0]);
    }
}
