package stylist.com.glamhub;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;

public class OrderSpare extends AppCompatActivity {
    public String[] ShoppingItem=new String[20];
    public String[] ShoppingPrice=new String[20];
    public String[] ShoppingModel=new String[20];
    public String[] ShoppingPartname=new String[20];
    String partId,Model,partName,price;
    TextView ItemsCount,txtmodel,txtprice,txtpartname;
Button checkout,btnadmin,btnaddmore;
    int count;
    int counter;
    SharedPreferences pref;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_spare);

        img= (ImageView) findViewById(R.id.partimage);
        ItemsCount= (TextView) findViewById(R.id.txtitems);

        txtmodel= (TextView) findViewById(R.id.txtmodel);
        txtprice= (TextView) findViewById(R.id.txtprice);
        txtpartname= (TextView) findViewById(R.id.textView2);

        pref=getApplicationContext().getSharedPreferences("arrayitem",0);
        count= pref.getInt("count",0);

        if(count== 0){
            count = 0;
            ItemsCount.setText(""+count);
        }
        ItemsCount.setText(""+count);

        Bundle extras=getIntent().getExtras();
        Bitmap btm=(Bitmap)extras.getParcelable("image");
        img.setImageBitmap(btm);

        partId=extras.getString("partId");
        Model=extras.getString("model");
        price=extras.getString("price");
        partName=extras.getString("partname");
        txtmodel.setText(Model);
        txtprice.setText(price);
        txtpartname.setText(partName);
       // Toast.makeText(this, "inorderTable \tId"+partId+"\tprice"+price, Toast.LENGTH_SHORT).show();

        ShoppingItem[count]= partId;
        ShoppingPrice[count]= price;
        ShoppingModel[count]=Model;
        ShoppingPartname[count]=partName;
        //count++;
for (int i=0;i<count+1;i++)
{
    Log.d("shopx",ShoppingItem[i] +"\n"+"\n"+ShoppingPrice[i]+"\n"+ShoppingModel[i]+"\n"+  ShoppingPartname[i]);
}
        btnaddmore= (Button) findViewById(R.id.btnmore);
        btnaddmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        btnadmin= (Button) findViewById(R.id.btnadmin);
        btnadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),purchasedItems.class);
                startActivity(intent);


            }
        });
        checkout= (Button) findViewById(R.id.btntoCart);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor=pref.edit();

                editor.putInt("size",ShoppingItem.length);

                editor.putString("shoppingitem"+count,ShoppingItem[count]);
                editor.putString("shoppingPrice"+count,ShoppingPrice[count]);
                editor.putString("shoppingModel"+count,ShoppingModel[count]);
                editor.putString("shoppingPartname"+count,ShoppingPartname[count]);
                //editor.putString("count","");
                count= count+1;
                ItemsCount.setText(""+count);
                editor.putInt("count",count);

                editor.commit();


                counter=pref.getInt("count",0);
                String array[]=new String[counter];
                String barray[]=new String[counter];
                String arrar[]=new String[counter];
                String barraty[]=new String[counter];
                for (int i=0;i<counter;i++)
                {
                    array[i]=pref.getString("shoppingitem"+i,null);
                    barray[i]=pref.getString("shoppingPrice"+i,null);
                    arrar[i]=pref.getString("shoppingModel"+i,null);
                    barraty[i]=pref.getString("shoppingPartname"+i,null);
                    // Log.d("arrat", array[i]+barray[i]+arrar[i]+barraty[i]);
                    Log.d("shopxi","item"+array[i]+"\n"+i+"\nprice"+barray[i]+"\t"+"\nModel "+arrar[i]+"\n part"+barraty[i]+"\nposition"+i);
                }

               // checkout.setEnabled(false);
                checkout.setClickable(false);
                checkout.setBackgroundResource(R.color.bright_foreground_disabled_holo_dark);
            }
        });


        FloatingActionButton fabitems = (FloatingActionButton) findViewById(R.id.numberofitems);
        fabitems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




               Intent intent=new Intent(getApplicationContext(),purchasedItems.class);
                 startActivity(intent);

            }
        });




    }

}
