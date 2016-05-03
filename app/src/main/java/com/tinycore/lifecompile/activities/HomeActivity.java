package com.tinycore.lifecompile.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.tinycore.lifecompile.R;
import com.tinycore.lifecompile.fragments.NoteListFragment;
import com.tinycore.lifecompile.services.LifeCompileService;

public class HomeActivity extends AppCompatActivity {

    private LifeCompileService _lifcompileService;
    private ListView _listView;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle bundle = this.getIntent().getExtras();

        if(bundle != null && bundle.getInt("loadFragment") != 0)
        {
            displayView(bundle.getInt("loadFragment"), bundle);
        }
        else
        {
            displayView(0, null);
        }
    }

    public void displayView(int position, Bundle bundle) {
        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = new NoteListFragment();
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
