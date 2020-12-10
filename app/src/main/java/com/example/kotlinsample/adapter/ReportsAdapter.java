package com.example.kotlinsample.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.kotlinsample.R;
import com.example.kotlinsample.model.ReportModel;

import java.util.ArrayList;

public class ReportsAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<ReportModel> reportModels;

    public ReportsAdapter(Context context, ArrayList<ReportModel> reportModels) {
        this.context = context;
        this.reportModels = reportModels;
    }

    @Override
    public int getGroupCount() {
        return reportModels.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return reportModels.get(groupPosition).getDates().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return reportModels.get(groupPosition).getPlace();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return reportModels.get(groupPosition).getDates().get(childPosition).getValue();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.expandable_list_group, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.expandable_list_item, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);

        txtListChild.setText(childText);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
