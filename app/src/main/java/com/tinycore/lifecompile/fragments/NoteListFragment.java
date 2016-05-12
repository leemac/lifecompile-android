package com.tinycore.lifecompile.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.tinycore.lifecompile.R;
import com.tinycore.lifecompile.activities.EditItemActivity;
import com.tinycore.lifecompile.activities.HomeActivity;
import com.tinycore.lifecompile.activities.NewItemActivity;
import com.tinycore.lifecompile.adapters.NoteListAdapter;
import com.tinycore.lifecompile.models.Note;
import com.tinycore.lifecompile.network.LifeCompileServiceHelper;
import com.tinycore.lifecompile.services.LifeCompileService;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoteListFragment extends Fragment {
    private ListView _listView;
    private LifeCompileService _gardenrrService;
    private HomeActivity _rootActivity;

    public NoteListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _gardenrrService = LifeCompileServiceHelper.GetService();
    }

    @Override
    public void onResume() {
        super.onResume();

        _rootActivity =  ((HomeActivity)getActivity());

        _rootActivity.getSupportActionBar().setTitle(R.string.title_notes);

        LoadList();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId()==R.id.listview_gardens) {
            MenuInflater inflater = _rootActivity.getMenuInflater();
            inflater.inflate(R.menu.note_list_menu, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Note note = (Note) _listView.getItemAtPosition(info.position);

        switch(item.getItemId()) {
            case R.id.edit:

                Bundle bundle = new Bundle();
                bundle.putInt("id", note.Id);

                Intent intent = new Intent(_rootActivity, EditItemActivity.class);
                intent.putExtras(bundle);

                startActivity(intent);

                return true;
            case R.id.delete:

                Call<Note> listGardenCall = _gardenrrService.deleteNote(note.Id);

                final Context context = this.getActivity();

                listGardenCall.enqueue(new Callback<Note>() {
                    @Override
                    public void onResponse(Call<Note> call, Response<Note> response) {
                        if (response.isSuccessful()) {
                            LoadList();
                        } else {
                            int statusCode = response.code();

                            // handle request errors yourself
                            ResponseBody errorBody = response.errorBody();
                            Toast.makeText(context, errorBody.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Note> call, Throwable t) {
                        Toast.makeText(context, "Failure! " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_note_list, container, false);

        _listView = (ListView) rootView.findViewById(R.id.listview_gardens);

        registerForContextMenu(_listView);

        _listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Note gardenListItem = (Note) parent.getItemAtPosition(position);
                Bundle bundle = new Bundle();
                bundle.putInt("id", gardenListItem.Id);

                ((HomeActivity) getActivity()).displayView(4, bundle);
            }
        });

        LoadList();

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(rootView.getContext(), NewItemActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

    private void LoadList(){
        Call<ArrayList<Note>> listGardensCall = _gardenrrService.getNotes();

        final Context context = this.getActivity();

        listGardensCall.enqueue(new Callback<ArrayList<Note>>() {
            @Override
            public void onResponse(Call<ArrayList<Note>> call, Response<ArrayList<Note>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Note> garden = response.body();

                    NoteListAdapter arrayAdapter = new NoteListAdapter(context, new ArrayList<>(garden));

                    _listView.setAdapter(arrayAdapter);
                } else {
                    ResponseBody errorBody = response.errorBody();
                    Toast.makeText(context, errorBody.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Note>> call, Throwable t) {
                Toast.makeText(context, "Failure! " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
