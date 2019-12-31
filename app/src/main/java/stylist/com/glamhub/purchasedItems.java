package stylist.com.glamhub;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import stylist.com.glamhub.PurchasedItems.PurchaseAdapter;
import stylist.com.glamhub.Verify.VerificationAccount;
import stylist.com.glamhub.internetError.NoInternetActivity;

/**
 * Created by OSCAR on 5/6/2017.
 */

public class purchasedItems extends AppCompatActivity {

    private CoordinatorLayout coordinatorLayout;

    private RecyclerView recyclerView;
    private List<Updates> mylist, msearch2;
    mSearchAdapter msearchAdapter;
    TextView tempty;
    Button btncheckout;
    int counter;
    private View mProgressView;
    TextView txtprogress;
    SharedPreferences pref,regd;
    private final String BUYSPARES_URL = "http://joslabs.co.ke/josmotos/Spareorder.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mylist_activity);
        btncheckout= (Button) findViewById(R.id.btncheckout);
        tempty= (TextView) findViewById(R.id.txtempty);

        ///   setContentView (R.layout.fragment_updates);
        recyclerView = (RecyclerView) findViewById(R.id.mylist_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(null);
        mProgressView = findViewById(R.id.simpleProgressBar);
        txtprogress= (TextView) findViewById(R.id.txtprogress);





        this.mylist = new ArrayList<>();
        regd = getApplicationContext().getSharedPreferences("regd", 0);
        pref=getApplicationContext().getSharedPreferences("arrayitem",0);
        counter=pref.getInt("count",0);
       if (counter==0)
        {
            showEmpty();
        }
        presetdata();
btncheckout.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
        mProgressView.animate().setDuration(shortAnimTime);
        mProgressView.setVisibility(View.VISIBLE);
        txtprogress.setVisibility(View.VISIBLE);
        btncheckout.setEnabled(false);
        sendData();
    }
});

    }

    private void showEmpty() {
        tempty.setVisibility(View.VISIBLE);
        btncheckout.setVisibility(View.GONE);
    }


    private void getData() {
    }

    PurchaseAdapter partitems;

    private void presetdata() {
       // mylist.add(new Updates("Front Signal Lamp","KSh 4000","a","Lexus D34"));


        String itemId[]=new String[counter];
        String price[]=new String[counter];
        String model[]=new String[counter];
        String partName[]=new String[counter];
        for (int i=0;i<counter;i++)
        {
            itemId[i]=pref.getString("shoppingitem"+i,null);
            price[i]=pref.getString("shoppingPrice"+i,null);
            model[i]=pref.getString("shoppingModel"+i,null);
            partName[i]=pref.getString("shoppingPartname"+i,null);

           // Toast.makeText(this,array[i]+"item"+i+"\n"+barray[i]+"\t"+i,Toast.LENGTH_LONG).show();
            if(itemId[i]!=null) {
                mylist.add(new Updates(partName[i], price[i], itemId[i], model[i]));
                partitems = new PurchaseAdapter(mylist, getApplicationContext());

                recyclerView.setAdapter(partitems);
            }
        }

    }
    private void sendData() {

        regd.getString("phone",null);
        boolean validated=regd.getBoolean("validated",false);
        if(validated)
        {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, BUYSPARES_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("responseats", response);
                    mProgressView.setVisibility(View.GONE);
                    txtprogress.setVisibility(View.GONE);
                    //  Toast.makeText(Bus_book_check_seats.this, "Expecting responce", Toast.LENGTH_SHORT).show();
                    if (response.toString().matches("0")||response.toString().contains("Error")) {

                        Log.d("myrespo", response);
                        Purchasefailed();

                    } else {


                        // Toast.makeText(RegisterActivity.this, pilotid, Toast.LENGTH_SHORT).show();
                        PurchaseSuceessDialog();

                    }

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Log.d("responseats", response);


                    } catch (JSONException e) {
                        Log.e("exception ", "Exception encoutered ");
                        //  Toast.makeText(getContext(),"Exception encoutered ",Toast.LENGTH_LONG);
                        e.printStackTrace();

                    }
                    //JsonArrayRequest jsonArrayRequest=
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    mProgressView.setVisibility(View.GONE);
                    txtprogress.setVisibility(View.GONE);
                    if (error instanceof NoConnectionError) {
                        Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_LONG).show();
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
                    counter =pref.getInt("count",0);
                    params.put("items", counter + "");
                    String itemId[] = new String[counter];
                    for (int i = 0; i < counter; i++) {
                        itemId[i] = pref.getString("shoppingitem" + i, null);
                        params.put("shoppingitem" + i, itemId[i]);
                    }

                    params.put("phone", regd.getString("phone", null));
                    params.put("model", regd.getString("model", null));
                    params.put("type", regd.getString("type", null));
                   params.put("username",regd.getString("username", null));
                    params.put("clientId",regd.getString("clientId",null));
                    // params.put(REG_ID,r)
                    System.out.print("was here");
                    // Toast.makeText(getApplicationContext(),username+"\t"+phone,Toast.LENGTH_LONG).show();
                    Log.e("paraitemx", params.toString() + "params sent");
                    ;
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
            // getApplicationContext().getSharedPreferences("arrayitem", 0).edit().clear().apply();

        }
        else
        {
            String prefphone=pref.getString("phone",null);

            if (prefphone==null) {
                mProgressView.setVisibility(View.GONE);
                txtprogress.setVisibility(View.GONE);
                btncheckout.setEnabled(true);
                final AlertDialog dialog = new AlertDialog.Builder(purchasedItems.this).create();
                dialog.setTitle("Register");
                dialog.setMessage("Register first to complete this order");
                dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialog.dismiss();


                        Intent intent = new Intent(getApplicationContext(), Motorregister.class);
                        startActivity(intent);


                    }
                });
                dialog.setIcon(R.drawable.notregistered);
                dialog.show();
            }
            if (!validated)
            {
                mProgressView.setVisibility(View.GONE);
                txtprogress.setVisibility(View.GONE);
                btncheckout.setEnabled(true);
                final AlertDialog dialog = new AlertDialog.Builder(purchasedItems.this).create();
                dialog.setTitle("Validation error");
                dialog.setMessage("Your account is not verified");
                dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialog.dismiss();


                        Intent intent = new Intent(getApplicationContext(), VerificationAccount.class);
                        startActivity(intent);


                    }
                });
                dialog.setIcon(R.drawable.notregistered);
                dialog.show();
            }
        }

    }





    private void PurchaseSuceessDialog() {

        getApplicationContext().getSharedPreferences("arrayitem",0).edit().clear().apply();
        int k=pref.getInt("count",0);
        final AlertDialog dialog = new AlertDialog.Builder(purchasedItems.this).create();
        dialog.setTitle("Success");
        dialog.setMessage("Your order was successful ,an agent will confirm your order shortly");
        dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();



                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);


            }
        });
        dialog.setIcon(R.drawable.tickd);
        dialog.show();
    }

    private void Purchasefailed() {
        Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show();
        final AlertDialog dialog = new AlertDialog.Builder(purchasedItems.this).create();
        dialog.setTitle("Fail");
        dialog.setMessage("Your order was failed,contact our agent for more information");
        dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();



                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);


            }
        });
        dialog.setIcon(R.drawable.warning);
        dialog.show();
    }


}




