package com.tinycore.lifecompile.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.tinycore.lifecompile.R;
import com.tinycore.lifecompile.activities.HomeActivity;
import com.tinycore.lifecompile.network.LifeCompileServiceHelper;
import com.tinycore.lifecompile.services.LifeCompileService;

public class NoteEditorFragment extends Fragment {
    private LifeCompileService _gardenrrService;

    public NoteEditorFragment() {
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
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle(R.string.title_note_edit);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_note_editor, container, false);

        return rootView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
