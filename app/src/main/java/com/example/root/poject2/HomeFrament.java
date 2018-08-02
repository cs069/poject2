package com.example.root.poject2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeFrament extends Fragment {

    CardView cardViewQuiz,cardViewPractise,cardView1,cardView2;
    Context context;
    DatabaseReference mNoticeRef;
    TextView textViewNotice;
    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View sd= inflater.inflate(R.layout.fragment_home,container,false);
        context=sd.getContext();
        cardViewQuiz=sd.findViewById(R.id.quiz);
        cardViewPractise=sd.findViewById(R.id.cardViewPractise);
        cardView1=sd.findViewById(R.id.cardView1);
        cardView2=sd.findViewById(R.id.cardView2);
        progressBar=sd.findViewById(R.id.progressbarNotice);
        progressBar.setVisibility(View.VISIBLE);
        textViewNotice=sd.findViewById(R.id.noticeView);
        textViewNotice.setSelected(true);
        progressBar.setVisibility(View.GONE);
        mNoticeRef= FirebaseDatabase.getInstance().getReference("Notice");
        mNoticeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String notice = dataSnapshot.getValue(String.class);
                // DataSnapshot q=dataSnapshot.child("Question");
                System.out.println(notice);
                textViewNotice.setText(notice);
                //System.out.println(dataSnapshot.getValue());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        cardViewQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cardQuizIntent = new Intent( getContext(),QuizActivity.class);
                getActivity().startActivity(cardQuizIntent);
            }
        });
        cardViewPractise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment someFragment = new PractiseFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, someFragment ); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();

            }
        });
        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Upcoming soon", Toast.LENGTH_SHORT).show();
            }
        });
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Upcoming soon", Toast.LENGTH_SHORT).show();
            }
        });
        return sd;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Home");
    }
    }
