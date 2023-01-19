package com.example.razwanul1407.camera;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.razwanul1407.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;


public class camera_recfragment extends Fragment implements SearchView.OnQueryTextListener {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private SearchView camerasearch;
    RecyclerView camera_recview;
    camera_adapter adapter;
    public camera_recfragment() {

    }


    public static camera_recfragment newInstance(String param1, String param2) {
        camera_recfragment fragment = new camera_recfragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_camera_recfragment, container, false);

        camera_recview=(RecyclerView)view.findViewById(R.id.camera_recview);
        camera_recview.setLayoutManager(new LinearLayoutManager(getContext()));
        camerasearch=(SearchView)view.findViewById(R.id.camera_search);
        camerasearch.setOnQueryTextListener(this);

        FirebaseRecyclerOptions<camera_model> options =
                new FirebaseRecyclerOptions.Builder<camera_model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Product_camera"), camera_model.class)
                        .build();
        adapter=new camera_adapter(options);
        camera_recview.setAdapter(adapter);
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        searchfor_camera(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

         searchfor_camera(newText);
        return false;
    }

    private void searchfor_camera(String query) {

        FirebaseRecyclerOptions<camera_model> optionsForSearch=
                new FirebaseRecyclerOptions.Builder<camera_model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Product_camera").orderByChild("name").startAt(query).endAt(query+"\uf8ff"),camera_model.class)
                        .build();

        adapter = new camera_adapter(optionsForSearch);
        adapter.startListening();
        camera_recview.setAdapter(adapter);
    }
}