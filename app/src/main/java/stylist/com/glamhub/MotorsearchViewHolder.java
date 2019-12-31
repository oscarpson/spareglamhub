package stylist.com.glamhub;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by OSCAR on 3/14/2017.
 */

public class MotorsearchViewHolder extends RecyclerView.ViewHolder {
    ImageView imgMotorsearch;
    TextView mSearchdetails,mSearchtype,mSearchmodel;

    public MotorsearchViewHolder(View itemView) {
        super(itemView);
        imgMotorsearch=(ImageView)itemView.findViewById(R.id.imgMsearchImage);
        mSearchmodel=(TextView)itemView.findViewById(R.id.txtMsearchModel);
        mSearchtype=(TextView)itemView.findViewById(R.id.txtMsearchType);
        mSearchdetails=(TextView)itemView.findViewById(R.id.txtMsearchDetails);
    }
}
