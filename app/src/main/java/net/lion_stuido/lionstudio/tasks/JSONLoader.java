package net.lion_stuido.lionstudio.tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by lester on 09.10.14.
 */
public class JSONLoader extends AsyncTask<String, Void, String> {
    private String jsonResult;
    @Override
    protected String doInBackground(String... params) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(params[0]);

        try {
            HttpResponse response = httpclient.execute(httpGet);
            jsonResult = inputStreamToString(
                    response.getEntity().getContent()).toString();
        }

        catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    private StringBuilder inputStreamToString(InputStream is) {
        String rLine = "";
        StringBuilder answer = new StringBuilder();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));

        try {
            while ((rLine = rd.readLine()) != null) {
                answer.append(rLine);
            }
        }

        catch (IOException e) {
            e.printStackTrace();
            Log.i(getClass().toString(),"fail");
        }
        return answer;
    }
    @Override
    protected void onPostExecute(String result) {
        Log.i(getClass().toString(),"JSON!!! - "+ jsonResult);
    }

}
