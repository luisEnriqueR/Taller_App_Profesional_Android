package com.example.luis.prueba;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.*;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class MainActivity extends AppCompatActivity {

    static final String TAG = MainActivity.class.getSimpleName();
    private static final String URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?";
    private static AsyncHttpClient client = new AsyncHttpClient();
    SendDataFromService dataFromService;


    //String jsonResponse = "";
    JSONObject jsonResponse;

    EditText latitude_text;
    EditText longitud_text;
    Button buscar_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        longitud_text = (EditText) findViewById(R.id.longitudET);
        latitude_text = (EditText) findViewById(R.id.latitudeET);
        buscar_button = (Button) findViewById(R.id.buscarBtn);
    }

    public void searchPlaces(View view){
        String longitud = longitud_text.getText().toString();
        String latitud = latitude_text.getText().toString();
        float longitude;
        float latitude;

        try{
            if (!(latitud.isEmpty() && longitud.isEmpty())) {
                longitude = Float.parseFloat(longitud);
                latitude = Float.parseFloat(latitud);
                Snackbar.make(view, latitude + " " + latitude, Snackbar.LENGTH_LONG).show();

                RequestParams requestParams = new RequestParams();
                requestParams.put("location", "19.4097564,-99.16954370000002");
                requestParams.put("key", "AIzaSyA7m5YQp_OQXvZ7DzylErwubKq7BhIVUcs");
                requestParams.put("radius", "2000");
                client.get(URL, requestParams, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);

                        jsonResponse = response;
                        Log.i(TAG, "onSuccess: " + jsonResponse);
                        goToFragment();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        Log.e(TAG, "onFailure: " + errorResponse);
                    }
                });

            } else {
                //Snackbar.make(view, "Los campos no deben ser vacios", Snackbar.LENGTH_SHORT).show();
                Toast.makeText(this, "Los campos no deben ser vacios", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e){
            Snackbar.make(view, "Los campos deben numeros decimales", Snackbar.LENGTH_SHORT).show();
        }
    }

    public void goToFragment(){
        ResultListFragment fragment = new ResultListFragment();

        dataFromService = (SendDataFromService) fragment;
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_result, fragment);
        fragmentTransaction.commit();
    }

    public interface SendDataFromService{
        void sendData(JSONObject jsonResponse);
    }
}

//https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=19.4097564,-99.16954370000002&radius=2000&type=bank&key=AIzaSyA7m5YQp_OQXvZ7DzylErwubKq7BhIVUcs