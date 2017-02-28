package com.example.rai.threaddemo;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Table extends AppCompatActivity {
    ListView l;
    String TeamName[];
    String Points[];
    String img[];
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        l = (ListView)findViewById(R.id.listview);
        new TabelAsyncTask().execute("http://192.168.43.68/BarclaysLeagueTable/league.php");
    }
    public class TabelAsyncTask extends AsyncTask<String, Void, String> {

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
            try{
                JSONObject jo = new JSONObject(result);
                JSONArray ja = jo.getJSONArray("team");
                TeamName = new String[ja.length()];
                Points = new String[ja.length()];
                img = new String[ja.length()];
                for(int i=0;i<ja.length();i++)
                {
                    TeamName[i] = ja.getJSONObject(i).getString("name");
                    Points[i] = ja.getJSONObject(i).getString("points");
                    img[i] = ja.getJSONObject(i).getString("logo");
                }
                showinListView();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public void showinListView() {
        CustomAdapter cA = new CustomAdapter(Table.this, TeamName, Points, img);
        l.setAdapter(cA);
    }

    public String httprequest(String url) throws IOException
    {
        OkHttpClient client = new OkHttpClient();
        Request request =  new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
