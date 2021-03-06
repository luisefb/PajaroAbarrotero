package com.kreativeco.pjaroabarrotero.libraries;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class KCOParseJSON {

    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";

    // definimos el constructor
    public KCOParseJSON() {

    }

    public JSONObject getJSONFromUrl(String url, List<NameValuePair> params) {

        // hacemos una peticion de tipo HTTP
        try {
            // cliente por defecto --> defaultHttpClient
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(params));

            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();
            Log.d("JSON Parse", json);
        } catch (Exception e) {
            Log.e("Error en el buffer", "Error al convertir el resultado " + e.toString());
        }

        // tratamos de parsear el string a un objeto de tipo JSON
        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("Parseo JSON", "Error al parsear los datos " + e.toString());
        }

        // regresa el string JSON
        return jObj;

    }
}