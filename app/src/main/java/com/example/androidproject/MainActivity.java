/* Assignment: final
 *   Campus: Ashdod
 *   Author: Stav Shlomovich, ID: 316132802
 * */
package com.example.androidproject;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;
import java.lang.reflect.Type;
import java.util.ArrayList;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {
    /**
     * The App shared prefs.
     */
    SharedPreferences appSharedPrefs;
    int count = 0;
    ProductHelper myDb;
    ArrayList<List> lists;
    ArrayList<Amount> amounts;// the amount of products in each list
    ListsAdapter listsAdapter;
    CheckBox checkBox;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ActivityCompat.requestPermissions(this,new String[]{READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main);
        myDb = new ProductHelper(this);//creating a data base
        myDb.open();
        lists = new ArrayList<>();
        checkBox = (CheckBox) findViewById(R.id.checkbox);
        listView= findViewById(R.id.lists);
        viewLists();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /**
                 * on item click we move to the products list of the selected list.
                 */
                List temp = (List) listView.getItemAtPosition(position);
                String text = temp.getListName();
                Intent i = new Intent(MainActivity.this, Products.class);
                i.putExtra("listName", text);
                i.putExtra("listId", temp.getListId());
                startActivity(i);
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

            case R.id.action_add:{
                Intent i = new Intent(MainActivity.this, AddList.class);
                readListsAmount();
                count = amounts.size();
                amounts.add(new Amount(count,0));
                saveListsAmount();
                i.putExtra("amount", count);
                startActivity(i);
                return true;
            }

            case R.id.action_delete:{

                myDb.deleteListsData();
                Toast.makeText(this,"The selected list were deleted!",Toast.LENGTH_LONG).show();

                listsAdapter.clear();
                listsAdapter.notifyDataSetChanged();
                viewLists();
                return true;
            }

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void viewLists() {
        /**
         * View all the lists from the data base, and populate the adapter.
         */
        Cursor res = myDb.getAllLists();

        if(res.getCount() == 0) {
            // show message
            showMessage("Welcome","You don't have any lists yet!");
            return;
        }

        while (res.moveToNext()) {
            List temp = new List(res.getString(1),res.getInt(0),res.getString(2));
            lists.add(temp);
        }
        listsAdapter = new ListsAdapter(this, R.layout.list_adapter_layout, lists);
        listView.setAdapter(listsAdapter);
    }


    public void showMessage(String title,String Message){
        /**
         * Show error message.
         * @param title   the title
         * @param Message the message to show
         */
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
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
        Type listType = new TypeToken<java.util.List<List>>(){}.getType();
        amounts = gson.fromJson(json, listType);
        if (amounts == null) {
            amounts = new ArrayList<Amount>();
        }
    }

}