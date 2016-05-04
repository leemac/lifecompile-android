package com.tinycore.lifecompile.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tinycore.lifecompile.R;
import com.tinycore.lifecompile.models.Note;
import com.tinycore.lifecompile.models.Token;
import com.tinycore.lifecompile.network.LifeCompileServiceHelper;
import com.tinycore.lifecompile.services.LifeCompileService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewItemActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private LifeCompileService _lifcompileService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        _lifcompileService = LifeCompileServiceHelper.GetService();
    }

    public void saveClick(View view) {
        TextView contentTextView = (TextView) this.findViewById(R.id.editTextNoteContent);

        final Call<Note> addNoteCall = _lifcompileService.createNote(contentTextView.getText().toString());
        final Context context = this;

        addNoteCall.enqueue(new Callback<Note>() {

            @Override
            public void onResponse(Call<Note> call, Response<Note> response) {
                Log.d("Login", response.toString());

                if (response.isSuccessful()) {
                    finish();
                } else {
                    ResponseBody errorBody = response.errorBody();
                    Log.d("Login", errorBody.toString());
                    Toast.makeText(context, errorBody.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Note> call, Throwable t) {
                Log.d("Login Failed", t.getMessage());
                Toast.makeText(context, "Failure! " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void cancelClick(View view) {
        finish();
    }
}
