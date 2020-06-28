package com.ravimishra.tradzhub.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ravimishra.tradzhub.Adapter.TabRecyclerViewAdapter;
import com.ravimishra.tradzhub.Model.TabRecyclerViewModel;
import com.ravimishra.tradzhub.R;

import java.util.ArrayList;
import java.util.List;

public class FlashDealFragment extends Fragment {

    RecyclerView recyclerView;
    List<TabRecyclerViewModel> tabRecyclerViewModel = new ArrayList<>();

    public FlashDealFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_just_in, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);

        // recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        TabRecyclerViewAdapter adapter = new TabRecyclerViewAdapter(getActivity(), tabRecyclerViewModel);
        recyclerView.setAdapter(adapter);
        tabRecyclerViewModel.clear();
        tabRecyclerViewModel.add(new TabRecyclerViewModel("iPhone 8", "$ 199", "(20%off)", "4.1", "img1"));
        tabRecyclerViewModel.add(new TabRecyclerViewModel("Macbook Air", "$ 499", "(20%off)", "4.1", "img1"));
        tabRecyclerViewModel.add(new TabRecyclerViewModel("Men's Shirt", "$ 49", "(20%off)", "4.1", "img1"));
        tabRecyclerViewModel.add(new TabRecyclerViewModel("Women's Top", "$ 99", "(20%off)", "4.1", "img1"));
        tabRecyclerViewModel.add(new TabRecyclerViewModel("Whirlpool Washing Machine", "$ 149", "(20%off)", "4.1", "img1"));
        tabRecyclerViewModel.add(new TabRecyclerViewModel("Womens's Jacket", "$ 499", "(20%off)", "4.1", "img1"));

        return view;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
