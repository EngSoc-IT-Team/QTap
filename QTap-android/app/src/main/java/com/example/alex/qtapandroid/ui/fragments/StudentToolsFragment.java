package com.example.alex.qtapandroid.ui.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alex.qtapandroid.R;
import com.example.alex.qtapandroid.ICS.ParseICS;
import com.example.alex.qtapandroid.common.database.courses.CourseManager;

import java.util.List;


/**
 * Created by Carson on 02/12/2016.
 * Holds information pertinent to students
 */
public class StudentToolsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_student_tools, container, false);
        CardView solusCard = (CardView) v.findViewById(R.id.solus_card);
        CardView outlookCard = (CardView) v.findViewById(R.id.outlook_card);
        CardView onqCard = (CardView) v.findViewById(R.id.onq_card);

        solusCard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://www.my.queensu.ca/"));
                startActivity(intent);
            }
        });
        outlookCard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://login.microsoftonline.com/"));
                startActivity(intent);
            }
        });
        onqCard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://www.queensu.ca/"));
                startActivity(intent);
            }
        });
        return v;
    }
}