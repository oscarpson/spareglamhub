package stylist.com.glamhub;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import stylist.com.glamhub.addfuel.FuelEdit;
import stylist.com.glamhub.internetError.NoInternetActivity;


public class Fuel extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView;
    private List<Fuelgetters> fuel,updates2;
    private SwipeRefreshLayout swipeRefreshLayout;
    private View mProgressView;
    TextView txtprogress;
    private final String UPDATES_URL="http://joslabs.co.ke/josmotos/fuel.php";
  //  private final String VERIFY_URL = "http://joslabs.co.ke/josmotos/register.php";

    public CoordinatorLayout coordinatorLayout;
    private String [] sname=new String [30];
    private String [] spetrol=new String [30];
    private String [] sdiesel=new String [30];
    private String [] sparaffin=new String [30];
    private int stationsize;
    SharedPreferences pref;
    public Fuel()
    {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.fragment_fuel, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.fuel_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(null);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.addfuel);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ints=new Intent(getContext(), FuelEdit.class);
                startActivity(ints);
            }
        });
     //   this.coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.coordinatorLayoutUpdatesFragmentLayout);
        this.swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayoutUpdatesFragment);

        this.swipeRefreshLayout.setOnRefreshListener(this);

        mProgressView = view.findViewById(R.id.simpleProgressBar);
        txtprogress= (TextView)view. findViewById(R.id.txtprogress);


        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
        mProgressView.animate().setDuration(shortAnimTime);
        mProgressView.setVisibility(View.VISIBLE);
        txtprogress.setVisibility(View.VISIBLE);
        this.fuel = new ArrayList<>();

        presetFuel();
        return view;
    }
    FuelAdapter fuelprice;

    private void presetFuel() {
        fuel.clear();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPDATES_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mProgressView.setVisibility(View.GONE);
                txtprogress.setVisibility(View.GONE);
                try {



                    //   Toast.makeText(getApplicationContext(),"Success1",Toast.LENGTH_LONG).show();
                    JSONObject jsonObject = new JSONObject(response);
                    pref=getActivity().getSharedPreferences("stations",0);
                    getContext().getSharedPreferences("stations",0).edit().clear().apply();

                    SharedPreferences.Editor editor=pref.edit();
                    editor.putString("jsons",jsonObject.toString());
                    editor.apply();

                    String jstring=  pref.getString("jsons",null);
                    Log.d("responceserver",response);

                    JSONArray jsonArray = jsonObject.getJSONArray("news");

                    Log.d("jstrings",jstring);
                    Log.d("dataserver",jsonArray.toString());
                    for (int i=0;i<jsonArray.length();i++) {
                        stationsize=jsonArray.length();
                        JSONObject jor=jsonArray.getJSONObject(i);
                        Log.d("fuelthings","onResponse:"+jor.getString("petrolPrice"));

                        //saving fuel  details
                        //getdata size of array
                       // editor.putInt("stationsize",stationsize);
                        //editor.putString

                       fuel.add(new Fuelgetters(jor.getString("stationName"),jor.getString("petrolPrice"),jor.getString("dieselPrice"),jor.getString("paraffinPrice"),jor.getString("latitude"),jor.getString("longitude")));
                        //fuel.add(new Fuelgetters("ngara","98","87","97"));
                        Log.d("oilkey", jor.getString("stationName")+jor.getString("dieselPrice"));

                        fuelprice  = new FuelAdapter (fuel,getActivity());
                        recyclerView.setAdapter(fuelprice);
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
                if (error instanceof NoConnectionError) {
                    Toast.makeText(getContext(), "No Internet", Toast.LENGTH_LONG).show();
                    mProgressView.setVisibility(View.GONE);
                    txtprogress.setVisibility(View.GONE);
                    try {
                        FuelOfflineData();
                    } catch (JSONException e) {


                        e.printStackTrace();
                    }
                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();


                Log.e("paramx","params sent" );
                return params;
            }
        };

        int x = 2;// retry count
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS*48,
                x, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getContext()).

                add(stringRequest);
    }

    private void FuelOfflineData() throws JSONException {
        pref=getActivity().getSharedPreferences("stations",0);
        String jstring=  pref.getString("jsons",null);
        if (jstring!=null) {
            JSONObject jsonObject = new JSONObject(jstring);
            JSONArray jsonArray = jsonObject.getJSONArray("news");
            Log.d("jstrings", jstring);
            Log.d("dataserver", jsonArray.toString());
            Toast.makeText(getContext(), "You are offline", Toast.LENGTH_SHORT).show();
            for (int i = 0; i < jsonArray.length(); i++) {
                stationsize = jsonArray.length();
                JSONObject jor = jsonArray.getJSONObject(i);

                fuel.add(new Fuelgetters(jor.getString("stationName"),jor.getString("petrolPrice"),jor.getString("dieselPrice"),jor.getString("paraffinPrice"),jor.getString("latitude"),jor.getString("longitude")));

                fuelprice = new FuelAdapter(fuel, getActivity());
                recyclerView.setAdapter(fuelprice);


            }
        }
        else {
            Intent intent=new Intent(getContext(),NoInternetActivity.class);
            startActivity(intent);
        }

    }


    @Override
    public void onRefresh() {
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN);

        swipeRefreshLayout.setRefreshing(true);
        (new Handler()).postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
              fuel.clear();
                presetFuel();

            }
        }, 2000);


    }
    }


