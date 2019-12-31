package stylist.com.glamhub;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import stylist.com.glamhub.Feedback.Firechats;
import stylist.com.glamhub.Feedback.Reviews;
import stylist.com.glamhub.about.Moreinfomation;

/**
 * Created by Belal on 3/18/2016.
 */
//Class extending service as it is a service that will run in background
public class NotificationListener extends Service {

        String id, fireId;
        DatabaseReference dbref;
        SharedPreferences sharedPreferences, pref;
    int code;

        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        //When the service is started
        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            //Opening sharedpreferences

            SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREF, MODE_PRIVATE);
            pref = getSharedPreferences("regd", 0);
            id = pref.getString("fireId", null);
            Firebase.setAndroidContext(this);
            //Getting the firebase id from sharedpreferences
            //originalcode     id = sharedPreferences.getString(Constants.UNIQUE_ID, null);
            dbref = FirebaseDatabase.getInstance().getReference();
            //Creating a firebase object

            Firebase firebase = new Firebase(Constants.FIREBASE_APP + "notifications/" + id);
            Log.e("dataxsi", "\n" + id);
            code= (int) ((Math.random()*9)+1);
            //Adding a valueevent listener to firebase
            //this will help us to  track the value changes on firebase
            //working




         /*   dbref.child("notifications").child(id).child("message").addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                @Override
                public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                    try {
                        Log.e("dataxis", dataSnapshot.getValue().toString());
                        ;
                        Reviews rvw=dataSnapshot.getValue(Reviews.class);
                        Log.e("dataxx",rvw.getMessage());

                        String mssage = rvw.getMessage();
                       // String status = dataSnapshot.child("status").getValue().toString();
                        String status=rvw.getStatus();
                        String uid = rvw.getUserKey();

                        Log.e("dataxis", dataSnapshot.getValue().toString() + "\n" + mssage + "\t" + uid + "\n");
                        if (status.equals("0")) {
                            showNotification(mssage, uid);
                        }
                    }catch (Exception e)
                    {
                        Log.e("Exception occured",e.getMessage().toString());
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

*/
            try {

                dbref.child("notifications").child(id).addChildEventListener(new com.google.firebase.database.ChildEventListener() {
                    @Override
                    public void onChildAdded(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {
                        try {
                            Log.e("dataxix", dataSnapshot.getValue().toString() + "\n" + "\t" + dataSnapshot.getKey());
                            Log.e("dataxis", dataSnapshot.getValue().toString());
                            ;
                            Reviews rvw = dataSnapshot.getValue(Reviews.class);
                            Log.e("dataxx", rvw.getMessage());

                            String mssage = rvw.getMessage();
                            // String status = dataSnapshot.child("status").getValue().toString();
                            String status = rvw.getStatus();
                            String uid = rvw.getUserKey();
                            Log.e("dataxis", dataSnapshot.getValue().toString() + "\n" + mssage + "\t" + uid + "\n");
                            if (!("1".equals(status))) {
                                showNotification(mssage, uid, status);
                            }
                        } catch (Exception e) {

                        }
                    }

                    @Override
                    public void onChildChanged(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {
                        try {
                            Log.e("dataxix", dataSnapshot.getValue().toString() + "\n" + "\t" + dataSnapshot.getKey());
                            Log.e("dataxis", dataSnapshot.getValue().toString());
                            ;
                            Reviews rvw = dataSnapshot.getValue(Reviews.class);
                            Log.e("dataxx", rvw.getMessage());

                            String mssage = rvw.getMessage();
                            // String status = dataSnapshot.child("status").getValue().toString();
                            String status = rvw.getStatus();
                            String uid = rvw.getUserKey();

                            Log.e("dataxis", dataSnapshot.getValue().toString() + "\n" + mssage + "\t" + uid + "\n");
                            if (!("1".equals(status))) {
                                showNotification(mssage, uid, status);
                            }
                        } catch (Exception e) {

                        }

                    }

                    @Override
                    public void onChildRemoved(com.google.firebase.database.DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }


                });
            }catch (Exception e)
            {

            }

  /*      dbref.child("notifications").child("-KwiaVpNYqan9d6OHEir").child("-Kw_vyVb8JnNWqaZAwmo").child("username").setValue("Kimani mwega").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(NotificationListener.this, "Chats added succesfully", Toast.LENGTH_SHORT).show();
            }
        });
firebase.addChildEventListener(new ChildEventListener() {
    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
       Log.e("firedatax", dataSnapshot.getValue().toString());
        ChatsReal chats=dataSnapshot.getValue(ChatsReal.class);
        //chats.getChattext()



        showNotification( chats.getChattext());
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        Log.e("firedataxb", dataSnapshot.getValue().toString());
        ChatsReal chats=dataSnapshot.getValue(ChatsReal.class);
        //chats.getChattext()
       // showNotification( chats.getChattext());
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(FirebaseError firebaseError) {

    }
});*/
       /* firebase.addValueEventListener(new ValueEventListener() {

            //This method is called whenever we change the value in firebase
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //Getting the value from firebase
                //We stored none as a initial value
                //String msg = snapshot.child("time").toString();
                String msg="New client message from";
                //So if the value is none we will not create any notification
                if (msg.equals("none"))
                    return;

                //If the value is anything other than none that means a notification has arrived
                //calling the method to show notification
                //String msg is containing the msg that has to be shown with the notification
                showNotification(msg);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.e("The read failed: ", firebaseError.getMessage());
            }
        });*/

                return START_STICKY;
            }


        private void showNotification(String msg, String uid,String status) {
            //Creating a notification

            String title = "";
            Intent intent;
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
            builder.setSmallIcon(R.drawable.ic_stat_name);
            Uri alarmsound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            builder.setSound(alarmsound);
            if (status.equals("0"))
            {
                 title="New Chat";
                 intent = new Intent(this, Firechats.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
                builder.setContentIntent(pendingIntent);
                builder.addAction(R.drawable.ic_stat_name,"www.joslabs.co.ke", PendingIntent.getActivity(this, 0, intent, 0));
            }
            if (status.equals("2"))
            {
                title="Did you know";
               Intent intents = new Intent(this,Moreinfomation.class);
                Bundle b=new Bundle();
                b.putString("info",msg);
                intents.putExtras(b);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, code, intents, 0);

                builder.setContentIntent(pendingIntent);
                builder.addAction(R.drawable.ic_stat_name,"www.joslabs.co.ke", PendingIntent.getActivity(this, 0, intents, 0));
            }
            if (status.equals("3"))
            {
                title="New Chat";
                intent = new Intent(this, MainActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
                builder.setContentIntent(pendingIntent);
                builder.addAction(R.drawable.ic_stat_name,"www.joslabs.co.ke", PendingIntent.getActivity(this, 0, intent, 0));
            }
            builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.traffic2));
            builder.setContentTitle(title);
            builder.setContentText(msg);
            builder.setAutoCancel(true);


            builder.setDefaults(~android.app.Notification.DEFAULT_SOUND);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(1, builder.build());
            updateStatus(uid);

        }

        private void updateStatus(String uid) {
            dbref.child("notifications").child(id).child("message").child("status").setValue("1").addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(NotificationListener.this, "Chats added succesfully", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
