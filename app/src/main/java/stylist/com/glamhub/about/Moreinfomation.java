package stylist.com.glamhub.about;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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

import stylist.com.glamhub.DbHelper;
import stylist.com.glamhub.R;
import stylist.com.glamhub.User;
import stylist.com.glamhub.internetError.NoInternetActivity;

public class Moreinfomation extends AppCompatActivity {
TextView txtmoreinfo,header;
    private final String MOREDATA_URL = "http://joslabs.co.ke/josmotos/moredata.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moreinfomation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
txtmoreinfo= (TextView) findViewById(R.id.txtmoreinfo);
        header= (TextView) findViewById(R.id.idheader);
        Bundle extras=getIntent().getExtras();

if(  extras.getString("info")!=null) {
    txtmoreinfo.setText(extras.getString("info"));
    Log.e("notydata",  extras.getString("info")+"\tm");
}


        StringRequest stringRequest = new StringRequest(Request.Method.POST, MOREDATA_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("userData", response);


                if (response.toString().equals("0") ||response.toString().contains("error")) {

                } else {


                }



                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("news");
                    for (int i=0;i<jsonArray.length();i++) {

                        JSONObject jor = jsonArray.getJSONObject(i);
                        jor.getString("title");

                        txtmoreinfo.setText(jor.getString("moredata"));
                        header.setText(jor.getString("title"));
                    }


                } catch (JSONException e) {

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
