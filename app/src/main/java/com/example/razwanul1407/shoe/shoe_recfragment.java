package com.example.razwanul1407.shoe;

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

public class shoe_recfragment extends Fragment implements SearchView.OnQueryTextListener {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    private SearchView shoesearch;
    RecyclerView shoe_recview;
    shoe_adapter adapter;
    public shoe_recfragment() {

    }

    public static shoe_recfragment newInstance(String param1, String param2) {
        shoe_recfragment fragment = new shoe_recfragment();
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

        View view= inflater.inflate(R.layout.fragment_shoe_recfragment, container, false);
        shoe_recview=(RecyclerView)view.findViewById(R.id.shoe_recview);
        shoe_recview.setLayoutManager(new LinearLayoutManager(getContext()));
        shoesearch=(SearchView)view.findViewById(R.id.shoe_search);
        shoesearch.setOnQueryTextListener(this);
        FirebaseRecyclerOptions<shoe_model> options =
                new FirebaseRecyclerOptions.Builder<shoe_model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Product_shoe"), shoe_model.class)
                        .build();

        adapter=new shoe_adapter(options);
        shoe_recview.setAdapter(adapter);
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
        searchfor_shoe(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        searchfor_shoe(newText);
        return false;
    }

    private void searchfor_shoe(String query) {
        FirebaseRecyclerOptions<shoe_model> optionsForSearch=
                new FirebaseRecyclerOptions.Builder<shoe_model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Product_shoe").orderByChild("name").startAt(query).endAt(query+"\uf8ff"),shoe_model.class)
                        .build();

        adapter = new shoe_adapter(optionsForSearch);
        adapter.startListening();
        shoe_recview.setAdapter(adapter);
    }
}