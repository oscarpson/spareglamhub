package stylist.com.glamhub;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
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
import com.firebase.client.Firebase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import stylist.com.glamhub.SmsGateway.AfricasTalkingGateway;
import stylist.com.glamhub.SmsGateway.GatewayKey;
import stylist.com.glamhub.Verify.VerificationAccount;
import stylist.com.glamhub.fireb.Fireb;
import stylist.com.glamhub.internetError.NoInternetActivity;
import stylist.com.glamhub.menuitems.Terms;

public class Motorregister extends ActionBarActivity {
 Spinner spngender;
    TextView jname,jphone,jmodel,jtype,termlink;
    Button jregister;
    final String USERNAME="Name";
    final String PHONE_NO="Phone_No";
    final String MODEL="Model";
    final static  String VCODE="vcode";
    final String TYPE="Type";
    int verifycode;
SharedPreferences pref;
    private final String VERIFY_URL = "http://joslabs.co.ke/josmotos/register.php";
     String jnames ;
     String jphones;
     String jmodels ;
    String jtypes;
    private View mProgressView;

    String  clientId;
    CheckBox checkBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
pref=getApplicationContext().getSharedPreferences("regd",0);

        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_motorregister);
        jname = (TextView) findViewById(R.id.jname);
        jphone = (TextView) findViewById(R.id.jphone);
        jmodel = (TextView) findViewById(R.id.jmodel);
        jtype= (TextView) findViewById(R.id.jtype);
        termlink= (TextView) findViewById(R.id.tvAgreeTermsLink);
        jregister = (Button) findViewById(R.id.jregister_button);
        checkBox= (CheckBox) findViewById(R.id.checkbox);
        mProgressView = findViewById(R.id.simpleProgressBar);




        termlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), Terms.class);
                startActivity(intent);
            }
        });
        verifycode= (int) ((Math.random()*9000)+1000);
        jregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jregister.setVisibility(View.GONE);


                if (validate()) {

                    if (checked()) {
                        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
                        mProgressView.animate().setDuration(shortAnimTime);
                        mProgressView.setVisibility(View.VISIBLE);

                        Firebase firebase = new Firebase(Constants.FIREBASE_APP);

                        //Pushing a new element to firebase it will automatically create a unique id
                        Firebase newFirebase = firebase.push();

                        //Creating a map to store name value pair
                        Map<String, String> val = new HashMap<>();

                        //pushing msg = none in the map
                        val.put("msg", "none");

                        //saving the map to firebase
                        newFirebase.setValue(val);

                        //Getting the unique id generated at firebase
                        String uniqueId = newFirebase.getKey();
                        Toast.makeText(Motorregister.this, uniqueId+"", Toast.LENGTH_SHORT).show();
                        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREF, MODE_PRIVATE);

                        //Opening the shared preferences editor to save values
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putString(Constants.UNIQUE_ID, uniqueId);

                        //Saving the boolean as true i.e. the device is registered
                        editor.putBoolean(Constants.REGISTERED, true);

                        //Applying the changes on sharedpreferences
                        editor.apply();

                        //Starting our listener service once the device is registered
                        startService(new Intent(getBaseContext(), NotificationListener.class));
                        SendToServer(uniqueId);




                    } else

                    {
                        final AlertDialog dialog = new AlertDialog.Builder(Motorregister.this).create();
                        dialog.setTitle("Check terms");
                        dialog.setMessage("You must accept terms and condition to proceed");
                        dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialog.dismiss();
                            }
                        });
                        dialog.setIcon(R.mipmap.error);
                        dialog.show();
                    }
                }
            }
        });




    }

    private void SendToServer(final String uniqueId) {
        jnames = jname.getText().toString();
        jphones = jphone.getText().toString();
        jmodels = jmodel.getText().toString();
        jtypes = jtype.getText().toString();
        final DbHelper dbHelper = new DbHelper(getApplicationContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, VERIFY_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("userData", response);
                mProgressView.setVisibility(View.GONE);

                if (response.toString().equals("0") ||response.toString().contains("error")) {

                } else {

                    clientId=response.toString();
                    dbHelper.addUser(new User(jnames, jmodels, "true", null, null, null, jtypes, null));
                    showLoginSuceessDialog(uniqueId);
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
                mProgressView.setVisibility(View.GONE);


                if (error instanceof NoConnectionError) {
                    //  Toast.makeText(Motorregister.this, "No Internet", Toast.LENGTH_LONG).show();
                }

                if (error instanceof NetworkError) {
                    // Toast.makeText(Motorregister.this, "No Internet", Toast.LENGTH_LONG).show();
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
                params.put(USERNAME, jnames);
                params.put(PHONE_NO, jphones);
                params.put(MODEL, jmodels);
                params.put(TYPE, jtypes);
                params.put(VCODE, verifycode + "");
                params.put("firebaseid",uniqueId);
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
    }

    private boolean checked() {
        if (checkBox.isChecked())
        {
            return  true;
        }
        return true;
    }

    private boolean validate(){
        Pattern p = Pattern.compile("[^A-Za-z0-9-\\s+]");
        Matcher m = p.matcher(jname.getText().toString());
        boolean found = m.find();

        if(jname.getText().toString().length()==0)

        {
            jname.setError("Name field is required");
            jname.requestFocus();
            return false;
        }

        else if(jname.getText().toString().length()<3)

        {
            jname.setError("Name should not be less than 3 characters");
            jname.requestFocus();
            return false;
        }

        if(found)

        {
            jname.setError("Invalid characters in the name");
            jname.requestFocus();
            return false;
        }


        if(jmodel.getText().toString().length()==0)

        {
            jmodel.setError("Model field is required");
            jmodel.requestFocus();
            return false;
        }
        if(jtype.getText().toString().length()==0)

        {
            jtype.setError("Type field is required");
            jtype.requestFocus();
            return false;
        }

        if(jphone.getText().toString().length()==0)

        {
            jphone.setError("Phone Number is required");
            jphone.requestFocus();
            return false;

        }

        if((jphone.getText().toString().length()>0&&jphone.getText().toString().length()<10)||jphone.getText().toString().length()>13)

        {
            jphone.setError("Incorrect phone number");
            jphone.requestFocus();
            return false;

        }

        try

        {


            int num = Integer.parseInt(jphone.getText().toString().replaceAll("\\s+", ""));
            if (jphone.getText().toString().contains("-")) {
                jphone.setError("Incorrect phone number format");
                jphone.requestFocus();
                return false;
            }
        }

        catch(NumberFormatException e)

        {
            jphone.setError("Incorrect phone number format");
            jphone.requestFocus();
            return false;
        }

        return true;
    }


    private void showLoginErrorDialog() {


            final AlertDialog dialog = new AlertDialog.Builder(Motorregister.this).create();
            dialog.setTitle("Error");
            dialog.setMessage("Registration. The telephone number has already been used to register an account");
            dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialog.dismiss();
                }
            });
            dialog.setIcon(R.mipmap.error);
            dialog.show();
        }

    private void showLoginSuceessDialog(String uniqueId) {
        SharedPreferences.Editor editor=pref.edit();
        editor.putString("username",jnames);
        editor.putString("phone",jphones);
        editor.putString("model",jmodels);
        editor.putString("type",jtypes);
        editor.putBoolean("validated",false);
        editor.putString("clientId",clientId);
        editor.putBoolean("isregistered",true);
        editor.putString("fireId",uniqueId);
        editor.apply();
        new Sendmsg().execute("0708396044");
        Log.e("dialogme", "showdialogme" );
            final AlertDialog dialog = new AlertDialog.Builder(Motorregister.this).create();
            dialog.setTitle("Success");
            dialog.setMessage("Registration was successful please enter sms verification code");
            dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialog.dismiss();

                    Intent intent = new Intent(getApplicationContext(), VerificationAccount.class);
                    startActivity(intent);

                }
            });
        dialog.setIcon(R.drawable.tickd);
            dialog.show();
        }


    private void mainclass() {

        Intent intent = new Intent(Motorregister.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    class Sendmsg extends AsyncTask
    {



        @Override
        protected Object doInBackground(Object[] params) {
         // pref=getApplicationContext().getSharedPreferences("regd",0);
            String  phone = pref.getString("phone", null);

            AfricasTalkingGateway gateway= new AfricasTalkingGateway(new GatewayKey().username,new GatewayKey().gatewayKey);
            try {
                //String phone="0708396044";
                String mesage="Use code :\t"+verifycode+" to verify  your Kenyan traffic account";
                gateway.sendMessage(phone,mesage);
                Log.d("ERROR_FOUND_NOT"," error");
            } catch (Exception e) {
                Log.d("ERROR_FOUND",e.toString()+" error");
                Toast.makeText(getApplicationContext(),"Error occured",Toast.LENGTH_SHORT);

            }
            return null;
        }
    }

}

