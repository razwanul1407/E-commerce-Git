package com.example.razwanul1407.watch;

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


public class watch_recfragment extends Fragment implements SearchView.OnQueryTextListener {

  private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private SearchView watchsearch;
    RecyclerView watch_recview;
    watch_adapter adapter;
    public watch_recfragment() {

    }


    public static watch_recfragment newInstance(String param1, String param2) {
        watch_recfragment fragment = new watch_recfragment();
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

        View view= inflater.inflate(R.layout.fragment_watch_recfragment, container, false);
        watch_recview=(RecyclerView)view.findViewById(R.id.watch_recview);
        watch_recview.setLayoutManager(new LinearLayoutManager(getContext()));
        watchsearch=(SearchView)view.findViewById(R.id.watch_search);
        watchsearch.setOnQueryTextListener(this);
        FirebaseRecyclerOptions<watch_model> options =
                new FirebaseRecyclerOptions.Builder<watch_model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Product_watch"), watch_model.class)
                        .build();

        adapter=new watch_adapter(options);
        watch_recview.setAdapter(adapter);
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
        searchfor_watch(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        searchfor_watch(newText);
        return false;
    }

    private void searchfor_watch(String query) {
        FirebaseRecyclerOptions<watch_model> optionsForSearch=
                new FirebaseRecyclerOptions.Builder<watch_model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Product_watch").orderByChild("name").startAt(query).endAt(query+"\uf8ff"),watch_model.class)
                        .build();

        adapter = new watch_adapter(optionsForSearch);
        adapter.startListening();
        watch_recview.setAdapter(adapter);

    }
}