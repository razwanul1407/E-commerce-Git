package com.example.razwanul1407.shirt;

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


public class shirt_recfragment extends Fragment implements SearchView.OnQueryTextListener {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    private SearchView shirtsearch;
    RecyclerView shirt_recview;
    shirt_adapter adapter;
    public shirt_recfragment() {

    }


    public static shirt_recfragment newInstance(String param1, String param2) {
        shirt_recfragment fragment = new shirt_recfragment();
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

       View view= inflater.inflate(R.layout.fragment_shirt_recfragment, container, false);

       shirt_recview=(RecyclerView)view.findViewById(R.id.shirt_recview);
       shirt_recview.setLayoutManager(new LinearLayoutManager(getContext()));
       shirtsearch=(SearchView)view.findViewById(R.id.shirt_search);
       shirtsearch.setOnQueryTextListener(this);

        FirebaseRecyclerOptions<shirt_model> options =
                new FirebaseRecyclerOptions.Builder<shirt_model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Product_shirt"), shirt_model.class)
                        .build();

        adapter=new shirt_adapter(options);
        shirt_recview.setAdapter(adapter);
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
        searchfor_shirt(query);
        return false;
    }


    @Override
    public boolean onQueryTextChange(String newText) {
        searchfor_shirt(newText);
        return false;
    }

    private void searchfor_shirt(String query) {
        FirebaseRecyclerOptions<shirt_model> optionsForSearch=
                new FirebaseRecyclerOptions.Builder<shirt_model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Product_shirt").orderByChild("name").startAt(query).endAt(query+"\uf8ff"),shirt_model.class)
                        .build();

        adapter = new shirt_adapter(optionsForSearch);
        adapter.startListening();
        shirt_recview.setAdapter(adapter);
    }

}