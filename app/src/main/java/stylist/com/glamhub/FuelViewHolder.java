package stylist.com.glamhub;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by OSCAR on 3/12/2017.
 */

public class FuelViewHolder  extends RecyclerView.ViewHolder {
    TextView pStation,petrolPrice,diselPrice,paraffinPrice,flat,flong;
    RelativeLayout layerfuel;
    public FuelViewHolder(View itemView) {
        super(itemView);
        pStation=(TextView)itemView.findViewById(R.id.txtfstation);
        petrolPrice=(TextView)itemView.findViewById(R.id.txtfPetrolPrice);
        diselPrice=(TextView)itemView.findViewById(R.id.txtfDiselPrice);
        paraffinPrice=(TextView)itemView.findViewById(R.id.txtfParafinPrice);
        layerfuel= (RelativeLayout) itemView.findViewById(R.id.layerfuel);
        flat= (TextView) itemView.findViewById(R.id.flat);
        flong= (TextView) itemView.findViewById(R.id.flong);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layerfuel= (RelativeLayout) v.findViewById(R.id.layerfuel);

                Intent intent=new Intent(v.getContext(),MapsActivity.class);
                intent.putExtra("flat",flat.getText().toString());
                intent.putExtra("flong",flong.getText().toString());
                intent.putExtra("fStation",pStation.getText().toString());
                intent.putExtra("fCost","Petrol:"+petrolPrice.getText().toString()+"\tDiesel:"+diselPrice.getText().toString()+"\tParaffin :"+paraffinPrice.getText().toString());
               // Toast.makeText(v.getContext(), "to mainpage", Toast.LENGTH_SHORT).show();
                v.getContext().startActivity(intent);
            }
        });
    }
}
