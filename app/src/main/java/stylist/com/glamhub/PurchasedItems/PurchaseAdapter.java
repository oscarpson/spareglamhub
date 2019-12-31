package stylist.com.glamhub.PurchasedItems;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

import stylist.com.glamhub.R;
import stylist.com.glamhub.Updates;

/**
 * Created by OSCAR on 5/7/2017.
 */

public class PurchaseAdapter extends RecyclerView.Adapter<PurchaseViewHolder> {

        List<Updates> list = Collections.emptyList();
        Context context;
        private CoordinatorLayout coordinatorLayout;
SharedPreferences pref;
        public PurchaseAdapter (List <Updates> list, Context context)
        {
            this.list = list;
            this.context = context;
        }

        @Override
        public PurchaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //Inflate the layout, initialize the view holder
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.purchase_activity, parent, false);
           //SharedPreferences pref=Context.getSharedPreferences("arrayitem",Context.MODE_PRIVATE);


            PurchaseViewHolder holder = new PurchaseViewHolder(view);
            this.coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.coordinatorLayoutUpdatesFragmentLayout);
            return holder;
        }
        public void onBindViewHolder(PurchaseViewHolder holder, final int position) {
            final Updates currentNews = list.get(position);


            //use the provided viewHolder on the oncreateviewholder method to populate the current row
            final PurchaseViewHolder localHolder = holder;
            localHolder.price.setText(currentNews.getPrice());
            localHolder.partId.setText(currentNews.getSpareId());



            //String year = dateValue.substring(dateValue.length() - 4, dateValue.length());
            //dateValue = dateValue.substring(0, dateValue.lastIndexOf(':'));

            localHolder.Partname.setText(list.get(position).getPartname());
            localHolder.Model.setText(list.get(position).getVehiclemodel());

            localHolder.btnitemcancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // list.remove(list.get(position));
                    try {
                        remove(list.get(position));

                        //list.notifyAll();
                        list.size();
                       // Toast.makeText(context, "ItemRemoved" + "\n size" + list.size(), Toast.LENGTH_LONG).show();
                        //list.notify();
                    }
                    catch (Exception e)
                    {
                       // Toast.makeText(context, "Only one item", Toast.LENGTH_SHORT).show();
                        list.clear();
                    }
                }

            });


            //localHolder.imageView.setImageDrawable(list.get(position).getImage());

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
           pref=context.getSharedPreferences("arrayitem",0);
            SharedPreferences.Editor editor=pref.edit();


            String toremove=pref.getString("shoppingPrice"+position,null);
            editor.remove("shoppingitem"+position);
            editor.remove("shoppingPrice"+position);
            editor.remove("shoppingModel"+position);
            editor.remove("shoppingPartname"+position);

            editor.apply();



            list.remove(position);
           // itemId[i]=pref.getString("shoppingitem"+i,null);
           // price[i]=pref.getString("shoppingPrice"+i,null);
            notifyItemRemoved(position);
           if(list.isEmpty())
            {
                // int coun=0;
                editor.putInt("count",0);
                editor.apply();
            }

            int  counter=pref.getInt("count",0);
            
            Log.d("remove position","p"+position+"\n counter"+counter+"\n torem"+toremove);
           // Toast.makeText(context, "Doing it via method\n"+""+position+"\n\nremaining data is \t"+counter+"to rem\t"+toremove, Toast.LENGTH_SHORT).show();

            String itemId[]=new String[counter];
            String price[]=new String[counter];
            String model[]=new String[counter];
            String partName[]=new String[counter];
            int countere=0;
            for (int i=0;i<counter;i++)
            {
                itemId[i]=pref.getString("shoppingitem"+i,null);
                price[i]=pref.getString("shoppingPrice"+i,null);
                model[i]=pref.getString("shoppingModel"+i,null);
                partName[i]=pref.getString("shoppingPartname"+i,null);
                editor.remove("shoppingitem"+i);
                editor.remove("shoppingPrice"+i);
                editor.remove("shoppingModel"+i);
                editor.remove("shoppingPartname"+i);
                editor.apply();
                // Toast.makeText(this,array[i]+"item"+i+"\n"+barray[i]+"\t"+i,Toast.LENGTH_LONG).show();
                if(itemId[i]!=null) {

                    editor.putString("shoppingitem"+countere,itemId[i]);
                    editor.putString("shoppingPrice"+countere,price[i]);
                    editor.putString("shoppingModel"+countere, model[i]);
                    editor.putString("shoppingPartname"+countere, partName[i]);
                    countere=countere+1;
                   editor.putInt("count",countere);
                    editor.apply();
                }
            }

        }




    }

