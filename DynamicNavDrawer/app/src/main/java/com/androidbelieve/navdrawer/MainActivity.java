package com.androidbelieve.navdrawer;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.JsonReader;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private Context context=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * Setting up the DrawerLayout,NavigationView and Toolbar.
         */


        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
         toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /**
         * Accessing the NavigationView menu.
         * The getMenu method returns menu resource used by navigationView.
         * Later,we will add items to this menu.
         */

        Menu drawerMenu = navigationView.getMenu();


        /**
         * Creating object of AsyncClass - 'PopulateMenuItems' to get items from the database.
         * Here,we pass the above menu so that after retrieving items we can add this to it.
         */



        PopulateMenuItems populateMenuItems = new PopulateMenuItems();
        populateMenuItems.execute(drawerMenu);

        /**
         * This is required to handle the onItemClick on Navigation Drawer Items.
         */

        NavigationView.OnNavigationItemSelectedListener item_click_listener = new NavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(MenuItem item)
            {

                // This a sample code to change fragments depending on the menu item.


                Toast.makeText(context , ""+item.toString(),Toast.LENGTH_SHORT).show();
                getSupportActionBar().setTitle(item.toString());
                 if(item.toString().toLowerCase().equals("student profile"))
                   {

                       /*
                        Here call Fragment Based on selected menu
                        FragmentManager mFragmentManager;
                        FragmentTransaction mFragmentTransaction;

                        mFragmentManager = getSupportFragmentManager();
                        mFragmentTransaction = mFragmentManager.beginTransaction();

                        mFragmentTransaction.replace(R.id.containerView,new FragmentAboutUs()).commit();*/






                  }


                drawerLayout.closeDrawers();
                return true;
            }
        };

        /**
         * Attaching the above listener to NavigationView
         */

        navigationView.setNavigationItemSelectedListener(item_click_listener);

        /**
         * Setting up Drawer Toggle for Toolbar.
         */


        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout, toolbar,R.string.app_name,
                R.string.app_name);

        drawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();


    }



    public class PopulateMenuItems extends android.os.AsyncTask<Menu,Void,Void>{

        @Override
        protected Void doInBackground(final Menu... params) {
            try {
                /**
                 *An arrayList to hold the items populated from database.
                 */
                final ArrayList menuItems = new ArrayList();

                /**
                 * Here, declare the URL. If on localhost, just write the localhost ip and add the path.
                 * The url object parses the urlString and throws 'MalformedURLException' if  URL could not be parsed.
                 * OpenConnection returns a connection referred by URL.
                 */

                String urlString = "http://api.androidhive.info/contacts/";




                ServiceHandler sh= new ServiceHandler();
                String jsondata = sh.makeServiceCall(urlString,ServiceHandler.GET);
                //jsondata=convertToJsonFormat(jsondata);
                Log.d("json data " , jsondata);


                JSONObject obj= new JSONObject(jsondata);

                JSONArray arr = obj.getJSONArray("contacts");
                menuItems.clear();
                Menu drawerMenu = params[0];
                menuItems.add("Sathish Gadde");
                for(int i=0;i<arr.length();i++)
                {
                    JSONObject c = arr.getJSONObject(i);

                    String title = c.getString("name");


                    menuItems.add(title);




                }




                /**
                 * runOnUiThread is used to update UI i.e Add Items to NavigationView Menu from this non-UI thread(AysncTask).
                 */


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        /**
                         * params[0] refers to the first parameter passed to this method i.e a menu.
                         * Add items to this menu by iterating the arrayList.
                         */

                        Menu drawerMenu = params[0];
                        Log.d("paramsss[0]",""+params[0]);
                        for (int temp=0;temp<=menuItems.size()-1;temp++){
                            Log.d("String",menuItems.get(temp).toString());
                            drawerMenu.add(menuItems.get(temp).toString());
                        }

                    }
                });

            }

            catch (Exception e){
                Log.e("Exception",e.toString());
            }

            return null;
        }



    }


}
