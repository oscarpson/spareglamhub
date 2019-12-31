package stylist.com.glamhub.myorders;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
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
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import stylist.com.glamhub.FuelAdapter;
import stylist.com.glamhub.Fuelgetters;
import stylist.com.glamhub.MainActivity;
import stylist.com.glamhub.Motorregister;
import stylist.com.glamhub.R;
import stylist.com.glamhub.Updates;
import stylist.com.glamhub.internetError.NoInternetActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OrdersmadeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OrdersmadeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrdersmadeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
List<Updates>myorder;
    RecyclerView recyclerView;
    MyordersAdapter adapter;
SharedPreferences reqd;
    String ClientId;
    private View mProgressView;
    TextView txtprogress,txtnotregistered;
    Button register;
    private SwipeRefreshLayout swipeRefreshLayout;
    private OnFragmentInteractionListener mListener;
    CardView cardView;
    SharedPreferences pref;

    public OrdersmadeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrdersmadeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrdersmadeFragment newInstance(String param1, String param2) {
        OrdersmadeFragment fragment = new OrdersmadeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_ordersmade, container, false);
        recyclerView= (RecyclerView) view.findViewById(R.id.myorder_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(null);
        this.swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayoutUpdatesFragment);
        txtnotregistered= (TextView) view.findViewById(R.id.txtnotregister);
        register= (Button) view.findViewById(R.id.btnregister);
        cardView= (CardView) view.findViewById(R.id.cardview);

        reqd=getContext().getSharedPreferences("regd",0);
        ClientId=reqd.getString("clientId",null);
        if (ClientId==null)
        {
           // cardView.setVisibility(View.VISIBLE);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getContext(), Motorregister.class);
                    //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });
        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), Motorregister.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });
        this.swipeRefreshLayout.setOnRefreshListener(this);
        mProgressView = view.findViewById(R.id.simpleProgressBar);
        txtprogress= (TextView) view.findViewById(R.id.txtprogress);


        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
        mProgressView.animate().setDuration(shortAnimTime);
        mProgressView.setVisibility(View.VISIBLE);
        txtprogress.setVisibility(View.VISIBLE);

        myorder=new ArrayList<>();
        myorder.clear();

        setMyorders();
        return  view;
    }

    private void setMyorders() {

       // for(int i=0;i<10;i++) {
      //  int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
      //  mProgressView.animate().setDuration(shortAnimTime);

        StringRequest stringRequest=new StringRequest(Request.Method.POST, "http://joslabs.co.ke/josmotos/clientvieworder.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                mProgressView.setVisibility(  View.GONE);
                txtprogress.setVisibility(View.GONE);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    pref=getActivity().getSharedPreferences("ordersmade",0);
                    getContext().getSharedPreferences("ordersmade",0).edit().clear().apply();

                    SharedPreferences.Editor editor=pref.edit();
                    editor.putString("jsons",jsonObject.toString());
                    editor.apply();

                    String jstring=  pref.getString("jsons",null);
                    Log.d("myquotes",response);
                    JSONArray jsonArray = jsonObject.getJSONArray("myqoutes");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jor = jsonArray.getJSONObject(i);



                        myorder.add(new Updates("Sparename:\t"+jor.getString("sparename"), jor.getString("cartype")+"\t"+jor.getString("model"),
                                jor.getString("orderId"),  jor.getString("total"),  jor.getString("orderId"), "02.05.2017",jor.getString("photoStatus")));//photo status=jor.getString("photoStatus")

                        adapter = new MyordersAdapter(myorder, getContext());
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
                    try {
                        ordersOfflineData();
                    } catch (JSONException e) {


                        e.printStackTrace();
                    }
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
                params.put("clientId",ClientId);
                Log.e("paramx", "params sent");
                return params;
            }
        };

        int x = 2;// retry count
        stringRequest.setRetryPolicy(new

                DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48,
                x, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getContext()).

                add(stringRequest);


    }

    private void ordersOfflineData() throws JSONException {
        pref=getActivity().getSharedPreferences("ordersmade",0);
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

                myorder.add(new Updates(jor.getString("sparename"), jor.getString("cartype")+"\t"+jor.getString("model"),
                        jor.getString("orderId"),  jor.getString("total"),  jor.getString("orderId"), "02.05.2017",jor.getString("photoStatus")));

                adapter = new MyordersAdapter(myorder, getContext());
                recyclerView.setAdapter(adapter);

            }
        }
        else {
            Intent intent=new Intent(getContext(),NoInternetActivity.class);
            startActivity(intent);
        }

    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN);

        swipeRefreshLayout.setRefreshing(true);
        (new Handler()).postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
                myorder.clear();

                setMyorders();
            }
        }, 2000);

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

