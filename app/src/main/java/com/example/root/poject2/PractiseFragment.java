package com.example.root.poject2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PractiseFragment extends Fragment {
    DatabaseReference  mQuestion1Ref,mQuestion2Ref,mQuestion3Ref,mAnswerRef;
    TextView textViewQ1,textViewQ2,textViewQ3,textViewA1,textViewA2,textViewA3;
    int mQuestionNumber=0,mQuestionNo=1,mAnwerNo=0;
    Button buttonNext,buttonReNext;
    ProgressBar progressBar;
    CardView cardViewHome;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.practice, container, false);
        textViewQ1=rootView.findViewById(R.id.textViewQuestion1);
        textViewQ2=rootView.findViewById(R.id.textViewQuestion2);
        textViewQ3=rootView.findViewById(R.id.textViewQuestion3);
        textViewA1=rootView.findViewById(R.id.textViewAnswer1);
        textViewA2=rootView.findViewById(R.id.textViewAnswer2);
        cardViewHome=rootView.findViewById(R.id.cardViewHome);
        textViewA3=rootView.findViewById(R.id.textViewAnswer3);
        progressBar=rootView.findViewById(R.id.progressbar2);
        buttonReNext=rootView.findViewById(R.id.ButtonReNext);
        buttonReNext.setVisibility(View.INVISIBLE);
        buttonNext=rootView.findViewById(R.id.ButtonNext);
        progressBar.setVisibility(View.VISIBLE);
        checkConnect();
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            updateQuestion();

            }
        });

            return rootView;


    }
    public void updateQuestion(){

        mQuestion1Ref = FirebaseDatabase.getInstance().getReference(+mQuestionNumber+"/Question");
        mQuestion1Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String question = dataSnapshot.getValue(String.class);
               // String answer = dataSnapshot.getValue(String.class);

                // DataSnapshot q=dataSnapshot.child("Question");
                //System.out.println(q);
                textViewQ1.setText( +mQuestionNo+". " +question );
          //  DatabaseReference d= FirebaseDatabase.getInstance().getReference("Answer");
           // textViewA1.setText((CharSequence) d);

               // textViewA1.setText((mAnswerRef.getRef());
                //System.out.println(dataSnapshot.getValue());
                mQuestionNo++;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });mQuestionNumber++;

        mQuestion2Ref = FirebaseDatabase.getInstance().getReference(+mQuestionNumber+"/Question");
        mQuestion2Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String question = dataSnapshot.getValue(String.class);
                // DataSnapshot q=dataSnapshot.child("Question");
                //System.out.println(q);
                textViewQ2.setText( +mQuestionNo+". " +question );
                //System.out.println(dataSnapshot.getValue());
                mQuestionNo++;
                if (question==null){
                    nullQuestion();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });mQuestionNumber++;

        //QuestionView 3
        mQuestion3Ref = FirebaseDatabase.getInstance().getReference(+mQuestionNumber+"/Question");
        mQuestion3Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String question = dataSnapshot.getValue(String.class);
                // DataSnapshot q=dataSnapshot.child("Question");
                //System.out.println(q);
                textViewQ3.setText( +mQuestionNo+". " +question );
                //System.out.println(dataSnapshot.getValue());
                mQuestionNo++;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });mQuestionNumber++;
        int i;

        mAnswerRef = FirebaseDatabase.getInstance().getReference(+mAnwerNo+"/Answer");
        mAnswerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String answer = dataSnapshot.getValue(String.class);
                // DataSnapshot q=dataSnapshot.child("Question");
                //System.out.println(q);
                textViewA1.setText( answer );
                //System.out.println(dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });mAnwerNo++;
        mAnswerRef = FirebaseDatabase.getInstance().getReference(+mAnwerNo+"/Answer");
        mAnswerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String answer = dataSnapshot.getValue(String.class);
                // DataSnapshot q=dataSnapshot.child("Question");
                //System.out.println(q);
                textViewA2.setText( answer );
                //System.out.println(dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });mAnwerNo++;
        mAnswerRef = FirebaseDatabase.getInstance().getReference(+mAnwerNo+"/Answer");
        mAnswerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String answer = dataSnapshot.getValue(String.class);
                // DataSnapshot q=dataSnapshot.child("Question");
                //System.out.println(q);
                textViewA3.setText( answer );
                progressBar.setVisibility(View.GONE);
                //System.out.println(dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });mAnwerNo++;
    }
    public void nullQuestion() {
        buttonNext.setText("No more questions");
        buttonNext.setFocusable(false);
        buttonNext.setClickable(false);
        buttonReNext.setVisibility(View.VISIBLE);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "No more questions", Toast.LENGTH_SHORT).show();
            }
        });
        buttonReNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment someFragment = new HomeFrament();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, someFragment); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();


            }
        });
    }
        @Override
        public void onViewCreated (View view, @Nullable Bundle savedInstanceState){
            super.onViewCreated(view, savedInstanceState);
            getActivity().setTitle("Practise");

        }


    public void checkConnect(){

        ConnectivityManager connectivity = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivity.getActiveNetworkInfo();
        if (activeNetwork != null) { // connected to the internet
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                // connected to wifi
                updateQuestion();


            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                // connected to the mobile provider's data plan

                updateQuestion();

            }
             } else {

        cardViewHome.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.GONE);
            buttonNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(), "Not Connected to the internet", Toast.LENGTH_LONG).show();

                }
            });
            Toast.makeText(getContext(), "Not Connected to the internet", Toast.LENGTH_LONG).show();

        }
        }

}


