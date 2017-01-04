package com.example.andrea.byteconverter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements OnNavigationItemSelectedListener {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener((OnNavigationItemSelectedListener) this);


        final TextView text2 = (TextView) findViewById(R.id.textView2);
        final EditText text = (EditText) findViewById(R.id.editText);
        Button bottone = (Button) findViewById(R.id.button);
        bottone.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //nasconde la tastiera
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                //preleva l'indice degli spinner
                Spinner spinner_from = (Spinner) findViewById(R.id.spinner_from);
                Spinner spinner_to= (Spinner) findViewById(R.id.spinner_to);
                Integer index_f=spinner_from.getSelectedItemPosition();
                Integer index_t=spinner_to.getSelectedItemPosition();

                Double num;
                if (text.getText().toString().trim().length() == 0)
                    num = 0.0;
                else
                    num = Double.valueOf(text.getText().toString());

                Double ris = Converter(num, index_f, index_t);
                if (!pr() ){
                    DecimalFormat dff = new DecimalFormat("#.#####");
                    text2.setText(dff.format(ris) + " " + spinner_to.getSelectedItem().toString());
                   /* if ((double)dff.format(ris)==0.0)
                        toast_p();*/
                }else{
                    text2.setText(ris + " " + " " + spinner_to.getSelectedItem().toString());
                }
            }
        });




        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public Boolean pr(){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean isPr = pref.getBoolean("precision_switch", false);
        return isPr;
    }

    public void toast_p (){
        Context context = getApplicationContext();
        CharSequence text = "if this is not the result expected, try to turn on more precision in settings ;)";
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

    }

    Double Converter(Double num, Integer index_f, Integer index_t) {

        if (index_f > index_t){
            Integer n = index_f - index_t;
            for (int i=0; i< n; i++){
                num = num*1024.0;
            }
        }else if(index_f < index_t){
            Integer n = index_t - index_f;
            System.out.println("stampo n=" + n);
            for (int i=0; i< n; i++){
                System.out.println("stampo num prima=" + num);
                num = num/1024.0;
                System.out.println("stampo num=" + num);
            }
        }
        return num /*= (double)Math.round(num * 1000000) / 1000000 // 10 decimal-places*/;

    }


   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent settings = new Intent( this, SettingsActivity.class );
            settings.putExtra( SettingsActivity.EXTRA_SHOW_FRAGMENT, "com.example.andrea.byteconverter.SettingsActivity$GeneralPreferenceFragment" );
            settings.putExtra( SettingsActivity.EXTRA_NO_HEADERS, true );

            //Intent settings = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(settings);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    //funzioni per drawer

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        /*if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else*/
        if (id == R.id.nav_manage) {
            Intent settings = new Intent( this, SettingsActivity.class );
            settings.putExtra( SettingsActivity.EXTRA_SHOW_FRAGMENT, "com.example.andrea.byteconverter.SettingsActivity$GeneralPreferenceFragment" );
            settings.putExtra( SettingsActivity.EXTRA_NO_HEADERS, true );
            startActivity(settings);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
