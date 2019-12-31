package stylist.com.glamhub;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import stylist.com.glamhub.towMe.TowmeActivity;

/**
 * Created by OSCAR on 3/14/2017.
 */

public class mSearchViewHolder extends RecyclerView.ViewHolder {
    ImageView imgMotorsearch;
    TextView mSearchdetails,mSearchtype,mSearchmodel;
    RelativeLayout layertowme;

    public mSearchViewHolder(View itemView) {
        super(itemView);
        imgMotorsearch=(ImageView)itemView.findViewById(R.id.imgMsearchImage);
        mSearchmodel=(TextView)itemView.findViewById(R.id.txtMsearchModel);
        mSearchtype=(TextView)itemView.findViewById(R.id.txtMsearchType);
        mSearchdetails=(TextView)itemView.findViewById(R.id.txtMsearchDetails);
        layertowme= (RelativeLayout) itemView.findViewById(R.id.layertowme);

        layertowme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),TowmeActivity.class);

                intent.putExtra("owner",mSearchtype.getText().toString());
                intent.putExtra("place",mSearchdetails.getText().toString());
                intent.putExtra("maxweight",mSearchtype.getText().toString());
                imgMotorsearch.buildDrawingCache();
                Bitmap image=imgMotorsearch.getDrawingCache();
                Bundle extras=new Bundle();
                extras.putParcelable("image",image);
                intent.putExtras(extras);
                v.getContext().startActivity(intent);
            }
        });
    }
}
