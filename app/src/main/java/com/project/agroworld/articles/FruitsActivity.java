package com.project.agroworld.articles;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.project.agroworld.R;
import com.project.agroworld.articles.model.TechniquesResponse;
import com.project.agroworld.ui.AgroViewModel;

import java.util.ArrayList;

public class FruitsActivity extends AppCompatActivity {

    private ArrayList<TechniquesResponse> techniquesResponseArrayList = new ArrayList<>();
    private AgroViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruits);
        viewModel = ViewModelProviders.of(this).get(AgroViewModel.class);

        viewModel.getFruitsList().observe(this, fruitsResponses -> {
            if (!fruitsResponses.isEmpty()){
                techniquesResponseArrayList.clear();
                techniquesResponseArrayList.addAll(fruitsResponses);
            }
            Log.d("fruitsResponseArrayList", String.valueOf(techniquesResponseArrayList.size()));
        });

    }

}