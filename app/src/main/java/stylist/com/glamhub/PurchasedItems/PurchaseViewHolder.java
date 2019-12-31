package stylist.com.glamhub.PurchasedItems;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import stylist.com.glamhub.OrderSpare;
import stylist.com.glamhub.R;

/**
 * Created by OSCAR on 5/7/2017.
 */

public class PurchaseViewHolder
  extends RecyclerView.ViewHolder
{
    RelativeLayout partlayer;
    TextView Partname, price, Model,partId;

    Button btnitemcancel;

    public PurchaseViewHolder (final View itemView)
    {
        super(itemView);

        Partname = (TextView) itemView.findViewById(R.id.txtpartName);
        price = (TextView) itemView.findViewById(R.id.txtPartPrice);

        Model = (TextView) itemView.findViewById (R.id.txtVehicleModel);
        partId= (TextView) itemView.findViewById(R.id.partId);
        btnitemcancel= (Button) itemView.findViewById(R.id.btnitemcancel);
        partlayer= (RelativeLayout) itemView.findViewById(R.id.layerItem);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                partlayer= (RelativeLayout) v.findViewById(R.id.layerPart);
              //  Toast.makeText(v.getContext(), "This layer"+getAdapterPosition()+"\n"+partId.getText().toString(), Toast.LENGTH_SHORT).show();

                btnitemcancel= (Button) v.findViewById(R.id.btnitemcancel);
                btnitemcancel.setVisibility(View.VISIBLE);

            }
        });


        //share = (ImageView) itemView.findViewById(R.id.imgNewsShare);
    }
}