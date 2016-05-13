package com.tinycore.lifecompile.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.tinycore.lifecompile.R;
import com.tinycore.lifecompile.fragments.NoteListFragment;
import com.tinycore.lifecompile.fragments.TagListFragment;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ArrayList<String> mMenuItems;
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ConfigureDrawer();

        Bundle bundle = this.getIntent().getExtras();

        if (bundle != null && bundle.getInt("loadFragment") != 0) {
            displayView(bundle.getInt("loadFragment"), bundle);
        } else {
            displayView(0, null);
        }
    }

    private void ConfigureDrawer() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mMenuItems = new ArrayList<>();

        mMenuItems.add(getString(R.string.drawer_home));
        mMenuItems.add(getString(R.string.drawer_tags));
        mMenuItems.add(getString(R.string.drawer_logoff));

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mMenuItems));

        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,        /* DrawerLayout object */
                mToolbar,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle("The title");
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("The drawer title");
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            String chosenText = mMenuItems.get(position);

            switch (chosenText){
                case "Home":
                    displayView(0, new Bundle());
                    break;
                case "Tags":
                    displayView(1, new Bundle());
                    break;
                case "Logoff":
                    SharedPreferences settings = PreferenceManager
                            .getDefaultSharedPreferences(view.getContext());
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("token", "");
                    editor.apply();
                    break;
            }

            mDrawerLayout.closeDrawer(mDrawerList);
        }
    }

    public void displayView(int position, Bundle bundle) {
        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = new NoteListFragment();
                break;
            case 1:
                fragment = new TagListFragment();
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

}
