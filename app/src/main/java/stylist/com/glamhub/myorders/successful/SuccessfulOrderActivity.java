package stylist.com.glamhub.myorders.successful;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
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
import stylist.com.glamhub.internetError.NoInternetActivity;
import stylist.com.glamhub.myorders.BestPrice;

public class SuccessfulOrderActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    RecyclerView recyclerView;
    List<BestPrice>successful;
    CompleteAdapter successfulAdapter;
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
        setContentView(R.layout.activity_successful_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        recyclerView= (RecyclerView) findViewById(R.id.success_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(null);
        this.swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayoutUpdatesFragment);

        this.swipeRefreshLayout.setOnRefreshListener(this);
        mProgressView = findViewById(R.id.simpleProgressBar);
        txtprogress= (TextView) findViewById(R.id.txtprogress);


        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
        mProgressView.animate().setDuration(shortAnimTime);
        mProgressView.setVisibility(View.VISIBLE);
        txtprogress.setVisibility(View.VISIBLE);

        this.successful=new ArrayList<>();
        successful.clear();
        
       SuccessfulOrders();
        //PresetOrders();

    }

    private void PresetOrders() {
        for (int i=0;i<10;i++)
        {

            successful.add(new BestPrice("sparename","500","Germany", "22:00 23.7.2017","Already Paid","Radio"));
            successfulAdapter = new CompleteAdapter(successful, getApplicationContext());
            recyclerView.setAdapter(successfulAdapter);
        }
    }

    private void SuccessfulOrders()
    {

        //   for (int i=0;i<10;i++) {

        StringRequest stringRequest=new StringRequest(Request.Method.POST, "http://joslabs.co.ke/josmotos/mysorders.php", new Response.Listener<String>() {
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


                        successful.add(new BestPrice(jor.getString("garage"),jor.getString("price"),"Germany", jor.getString("timeSelected"),jor.getString("status"),jor.getString("sparename")));
                       successfulAdapter = new CompleteAdapter(successful, getApplicationContext());
                        recyclerView.setAdapter(successfulAdapter);
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
                    Intent intent=new Intent(getApplicationContext(), NoInternetActivity.class);
                    startActivity(intent);
                    finish();
                }

                if (error instanceof NetworkError) {
                    // Toast.makeText(Motorregister.this, "No Internet", Toast.LENGTH_LONG).show();
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

        // }
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN);

        swipeRefreshLayout.setRefreshing(true);
        (new Handler()).postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
                successful.clear();

                SuccessfulOrders();
            }
        }, 2000);


    }
}
