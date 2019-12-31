package stylist.com.glamhub.myorders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import stylist.com.glamhub.R;
import stylist.com.glamhub.Updates;
import stylist.com.glamhub.internetError.NoInternetActivity;

public class BestpriceActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
RecyclerView recyclerView;
   private List<BestPrice>bestprices;
    String orderId,clientId;
    BestpriceAdapter adapter;
    SharedPreferences regd;
    private View mProgressView;
    TextView txtprogress;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bestprice);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);



        recyclerView= (RecyclerView) findViewById(R.id.bestprice_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(null);
        Bundle extras=getIntent().getExtras();
       orderId= extras.getString("orderId");
        regd=getApplication().getSharedPreferences("regd",0);
        clientId=regd.getString("clientId",null);
        this.swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayoutUpdatesFragment);

        this.swipeRefreshLayout.setOnRefreshListener(this);
        mProgressView = findViewById(R.id.simpleProgressBar);
        txtprogress= (TextView) findViewById(R.id.txtprogress);


        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
        mProgressView.animate().setDuration(shortAnimTime);
        mProgressView.setVisibility(View.VISIBLE);
        txtprogress.setVisibility(View.VISIBLE);
        this.bestprices=new ArrayList<>();
        addBestprice();

    }

    private void addBestprice() {
bestprices.clear();
        //for (int i=0;i<10;i++) {

        //}

        StringRequest stringRequest=new StringRequest(Request.Method.POST, "http://joslabs.co.ke/josmotos/Clientquoteds.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mProgressView.setVisibility(  View.GONE);
                txtprogress.setVisibility(View.GONE);
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    Log.d("myquotes",response);
                    JSONArray jsonArray = jsonObject.getJSONArray("myqoutes");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jor = jsonArray.getJSONObject(i);



                        bestprices.add(new BestPrice(jor.getString("garage"), jor.getString("price"), jor.getString("manufacturer"), jor.getString("orderID"), jor.getString("mechId"), "",jor.getString("timeQuoted")));
//http://joslabs.co.ke/josmotos/Clientquoteds.php
                        adapter = new BestpriceAdapter(getApplication(), bestprices);
                        recyclerView.setAdapter(adapter);

                    }

                } catch (JSONException e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Student Error", error.toString());
                error.printStackTrace();
                mProgressView.setVisibility(  View.GONE);
                txtprogress.setVisibility(View.GONE);
                if (error instanceof NoConnectionError) {
                    //  Toast.makeText(Motorregister.this, "No Internet", Toast.LENGTH_LONG).show();


                }

                if (error instanceof NetworkError) {
                    // Toast.makeText(Motorregister.this, "No Internet", Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(getApplicationContext(), NoInternetActivity.class);;
                    startActivity(intent);
                    finish();
                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                System.out.print("was here");
                params.put("orderId",orderId);
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

    @Override
    public void onRefresh() {

    }
}


