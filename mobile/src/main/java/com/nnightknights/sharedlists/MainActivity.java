package com.nnightknights.sharedlists;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.EntypoModule;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.IoniconsModule;
import com.joanzapata.iconify.fonts.MaterialCommunityModule;
import com.joanzapata.iconify.fonts.MaterialModule;
import com.joanzapata.iconify.fonts.MeteoconsModule;
import com.joanzapata.iconify.fonts.SimpleLineIconsModule;
import com.joanzapata.iconify.fonts.TypiconsModule;
import com.joanzapata.iconify.fonts.WeathericonsModule;
import com.nnightknights.sharedlists.list.database.DaggerDatabaseAccessComponent;
import com.nnightknights.sharedlists.list.database.DatabaseAccessComponent;
import com.nnightknights.sharedlists.list.database.DatabaseAccessModule;
import com.nnightknights.sharedlists.list.database.DatabaseActions;
import com.nnightknights.sharedlists.list.database.DatabaseReader;
import com.nnightknights.sharedlists.list.database.entities.ListTitleFavoritePinnedTuple;
import com.nnightknights.sharedlists.list.navigation_drawer.NavigationDrawerItemSelectedListener;
import com.nnightknights.sharedlists.list.navigation_drawer.NavigationMenuBuilder;

public class MainActivity extends AppCompatActivity {
    private DatabaseReader databaseReader = new DatabaseReader();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        instantiateIconify();

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        instantiateFloatingActionButton();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        instantiateNavigationDrawerMenuItems();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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

    public void instantiateNavigationDrawerMenuItems() {
        NavigationView navigationView = findViewById(R.id.nav_view);

        DatabaseAccessComponent databaseAccessComponent =
                DaggerDatabaseAccessComponent.builder().databaseAccessModule(new DatabaseAccessModule(getApplicationContext())).build();
        databaseAccessComponent.inject(databaseReader);

        NavigationMenuBuilder navigationMenuBuilder = new NavigationMenuBuilder(navigationView, databaseReader);
        NavigationDrawerItemSelectedListener navigationDrawerItemSelectedListener = new NavigationDrawerItemSelectedListener(this);
        navigationMenuBuilder.buildMenu(navigationDrawerItemSelectedListener);
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

    private void instantiateIconify(){
        Iconify.with(new FontAwesomeModule())
                .with(new EntypoModule())
                .with(new TypiconsModule())
                .with(new MaterialModule())
                .with(new MaterialCommunityModule())
                .with(new MeteoconsModule())
                .with(new WeathericonsModule())
                .with(new SimpleLineIconsModule())
                .with(new IoniconsModule());
    }

    private void instantiateFloatingActionButton(){
        FloatingActionButton floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setBackgroundTintList(getResources().getColorStateList(R.color.colorAccent, getTheme()));
        floatingActionButton.setImageDrawable(new IconDrawable(getApplicationContext(), FontAwesomeIcons.fa_plus).colorRes(R.color.colorPrimary));
    }
}
