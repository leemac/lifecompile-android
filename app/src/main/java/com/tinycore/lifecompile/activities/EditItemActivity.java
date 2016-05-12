package com.tinycore.lifecompile.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tinycore.lifecompile.R;
import com.tinycore.lifecompile.models.Note;
import com.tinycore.lifecompile.models.Tag;
import com.tinycore.lifecompile.network.LifeCompileServiceHelper;
import com.tinycore.lifecompile.services.LifeCompileService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditItemActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private LifeCompileService _lifcompileService;
    private int _noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        _lifcompileService = LifeCompileServiceHelper.GetService();
        _noteId = getIntent().getExtras().getInt("id");

        final TextView contentTextView = (TextView) this.findViewById(R.id.editTextNoteContent);
        final TextView tagsTextView = (TextView) this.findViewById(R.id.editTextNoteTags);

        final Call<Note> addNoteCall = _lifcompileService.getNote(_noteId);
        final Context context = this;

        addNoteCall.enqueue(new Callback<Note>() {

            @Override
            public void onResponse(Call<Note> call, Response<Note> response) {
                Log.d("Login", response.toString());

                if (response.isSuccessful()) {
                    Note note = response.body();
                    contentTextView.setText(note.Content);

                    StringBuilder sb = new StringBuilder();
                    String delim = "";
                    for (Tag i : note.TagsFull) {
                        sb.append(i.Name).append(delim);
                        delim = ",";
                    }

                    if(sb.length() > 0)
                        sb.substring(0, sb.length() -1);

                    tagsTextView.setText(sb.toString());
                } else {
                    ResponseBody errorBody = response.errorBody();
                    Log.d("Edit Note", errorBody.toString());
                    Toast.makeText(context, errorBody.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Note> call, Throwable t) {
                Log.d("Edit Note Failed", t.getMessage());
                Toast.makeText(context, "Failure! " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void saveClick(View view) {
        TextView contentTextView = (TextView) this.findViewById(R.id.editTextNoteContent);
        TextView tagsTextView = (TextView) this.findViewById(R.id.editTextNoteTags);

        final Call<Note> addNoteCall = _lifcompileService.updateNote(_noteId, contentTextView.getText().toString(), tagsTextView.getText().toString());
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
