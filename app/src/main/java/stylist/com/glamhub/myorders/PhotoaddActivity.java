package stylist.com.glamhub.myorders;

import android.Manifest;
import android.app.ProgressDialog;
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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import stylist.com.glamhub.MainActivity;
import stylist.com.glamhub.R;

public class PhotoaddActivity extends AppCompatActivity {
    Button btnphoto,btnsave;
    CircleImageView camera,photos;
    ImageView imgProfilePic;
    final int TAKE_PHOTO_REQ = 100;
    FileUriExposedException fileUri;
    public int SELECT_PICTURE = 1;
    Bitmap bitmap;
    Bundle extras;
    String orderId;
    SharedPreferences pref;
    String phoned,models,username,clientId,  formated,pic;
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photoadd);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        imgProfilePic= (ImageView) findViewById(R.id.addPhoto);
        camera= (CircleImageView) findViewById(R.id.camerag);
        photos= (CircleImageView) findViewById(R.id.photos);
        extras=getIntent().getExtras();
        orderId=extras.getString("orderId");
        username=pref.getString("username",null);
        clientId=pref.getString("clientId",null);

        Calendar c=Calendar.getInstance();
        SimpleDateFormat df=new SimpleDateFormat("yyyyMMddHHmmss");
        formated=df.format(c.getTime());
        int secs=c.get(Calendar.DATE)+c.get(Calendar.MONTH)+c.get(Calendar.YEAR);
        String curdate= DateFormat.getDateTimeInstance().format(new Date());
        //Toast.makeText(this, ""+curdate, Toast.LENGTH_LONG).show();
        Log.e("timex",secs+"\n"+c.getTime()+"\n"+formated);


        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // requestCameraPermission();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(PhotoaddActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
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
        photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture from Gallery"), 1);
            }
        });

        btnsave= (Button) findViewById(R.id.btnsendphoto);
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePic();
                //change picture from gallery
            }
        });
    }
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

                imgProfilePic.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //CAmera
        if (requestCode ==TAKE_PHOTO_REQ) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap photo = extras.getParcelable("data");
                imgProfilePic.setImageBitmap(photo);

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
    private void changePic()

    {
        final ProgressDialog progressDialog=new ProgressDialog(PhotoaddActivity.this);
        progressDialog.setTitle("Updating pic ...");
        progressDialog.setMessage("We are setting your new profile pic");
        progressDialog.setIndeterminate(true);
        progressDialog.setIndeterminateDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.animation_icon));
        progressDialog.setCancelable(false);
        progressDialog.show();
        Toast.makeText(this, "uploading", Toast.LENGTH_SHORT).show();

        imgProfilePic.buildDrawingCache();
        //DbHelper dbHelper=new DbHelper(getApplicationContext());
        //final String token=dbHelper.getAllUsers().get(0).getToken();
        final Bitmap bmap = imgProfilePic.getDrawingCache();
        final ProgressDialog loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://joslabs.co.ke/josmotos/uploadPhoto.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if(progressDialog.isShowing())
                            progressDialog.dismiss();


                        loading.dismiss();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError)

                    {
                        Log.e("errornet",volleyError.getMessage());
                        if(progressDialog.isShowing())
                            progressDialog.dismiss();

                        //Dismissing the progress dialog
                        loading.dismiss();
                        if(volleyError instanceof NoConnectionError)
                        {
                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }

                        if(volleyError instanceof NetworkError)
                        {
                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
                String image = getStringImage(bmap);

                //Getting Image Name


                //Creating parameters
                Map<String,String> params = new Hashtable<String, String>();

                //Adding parameters
                params.put("username",username);
                params.put("clientId",clientId);

                params.put("namea", image);
                params.put("imgname",formated);

                Log.d("paramx",params.toString());
                Log.d("paramxcent",params.toString()+"\n"+image);

                //returning parameters
                return params;
            }
        };

        int x=2;//Creating a Request Queue

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48,
                x, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
    }
}

