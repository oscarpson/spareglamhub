package stylist.com.glamhub;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by admin on 10/18/2016.
 */

public class UpdatesViewHolder  extends RecyclerView.ViewHolder
{
    RelativeLayout partlayer;
    TextView PartName, Price, Vehicle,partId;
    ImageView  imageView;

    public UpdatesViewHolder (View itemView)
    {
        super(itemView);

        PartName = (TextView) itemView.findViewById(R.id.txtpartName);
        Price = (TextView) itemView.findViewById(R.id.txtPartPrice);
        imageView = (ImageView)itemView.findViewById(R.id.imgPartImage);
        Vehicle = (TextView) itemView.findViewById (R.id.txtVehicleModel);
        partId= (TextView) itemView.findViewById(R.id.partId);
        partlayer= (RelativeLayout) itemView.findViewById(R.id.layerPart);

       itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                partlayer= (RelativeLayout) v.findViewById(R.id.layerPart);
               // Toast.makeText(v.getContext(), "This laayer"+getAdapterPosition()+"\n"+partId.getText()+Price.getText().toString().toString(), Toast.LENGTH_SHORT).show();
                Log.d("itemIdx", ""+partId+"\t"+Price);
                Intent intent=new Intent(v.getContext(),OrderSpare.class);
                intent.putExtra("partId",partId.getText().toString());
                intent.putExtra("price",Price.getText().toString());
                intent.putExtra("partname",PartName.getText().toString());
                intent.putExtra("model",Vehicle.getText().toString());
                imageView.buildDrawingCache();
                Bitmap image=imageView.getDrawingCache();
                Bundle extras=new Bundle();
                extras.putParcelable("image",image);
                intent.putExtras(extras);
                v.getContext().startActivity(intent);
            }
        });


        //share = (ImageView) itemView.findViewById(R.id.imgNewsShare);
    }
}