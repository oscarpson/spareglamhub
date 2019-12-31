package stylist.com.glamhub;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

import stylist.com.glamhub.internetError.NoInternetActivity;

import static stylist.com.glamhub.Preference.pref;


public class UpdatesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private CoordinatorLayout coordinatorLayout;

SharedPreferences pref;

    EditText edtSearch;
    private RecyclerView recyclerView;
    private List<Updates> updates,updates2;
    UpdatesAdapter newsAdapter;
    private View mProgressView;
    TextView txtprogress;
    private SwipeRefreshLayout swipeRefreshLayout;
    private final String UPDATES_URL="http://joslabs.co.ke/josmotos/spares.php";
   // private final String UPDATES_URL="http://192.168.26.1/jsonarray/sparestable.php";


    public UpdatesFragment()
    {

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

      /*  SharedPreferences settings=getActivity().getSharedPreferences("prefs",0);
        boolean firstRun=settings.getBoolean("firstRun",false);
        if(firstRun==false)//if running for first time
        //Splash will load for first time
        {
            SharedPreferences.Editor editor=settings.edit();
            editor.putBoolean("firstRun",true);
            editor.commit();
            Intent i;
            i = new Intent(getActivity(),Motorregister.class);
            startActivity(i);
            //finish();

        }
*/
        View view =  inflater.inflate(R.layout.fragment_updates, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.updates_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(null);

       // this.coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.coordinatorLayoutUpdatesFragmentLayout);
        this.swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayoutUpdatesFragment);

        this.swipeRefreshLayout.setOnRefreshListener(this);
        mProgressView = view.findViewById(R.id.simpleProgressBar);
        txtprogress= (TextView)view. findViewById(R.id.txtprogress);


        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
        mProgressView.animate().setDuration(shortAnimTime);
        mProgressView.setVisibility(View.VISIBLE);
        txtprogress.setVisibility(View.VISIBLE);
        this.updates = new ArrayList<>();
        updates.clear();

        presetNews();

        edtSearch=(EditText)view.findViewById(R.id.edtsearch);
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updates2=new ArrayList<>();
                updates2.clear();
                //finalist.clear();


                //Log.d("Finalist code:",finalists3.get(0).getCode());
                //finalists2.clear();
                for (int i=0; i<updates.size();i++)
                {
                    if(updates.get(i).getPartname().toUpperCase().contains(edtSearch.getText().toString().toUpperCase())
                            || updates.get(i).getVehiclemodel().toUpperCase().contains(edtSearch.getText().toString().toUpperCase())
                           )
                    {
                        updates2.add(updates.get(i));

                    }



                }



                newsAdapter = new UpdatesAdapter(updates2, getContext());

                recyclerView.setAdapter(newsAdapter);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }

    private void presetNews()
   {
       StringRequest stringRequest = new StringRequest(Request.Method.POST, UPDATES_URL, new Response.Listener<String>() {
           @Override
           public void onResponse(String response) {
               mProgressView.setVisibility(View.GONE);
               txtprogress.setVisibility(View.GONE);
               try {

                   //   Toast.makeText(getApplicationContext(),"Success1",Toast.LENGTH_LONG).show();
                   JSONObject jsonObject = new JSONObject(response);
                   Log.d("responceserver",response);
                   pref=getActivity().getSharedPreferences("spares",0);
                   getContext().getSharedPreferences("spares",0).edit().clear().apply();

                   SharedPreferences.Editor editor=pref.edit();
                   editor.putString("jsons",jsonObject.toString());
                   editor.apply();
                   JSONArray jsonArray = jsonObject.getJSONArray("news");
                   Log.d("dataserver",jsonArray.toString());
                   for (int i=0;i<jsonArray.length();i++) {

                       JSONObject jor=jsonArray.getJSONObject(i);
                       updates.add(new Updates(jor.getString("partID"),"http://joslabs.co.ke/josmotos/photos/"+jor.getString("partPhoto"),
                               jor.getString("partName"),"KSh"+jor.getString("price"),jor.getString("carType")+"\t"+jor.getString("carModel")+"\t"+jor.getString("carYear")));

                       Log.d("imgurl","http://joslabs.co.ke/josmotos/photos/"+jor.getString("partPhoto"));


                      newsAdapter = new UpdatesAdapter (updates, getActivity());

                       recyclerView.setAdapter(newsAdapter);



                   }
                 //  updates.add(new Updates(1,"","kk","lop","jiu"));



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
                   Toast.makeText(getContext(), "No Internet", Toast.LENGTH_LONG).show();
                   try {
                       SparesOfflineData();
                   } catch (JSONException e) {


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
       stringRequest.setRetryPolicy(new

               DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS*48,
               x, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
       //stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
       Volley.newRequestQueue(getContext()).

               add(stringRequest);


   }

    private void SparesOfflineData() throws JSONException {
        pref=getActivity().getSharedPreferences("spares",0);
        String jstring=  pref.getString("jsons",null);
        if (jstring!=null) {
            JSONObject jsonObject = new JSONObject(jstring);
            JSONArray jsonArray = jsonObject.getJSONArray("news");
            Log.d("jstrings", jstring);
            Log.d("dataserver", jsonArray.toString());
            Toast.makeText(getContext(), "You are offline", Toast.LENGTH_SHORT).show();
            for (int i = 0; i < jsonArray.length(); i++) {
                //stationsize = jsonArray.length();
                JSONObject jor = jsonArray.getJSONObject(i);

                updates.add(new Updates(jor.getString("partID"),"http://joslabs.co.ke/josmotos/photos/"+jor.getString("partPhoto"),
                        jor.getString("partName"),"KSh"+jor.getString("price"),jor.getString("carType")+"\t"+jor.getString("carModel")+"\t"+jor.getString("carYear")));


                newsAdapter = new UpdatesAdapter (updates, getActivity());

                recyclerView.setAdapter(newsAdapter);


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
                updates.clear();
                presetNews();
            }
        }, 2000);


    }


}
