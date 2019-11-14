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
import edu.uw.cstanf.nbcsample.ui.NewsItemClickListener;
import edu.uw.cstanf.nbcsample.ui.VerticalItemDecoration;

/** Displays news items a user has saved. */
public class SavedArticlesFragment extends Fragment {
    private static final int VERTICAL_ITEM_PADDING = 16;

    private final NewsItemClickListener newsItemClickListener;
    private RecyclerView savedArticlesRecycler;

    public SavedArticlesFragment(NewsItemClickListener newsItemClickListener) {
        this.newsItemClickListener = newsItemClickListener;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_savedarticles, container, false);
        savedArticlesRecycler = root.findViewById(R.id.savedarticles_recycler);

        RecyclerView.LayoutManager savedArticlesManager = new LinearLayoutManager(getContext());
        savedArticlesRecycler.setLayoutManager(savedArticlesManager);
        savedArticlesRecycler.addItemDecoration(new VerticalItemDecoration(VERTICAL_ITEM_PADDING));

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SavedArticlesViewModel viewModel = ViewModelProviders.of(this).get(SavedArticlesViewModel.class);
        RecyclerView.Adapter savedArticlesAdapter = new SavedArticlesAdapter(getContext(), newsItemClickListener, new ArrayList<>());
        savedArticlesRecycler.setAdapter(savedArticlesAdapter);

        // Subscribe to changes in saved articles to update the adapter UI.
        viewModel.getSavedArticles().observe(getViewLifecycleOwner(), ((SavedArticlesAdapter) savedArticlesAdapter)::setData);
    }
}
