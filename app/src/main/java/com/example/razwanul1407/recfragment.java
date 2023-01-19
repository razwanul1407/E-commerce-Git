package com.example.razwanul1407;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;


public class recfragment extends Fragment implements SearchView.OnQueryTextListener {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    private SearchView devicesearch;
    RecyclerView recView;
    myadapter adapter;

    public recfragment() {

    }


    public static recfragment newInstance(String param1, String param2) {
        recfragment fragment = new recfragment();
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

        View view = inflater.inflate(R.layout.fragment_recfragment, container, false);

        recView = (RecyclerView) view.findViewById(R.id.review);
        recView.setLayoutManager(new LinearLayoutManager(getContext()));

        devicesearch=(SearchView)view.findViewById(R.id.device_search);
        devicesearch.setOnQueryTextListener(this);

        FirebaseRecyclerOptions<model> options =
                new FirebaseRecyclerOptions.Builder<model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("students"), model.class)
                        .build();
        adapter = new myadapter(options);
        recView.setAdapter(adapter);


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
        searchfor_device(query);
        return false;
    }



    @Override
    public boolean onQueryTextChange(String newText) {
        searchfor_device(newText);
        return false;
    }

    private void searchfor_device(String query) {

        FirebaseRecyclerOptions<model> optionsForSearch=
                new FirebaseRecyclerOptions.Builder<model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("students").orderByChild("name").startAt(query).endAt(query+"\uf8ff"),model.class)
                        .build();

        adapter = new myadapter(optionsForSearch);
        adapter.startListening();
        recView.setAdapter(adapter);
    }
}

 /*
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        inflater.inflate(R.menu.searchmenu,menu);
        MenuItem item=menu.findItem(R.id.search).setVisible(false);
        SearchView searchView=(SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                processsearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                processsearch(query);
                return false;
            }
        });

       super.onCreateOptionsMenu(menu, inflater);
    }



    private void processsearch(String query)
    {
        FirebaseRecyclerOptions<model> options =
                new FirebaseRecyclerOptions.Builder<model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("students").orderByChild("course").startAt(query).endAt(query+"\uf8ff"), model.class)
                        .build();
        adapter= new myadapter(options);
        adapter.startListening();
        recView.setAdapter(adapter);
    }


  */

