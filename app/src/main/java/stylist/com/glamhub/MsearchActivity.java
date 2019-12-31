package stylist.com.glamhub;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
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

import stylist.com.glamhub.internetError.NoInternetActivity;

public class MsearchActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private CoordinatorLayout coordinatorLayout;
    private View mProgressView;
    TextView txtprogress;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private List<mSearch> mSearches,msearch2;
    mSearchAdapter msearchAdapter;
    SharedPreferences pref;
    Button btnseach;
    EditText edtSearch;
    private final String UPDATES_URL="http://joslabs.co.ke/josmotos/towme.php";

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msearch);


        recyclerView = (RecyclerView) findViewById(R.id.msearch_recycle_view);
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
        this.mSearches = new ArrayList<>();
        // presetStudent();
        getData();

        edtSearch=(EditText)findViewById(R.id.edtsearch);
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                msearch2=new ArrayList<>();
                msearch2.clear();

                for (int i=0; i<mSearches.size();i++)
                {
                    if(mSearches.get(i).getmSearchdetails().toUpperCase().contains(edtSearch.getText().toString().toUpperCase())
                            || mSearches.get(i).getmSearchtype().toUpperCase().contains(edtSearch.getText().toString().toUpperCase())
                            || mSearches.get(i).getmSearchmoel().toUpperCase().contains(edtSearch.getText().toString().toUpperCase()))
                    {
                        msearch2.add(mSearches.get(i));

                    }

                }

                msearchAdapter = new mSearchAdapter(msearch2, getApplication());

                recyclerView.setAdapter(msearchAdapter);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
      /*  btnseach=(Button)findViewById(R.id.btnsearch);
        btnseach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msearch2=new ArrayList<>();
                msearch2.clear();
                //finalist.clear();


                //Log.d("Finalist code:",finalists3.get(0).getCode());
                //finalists2.clear();
                for (int i=0; i<mSearches.size();i++)
                {
                    if(mSearches.get(i).getmSearchdetails().toUpperCase().contains(edtSearch.getText().toString().toUpperCase())
                            || mSearches.get(i).getmSearchtype().toUpperCase().contains(edtSearch.getText().toString().toUpperCase())
                            || mSearches.get(i).getmSearchmoel().toUpperCase().contains(edtSearch.getText().toString().toUpperCase()))
                    {
                        msearch2.add(mSearches.get(i));

                    }



                }



                msearchAdapter = new mSearchAdapter(msearch2, getApplication());

                recyclerView.setAdapter(msearchAdapter);


            }

        });*/

    }
    mSearchAdapter searchAdapter;
    private void getData() {
        mSearches.clear();




       // recyclerView.setAdapter(searchAdapter);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPDATES_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mProgressView.setVisibility(View.GONE);
                txtprogress.setVisibility(View.GONE);
                try {




                    //   Toast.makeText(getApplicationContext(),"Success1",Toast.LENGTH_LONG).show();
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d("responceserver",response);
                    pref=getApplicationContext().getSharedPreferences("towme",0);
                   getApplicationContext().getSharedPreferences("towme",0).edit().clear().apply();

                    SharedPreferences.Editor editor=pref.edit();
                    editor.putString("jsons",jsonObject.toString());
                    editor.apply();
                    JSONArray jsonArray = jsonObject.getJSONArray("news");
                    Log.d("dataserver",jsonArray.toString());
                    for (int i=0;i<jsonArray.length();i++) {

                        JSONObject jor=jsonArray.getJSONObject(i);
                        mSearches.add(new mSearch("http://joslabs.co.ke/josmotos/img/"+jor.getString("pic"),"Max Weight: "+
                                jor.getString("Weight"),"tower "+jor.getString("userName"),"Around :"+jor.getString("Resdence")));
                       Log.d("imgurl","http://joslabs.co.ke/josmotos/img/"+jor.getString("pic"));

                        searchAdapter  = new mSearchAdapter (mSearches, getApplicationContext());
                        recyclerView.setAdapter(searchAdapter);
                        //  probox = jor.getString("probox");

                        // vname.setText(server_name);
                        //pass.setText(server_pass);


                    }
                    //mSearches.add(new Updates(1,"","kk","lop","jiu"));






                } catch (JSONException e) {
                    Log.e("exception ","Exception encoutered ");
                    //  Toast.makeText(getApplicationContext(),"Exception encoutered ",Toast.LENGTH_LONG);
                    e.printStackTrace();

                }
                //JsonArrayRequest jsonArrayRequest=
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mProgressView.setVisibility(View.GONE);
                txtprogress.setVisibility(View.GONE);
                error.printStackTrace();
                if(error instanceof NoConnectionError)
                {
                    Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_LONG).show();
                    try {
                        SparesOfflineData();
                    } catch (JSONException e) {

                        Intent intent=new Intent(getApplicationContext(),NoInternetActivity.class);
                        startActivity(intent);
                        e.printStackTrace();
                    }
                }


            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                System.out.print("params sent");
                Log.e("paramx","params sent" );
                return params;
            }
        };

        int x = 2;// retry count
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS*48,
                x, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getApplicationContext()).

                add(stringRequest);

    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN);

        swipeRefreshLayout.setRefreshing(true);
        (new Handler()).postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
                mSearches.clear();

                getData();
            }
        }, 2000);

    }
    private void SparesOfflineData() throws JSONException {
        pref=getApplicationContext().getSharedPreferences("towme",0);
        String jstring=  pref.getString("jsons",null);
        if (jstring!=null) {
            JSONObject jsonObject = new JSONObject(jstring);
            JSONArray jsonArray = jsonObject.getJSONArray("news");
            Log.d("jstrings", jstring);
            Log.d("dataserver", jsonArray.toString());
            Toast.makeText(getApplicationContext(), "You are offline", Toast.LENGTH_SHORT).show();
            for (int i = 0; i < jsonArray.length(); i++) {
                //stationsize = jsonArray.length();
                JSONObject jor = jsonArray.getJSONObject(i);

                mSearches.add(new mSearch("http://joslabs.co.ke/josmotos/img/"+jor.getString("pic"),"Max Weight: "+
                        jor.getString("Weight"),"tower "+jor.getString("userName"),"Around :"+jor.getString("Resdence")));
                Log.d("imgurl","http://joslabs.co.ke/josmotos/img/"+jor.getString("pic"));

                searchAdapter  = new mSearchAdapter (mSearches, getApplicationContext());
                recyclerView.setAdapter(searchAdapter);


            }
        }
        else {
            Intent intent=new Intent(getApplicationContext(),NoInternetActivity.class);
            startActivity(intent);
        }

    }
}
