package stylist.com.glamhub.Feedback;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import stylist.com.glamhub.R;

/**
 * Created by OSCAR on 9/28/2017.
 */

public class AdminViewHolder extends RecyclerView.ViewHolder {
    TextView time,reviewmessage,userkey,userId,username;
    public AdminViewHolder(final View itemView) {
        super(itemView);

        time= (TextView) itemView.findViewById(R.id.txttime);
        reviewmessage= (TextView) itemView.findViewById(R.id.txturchat);
        userkey= (TextView) itemView.findViewById(R.id.txtuserkey);
        userId= (TextView) itemView.findViewById(R.id.txtuserId);
        username= (TextView) itemView.findViewById(R.id.txtusername);
        reviewmessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intes=new Intent(itemView.getContext(),AdminReply.class);
                intes.putExtra("userKey",userkey.getText().toString());
              intes.putExtra("userId",userId.getText().toString());
                itemView.getContext().startActivity(intes);
            }
        });
    }
}