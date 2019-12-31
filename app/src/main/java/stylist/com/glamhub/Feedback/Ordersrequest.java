package stylist.com.glamhub.Feedback;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.FileUriExposedException;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;
import stylist.com.glamhub.MainActivity;

import stylist.com.glamhub.Motorregister;
import stylist.com.glamhub.R;

import stylist.com.glamhub.Verify.VerificationAccount;
import stylist.com.glamhub.internetError.NoInternetActivity;
import stylist.com.glamhub.myorders.PhotoaddActivity;

import static stylist.com.glamhub.R.id.imgProfilePic;

public class Ordersrequest extends AppCompatActivity {
Button btnorder;
    EditText phone,model,sparename;
    SharedPreferences pref;
    String phoned,models,sparenames,imageName,  formated,pic;
    CircleImageView uploadphoto,camera;
    ImageView sparephoto;
    final static String PHONE="phone";

    final static  String TYPE="mtype";
    final  static  String MODEL_SENT="model";
    final  static  String PHONE_PREF="phoneb";
    final static String SPARE_ORDERING="spare";
    final static  String USER_NAME="username";
    final static  String CLIENT_ID="clientId";
    String yourPhone,yourModel,username,clientId,mtype;
    final int TAKE_PHOTO_REQ = 100;
    FileUriExposedException fileUri;
    public int SELECT_PICTURE = 1;
    Bitmap bitmap,bitmapx;
    final  static  String ORDERING_URL= "http://joslabs.co.ke/josmotos/placeorder.php";
    private View mProgressView;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_PICTURE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);


                //Setting the Bitmap to ImageView
                // cropImageView.setImageBitmap(bitmap);
                // Bitmap cropped = cropImageView.getCroppedImage();

                //imageView.setImageBitmap(bitmap);

                sparephoto.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == TAKE_PHOTO_REQ) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap photo = extras.getParcelable("data");
                uploadphoto.setImageBitmap(photo);

            }
        }
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordersrequest);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        pref=getApplicationContext().getSharedPreferences("regd",0);
        bitmap=null;
        yourPhone=pref.getString("phone",null);
       yourModel=pref.getString("model",null);

        uploadphoto= (CircleImageView) findViewById(R.id.imgcamera);
        camera= (CircleImageView) findViewById(R.id.imgphotos);
        sparephoto= (ImageView) findViewById(R.id.sparephoto);
        username=pref.getString("username",null);
            clientId=pref.getString("clientId",null);
        mtype=pref.getString("type",null);
        btnorder= (Button) findViewById(R.id.btnorder);
        phone= (EditText) findViewById(R.id.edtphone);
        model= (EditText) findViewById(R.id.edtcarmodels);


        Calendar c=Calendar.getInstance();
        SimpleDateFormat df=new SimpleDateFormat("yyyyMMddHHmmss");
        formated=df.format(c.getTime());
        int secs=c.get(Calendar.DATE)+c.get(Calendar.MONTH)+c.get(Calendar.YEAR);
        String curdate= DateFormat.getDateTimeInstance().format(new Date());
        //Toast.makeText(this, ""+curdate, Toast.LENGTH_LONG).show();
        Log.e("timex",secs+"\n"+c.getTime()+"\n"+formated);




        if (yourPhone!=null && yourModel!=null) {


            phone.setText(yourPhone + "");
            model.setText("\t"+mtype+""+yourModel + "");
        }
        model= (EditText) findViewById(R.id.edtcarmodels);
        sparename= (EditText) findViewById(R.id.edtspare);
        final boolean validated=pref.getBoolean("validated",false);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(Ordersrequest.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        // new Intent("com.android.camera.action.CROP");

                        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString());


                        startActivityForResult(intent, TAKE_PHOTO_REQ);

                    }
                } else {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString());


                    startActivityForResult(intent, TAKE_PHOTO_REQ);
                }
            }
        });
        uploadphoto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture from Gallery"), 1);
            }
        });

        btnorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
                mProgressView.animate().setDuration(shortAnimTime);
                mProgressView.setVisibility(View.VISIBLE);*/
                if (yourPhone==null) {
                    Notregistered();
                }
                else {
                    if (Valid()) {
                        phoned = phone.getText().toString();
                        models = model.getText().toString();
                        sparenames = sparename.getText().toString();


                        // Toast.makeText(this, "uploading", Toast.LENGTH_SHORT).show();

                        uploadphoto.buildDrawingCache();
                        //DbHelper dbHelper=new DbHelper(getApplicationContext());
                        //final String token=dbHelper.getAllUsers().get(0).getToken();
                        final Bitmap bmap = uploadphoto.getDrawingCache();
                        final ProgressDialog loading = ProgressDialog.show(Ordersrequest.this, "Uploading...", "Please wait...", false, false);
                        //   if(validated) {


                        StringRequest stringRequest = new StringRequest(Request.Method.POST, ORDERING_URL, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
//progressDialog.dismiss();


                                loading.dismiss();
                                if (response.toString().equals("1")) {
                                    // dbHelper.addUser(new User( jnames,jmodels,"true",null,null,null, jtypes,null));
                                    showOrderSuceessDialog();
                                } else {
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
                                Log.d("Chats Error", error.toString());
                                // progressDialog.dismiss();
                                loading.dismiss();

                                error.printStackTrace();
                                if (error instanceof NoConnectionError) {
                                    Intent intent = new Intent(getApplicationContext(), NoInternetActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();
                                }


                            }

                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                // Log.d("Token: ",dbHelper.getAllUsers().get(0).getToken());
                                // params.put("token",dbHelper.getAllUsers().get(0).getToken());
                                if (bitmap != null) {
                                    pic = getStringImage(bitmap);
                                } else {
                                    pic = "";
                                }

                                params.put(PHONE, phoned);
                                params.put(SPARE_ORDERING, sparenames);
                                params.put(MODEL_SENT, models);
                                params.put(PHONE_PREF, yourPhone);
                                params.put(USER_NAME, username);
                                params.put(CLIENT_ID, clientId);
                                params.put(TYPE, mtype);
                                params.put("namea", pic);
                                params.put("imgname", formated);

                                Log.d("paramx", params.toString());
                                return params;

                            }
                            //Map<String,String>paramsb=new Hashtable<String, String>();

                        };


                        int x = 2;// retry count
                        stringRequest.setRetryPolicy(new

                                DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48,
                                x, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                        //stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                        Volley.newRequestQueue(getApplicationContext()).

                                add(stringRequest);
                    }
                }


            }
        });


    }

    private void showOrderSuceessDialog() {
        final AlertDialog dialog = new AlertDialog.Builder(Ordersrequest.this).create();
        dialog.setTitle("Success");
        dialog.setMessage("Your order was successful an agent will confirm it soon");
        dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();



                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();


            }
        });
        dialog.setIcon(R.drawable.tickd);
        dialog.show();

    }
    private void Notregistered() {
        final AlertDialog dialog = new AlertDialog.Builder(Ordersrequest.this).create();
        dialog.setTitle("Register ");
        dialog.setMessage("Register to make this order");
        dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();



                Intent intent = new Intent(getApplicationContext(), Motorregister.class);
                startActivity(intent);
                finish();


            }
        });
        dialog.setIcon(R.drawable.tickd);
        dialog.show();

    }

    private boolean Valid() {




        if(model.getText().toString().length()==0)

        {
            model.setError("Model field is required");
            model.requestFocus();
            return false;
        }
        if(sparename.getText().toString().length()==0)

        {
           sparename.setError("SpareName field is required");
            sparename.requestFocus();
            return false;
        }

        if(phone.getText().toString().length()==0)

        {
            phone.setError("Phone Number is required");
            phone.requestFocus();
            return false;

        }

        if((phone.getText().toString().length()>0&&phone.getText().toString().length()<10)||phone.getText().toString().length()>13)

        {
            phone.setError("Incorrect phone number");
            phone.requestFocus();
            return false;

        }

        try

        {


            int num = Integer.parseInt(phone.getText().toString().replaceAll("\\s+", ""));
            if (phone.getText().toString().contains("-")) {
                phone.setError("Incorrect phone number format");
                phone.requestFocus();
                return false;
            }
        }

        catch(NumberFormatException e)

        {
            phone.setError("Incorrect phone number format");
            phone.requestFocus();
            return false;
        }

        return true;
    }

}
