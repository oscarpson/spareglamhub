package stylist.com.glamhub.Feedback;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import stylist.com.glamhub.R;


/**
 * Created by OSCAR on 9/10/2017.
 */

public class ReviewViewHolder extends RecyclerView.ViewHolder {
    TextView time,reviewmessage,adminchat;
    public ReviewViewHolder(View itemView) {
        super(itemView);

        time= (TextView) itemView.findViewById(R.id.txttime);
        reviewmessage= (TextView) itemView.findViewById(R.id.txtmychat);
        adminchat= (TextView) itemView.findViewById(R.id.txtadminchat);
    }
}
