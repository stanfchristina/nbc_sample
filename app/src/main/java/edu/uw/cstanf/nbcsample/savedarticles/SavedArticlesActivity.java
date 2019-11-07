package edu.uw.cstanf.nbcsample.savedarticles;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import edu.uw.cstanf.nbcsample.R;

public class SavedArticlesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savedarticles);

        SavedArticlesViewModel viewModel = new SavedArticlesViewModel(this.getApplication());
        RecyclerView savedArticlesRecycler = findViewById(R.id.savedarticles_recycler);
        RecyclerView.LayoutManager savedArticlesManager = new LinearLayoutManager(this);
        RecyclerView.Adapter savedArticlesAdapter = new SavedArticlesAdapter(this.getApplication(), this, viewModel.getSavedArticles().getValue());

        savedArticlesRecycler.setAdapter(savedArticlesAdapter);
        savedArticlesRecycler.setLayoutManager(savedArticlesManager);

        viewModel.getSavedArticles().observe(this, ((SavedArticlesAdapter) savedArticlesAdapter)::setData);
    }
}
