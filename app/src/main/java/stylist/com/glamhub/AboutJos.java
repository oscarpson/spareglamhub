package stylist.com.glamhub;



import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

public class AboutJos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_jos);

        final AlertDialog.Builder builder = new AlertDialog.Builder(AboutJos.this);

        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.aboutjos_dialog,null);

        builder.setView(dialogView);



        final AlertDialog dialog = builder.create();
        dialog.show();
    }
}
