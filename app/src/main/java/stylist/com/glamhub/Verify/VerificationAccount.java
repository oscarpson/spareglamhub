package stylist.com.glamhub.Verify;

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
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;

import stylist.com.glamhub.DbHelper;
import stylist.com.glamhub.MainActivity;
import stylist.com.glamhub.R;
import stylist.com.glamhub.User;
import stylist.com.glamhub.internetError.NoInternetActivity;

public class VerificationAccount extends AppCompatActivity {
    Button btnverify;
    EditText edtcode,Phoned;
    SharedPreferences pref;
    String   phone,vcode;
    final static  String PHONE="phone";
    final  static  String VCODE="vcode";
    final static String VERIFICATION_URL="http://joslabs.co.ke/josmotos/verifymotos.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_account);
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        pref=getApplicationContext().getSharedPreferences("regd",0);
        phone = pref.getString("phone", null);
        btnverify= (Button) findViewById(R.id.btnverify);
        Phoned= (EditText) findViewById(R.id.txtphone);
        Phoned.setText(phone);
        edtcode= (EditText) findViewById(R.id.code);
        btnverify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(verifycode())
                {


                  //  phone = pref.getString("phone", null);
                    vcode=edtcode.getText().toString();
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, VERIFICATION_URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                Log.d("orderSuccess", response);
                                if (response.toString().equals("1")) {
                                    // dbHelper.addUser(new User( jnames,jmodels,"true",null,null,null, jtypes,null));
                                    showVerifiedSuceessDialog();
                                } else {
                                    Log.d("userData2", "error occured");
                                    FailedRequests();
                                }


                                //   Toast.makeText(getApplicationContext(),"Success1",Toast.LENGTH_LONG).show();
                                JSONObject jsonObject = new JSONObject(response);
                                Log.d("responceserver", response);



                            } catch (JSONException e) {
                                Log.e("exception ", "Exception encoutered ");
                                //  Toast.makeText(getApplicationContext(),"Exception encoutered ",Toast.LENGTH_LONG);
                                e.printStackTrace();

                            }
                            //JsonArrayRequest jsonArrayRequest=
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (error instanceof NoConnectionError) {
                                //    Toast.makeText(getApplicationContext(),"No Internet",Toast.LENGTH_LONG).show();
                                Intent intent=new Intent(getApplicationContext(), NoInternetActivity.class);
                                startActivity(intent);
                            }



                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put(VCODE, vcode);
                            params.put(PHONE, phone);
                            System.out.print("params sent");
                            Log.e("paramx", params+"");
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
                else
                {
                notRegistered();
                }


        }
    }    );
    }

    private void notRegistered() {
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
                dialog.setTitle("Not Registered");
                dialog.setMessage("please register first");
                dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialog.dismiss();


                    }
                });
                dialog.setIcon(R.drawable.warning);
                dialog.show();

            }




    private void FailedRequests() {

        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setTitle("Failed");
        dialog.setMessage("Wrong code try again");
        dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();


            }
        });
        dialog.setIcon(R.drawable.warning);
        dialog.show();

    }

    private void showVerifiedSuceessDialog() {
        SharedPreferences.Editor editor=pref.edit();

        //dbHelper.updateStatus(t)
       editor.putBoolean("validated",true);
        editor.apply();

        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setTitle("success");
        dialog.setMessage("Verification  success");
        dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();

Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

            }
        });
        dialog.setIcon(R.drawable.success_tick);
        dialog.show();
    }

    private boolean verifycode() {

        if(edtcode.getText().toString().length()==0)

        {
            edtcode.setError("Code Number is required");
            edtcode.requestFocus();
            return false;

        }

        if((edtcode.getText().toString().length()>0&&edtcode.getText().toString().length()<4))

        {
            edtcode.setError("Incorrect Code ");
            edtcode.requestFocus();
            return false;

        }
        return  true;
    }


}

