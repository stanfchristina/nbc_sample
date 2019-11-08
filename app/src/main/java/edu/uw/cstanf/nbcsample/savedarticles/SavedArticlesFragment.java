package edu.uw.cstanf.nbcsample.savedarticles;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.uw.cstanf.nbcsample.R;

/** Displays articles a user has saved. */
public class SavedArticlesFragment extends Fragment {
    private RecyclerView savedArticlesRecycler;

    public static SavedArticlesFragment newInstance() {
        return new SavedArticlesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_savedarticles, container, false);
        savedArticlesRecycler = root.findViewById(R.id.savedarticles_recycler);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SavedArticlesViewModel viewModel = ViewModelProviders.of(this).get(SavedArticlesViewModel.class);

        RecyclerView.LayoutManager savedArticlesManager = new LinearLayoutManager(this.getContext());
        RecyclerView.Adapter savedArticlesAdapter = new SavedArticlesAdapter(this.getActivity().getApplication(), this.getContext(), new ArrayList<>());

        savedArticlesRecycler.setAdapter(savedArticlesAdapter);
        savedArticlesRecycler.setLayoutManager(savedArticlesManager);

        viewModel.getSavedArticles().observe(getViewLifecycleOwner(), ((SavedArticlesAdapter) savedArticlesAdapter)::setData);
    }
}
