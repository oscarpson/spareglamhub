package stylist.com.glamhub.addfuel;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import stylist.com.glamhub.R;
import stylist.com.glamhub.internetError.NoInternetActivity;

public class FuelEdit extends AppCompatActivity {
Button btnfueladd;
    EditText paraffin,diesel,petrol,stationname;
    String sparrafin,sdiesel,spetrol,stringstation;
    private final String FUELADD_URL = "http://joslabs.co.ke/josmotos/fueladd.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnfueladd= (Button) findViewById(R.id.btnsubmit);
        paraffin= (EditText) findViewById(R.id.edtfparrafin);
        diesel= (EditText) findViewById(R.id.edtfdisel);
        petrol= (EditText) findViewById(R.id.edtfpetrol);
        stationname= (EditText) findViewById(R.id.edtfstationname);
        btnfueladd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(verify())
                {
                    sparrafin=paraffin.getText().toString();
                    sdiesel=diesel.getText().toString();
                    spetrol=petrol.getText().toString();
                    stringstation=stationname.getText().toString();

                    StringRequest stringRequest = new StringRequest(Request.Method.POST,FUELADD_URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("userData", response);


                            if (response.toString().equals("0") ||response.toString().contains("error")) {

                            } else {


                            }




                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("Student Error", error.toString());
                            error.printStackTrace();



                            if (error instanceof NoConnectionError) {
                                //  Toast.makeText(Motorregister.this, "No Internet", Toast.LENGTH_LONG).show();
                            }

                            if (error instanceof NetworkError) {
                                // Toast.makeText(Motorregister.this, "No Internet", Toast.LENGTH_LONG).show();
                                Intent intent=new Intent(getApplicationContext(), NoInternetActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            }

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("station",stringstation);
                            params.put("parrafin",sparrafin);
                            params.put("petrol",spetrol);
                            params.put("diesel",sdiesel);
                            System.out.print("was here");
                            Log.e("paramx", "params sent");
                            return params;
                        }
                    };

                    int x = 2;// retry count
                    stringRequest.setRetryPolicy(new

                            DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48,
                            x, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    //stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    Volley.newRequestQueue(getApplicationContext()).

                            add(stringRequest);
                }
            }
        });
    }

    private boolean verify() {
        Pattern p = Pattern.compile("[^A-Za-z0-9-\\s+]");
        Matcher m = p.matcher(stationname.getText().toString());
        boolean found = m.find();

        if(stationname.getText().toString().length()==0)

        {
            stationname.setError("Name field is required");
            stationname.requestFocus();
            return false;
        }

        else if(stationname.getText().toString().length()<3)

        {
            stationname.setError("Name should not be less than 3 characters");
            stationname.requestFocus();
            return false;
        }

        if(found)

        {
            stationname.setError("Invalid characters in the name");
            stationname.requestFocus();
            return false;
        }


        if(petrol.getText().toString().length()==0)

        {
            petrol.setError("Model field is required");
            petrol.requestFocus();
            return false;
        }
        if(diesel.getText().toString().length()==0)

        {
            diesel.setError("Type field is required");
            diesel.requestFocus();
            return false;
        }

        if(paraffin.getText().toString().length()==0)

        {
            paraffin.setError("Field is required");
            paraffin.requestFocus();
            return false;

        }


        return  true;
    }

}
