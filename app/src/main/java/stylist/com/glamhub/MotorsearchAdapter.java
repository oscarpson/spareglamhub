package stylist.com.glamhub;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by OSCAR on 3/14/2017.
 */

public class MotorsearchAdapter extends RecyclerView.Adapter<MotorsearchViewHolder> {
    List<motorsearchGetters> list= Collections.emptyList();
    Context context;
    public MotorsearchAdapter(List<motorsearchGetters>list,Context context)
    {
        this.list=list;
        this.context=context;
    }
    @Override
    public MotorsearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.motorsearch_activity,parent,false);
       MotorsearchViewHolder holder=new MotorsearchViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MotorsearchViewHolder holder, int position) {
        motorsearchGetters search=list.get(position);
     final MotorsearchViewHolder localholder=holder;
        Picasso
                .with(context)
                .load(search.getImgmSearch())
                .into(localholder.imgMotorsearch);
        holder.mSearchdetails.setText(list.get(position).getmSearchdetails());
        holder.mSearchtype.setText(list.get(position).getmSearchtype());
        holder.mSearchmodel.setText(list.get(position).getmSearchmoel());

    }

    @Override
    public int getItemCount() {
        return (list != null) ? list.size() : 0;
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
    public void insert (int position, motorsearchGetters msearch)
    {
        list.add(position, msearch);
        notifyItemInserted(position);
    }
    public void remove (motorsearchGetters msearch)
    {
        int position = list.indexOf (msearch);
        list.remove(position);
        notifyItemRemoved(position);
    }
}
