package mickael.koc.channelmessaging2;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by kocm on 20/01/2017.
 */
public class Datalayer extends AsyncTask<String,Integer, String> {
    private ArrayList<onDownloadCompleteListener> listeners = new ArrayList<onDownloadCompleteListener> ();
    protected String username;
    protected String pwd;

    public Datalayer(String user, String pw)
    {
        this.username=user;
        this.pwd=pw;
    }
    public void setOnNewsDownloadComplete (onDownloadCompleteListener listener)
    {
        // Store the listener object
        this.listeners.add(listener);
    }
    private void newsDownloadCompleted(String myNews){

    }


    @Override
    protected String doInBackground(String... arg0) {
        String retour = "";

        HashMap<String,String> test = new HashMap<String, String>();
        test.put("username",this.username);
        test.put("password",this.pwd);
        retour = performPostCall("http://www.raphaelbischof.fr/messaging/?function=connect",test);

        //publishProgress(new Integer((i*100)/arg0.length));
        return retour;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        for (onDownloadCompleteListener oneListener : listeners)
        {
            oneListener.onDownloadComplete(s);
        }
    }

    public String performPostCall(String requestURL, HashMap<String, String> postDataParams) {
        URL url;
        String response = "";
        try
        {
            url = new URL(requestURL) ;
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout (15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));
            writer.flush();
            writer.close();
            os.close() ;
            int responseCode  =conn.getResponseCode();
            if
                    (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br =new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response+=line ;
                }
            }
            else
            {
                response
                        ="marche pas";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }


    private String getPostDataString (HashMap<String, String> params) throws UnsupportedEncodingException
    {
        StringBuilder result = new StringBuilder() ;
        boolean first = true ;
        for (Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&") ;
            result.append  (URLEncoder.encode (entry.getKey(), "UTF-8")) ;
            result.append ("=" ) ;
            result.append (URLEncoder.encode(entry.getValue(), "UTF-8")) ;
        }

        return result.toString() ;
    }


}
