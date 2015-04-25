package com.ntubus.ntubus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;

    private Drawer.Result result = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        setSupportActionBar(mToolbar);

        result = new Drawer()
                .withActivity(this)
                .withToolbar(mToolbar)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.test).withIcon(GoogleMaterial.Icon.gmd_wb_sunny).withIdentifier(1).withCheckable(false),
                        new SecondaryDrawerItem().withName(R.string.test).withIcon(GoogleMaterial.Icon.gmd_wb_sunny).withIdentifier(1).withCheckable(false),
                        new SectionDrawerItem().withName(R.string.test)
                )
                .build();

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }
}
