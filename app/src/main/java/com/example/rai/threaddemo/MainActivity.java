package com.example.rai.threaddemo;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    TextView tx;
    Button b,bsc,bload, bhttp, btable;
    ProgressBar pb;
    Thread th;
    WebView wView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        th = new Thread(new myThread());
        tx = (TextView)findViewById(R.id.text);
        b = (Button)findViewById(R.id.btn);
        bsc = (Button)findViewById(R.id.btnAsync);
        bload = (Button)findViewById(R.id.btnLoad);
        bhttp = (Button)findViewById(R.id.btnHttp);
        btable= (Button)findViewById(R.id.btnTable);
        pb = (ProgressBar)findViewById(R.id.progressBar);
        wView = (WebView)findViewById(R.id.webView);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                th.start();
            }
        });

        bsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new myTask().execute();
            }
        });

        bload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               loadWebView("http://google.com");
            }
        });

        bhttp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new URLAsyncTask().execute("http://192.168.0.102/img/fb.php");
            }
        });

        btable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Table.class);
                startActivity(i);
            }
        });
    }

    class myThread implements Runnable{

        @Override
        public void run() {
            for (int i=0;i<=100;i++)
            {
                try{
                    Thread.sleep(100);
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
                Message message = Message.obtain();
                message.arg1 = i;
                handler.sendMessage(message);
            }
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
                if(msg.arg1 == 100)
                {
                    tx.setText("Done");
                }
            else
                {
                    tx.setText(msg.arg1+"%");
                }
            pb.setProgress(msg.arg1);
        }
    };
    class myTask extends AsyncTask<String, Integer, String>{

        @Override
        protected void onPreExecute(){
            pb.setProgress(0);
        }

        @Override
        protected String doInBackground(String...params) {
            for (int i=0;i<=100;i++)
            {
                try{
                    Thread.sleep(100);
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
                publishProgress(i);
            }
            return "AsyncTask Done";
        }

        @Override
        protected void onProgressUpdate(Integer...values){
            pb.setProgress(values[0]);
            tx.setText(values[0]+"%");
        }

        @Override
        protected void onPostExecute(String result){

            tx.setText(result);
        }
    }

        public void loadWebView(String url){
            wView.setVisibility(View.VISIBLE);
            wView.loadUrl(url);
            wView.setWebViewClient(new WebViewClient()
            {
                @Override
                public boolean shouldOverrideUrlLoading(WebView wv, String url) {
                    wv.loadUrl(url);
                    return true;
                }
            });
            wView.setWebChromeClient(new WebChromeClient()
            {
                @Override
                public void onProgressChanged(WebView view, int newProgress) {
                    pb.setProgress(newProgress);
                }
            });
        }
        public class URLAsyncTask extends AsyncTask<String, Integer, String> {

            @Override
            protected String doInBackground(String... params) {
                String result ="";
                try{
                    result = httprequest(params[0]);
                }
                catch(IOException ex)
                {
                    ex.printStackTrace();
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result) {
                loadWebView(result);
            }
        }



        public String httprequest(String url) throws IOException
        {
            OkHttpClient client = new OkHttpClient();
            Request request =  new Request.Builder().url(url).build();
            Response response = client.newCall(request).execute();
            return response.body().string();
        }
}
