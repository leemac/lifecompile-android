package com.tinycore.lifecompile.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.tinycore.lifecompile.R;
import com.tinycore.lifecompile.activities.HomeActivity;
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
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle(R.string.title_notes);

        LoadList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_note_list, container, false);

        _listView = (ListView) rootView.findViewById(R.id.listview_gardens);

        _listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Note gardenListItem = (Note) parent.getItemAtPosition(position);
                Bundle bundle = new Bundle();
                bundle.putInt("id", gardenListItem.Id);

                ((HomeActivity) getActivity()).displayView(4, bundle);
            }
        });

        _listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {

                Note gardenListItem = (Note) parent.getItemAtPosition(position);
                Bundle bundle = new Bundle();
                bundle.putInt("id", gardenListItem.Id);

                ((HomeActivity) getActivity()).displayView(1, bundle);

                return true;
            }
        });

        LoadList();
//
//        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(rootView.getContext(), NoteCreateActivity.class);
//                startActivity(intent);
//            }
//        });

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
                    int statusCode = response.code();

                    // handle request errors yourself
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
