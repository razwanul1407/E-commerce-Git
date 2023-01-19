package com.example.razwanul1407.sports;

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


public class sports_recfragment extends Fragment implements SearchView.OnQueryTextListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private SearchView sportssearch;
    RecyclerView sports_recview;
    sports_adapter adapter;
    public sports_recfragment() {

    }


    public static sports_recfragment newInstance(String param1, String param2) {
        sports_recfragment fragment = new sports_recfragment();
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

        View view= inflater.inflate(R.layout.fragment_sports_recfragment, container, false);
        sports_recview=(RecyclerView)view.findViewById(R.id.sports_recview);
        sports_recview.setLayoutManager(new LinearLayoutManager(getContext()));
        sportssearch=(SearchView)view.findViewById(R.id.sports_search);
        sportssearch.setOnQueryTextListener(this);

        FirebaseRecyclerOptions<sports_model> options =
                new FirebaseRecyclerOptions.Builder<sports_model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Product_sports"), sports_model.class)
                        .build();

        adapter=new sports_adapter(options);
        sports_recview.setAdapter(adapter);
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
        searchfor_sports(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        searchfor_sports(newText);
        return false;
    }

    private void searchfor_sports(String query) {
        FirebaseRecyclerOptions<sports_model> optionsForSearch=
                new FirebaseRecyclerOptions.Builder<sports_model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Product_sports").orderByChild("name").startAt(query).endAt(query+"\uf8ff"),sports_model.class)
                        .build();

        adapter = new sports_adapter(optionsForSearch);
        adapter.startListening();
        sports_recview.setAdapter(adapter);
    }
}