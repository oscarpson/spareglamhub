package stylist.com.glamhub;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
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

public class MotorSearch extends AppCompatActivity {
Button btnseach;
    EditText edtsearch;
    private RecyclerView recyclerView;
   MotorsearchAdapter msearchAdapter;
    private List<motorsearchGetters> msearch,msearch2;
    public MotorSearch()
    {

    }

    private final String USER_CHAT_URL = "http://192.168.171.1/jsonarray/news.php";
    private final String USER_FEEDBACK_URL = "http://account.bethechange.co.ke/api/feedback";
    private final String UPDATES_URL="http://joslabs.co.ke/josmotos/spares.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motor_search);

        recyclerView = (RecyclerView) findViewById(R.id.motorsearch_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(null);

        //  this.swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayoutStudentFragment);

        //   this.swipeRefreshLayout.setOnRefreshListener(this);

        this.msearch = new ArrayList<>();
        // presetStudent();
        getData();
        edtsearch=(EditText)findViewById(R.id.edtsearch);
        btnseach=(Button)findViewById(R.id.btnsearch);
        btnseach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msearch2=new ArrayList<>();
                msearch2.clear();
                //finalist.clear();


                //Log.d("Finalist code:",finalists3.get(0).getCode());
                //finalists2.clear();
                for (int i=0; i<msearch.size();i++)
                {
                    if(msearch.get(i).getmSearchdetails().toUpperCase().contains(edtsearch.getText().toString().toUpperCase())
                            || msearch.get(i).getmSearchtype().toUpperCase().contains(edtsearch.getText().toString().toUpperCase())
                            || msearch.get(i).getmSearchmoel().toUpperCase().contains(edtsearch.getText().toString().toUpperCase()))
                    {
                        msearch2.add(msearch.get(i));

                    }



                }



                msearchAdapter = new MotorsearchAdapter(msearch2, getApplication());

                recyclerView.setAdapter(msearchAdapter);


            }

        });

    }
MotorsearchAdapter motosrch;
    private void getData() {
        msearch.clear();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPDATES_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {




                    //   Toast.makeText(getApplicationContext(),"Success1",Toast.LENGTH_LONG).show();
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d("responceserver",response);

                    JSONArray jsonArray = jsonObject.getJSONArray("news");
                    Log.d("dataserver",jsonArray.toString());
                    for (int i=0;i<jsonArray.length();i++) {

                        JSONObject jor=jsonArray.getJSONObject(i);
                        msearch.add(new motorsearchGetters("http://joslabs.co.ke/josmotos/photos/"+jor.getString("partPhoto"),
                                "Vehicle Type: "+jor.getString("carType"),"Part-name"+jor.getString("partName"),"Model Year: "+jor.getString("carYear")));
                       // Log.d("imgurl","http://192.168.26.1/jsonarray/img/"+jor.getString("jpic"));

                        motosrch  = new MotorsearchAdapter (msearch, getApplicationContext());
                        recyclerView.setAdapter(motosrch);
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
}
