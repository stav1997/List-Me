/* Assignment: final
 *   Campus: Ashdod
 *   Author: Stav Shlomovich, ID: 316132802
 * */
package com.example.androidproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
/**
 * The type Add product.
 */
public class AddProduct extends MainActivity{
    /**
     * The List id.
     */
    int listId, /**
     * The product Id.
     */
    id;

    EditText etName, etQuantity, etPrice, etDescription;
    TextView tvName, tvQuantity, tvPrice, tvDescription;
    TextView tvListName;
    String listName;
    String state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        listName = getIntent().getStringExtra("listName");
        listId =  getIntent().getIntExtra("listId",0);// list ID
        id =  getIntent().getIntExtra("productId",0);//product ID
        state = getIntent().getStringExtra("productState");

        etName =findViewById(R.id.etName);
        etQuantity =findViewById(R.id.etQuantity);
        etPrice =findViewById(R.id.etPrice);
        etDescription =findViewById(R.id.etDescription);

        etName.setText(getIntent().getStringExtra("productName"));
        etQuantity.setText(getIntent().getStringExtra("quantity"));
        etPrice.setText(getIntent().getStringExtra("price"));
        etDescription.setText(getIntent().getStringExtra("note"));

        tvName = findViewById(R.id.tvName);
        tvQuantity = (TextView) findViewById(R.id.tvQuantity);
        tvPrice = (TextView) findViewById(R.id.tvPrice);
        tvDescription = (TextView) findViewById(R.id.tvDescription);
        tvListName = (TextView) findViewById(R.id.tvListName);
        tvListName.setText("Add item to "+listName+" list:");
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        /**
         * creating a menu.
         */
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        MenuItem item = menu.findItem(R.id.action_delete);
        item.setVisible(false);
        MenuItem item1 = menu.findItem(R.id.action_add);
        item1.setVisible(false);
        MenuItem item2 = menu.findItem(R.id.action_share);
        item2.setVisible(false);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        /**
         * the option selected from the menu.
         */
        switch (item.getItemId()) {
            case R.id.action_home:{
                startActivity(new Intent(this, MainActivity.class));
                return true;
            }

            case R.id.action_cancel:{
                Intent i = new Intent(AddProduct.this, Products.class);
                i.putExtra("listName",listName);
                i.putExtra("listId", listId);
                startActivity(i);
                return true;
            }

            case R.id.action_save:{
                saveDate();
                return true;
            }

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void saveDate(){
        /**
         * Save product in the database and go back to the Products activity.
         */
        String productName = etName.getText().toString();
        String productQuantity = etQuantity.getText().toString();
        String productPrice = etPrice.getText().toString()+"₪";
        String productDescription = etDescription.getText().toString();
        if (myDb.checkProductId( listId,  id)){
            if (etName.getText().toString().trim().length() == 0){
                Toast.makeText(AddProduct.this,"You must enter product name!",Toast.LENGTH_LONG).show();
            }
            else {
                if (myDb.checkProductName(listId,productName)){
                    if (etQuantity.getText().toString().trim().length() == 0){
                        productQuantity = "-";
                    }
                    if (etPrice.getText().toString().trim().length() == 0){
                        productPrice = "-";
                    }
                    if (etDescription.getText().toString().trim().length() == 0){
                        productDescription = "-";
                    }

                    myDb.insertNewProduct(id, productName, productQuantity,productPrice,productDescription,listId,"false");
                    Intent i = new Intent(AddProduct.this, Products.class);
                    i.putExtra("listName",listName);
                    i.putExtra("listId", listId);
                    startActivity(i);
                }
                else {
                    Toast.makeText(AddProduct.this,"This product is already on the list!",Toast.LENGTH_LONG).show();
                }
            }
        }
        else {//if we are just changing details
            if (etName.getText().toString().trim().length() == 0){
                Toast.makeText(AddProduct.this,"You must enter product name!",Toast.LENGTH_LONG).show();
            }
            else {
                if (etQuantity.getText().toString().trim().length() == 0){
                    productQuantity = "-";
                }
                if (etPrice.getText().toString().trim().length() == 0){
                    productPrice = "-";
                }
                if (etDescription.getText().toString().trim().length() == 0){
                    productDescription = "-";
                }
                myDb.updateData(id, productName, productQuantity,productPrice,productDescription,listId,state);
                Toast.makeText(AddProduct.this,"The product details has been updated",Toast.LENGTH_LONG).show();

                Intent i = new Intent(AddProduct.this, Products.class);
                i.putExtra("listName",listName);
                i.putExtra("listId", listId);
                startActivity(i);
            }
        }
    }
}