package stylist.com.glamhub;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

/**
 * Created by admin on 10/18/2016.
 */

public class UpdatesAdapter extends RecyclerView.Adapter <UpdatesViewHolder>
{
    List<Updates> list = Collections.emptyList();
    Context context;
    private CoordinatorLayout coordinatorLayout;

    public UpdatesAdapter (List <Updates> list, Context context)
    {
        this.list = list;
        this.context = context;
    }

    @Override
    public UpdatesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the view holder
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.update_activity, parent, false);
        UpdatesViewHolder holder = new UpdatesViewHolder(view);
     //   this.coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.coordinatorLayoutUpdatesFragmentLayout);
        return holder;
    }
    public void onBindViewHolder(UpdatesViewHolder holder, final int position) {
        final Updates currentNews = list.get(position);


        //use the provided viewHolder on the oncreateviewholder method to populate the current row
        final UpdatesViewHolder localHolder = holder;
        localHolder.Price.setText(currentNews.getPrice());
        localHolder.partId.setText(currentNews.getSpareId());



        //String year = dateValue.substring(dateValue.length() - 4, dateValue.length());
        //dateValue = dateValue.substring(0, dateValue.lastIndexOf(':'));
        localHolder.Vehicle.setText(currentNews.getVehiclemodel());
        localHolder.PartName.setText(list.get(position).getPartname());


        //localHolder.imageView.setImageDrawable(list.get(position).getImage());

        Picasso
                .with(context)
                .load(currentNews.getImage())
               .fit()
               // .centerCrop()
                .into(localHolder.imageView);
        Log.d("imglocal", "onBindViewHolder:"+currentNews.getImage().toString());
        System.out.print("Image Url: "+currentNews.getImage());
      // localHolder.imageView.setImageDrawable(currentNews.getImage());
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
    public void insert (int position, Updates updates)
    {
        list.add(position, updates);
        notifyItemInserted(position);
    }
    public void remove (Updates updates)
    {
        int position = list.indexOf (updates);
        list.remove(position);
        notifyItemRemoved(position);
    }




}
