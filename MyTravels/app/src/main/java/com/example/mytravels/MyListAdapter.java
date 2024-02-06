package com.example.mytravels;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Locale;

// Custom adapter to show destinations in ListView
public class MyListAdapter extends BaseAdapter implements Filterable{

    Context c;
    // Two arrays, items - with all existing items, tempItems - using for custom filter
    ArrayList<Destination> items, tempItems;

    // Custom filter to find items by name
    CustomFilter cs;

    // Constructor
    public MyListAdapter(Context c, ArrayList<Destination> items){
        this.c = c;
        this.items = items;
        this.tempItems = items;
    }

    // Size of array
    @Override
    public int getCount() {
        return items.size();
    }

    // Get item on a specific position
    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    // Get position of an item
    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.activity_image_list, null);

        // Finding TextView for name (textView1) and price (Price) and ImageView (imageView1)
        // for image of an item in activity_image_list.xml
        TextView t1 = row.findViewById(R.id.textView1);
        TextView t2 = row.findViewById(R.id.Price);
        ImageView i1 = row.findViewById(R.id.imageView1);

        // Setting textView and ImageView of item
        t1.setText(items.get(position).getName());
        t2.setText(String.valueOf(items.get(position).getPrice()));
        i1.setImageResource(items.get(position).getImage());

        return row;
    }

    // Getting custom filter
    @Override
    public Filter getFilter() {

        if(cs == null){
            cs = new CustomFilter();
        }

        return cs;
    }

    // Creating custom filter
    class CustomFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            // Check word
            if(constraint != null && constraint.length()>0) {
                constraint = constraint.toString().toUpperCase();

                // New Array for filtered items
                ArrayList<Destination> filters = new ArrayList<>();

                // loop through array of destinations
                for (int i = 0; i < tempItems.size(); i++) {
                    // Check if name of destination contains written word
                    if (tempItems.get(i).getName().toUpperCase().contains(constraint)) {
                        // Create destination with name, price and image
                        Destination dest = new Destination(tempItems.get(i).getName(), tempItems.get(i).getPrice(), tempItems.get(i).getImage());
                        // Add destination to array of filtered items
                        filters.add(dest);
                    }
                }
                //Result of filtration
                results.count = filters.size();
                results.values = filters;
            } else {
                // Nothing is found
                results.count = tempItems.size();
                results.values = tempItems;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            // Show results
            items = (ArrayList<Destination>) results.values;
            notifyDataSetChanged();
        }
    }
}
