package stylist.com.glamhub.myorders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import stylist.com.glamhub.R;
import stylist.com.glamhub.Updates;

/**
 * Created by OSCAR on 7/4/2017.
 */

public class MyordersAdapter extends RecyclerView.Adapter<MyordersAdapter.MyOrdersViewHolder> {
List<Updates>myorders= Collections.emptyList();
    Context context;

    public MyordersAdapter(List<Updates> myorders, Context context) {
        this.myorders = myorders;
        this.context = context;
    }

    @Override
    public MyOrdersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.myordersview_activity,parent,false);
        MyOrdersViewHolder holder=new MyOrdersViewHolder(view);
        return  holder;
    }

    @Override
    public void onBindViewHolder(MyOrdersViewHolder holder, int position) {

        Updates orders=myorders.get(position);
        MyOrdersViewHolder localholder=holder;

        localholder.sparename.setText(orders.getPartname());
        localholder.model.setText(orders.getVehiclemodel());
        localholder.time.setText(orders.getOrdertime());
        localholder.viewers.setText(orders.getViewers());
        localholder.txtorderId.setText(orders.getOrderId());

       if( myorders.get(position).getPhotostatus().equals("1"))
       {
           localholder.btncamera.setVisibility(View.VISIBLE);
           localholder.imguser.setVisibility(View.VISIBLE);
       }

    }

    @Override
    public int getItemCount() {
        return (myorders != null) ? myorders.size() : 0;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public class MyOrdersViewHolder extends RecyclerView.ViewHolder {
        TextView sparename,model,time,viewers,txtorderId;
        Button btncamera;
        CardView cardViewm;
        ImageView imguser;
        public MyOrdersViewHolder(final View itemView) {
            super(itemView);

            sparename= (TextView) itemView.findViewById(R.id.txtsparename);
            model= (TextView) itemView.findViewById(R.id.txtmodelname);
            time= (TextView) itemView.findViewById(R.id.txttime);
            viewers= (TextView) itemView.findViewById(R.id.txtViewers);
            btncamera= (Button) itemView.findViewById(R.id.btnaddphoto);
            imguser= (ImageView) itemView.findViewById(R.id.imguser);
            cardViewm= (CardView) itemView.findViewById(R.id.cardview);
            txtorderId= (TextView) itemView.findViewById(R.id.txtorderid);
            cardViewm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(itemView.getContext(),BestpriceActivity.class);
                    intent.putExtra("orderId",txtorderId.getText().toString());
                    v.getContext().startActivity(intent);
                }
            });


            btncamera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent=new Intent(itemView.getContext(),PhotoaddActivity.class);
                    Bundle extras=new Bundle();
                    intent.putExtra("orderId",txtorderId.getText());
                    itemView.getContext().startActivity(intent);
                }
            });

        }
    }
}
