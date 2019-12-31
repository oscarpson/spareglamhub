package stylist.com.glamhub.myorders;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
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

import java.util.HashMap;
import java.util.Map;

import stylist.com.glamhub.MainActivity;
import stylist.com.glamhub.R;
import stylist.com.glamhub.internetError.NoInternetActivity;

public class ShowMessage extends AppCompatActivity {
    String price, garage, orderId, clientId;
    SharedPreferences regd;
    private View mProgressView;
    TextView txtprogress;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_message);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle extras= getIntent().getExtras();
        price=extras.getString("price");
        garage=extras.getString("garage");
        orderId=extras.getString("orderId");
        regd=getApplicationContext().getSharedPreferences("regd",0);
        clientId=regd.getString("clientId", null);
        mProgressView = findViewById(R.id.simpleProgressBar);
        txtprogress= (TextView) findViewById(R.id.txtprogress);


        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
        mProgressView.animate().setDuration(shortAnimTime);
        mProgressView.setVisibility(View.VISIBLE);
        txtprogress.setVisibility(View.VISIBLE);



        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://joslabs.co.ke/josmotos/selectedquote.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("userData", response);
                mProgressView.setVisibility(  View.GONE);
                txtprogress.setVisibility(View.GONE);
                if (response.toString().equals("1")) {

                    // Intent ints=new Intent(context,ShowMessage.class);
                    // context.startActivity(ints);
                    showLoginSuceessDialog();

                } else  if (response.toString().equals("2")){

                    YourOrderwassuccess();
                }

                else {
                    Log.d("userData2", "error occured");
                }


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getInt("Success") == 1) {
                        Log.d("responces", "correct");
                    }

                    Log.e("responce2", response);

                    JSONArray jsonArray = jsonObject.getJSONArray("news");
                    // for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jor = jsonArray.getJSONObject(1);


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
                    Intent intent=new Intent(getApplicationContext(), NoInternetActivity.class);
                    startActivity(intent);
                    finish();

                }

                if (error instanceof NetworkError) {
                    // Toast.makeText(Motorregister.this, "No Internet", Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(getApplicationContext(), NoInternetActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("clientId",clientId);
                params.put("garage",garage);
                params.put("price",price);
                params.put("orderId",orderId);

                System.out.print("was here");
                Log.e("paramx", params.toString());
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
    private void YourOrderwassuccess() {
        final AlertDialog dialog = new AlertDialog.Builder(ShowMessage.this).create();
        dialog.setTitle("Order already exist");
        dialog.setMessage("Your order worth"+price+"\tfrom\t"+garage+"alredy exist and is being processed");
        dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
                Intent ints=new Intent(getApplicationContext(), MainActivity.class);
                startActivity(ints);
                finish();
            }
        });
        dialog.setIcon(R.drawable.warning);
        dialog.show();
    }

    private void showLoginSuceessDialog() {
        final AlertDialog dialog = new AlertDialog.Builder(ShowMessage.this).create();
        dialog.setTitle("Order success");
        dialog.setMessage("ordering"+"\t at this "+"price");
        dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
                Intent ints=new Intent(getApplicationContext(), MainActivity.class);
                ints.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(ints);
                finish();
            }
        });
        dialog.setIcon(R.drawable.success_tick);
        dialog.show();
    }

}
