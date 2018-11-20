package csc472.depaul.edu.transit;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import csc472.depaul.edu.transit.Bus.BusDirectionFragment;
import csc472.depaul.edu.transit.Bus.BusFragment;
import csc472.depaul.edu.transit.Bus.BusPrediction;
import csc472.depaul.edu.transit.Bus.BusPredictionsFragment;
import csc472.depaul.edu.transit.Bus.BusRoute;
import csc472.depaul.edu.transit.Bus.BusStop;
import csc472.depaul.edu.transit.Bus.BusStopsFragment;
import csc472.depaul.edu.transit.metra.MetraDisplayFragment;
import csc472.depaul.edu.transit.metra.MetraHomeFragment;
import csc472.depaul.edu.transit.metra.MetraInfo;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        BusFragment.BusFragmentListener, BusDirectionFragment.BusDirectionFragmentListener, BusStopsFragment.BusStopsFragmentListener,
        MetraHomeFragment.MetraHomeListener {

    private DrawerLayout drawer;

    //gets connection status
    private ConnectivityReceiver connectivityReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        registerConnectivityReceiver();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            // TODO: Start map fragment instead of bus fragment
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new MapsFragment()).commit();
            navigationView.setCheckedItem(R.id.map);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch(menuItem.getItemId()) {
            case R.id.map:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new MapsFragment()).commit();
                break;
            case R.id.bus:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new BusFragment()).commit();
                break;
            case R.id.train:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new TrainFragment()).commit();
                break;
            case R.id.metra:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new MetraHomeFragment()).commit();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onBusClick(BusRoute busRoute) {
        BusDirectionFragment busDirectionFragment = BusDirectionFragment.newInstance(busRoute);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, busDirectionFragment);
        fragmentTransaction.addToBackStack(null); // This lets the user hit the back button and go back for another search
        fragmentTransaction.commit();
    }

    @Override
    public void onDirectionClick(BusRoute busRoute, String direction) {
        BusStopsFragment busStopsFragment = BusStopsFragment.newInstance(busRoute, direction);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, busStopsFragment);
        fragmentTransaction.addToBackStack(null); // This lets the user hit the back button and go back for another search
        fragmentTransaction.commit();
    }

    @Override
    public void onStopClick(BusRoute busRoute, BusStop busStop) {
        BusPredictionsFragment busPredictionsFragment = BusPredictionsFragment.newInstance(busRoute,busStop);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, busPredictionsFragment);
        fragmentTransaction.addToBackStack(null); // This lets the user hit the back button and go back for another search
        fragmentTransaction.commit();
    }

    @Override
    public void metraDisplayValues(MetraInfo input) {
        MetraDisplayFragment displayFragment = new MetraDisplayFragment();
        displayFragment.updateMetraValues(input);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, displayFragment);
        fragmentTransaction.addToBackStack(null); // This lets the user hit the back button and go back for another search
        fragmentTransaction.commit();
    }

    //gets connection status
    private ConnectivityReceiver getConnectivityReceiver() {
        if (connectivityReceiver == null)
            connectivityReceiver = new ConnectivityReceiver();

        return connectivityReceiver;
    }

    private void registerConnectivityReceiver() {
        try {
            // if (android.os.Build.VERSION.SDK_INT >= 26) {
            IntentFilter filter = new IntentFilter();
            filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            //filter.addAction(WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION);
            //filter.addAction(ConnectivityManager.EXTRA_NO_CONNECTIVITY);
            //filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
            //filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
            //filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
            registerReceiver(getConnectivityReceiver(), filter);

            Log.v("jeremy","registerConnectivityReceiver called");
        } catch (Exception e) {
            Log.e("jeremy", e.getMessage());
        }
    }

}
