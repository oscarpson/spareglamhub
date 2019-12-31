package stylist.com.glamhub.myorders;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import stylist.com.glamhub.Motorregister;
import stylist.com.glamhub.R;
import stylist.com.glamhub.User;

/**
 * Created by OSCAR on 7/13/2017.
 */

public class BestpriceAdapter extends RecyclerView.Adapter<BestpriceAdapter.BestpriceViewHolder> {
Context context;
    Context cont;
    List<BestPrice>bestprices;
    SharedPreferences regd;
    String clientId;
    String sorderId;
    String sgarage;
    String sprice;

    public BestpriceAdapter(Context context, List<BestPrice> bestprices) {
        this.context = context;
        this.bestprices = bestprices;
    }

    @Override
    public BestpriceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.bestprice_activity,parent,false);
        BestpriceViewHolder holder=new BestpriceViewHolder(view);

        regd=context.getSharedPreferences("regd",0);
        clientId=regd.getString("clientId",null);
        return holder;
    }

    @Override
    public void onBindViewHolder(BestpriceViewHolder holder, int position) {

        BestPrice bestprice=bestprices.get(position);
        BestpriceViewHolder localholder=holder;

        localholder.time.setText(bestprice.getTime());
        localholder.username.setText(bestprice.getUsername());
        localholder.price.setText(bestprice.getPrice());
        localholder.manufacturer.setText(bestprice.getManufacturer());
        localholder.orderId.setText(bestprice.getOrderId());



    }

    @Override
    public int getItemCount() {
        return (bestprices != null) ? bestprices.size() : 0;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public class BestpriceViewHolder extends RecyclerView.ViewHolder {
       Button btnorder;
        TextView username,price,manufacturer,orderId,spareId,mechId,time;
        public BestpriceViewHolder(View itemView) {
            super(itemView);

            username= (TextView) itemView.findViewById(R.id.txtusername);
            price= (TextView) itemView.findViewById(R.id.txtprice);
            manufacturer= (TextView) itemView.findViewById(R.id.txtmanufacturer);
            orderId= (TextView) itemView.findViewById(R.id.txtorderId);
            spareId= (TextView) itemView.findViewById(R.id.txtspareId);
            mechId= (TextView) itemView.findViewById(R.id.txtmechId);
            time= (TextView) itemView.findViewById(R.id.txttime);
            btnorder= (Button) itemView.findViewById(R.id.btnorder);

            btnorder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    sorderId=orderId.getText().toString();
                    sprice=price.getText().toString();
                    sgarage=username.getText().toString();
                    //sendOrder();
                    Intent intent= new Intent(v.getContext(),ShowMessage.class);
                    intent.putExtra("price",sprice);
                    intent.putExtra("garage",sgarage);
                    intent.putExtra("orderId", sorderId);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }



    public static class Bestprice
    {
        String username,price,manufacturer,orderId,spareId,mechId,time;

        public Bestprice(String username, String price, String manufacturer, String orderId, String spareId, String mechId,String time) {
            this.username = username;
            this.price = price;
            this.manufacturer = manufacturer;
            this.orderId = orderId;
            this.spareId = spareId;
            this.mechId = mechId;
            this.time=time;
        }

        public String getUsername() {
            return username;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getManufacturer() {
            return manufacturer;
        }

        public void setManufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getSpareId() {
            return spareId;
        }

        public void setSpareId(String spareId) {
            this.spareId = spareId;
        }

        public String getMechId() {
            return mechId;
        }

        public void setMechId(String mechId) {
            this.mechId = mechId;
        }

        public Bestprice() {
        }
    }
}
