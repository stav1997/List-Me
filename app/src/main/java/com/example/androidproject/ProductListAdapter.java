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
 * The type Product list adapter.
 */
public class ProductListAdapter extends ArrayAdapter<Product> implements CompoundButton.OnCheckedChangeListener{
    private static final String TAG = "ProductListAdapter";
    private Context context;
    int resource;
    Product product;
    ProductHelper productHelper;

    /**
     * Instantiates a new Product list adapter.
     *
     * @param context  the context
     * @param resource the resource
     * @param objects  the objects
     */
    public ProductListAdapter(Context context, int resource, ArrayList<Product> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource= resource;
    }


    @NonNull
    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {

        /**
         * this function populates the listview in the Products activity.
         */
        String name = getItem(position).getProductName();
        String quantity = getItem(position).getQuantity();
        String price = getItem(position).getPrice();
        String note = getItem(position).getNote();
        String state = getItem(position).isState();
        int id = getItem(position).getId();
        int listId = getItem(position).getListId();

        product = new Product(id, name,quantity, price, note, state, listId);
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent,false);
        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvAmount = (TextView) convertView.findViewById(R.id.tvAmount);
        TextView tvPrice = (TextView) convertView.findViewById(R.id.tvPrice);
        TextView tvNote = (TextView) convertView.findViewById(R.id.tvNote);
        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);
        checkBox.setTag(position);
        checkBox.setOnCheckedChangeListener(this);

        tvName.setText(name);
        tvAmount.setText(quantity);
        tvPrice.setText(price);
        tvNote.setText(note);

        if (product.getState()){
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
            productHelper.updateData(getItem(position).getId(), getItem(position).getProductName(), getItem(position).getQuantity(), getItem(position).getPrice(), getItem(position).getNote(), getItem(position).getListId(), getItem(position).isState());

        } else {
            getItem(position).setState("false");
            productHelper.updateData(getItem(position).getId(), getItem(position).getProductName(), getItem(position).getQuantity(), getItem(position).getPrice(), getItem(position).getNote(), getItem(position).getListId(), getItem(position).isState());
        }
    }
}



