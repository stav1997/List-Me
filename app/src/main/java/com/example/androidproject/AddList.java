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
import android.widget.Toast;


public class AddList extends MainActivity{
    /**
     * The type Add list.
     */
    EditText etName;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_list);

        etName =findViewById(R.id.etName);// the name can only accept big and small letters! specified in the xml file
        id= getIntent().getIntExtra("amount",0);
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
                Intent i = new Intent(AddList.this, MainActivity.class);
                startActivity(i);
                return true;
            }
            case R.id.action_save:{
                saveList();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void saveList(){
        /**
         * Save list in the database and go back to the main activity.
         */
        if (etName.getText().toString().trim().length() == 0){
            Toast.makeText(AddList.this,"You must enter list name!",Toast.LENGTH_LONG).show();
        }
        else {
            String listName = etName.getText().toString();
            if (myDb.checkListName(listName)){
                myDb.insertNewList(listName, id,"false");
                Intent i = new Intent(AddList.this, MainActivity.class);
                viewLists();
                startActivity(i);
            }
            else {
                Toast.makeText(AddList.this,"This list is already exists!",Toast.LENGTH_LONG).show();
            }
        }
    }
}