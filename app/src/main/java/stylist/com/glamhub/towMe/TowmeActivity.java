package stylist.com.glamhub.towMe;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import stylist.com.glamhub.R;

public class TowmeActivity extends AppCompatActivity {
ImageView imgtowme;
    TextView towowner,place,maxweight;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_towme);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       imgtowme= (ImageView) findViewById(R.id.imgMsearchImage);
        towowner= (TextView) findViewById(R.id.txtowner);
        place= (TextView) findViewById(R.id.txttowlocation);
        maxweight= (TextView) findViewById(R.id.txttowweight);
        Bundle extras=getIntent().getExtras();
        Bitmap btm=(Bitmap)extras.getParcelable("image");
        imgtowme.setImageBitmap(btm);

        towowner.setText(extras.getString("owner"));
        place.setText(extras.getString("place"));
        maxweight.setText(extras.getString("maxweight"));

    }

}
