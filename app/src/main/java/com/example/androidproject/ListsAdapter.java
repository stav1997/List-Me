/* Assignment: final
 *   Campus: Ashdod
 *   Author: Stav Shlomovich, ID: 316132802
 * */
package com.example.androidproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import java.util.ArrayList;


/**
 * The type Lists adapter.
 */
public class ListsAdapter extends ArrayAdapter<List> implements CompoundButton.OnCheckedChangeListener{
    private static final String TAG = "ProductListAdapter";
    private Context context;
    int resource;
    List list;
    ProductHelper productHelper;

    /**
     * Instantiates a new Lists adapter.
     *
     * @param context  the context
     * @param resource the resource
     * @param objects  the objects
     */
    public ListsAdapter(Context context, int resource, ArrayList<List> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource= resource;
    }


    @NonNull
    @Override

    public View getView(int position,  View convertView, ViewGroup parent) {
        /**
         * this function populates the listview in the main activity.
         */
        String name = getItem(position).getListName();
        int id = getItem(position).getListId();
        String state = getItem(position).isState();

        list = new List(name,id, state);
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent,false);
        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);
        checkBox.setTag(position);
        checkBox.setOnCheckedChangeListener(this);
        tvName.setText(name);

        if (list.getState()){
            checkBox.setChecked(true);
        }
        else
        {
            checkBox.setChecked(false);
        }
        return convertView;
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int position = (int) buttonView.getTag();
        productHelper = new ProductHelper(context);

        if (isChecked) {
            getItem(position).setState("true");
            productHelper.updateListData(getItem(position).getListName(), getItem(position).getListId(), getItem(position).isState());

        } else {
            getItem(position).setState("false");
            productHelper.updateListData(getItem(position).getListName(), getItem(position).getListId(), getItem(position).isState());
        }
    }
}



