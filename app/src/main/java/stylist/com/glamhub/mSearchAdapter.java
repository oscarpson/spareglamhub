package stylist.com.glamhub;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

/**
 * Created by OSCAR on 3/14/2017.
 */

public class mSearchAdapter extends RecyclerView.Adapter<mSearchViewHolder>{
    List<mSearch> list = Collections.emptyList();
    Context context;


    public mSearchAdapter(List<mSearch> list, Context context)
    {
        this.list=list;
        this.context=context;
    }

    @Override
    public mSearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the view holder
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.msearch_activity, parent, false);
        mSearchViewHolder holder = new mSearchViewHolder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(mSearchViewHolder holder, int position) {


        final mSearch about = list.get(position);
        final mSearchViewHolder localholder=holder;

        //use the provided viewHolder on the oncreateviewholder method to populate the current row


        //String year = dateValue.substring(dateValue.length() - 4, dateValue.length());
        Picasso
                .with(context)
                .load(about.getImgmSearch())
                .into(localholder.imgMotorsearch);
        holder.mSearchdetails.setText(list.get(position).getmSearchdetails());
        holder.mSearchtype.setText(list.get(position).getmSearchtype());
        holder.mSearchmodel.setText(list.get(position).getmSearchmoel());

        //holder.phone.setText(list.get(position).getPhone());
        //holder.header.setVisibility(View.VISIBLE);
        //holder.showMore.setVisibility(View.VISIBLE);
        //localHolder.imageView.setImageDrawable(list.get(position).getImage());

    }


    @Override
    public int getItemCount() {
        return (list != null) ? list.size() : 0;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    //Insert a new item to the recyclerview on a predefined position
    public void insert (int position, mSearch about)
    {
        list.add(position, about);
        notifyItemInserted(position);
    }
    public void remove (mSearch about)
    {
        int position = list.indexOf (about);
        list.remove(position);
        notifyItemRemoved(position);
    }
}
