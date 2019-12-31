package stylist.com.glamhub;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


import stylist.com.glamhub.Feedback.Fireadmin;
import stylist.com.glamhub.Feedback.Firechats;
import stylist.com.glamhub.Feedback.Ordersrequest;
import stylist.com.glamhub.Verify.VerificationAccount;
import stylist.com.glamhub.fireb.Fireb;
import stylist.com.glamhub.maps.MapsActivitya;
import stylist.com.glamhub.menuitems.Faqs;
import stylist.com.glamhub.menuitems.Terms;
import stylist.com.glamhub.menuitems.privacyActivity;
import stylist.com.glamhub.myorders.OrdersmadeFragment;
import stylist.com.glamhub.myorders.successful.SuccessfulOrderActivity;

import static stylist.com.glamhub.R.id.tabs;
import static stylist.com.glamhub.R.id.txtcount;
import static stylist.com.glamhub.R.id.viewpager;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,OrdersmadeFragment.OnFragmentInteractionListener{
    LinearLayout layera,layerb,layerc;
    Dialog dialog;
    Context context=this;
    TextView count;
    LinearLayout layerone;
SharedPreferences pref,prefb;
    FloatingActionButton order,towbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        layerone = (LinearLayout) findViewById(R.id.layerone);
        //count= (TextView) findViewById(R.id.txtcount);
        pref = getApplicationContext().getSharedPreferences("arrayitem", 0);
        prefb = getApplicationContext().getSharedPreferences("regd", 0);
        count = (TextView) findViewById(txtcount);

        String nn = getResources().getResourceName(R.id.txtcount).replace("", "78");
        String kk = "50";
        getApplicationContext().getResources().getString(R.string.counts).toString().replaceAll(getResources().getString(R.string.counts).toString(), "89");

        //   Toast.makeText(context,  getApplicationContext().getResources().getString(R.string.counts).toString(), Toast.LENGTH_SHORT).show();
        
        setSupportActionBar(toolbar);

       // if (CheckifRegistered()) {

        if (savedInstanceState == null) {
            //Fragment fragment = null;
            Fragment fragment = null;
            Class fragmentClass = null;
            fragmentClass = TabFragment.class;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(context);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.floater_layout);
                dialog.show();
                towbtn = (FloatingActionButton) dialog.findViewById(R.id.fabc);
                towbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplication(), MsearchActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                order = (FloatingActionButton) dialog.findViewById(R.id.fabb);
                order.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplication(), Ordersrequest.class);
                        startActivity(intent);
                        finish();
                    }
                });

            }
        });
       // }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    private boolean CheckifRegistered() {

        Boolean isregistered =prefb.getBoolean("isregistered",false);
        boolean validated=prefb.getBoolean("validated",false);
        if (!isregistered ) {
            Intent ints=new Intent(getApplicationContext(),Motorregister.class);
            ints.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(ints);
            finish();
            return  false;
        }
        else if  (!validated && isregistered)
        {
            Intent ints=new Intent(getApplicationContext(),VerificationAccount.class);
            ints.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(ints);
            finish();
            return  false;
        }
        else {
            startService(new Intent(this, NotificationListener.class));
            return true;
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
NavigationView navigationView;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item1=  menu.findItem(R.id.action_cart);

        MenuItemCompat.setActionView(item1,R.layout.tabitems);

        int items= pref.getInt("count",0);
        layerone= (LinearLayout) ( MenuItemCompat.getActionView(item1));
        count= (TextView) layerone.findViewById(R.id.txtcount);
        count.setText(""+items);

        // count= (TextView) MenuItemCompat.getActionView(item1);

        layerone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),purchasedItems.class);
                startActivity(intent);
            }
        });

        hideReg();
        return true;
    }

    private  void hideReg()
    {
        boolean validated=prefb.getBoolean("validated",false);
        String phone=prefb.getString("phone",null);
        Log.e("validate ",validated+"");
        DbHelper dbHelper = new DbHelper(getApplicationContext());
        List<User> user = dbHelper.getAllUsers();

       /* if (user.size() < 0 && user.get(0).getPhoneNumber().equals(null)) {
            navigationView = (NavigationView) findViewById(R.id.nav_view);
            Menu nav_Menu = navigationView.getMenu();

            nav_Menu.findItem(R.id.nav_login).setVisible(true);
            nav_Menu.findItem(R.id.nav_mainregister).setVisible(true);
            nav_Menu.findItem(R.id.nav_verify).setVisible(false);
            nav_Menu.findItem(R.id.nav_camera).setVisible(true);

            return;
        }
        if (user.size() > 0 && user.get(0).getLogin_status().equals("true") && ! validated) {
            navigationView = (NavigationView) findViewById(R.id.nav_view);
            Menu nav_Menu = navigationView.getMenu();

            nav_Menu.findItem(R.id.nav_login).setVisible(true);
           // nav_Menu.findItem(R.id.navPoints)
            nav_Menu.findItem(R.id.nav_mainregister).setVisible(false);
            nav_Menu.findItem(R.id.nav_camera).setVisible(true);
            nav_Menu.findItem(R.id.nav_verify).setVisible(true);
            return;
        }*/
        if (phone==null) {
            navigationView = (NavigationView) findViewById(R.id.nav_view);
            Menu nav_Menu = navigationView.getMenu();

            nav_Menu.findItem(R.id.nav_login).setVisible(true);
            nav_Menu.findItem(R.id.nav_mainregister).setVisible(true);
            nav_Menu.findItem(R.id.nav_verify).setVisible(false);
            nav_Menu.findItem(R.id.nav_camera).setVisible(true);

            return;
        }
        if (phone!=null && ! validated) {
            navigationView = (NavigationView) findViewById(R.id.nav_view);
            Menu nav_Menu = navigationView.getMenu();

            nav_Menu.findItem(R.id.nav_login).setVisible(true);
            // nav_Menu.findItem(R.id.navPoints)
            nav_Menu.findItem(R.id.nav_mainregister).setVisible(false);
            nav_Menu.findItem(R.id.nav_camera).setVisible(true);
            nav_Menu.findItem(R.id.nav_verify).setVisible(true);
            return;
        }
        if (user.size() > 0 &&  validated) {
            navigationView = (NavigationView) findViewById(R.id.nav_view);
            Menu nav_Menu = navigationView.getMenu();

            nav_Menu.findItem(R.id.nav_login).setVisible(true);
            // nav_Menu.findItem(R.id.navPoints)
            nav_Menu.findItem(R.id.nav_mainregister).setVisible(false);
            nav_Menu.findItem(R.id.nav_camera).setVisible(true);
            nav_Menu.findItem(R.id.nav_verify).setVisible(false);
            return;
        }
        if (validated)
        {
            navigationView = (NavigationView) findViewById(R.id.nav_view);
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.nav_login).setVisible(true);
            // nav_Menu.findItem(R.id.navPoints)
            nav_Menu.findItem(R.id.nav_mainregister).setVisible(false);
            nav_Menu.findItem(R.id.nav_camera).setVisible(true);
            nav_Menu.findItem(R.id.nav_verify).setVisible(false);
            return;
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.action_cart) {

            Intent intent=new Intent(getApplicationContext(),purchasedItems.class);
            startActivity(intent);
        }
        if (id == R.id.action_howto) {

            Intent intent=new Intent(getApplicationContext(),HowtoActivity.class);
            startActivity(intent);
        }

        if (id == R.id.action_faq) {

            Intent intent=new Intent(getApplicationContext(),Faqs.class);
            startActivity(intent);
        }

        if (id == R.id.action_terms) {

            Intent intent=new Intent(getApplicationContext(),Terms.class);
            startActivity(intent);
        }
        if (id == R.id.action_privacy) {

            Intent intent=new Intent(getApplicationContext(),privacyActivity.class);
            startActivity(intent);
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = null;
        Class fragmentClass=null;
        if (id == R.id.nav_camera) {
            // Handle the camera action
            Intent intent=new Intent (getApplication(), MainActivity.class );

            startActivity(intent);
            finish();
       }
        else if (id == R.id.nav_mainregister) {
            // ViewPager viewPager;
            Intent intent=new Intent(getApplication(),Motorregister.class);
            startActivity(intent);

        }else if (id == R.id.nav_share) {
            try {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, "Jos Motors");
                String sAux = "This app Kenyan traffic is a sure way to avoid traffic";
                sAux = sAux + "\nDownload the app from  playstore using the link provided https://play.google.com/store/apps/details?id=stylist.com.glamhub";
                i.putExtra(Intent.EXTRA_TEXT, sAux);
                startActivity(Intent.createChooser(i, "Quality Product,Order Now"));

            } catch (Exception e) {

            }


        } else if (id == R.id.nav_login) {
            Intent intent=new Intent (getApplication(),MsearchActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_verify) {
            Intent intent=new Intent (getApplication(),VerificationAccount.class);
            startActivity(intent);
        } else if (id == R.id.nav_send) {
           try {
               AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

               LayoutInflater inflater = getLayoutInflater();
               View dialogView = inflater.inflate(R.layout.aboutjos_dialog,null);

               builder.setView(dialogView);




               final AlertDialog dialog = builder.create();
               dialog.show();
           }catch (Exception e)
           {

           }
        } else if (id == R.id.nav_feed) {
            Intent intent=new Intent (getApplication(),Ordersrequest.class);
            startActivity(intent);
        } else if (id == R.id.nav_success) {
            Intent intent=new Intent (getApplication(),SuccessfulOrderActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_geo) {
            Intent intent=new Intent (getApplication(),Firechats.class);
            startActivity(intent);

      /*  } else if (id == R.id.nav_fireadmn) {
            Intent intent=new Intent (getApplication(),Fireadmin.class);
            startActivity(intent);*/
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        //fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;


    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
