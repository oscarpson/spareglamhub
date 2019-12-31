package stylist.com.glamhub.myorders.successful;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;


import stylist.com.glamhub.R;
import stylist.com.glamhub.myorders.BestPrice;
import stylist.com.glamhub.myorders.BestpriceAdapter;


/**
 * Created by OSCAR on 7/3/2017.
 */

public class CompleteAdapter extends Adapter<CompleteAdapter.CompleteViewHolder> {
    List<BestPrice>completeOrders= Collections.emptyList();
    Context context;

    public CompleteAdapter(List<BestPrice> completeOrders, Context context) {
        this.completeOrders = completeOrders;
        this.context = context;
    }

    @Override
    public CompleteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.successful_activity,parent,false);
        CompleteViewHolder holder=new CompleteViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(CompleteViewHolder holder, int position) {

        BestPrice complete=completeOrders.get(position);
        CompleteViewHolder localholder=holder;

        localholder.username.setText(complete.getUsername());


        localholder.ordertime.setText(complete.getTime());
        localholder.sparename.setText(complete.getSparename());
//        localholder.boughtprice.setText(complete.getPrice());
        localholder.ursetprice.setText(complete.getPrice());

        if (complete.getStatus().equals("N"))
        {
            localholder.status.setText("Awaiting payments");
        }
        else
        {
            localholder.status.setText("Successfully paid ");
        }


    }

    @Override
    public int getItemCount() {
        return (completeOrders != null) ? completeOrders.size() : 0;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public class CompleteViewHolder extends RecyclerView.ViewHolder {
TextView sparename,ordertime,modelname,ursetprice,boughtprice,username,status;
        public CompleteViewHolder(View itemView) {
            super(itemView);

            sparename= (TextView) itemView.findViewById(R.id.txtsparename);
            ordertime= (TextView) itemView.findViewById(R.id.txttime);

            ursetprice= (TextView) itemView.findViewById(R.id.txturset);
           status= (TextView) itemView.findViewById(R.id.txtpaid);
            username= (TextView) itemView.findViewById(R.id.txtusername);


        }
    }
}

