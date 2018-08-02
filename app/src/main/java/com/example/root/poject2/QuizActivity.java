package com.example.root.poject2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

public class QuizActivity extends AppCompatActivity {
    TextView mScoreView,mQestion,playAgain,textViewPLus,textViewNotice,textViewLoading;
    Button mButtonChoice1,mButtonChoice2,mButtonChoice3,mButtonChoice4,mButtonQuite,mButtonShow,mButtonNext;
    ProgressBar progressBar,progressBar1,progressBar3;
    CardView cardViewQuestion,cardViewButton,cardViewNotice,cardViewScore;
    String mAnswer;
    int mScore=0, mQuestionNumber=0, questionNo=0,errorQuestion=1,correctQuestion=5;
    DatabaseReference mQuestionRef,mChoice1Ref,mChoice2Ref,mChoice3Ref,mChoice4Ref,mAnswerRef,mNoticeRef;
    MyCountDownTimer myCountDownTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        mQestion =findViewById(R.id.question);
        mButtonChoice1 =findViewById(R.id.choice1);
        mButtonChoice2 =findViewById(R.id.choice2);
        mButtonChoice3=findViewById(R.id.choice3);
        mButtonChoice4 =findViewById(R.id.choice4);
        mButtonQuite=findViewById(R.id.quit);
        mButtonShow=findViewById(R.id.show);
        mScoreView =findViewById(R.id.score);
        playAgain=findViewById(R.id.playAgain);
        textViewPLus=findViewById(R.id.textViewPlus);
        textViewNotice=findViewById(R.id.helpView);
        mButtonNext=findViewById(R.id.next);
        progressBar=findViewById(R.id.progressbar);
        progressBar1=findViewById(R.id.progressBar1);
        progressBar3=findViewById(R.id.progressbar3);
        cardViewQuestion=findViewById(R.id.cardviewQuestion);
        cardViewButton=findViewById(R.id.cardviewButton);
        cardViewNotice=findViewById(R.id.cardviewNotice);
        cardViewScore=findViewById(R.id.cardviewScore);
        textViewLoading=findViewById(R.id.textviewLoading);
        textViewNotice.setSelected(true);
        cardviewInvisible();

        textViewPLus.setVisibility(View.INVISIBLE);
        checkConnect();

        //firebase to get token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        System.out.println(refreshedToken);

        //checkConnect method has been call where connection is checked
        Toolbar toolbar = findViewById(R.id.toolbar);       // get the reference of Toolbar
        setSupportActionBar(toolbar);       // Setting/replace toolbar as the ActionBar
        getSupportActionBar().setTitle(" QUIZ ");       //set title of toolbar to HOME
        getSupportActionBar().setIcon(R.drawable.ic_arrow_back_black_24dp);      //set the icon of toolbar


        //When Button1
        mButtonChoice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mButtonChoice1.getText().equals(mAnswer)){
                    // updateScore(mScore);
                    updateQuestion();

                    correctSound();
                    changeColor();
                    mScore = mScore+5;
                    // mScore++;
                    visiblePLus();
                    updateScore(mScore);
                    myCountDownTimer.cancel();
                }
                else {
                    wrongSound();
                    updateQuestion();
                    vibrate();
                    errorAnswer();
                    delay();
                    myCountDownTimer.cancel();


                }
            }
        });
        //Button2
        progressBar.setVisibility(View.VISIBLE);
        mButtonChoice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myCountDownTimer.cancel();
                if (mButtonChoice2.getText().equals(mAnswer)){
                    updateQuestion();

                    mScore =mScore +5;
                    correctSound();
                    changeColor();
                    visiblePLus1();
                    updateScore(mScore);
                    myCountDownTimer.cancel();

                }
                else {
                    wrongSound();
                    updateQuestion();
                    vibrate();
                    errorAnswer();
                    delay();
                    myCountDownTimer.cancel();

                }
            }
        });
        //Button3

        mButtonChoice3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mButtonChoice3.getText().equals(mAnswer)){
                    updateQuestion();

                    mScore =mScore +5;
                    correctSound();
                    changeColor();
                    visiblePLus();

                    updateScore(mScore);
                    myCountDownTimer.cancel();

                }
                else {
                    wrongSound();
                    updateQuestion();
                    vibrate();
                    errorAnswer();
                    delay();
                    myCountDownTimer.cancel();

                }
            }
        });
        //Button4
        mButtonChoice4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mButtonChoice4.getText().equals(mAnswer)){
                    updateQuestion();

                    mScore =mScore +5;
                    correctSound();
                    changeColor();
                    visiblePLus1();
                    updateScore(mScore);
                    myCountDownTimer.cancel();

                }
                else {
                    wrongSound();
                    updateQuestion();
                    vibrate();
                    errorAnswer();
                    delay();
                    myCountDownTimer.cancel();

                }
            }
        });

        mButtonQuite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder =new AlertDialog.Builder(QuizActivity.this);
                builder.setTitle("Exit");
                builder.setMessage("Do u want to exit");
                builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                builder.setNegativeButton("Not Now", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog dialog=builder.create();
                dialog.show();
            }
        });
        mButtonShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder builder =new AlertDialog.Builder(QuizActivity.this);
                //  updateQuestion();
                builder.setMessage("Correct answer is: " +mAnswer);
                AlertDialog dialog=builder.create();
                dialog.show();

            }
        });
        mButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateQuestion();
                mButtonChoice1.setClickable(true);
                mButtonChoice1.setTextColor(Color.parseColor("#fffefe"));
                mButtonChoice2.setClickable(true);
                mButtonChoice2.setTextColor(Color.parseColor("#fffefe"));
                mButtonChoice3.setClickable(true);
                mButtonChoice3.setTextColor(Color.parseColor("#fffefe"));
                mButtonChoice4.setClickable(true);
                mButtonChoice4.setTextColor(Color.parseColor("#fffefe"));
            }
        });


    }
    public void updateQuestion(){
        invisible();
        progressBar3.setVisibility(View.VISIBLE);

        mQuestionRef= FirebaseDatabase.getInstance().getReference(+mQuestionNumber+"/Question");

        mQuestionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String question = dataSnapshot.getValue(String.class);
                // DataSnapshot q=dataSnapshot.child("Question");
                System.out.println(question);
                progressBar3.setVisibility(View.GONE);
                mQestion.setText(+questionNo+ ". " +question );
                //System.out.println(dataSnapshot.getValue());
                //i fqueation is empty do this
                if (question == null ){
                    mButtonChoice4.setVisibility(View.INVISIBLE);
                    mButtonChoice2.setVisibility(View.INVISIBLE);
                    mButtonChoice1.setVisibility(View.INVISIBLE);
                    mButtonChoice3.setVisibility(View.INVISIBLE);
                    mButtonShow.setVisibility(View.INVISIBLE);
                    mQestion.setVisibility(View.INVISIBLE);
                    playAgain.setVisibility(View.VISIBLE);
                    progressBar1.setVisibility(View.INVISIBLE);
                    mButtonNext.setVisibility(View.INVISIBLE);
                    final AlertDialog.Builder builder =new AlertDialog.Builder(QuizActivity.this);
                    builder.setTitle(" SCORE BOARD ");
                    //  updateQuestion();
                    builder.setMessage("Total score is:- \t " +mScore);
                    AlertDialog dialog=builder.create();
                    dialog.show();
                    playAgain.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent=new Intent(QuizActivity.this,QuizActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            updateQuestion();
                        }
                    });


                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        }); questionNo++;
        textViewInVisible();
        buttonInvisible();
        mChoice1Ref=FirebaseDatabase.getInstance().getReference(+mQuestionNumber+"/Choice1");
        mChoice1Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String choice1 = dataSnapshot.getValue(String.class);
                // DataSnapshot q=dataSnapshot.child("Question");
               // System.out.println(choice1);
                mButtonChoice1.setText(choice1);
               // myCountDownTimer.cancel();
                // System.out.println(dataSnapshot.getValue());


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mChoice2Ref=FirebaseDatabase.getInstance().getReference(+mQuestionNumber+"/Choice2");
        mChoice2Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String choice2 = dataSnapshot.getValue(String.class);
                // DataSnapshot q=dataSnapshot.child("Question");
              //  System.out.println(choice2);
                mButtonChoice2.setText(choice2);


                // System.out.println(dataSnapshot.getValue());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mChoice3Ref=FirebaseDatabase.getInstance().getReference(+mQuestionNumber+"/Choice3");
        mChoice3Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String choice3 = dataSnapshot.getValue(String.class);
                // DataSnapshot q=dataSnapshot.child("Question");
              //  System.out.println(choice3);
                mButtonChoice3.setText(choice3);

                //System.out.println(dataSnapshot.getValue());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mChoice4Ref=FirebaseDatabase.getInstance().getReference(+mQuestionNumber+"/Choice4");
        mChoice4Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String choice4 = dataSnapshot.getValue(String.class);
                // DataSnapshot q=dataSnapshot.child("Question");
               // System.out.println(choice4);

                mButtonChoice4.setText(choice4);
                // System.out.println(dataSnapshot.getValue());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mAnswerRef=FirebaseDatabase.getInstance().getReference(+mQuestionNumber+"/Answer");
        mAnswerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mAnswer  = dataSnapshot.getValue(String.class);
                progressBar.setVisibility(View.GONE);
               cardviewvisible(); //cardview has been visible
                progressBar.setVisibility(View.GONE);
                textViewLoading.setVisibility(View.GONE);
                startTimer();
                updateScore(mScore);
                progressBar1.setVisibility(View.VISIBLE);

                // DataSnapshot q=dataSnapshot.child("Question");
                System.out.println(mAnswer);
                // System.out.println(dataSnapshot.getValue());


            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });mQuestionNumber++;
        mNoticeRef=FirebaseDatabase.getInstance().getReference("Notice");
        mNoticeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String notice = dataSnapshot.getValue(String.class);
                // DataSnapshot q=dataSnapshot.child("Question");
               // System.out.println(notice);
                textViewNotice.setText(notice);
                //System.out.println(dataSnapshot.getValue());


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }
    public void updateScore(int score){
        mScoreView.setText(""+mScore);

    }
    public void errorAnswer(){
        Toast.makeText(this, "Incorrect answer", Toast.LENGTH_SHORT).show();
    }
    public void vibrate(){
        Vibrator vibrator;
        vibrator =(Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }
    public void correctSound(){
        MediaPlayer mp = MediaPlayer.create(this,R.raw.correct);
        mp.start();
    }
    public void wrongSound(){
        MediaPlayer mp1 = MediaPlayer.create(this,R.raw.wrong);
        mp1.start();
    }
    public void invisible(){
        playAgain.setVisibility(View.INVISIBLE);
        mButtonNext.setVisibility(View.INVISIBLE);


    }
    public void checkConnect(){

        ConnectivityManager connectivity = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivity.getActiveNetworkInfo();
        if (activeNetwork != null) { // connected to the internet
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                // connected to wifi


                //updateQuestion();
               AlertDialog();




            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                // connected to the mobile provider's data plan


                AlertDialog();
               //updateQuestion();


                // updateQuestion();
                //cardviewInvisible();
                //progressBar.setVisibility(View.VISIBLE);
                //textViewLoading.setVisibility(View.VISIBLE);

            }
        } else {
            // not connected to the internet
            cardviewvisible();
            progressBar.setVisibility(View.INVISIBLE);
            textViewLoading.setVisibility(View.INVISIBLE);
            mButtonChoice4.setVisibility(View.INVISIBLE);
            mButtonChoice2.setVisibility(View.INVISIBLE);
            mButtonChoice1.setVisibility(View.INVISIBLE);
            mButtonChoice3.setVisibility(View.INVISIBLE);
            mButtonShow.setVisibility(View.INVISIBLE);
            mButtonNext.setVisibility(View.INVISIBLE);
            playAgain.setVisibility(View.INVISIBLE);
            mButtonQuite.setVisibility(View.INVISIBLE);
            mButtonShow.setVisibility(View.INVISIBLE);
            mQestion.setTextColor(Color.parseColor("#FFE9041B"));
            mQestion.setText(" Not Connected to the Internet, \nConnect first");
            Toast.makeText(this, "Not connected to the Internet", Toast.LENGTH_LONG).show();

        }
    }
    public void changeColor() {
        mScoreView.setTextColor(Color.red(R.color.red));
        mScoreView.setTextSize(23);

    }
    public void visiblePLus() {
        textViewPLus.setVisibility(View.VISIBLE);
        textViewPLus.setText(+correctQuestion+" added");
        textViewPLus.setBackgroundColor(Color.parseColor("#FF0BD6BA"));

        textViewPLus.setTextColor(Color.parseColor("#FFFCFCFC"));


    }
    public void delay(){
        textViewPLus.setVisibility(View.VISIBLE);
        textViewPLus.setText( +errorQuestion+ " incorrect");
        textViewPLus.setTextColor(Color.parseColor("#FFFFFEFE"));
        textViewPLus.setBackgroundColor(Color.parseColor("#FFE70303"));
        errorQuestion++;


    }
    public void visiblePLus1(){
        textViewPLus.setVisibility(View.VISIBLE);
        textViewPLus.setText(+correctQuestion+ " added");
        //textViewPLus.setHintTextColor(Color.parseColor("#ff4081"));
        textViewPLus.setTextColor(Color.parseColor("#FFF9F7F7"));
        textViewPLus.setBackgroundColor(Color.parseColor("#FF033903"));

    }
    public void cardviewInvisible(){
        cardViewButton.setVisibility(View.INVISIBLE);
        cardViewScore.setVisibility(View.INVISIBLE);
        cardViewNotice.setVisibility(View.INVISIBLE);
        cardViewQuestion.setVisibility(View.INVISIBLE);
    }
    public void cardviewvisible(){
        cardViewButton.setVisibility(View.VISIBLE);
        cardViewScore.setVisibility(View.VISIBLE);
        cardViewNotice.setVisibility(View.VISIBLE);
        cardViewQuestion.setVisibility(View.VISIBLE);
    }
    public class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

            int progress = (int) (millisUntilFinished / 1000);

            progressBar1.setProgress(progressBar1.getMax() - progress);
        }

        @Override
        public void onFinish() {
            mButtonChoice1.setClickable(false);
            mButtonChoice1.setText("Time Out");
            mButtonChoice1.setTextColor(Color.parseColor("#fd000d"));
            mButtonChoice2.setClickable(false);
            mButtonChoice2.setText("Time Out");
            mButtonChoice2.setTextColor(Color.parseColor("#fd000d"));
            mButtonChoice3.setClickable(false);
            mButtonChoice3.setText("Time Out");
            mButtonChoice3.setTextColor(Color.parseColor("#fd000d"));
            mButtonChoice4.setClickable(false);
            mButtonChoice4.setText("Time Out");
            mButtonChoice4.setTextColor(Color.parseColor("#fd000d"));
            mButtonNext.setVisibility(View.VISIBLE);
        }
    }
    public void AlertDialog(){
        final AlertDialog.Builder builder =new AlertDialog.Builder(QuizActivity.this);
        builder.setTitle("Rules");
        builder.setMessage("Do u want to start");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                progressBar.setVisibility(View.VISIBLE);
                textViewLoading.setVisibility(View.VISIBLE);
                 updateQuestion();
            }
        });
        builder.setNegativeButton("Not Now", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               // dialogInterface.dismiss();
                finish();
            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();


    }
    public void textViewInVisible(){
        mQestion.setText("");

    }
    public void buttonInvisible(){
        mButtonChoice1.setText("");
        mButtonChoice2.setText("");
        mButtonChoice3.setText("");
        mButtonChoice4.setText("");
        progressBar1.setVisibility(View.INVISIBLE);
    }
    public void startTimer(){
    myCountDownTimer = new MyCountDownTimer(10000, 1000);
    myCountDownTimer.start();
}

}
