package stylist.com.glamhub;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

/**
 * Created by OSCAR on 3/12/2017.
 */

public class FuelAdapter extends  RecyclerView.Adapter <FuelViewHolder> {
    List<Fuelgetters> list = Collections.emptyList();
    Context context;
    private CoordinatorLayout coordinatorLayout;
    public FuelAdapter(List<Fuelgetters>list ,Context context)
    {
        this.context=context;
        this.list=list;
    }

    @Override
    public FuelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fuel_activity, parent, false);
        FuelViewHolder holder = new FuelViewHolder(view);
        this.coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.coordinatorLayoutFuelFragmentLayout);
        return holder;
    }

    @Override
    public void onBindViewHolder(FuelViewHolder holder, int position) {
       final Fuelgetters fuel=list.get(position);

        FuelViewHolder fuelViewHolder=holder;
        fuelViewHolder.pStation.setText(fuel.getpStation());
        fuelViewHolder.petrolPrice.setText(fuel.getPetrolPrice());
        fuelViewHolder.diselPrice.setText(fuel.getDiselPrice());
        fuelViewHolder.paraffinPrice.setText(fuel.getParaffinPrice());
        fuelViewHolder.flat.setText(fuel.getFlat());
        fuelViewHolder.flong.setText(fuel.getFlong());

        Log.d("stations", "onBindViewHolder:"+fuel.getpStation()+fuel.getPetrolPrice());


    }

    @Override
    public int getItemCount() {
        return (list != null) ? list.size() : 0;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
    public void insert(int position,Fuelgetters fuel)
    {
        list.add(position,fuel);
        notifyItemChanged(position);
    }
    public void remove(Fuelgetters fuel)
    {
        int position = list.indexOf (fuel);
        list.remove(position);
        notifyItemRemoved(position);
    }
}
