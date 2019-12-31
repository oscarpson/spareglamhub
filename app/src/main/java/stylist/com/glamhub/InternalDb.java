package stylist.com.glamhub;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class InternalDb extends AppCompatActivity {
EditText name,idnumber,pass;
    Button btnadd,btniget;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internal_db);
        final String phone_no ;
        final String password ;
        final String idnumber1;
        final DbHelper dbHelper=new DbHelper(getApplicationContext());
        btnadd=(Button)findViewById(R.id.btniadd);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=(EditText)findViewById(R.id.txtiname);
                idnumber=(EditText)findViewById(R.id.txtidnumber);
                pass=(EditText)findViewById(R.id.txtipass);

           String  password  =pass.getText().toString();
                String  phone_no=name.getText().toString();
                String  idnumber1 =idnumber.getText().toString();

                dbHelper.addUser(new User( phone_no,password,"false",null,null,null, idnumber1,null));

                User user=dbHelper.getUser(phone_no,password);
                System.out.print("Username:" + user.getPhoneNumber() + " Password" + user.password+ " Token: "+user.getToken());
                Log.d("user2",user.getPhoneNumber());
                Log.d("pass2",user.getPassword());


            }
        });
        btniget=(Button)findViewById(R.id.btniget);
        btniget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            dbHelper.getAllUsers();

               // String phone_no="" ;
                //final String password="" ;
                User user=dbHelper.getAllUsers().get(0);
                System.out.print("Username:" + user.getPhoneNumber() + " Password" + user.password+ " Token: "+user.getToken());
                Log.d("user2",user.getPhoneNumber());
                Log.d("pass2",user.getPassword());

            }
        });

    }
}
