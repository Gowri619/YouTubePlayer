package com.example.kotlinsample.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kotlinsample.R;
import com.example.kotlinsample.adapter.ReportsAdapter;
import com.example.kotlinsample.model.ReportModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.BindView;

public class ReportFragment extends BaseFragment {

    private static final String FRAGMENT_NAME = "Report";

    @BindView(R.id.recycler_view)
    ExpandableListView recyclerView;

    DatabaseReference databaseReference;
    private ArrayList<ReportModel> reportModels = new ArrayList<>();
    private ReportsAdapter reportsAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injector().inject(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("items");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                reportModels.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ReportModel reportModel = dataSnapshot.getValue(ReportModel.class);
                    reportModels.add(reportModel);
                }
                reportsAdapter = new ReportsAdapter(requireContext(), reportModels);
                recyclerView.setAdapter(reportsAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static ReportFragment newInstance() {
        return new ReportFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_report;
    }

    @Override
    public String getFragmentName() {
        return FRAGMENT_NAME;
    }

    @Override
    public void setTitle() {

    }

    @Override
    public void dispose() {

    }
}