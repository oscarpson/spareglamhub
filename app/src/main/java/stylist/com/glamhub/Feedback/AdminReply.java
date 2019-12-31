package stylist.com.glamhub.Feedback;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import stylist.com.glamhub.Constants;
import stylist.com.glamhub.R;

public class AdminReply extends AppCompatActivity {
    RecyclerView rcv;
    List<Reviews> reviewses;
    FirebaseRecyclerAdapter<Reviews,ReviewViewHolder> firebaseRecyclerAdapter;
    final  static  String Funga_URL="http://joslabs.co.ke/josmotos/josregistera.php";
    DatabaseReference dbref;
    Button sendchat,btncameraSend;
    EditText edtchat;
    String sedtchat,userId,usernames,photourl;
    SharedPreferences pref,sharedPreferences;
    static boolean calledAlready=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_reply);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(!calledAlready)
        {
            try {
                FirebaseDatabase.getInstance().setPersistenceEnabled(true);
                calledAlready = true;
            }
            catch (Exception e)
            {
                Toast.makeText(this, "Exception occured", Toast.LENGTH_SHORT).show();
                Log.e("exfire",e.getMessage().toString());
            }
        }

        Firebase.setAndroidContext(this);
        sendchat= (Button) findViewById(R.id.btnfeedSend);
        edtchat= (EditText) findViewById(R.id.edtFeed);
        pref=getApplicationContext().getSharedPreferences("regd",0);
        usernames=pref.getString("username",null);
        Bundle extras=getIntent().getExtras();
        final String userKey=extras.getString("userKey");
        final String userId=extras.getString("userId");
        //Toast.makeText(this, usernames+"", Toast.LENGTH_SHORT).show();
       // sharedPreferences = getSharedPreferences(Constants.SHARED_PREF, MODE_PRIVATE);
       // final String userKey = sharedPreferences.getString(Constants.UNIQUE_ID, null);
        rcv= (RecyclerView) findViewById(R.id.rcvreviews);
        rcv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rcv.setAdapter(null);
        dbref=FirebaseDatabase.getInstance().getReference();

        reviewses=new ArrayList<>();
        sendchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtchat.getText().length() == 0 || edtchat.getText().toString().equals("")) {
                    edtchat.setError("please enter message to send");
                    edtchat.requestFocus();
                } else {
                    sedtchat = edtchat.getText().toString();
                    edtchat.setText("");
                    //  imgquiz.setVisibility(View.GONE);

                    getWindow().setSoftInputMode(
                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                    );


                    Map<String, String> val = new HashMap<>();

                    //pushing msg = none in the map
                    val.put("reply", "none");
                   String userId = dbref.push().getKey();
                    Date today = Calendar.getInstance().getTime();
                    DateFormat df = new SimpleDateFormat("dd.MM.yyyy ", Locale.UK);
                    final String reportDate = df.format(today);
                    Reviews rvws = new Reviews(reportDate," "+sedtchat,userKey,userId,usernames);
                    // dbref.child("chats").child(userKey).child(userId).child("").setValue("");

                    dbref.child("chats").child(userKey).child(userId).setValue(rvws).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                           Reviews rvws = new Reviews(" "+sedtchat,userKey,"0");
                       dbref.child("notifications").child(userKey).child("message").setValue(rvws);
                            Toast.makeText(AdminReply.this, "Reviews added succesfully", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Reviews, ReviewViewHolder>(Reviews.class,R.layout.reviews_layout,ReviewViewHolder.class,dbref.child("chats").child(userKey)) {
            @Override
            protected void populateViewHolder(ReviewViewHolder viewHolder, Reviews model, int position) {

                viewHolder.reviewmessage.setText(model.getMessage());
                viewHolder.time.setText(model.time);
            }
        };
        rcv.setAdapter(firebaseRecyclerAdapter);


    }

}