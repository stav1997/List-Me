/* Assignment: final
 *   Campus: Ashdod
 *   Author: Stav Shlomovich, ID: 316132802
 * */
package com.example.androidproject;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class Products extends MainActivity {

    ArrayList<String> products;
    ArrayList<Product> theProducts;
    ProductListAdapter productAdapter;
    TextView listName;
    ListView listView;
    /**
     * The List id.
     */
    int listId;
    int count = 0;
    CheckBox checkBox;
    String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        readListsAmount();
        listName= findViewById(R.id.tvName);
        products = new ArrayList<>();
        theProducts = new ArrayList<>();
        name = getIntent().getStringExtra("listName");
        listId =  getIntent().getIntExtra("listId",0);
        listName.setText(name + " list:");
        checkBox = findViewById(R.id.checkbox);
        listView= findViewById(R.id.productsList);
        viewProducts(listId);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                /**
                 * on item long click we let the user modify the selected product information.
                 */
                Product temp = (Product) listView.getItemAtPosition(position);
                String productName = temp.getProductName();
                String productState = temp.isState();
                String quantity = temp.getQuantity();
                String price = temp.getPrice();
                String note = temp.getNote();
                int productId = temp.getId();

                Intent i = new Intent(Products.this, AddProduct.class);
                i.putExtra("productId", productId);
                i.putExtra("listName",name);
                i.putExtra("productName", productName);
                i.putExtra("productState", productState);
                i.putExtra("listId", listId);
                i.putExtra("quantity", quantity);
                i.putExtra("price", price);
                i.putExtra("note", note);
                startActivityForResult(i,1);
                theProducts.clear();
                productAdapter.clear();
                productAdapter.notifyDataSetChanged();
                viewProducts(listId);
                return true;
            }
        });
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        /**
         * creating a menu.
         */
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        MenuItem item = menu.findItem(R.id.action_save);
        item.setVisible(false);
        MenuItem item1 = menu.findItem(R.id.action_cancel);
        item1.setVisible(false);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        /**
         * the option selected from the menu.
         */
        switch (item.getItemId()) {
            case R.id.action_home:{
                Intent i = new Intent(Products.this, MainActivity.class);
                startActivityForResult(i,1);
                return true;
            }

            case R.id.action_add:{
                Intent i = new Intent(Products.this, AddProduct.class);
                saveListsAmount();
                i.putExtra("listName",name);
                i.putExtra("productId", count);
                i.putExtra("listId", listId);
                i.putExtra("quantity", "");
                i.putExtra("price", "");
                i.putExtra("note", "");
                i.putExtra("productState", "false");
                i.putExtra("productName", "");
                startActivityForResult(i,1);
                return true;
            }

            case R.id.action_delete:{
                myDb.deleteProductsData(listId);
                Toast.makeText(Products.this,"The selected products were deleted from the "+name+ " list!",Toast.LENGTH_LONG).show();

                productAdapter.clear();
                productAdapter.notifyDataSetChanged();
                viewProducts( listId);
                return true;
            }

            case R.id.action_share:{
                shareData(listId);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void viewProducts(int listId) {
        /**
         * View products of the list with the matching id.
         * @param listId the list id
         */
        Cursor res = myDb.getAllProducts(listId);//getting all the products with the listId from the data base
        if(res.getCount() == 0) {
            return;
        }

        while (res.moveToNext()) {
            Product temp = new Product(res.getInt(0),res.getString(2),res.getString(3),res.getString(4),res.getString(5),res.getString(6),listId);
            theProducts.add(temp);
        }
        productAdapter = new ProductListAdapter(this, R.layout.adapter_view_layout, theProducts);//populating the adapter
        listView.setAdapter(productAdapter);
        count ++;
    }

    private void shareData(int listId){
        /**
         * this function is used to share the list contents.
         *@param listId the id of the list we want to share.
         */
        StringBuffer  text = new StringBuffer (""+listName.getText().toString()+"" +"\n");
        Cursor res = myDb.getProducts(listId);
        int count = 1;
        if (res.getCount()==0){
            Toast.makeText(Products.this,"this list is empty!",Toast.LENGTH_LONG).show();
        }
        else {
            while (res.moveToNext()) {

                String name = res.getString(0);
                String amount = res.getString(1);
                String price = res.getString(2);
                String note = res.getString(3);

                String product = String.valueOf(count)+". Product: " + name + " Amount: " + amount + " Price: " + price + " Description: " + note + ". \n\n";
                text.append(product);//adding every product to the buffer we'll share.
                count++;
            }
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, text.toString());
            sendIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);//start the share intent.
        }
    }

    private void saveListsAmount() {
        /**
         * This function saves the data to the shared preferences from the amounts list.
         */
        appSharedPrefs = getSharedPreferences("Numbers", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(amounts);
        editor.putString("amounts", json);
        editor.apply();
    }

    private void readListsAmount() {
        /**
         * This function reads the data from the shared preferences and loads it into the amounts list.
         */
        SharedPreferences appSharedPrefs = getSharedPreferences("Numbers", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = appSharedPrefs.getString("amounts", "");
        Type listType = new TypeToken<java.util.List<Amount>>(){}.getType();

        amounts = gson.fromJson(json, listType);
        for (int i=0; i<amounts.size();i++){
            if (amounts.get(i).getId()==listId){
                count=amounts.get(i).getNum();
                amounts.get(i).setNum(count+1);
            }

        }
        if (amounts == null) {
            amounts = new ArrayList<Amount>();
        }
    }
}



