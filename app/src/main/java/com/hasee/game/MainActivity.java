package com.hasee.game;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btnRestart;
    int score1, score2;
    TextView txtScore1, txtScore2;
    TextView headerText, txtHelp, txtAbout;

    int PLAYER_O = 0;
    int PLAYER_X = 1;

    int activePlayer = PLAYER_O;

    int[] filledPos = {-1, -1, -1, -1, -1, -1, -1, -1, -1};

    boolean isGameActive = true;
    int countdown = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        headerText = findViewById(R.id.status);
        headerText.setText("");
        btnRestart = findViewById(R.id.resetButton);
        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (countdown==0){
                    Toast.makeText(getApplicationContext(), "Nothing!!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Reset game ???")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                restartGame();
                                score1=0;
                                score2=0;
                                txtScore1.setText(String.valueOf(score1));
                                txtScore2.setText(String.valueOf(score2));
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
            }
        });

        txtScore1 = findViewById(R.id.txtScore1);
        txtScore2 = findViewById(R.id.txtScore2);
        txtHelp = findViewById(R.id.txtHelp);
        txtHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogHelp();
            }
        });
        txtAbout = findViewById(R.id.txtAbout);
        txtAbout.setText("<< About");
        txtAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAbout();
            }
        });


        btn0 = findViewById(R.id.btn1);
        btn1 = findViewById(R.id.btn2);
        btn2 = findViewById(R.id.btn3);
        btn3 = findViewById(R.id.btn4);
        btn4 = findViewById(R.id.btn5);
        btn5 = findViewById(R.id.btn6);
        btn6 = findViewById(R.id.btn7);
        btn7 = findViewById(R.id.btn8);
        btn8 = findViewById(R.id.btn9);

        btn0.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);


    }

    private void showDialogAbout() {
        new AlertDialog.Builder(this)
                .setTitle("Của Hà làm nhé !")
                .setCancelable(false)
                .setPositiveButton("Dạ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    @SuppressLint("NewApi")
    @Override
    public void onClick(View v) {
        // logic for button press

        if (!isGameActive)
            return;

        Button clickedBtn = findViewById(v.getId());
        int clickedTag = Integer.parseInt(v.getTag().toString());

        if (filledPos[clickedTag] != -1) {
            return;
        }

        filledPos[clickedTag] = activePlayer;


        if (activePlayer == PLAYER_O) {
            clickedBtn.setText("O");
            clickedBtn.setTextColor(Color.parseColor("#ffffff"));
            activePlayer = PLAYER_X;
            headerText.setText("Player 2 turn");
            countdown++;

        } else {
            clickedBtn.setText("X");
            clickedBtn.setTextColor(Color.parseColor("#ff0000"));
            activePlayer = PLAYER_O;
            headerText.setText("Player 1 turn");
            countdown++;
        }

        checkForWin();
        if (countdown==9){
            restartGame();
        }

    }

    private void checkForWin() {
        //we will check who is winner and show
        int[][] winningPos = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

        for (int i = 0; i < 8; i++) {
            int val0 = winningPos[i][0];
            int val1 = winningPos[i][1];
            int val2 = winningPos[i][2];

            if (filledPos[val0] == filledPos[val1] && filledPos[val1] == filledPos[val2]) {
                if (filledPos[val0] != -1) {
                    //winner declare

                    isGameActive = false;

                    if (filledPos[val0] == PLAYER_O) {
                        showDialog("Player1 is winner");
                        headerText.setText("Player1 won!");
                        score1++;
                        txtScore1.setText(String.valueOf(score1));
                    }
                    else {
                        showDialog("Player2 is winner");
                        headerText.setText("Player2 won !");
                        score2++;
                        txtScore2.setText(String.valueOf(score2));
                    }

                }
            }
        }


    }

    private void showDialog(String winnerText) {
        new AlertDialog.Builder(this)
                .setTitle(winnerText)
                .setCancelable(false)
                .setPositiveButton("Next round", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        restartGame();
                    }
                })
                .show();
    }
    private void showDialogHelp(){
        new AlertDialog.Builder(this)
                .setTitle("Chơi đi rồi biết ^.^")
                .setCancelable(false)
                .setNegativeButton("Dạ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }


    @SuppressLint("NewApi")
    private void restartGame() {
        activePlayer = PLAYER_O;
        headerText.setText("");
        filledPos = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1};
        btn0.setText("");
        btn1.setText("");
        btn2.setText("");
        btn3.setText("");
        btn4.setText("");
        btn5.setText("");
        btn6.setText("");
        btn7.setText("");
        btn8.setText("");
        countdown=0;

//        btn0.setBackground(getDrawable(android.R.color.darker_gray));
//        btn1.setBackground(getDrawable(android.R.color.darker_gray));
//        btn2.setBackground(getDrawable(android.R.color.darker_gray));
//        btn3.setBackground(getDrawable(android.R.color.darker_gray));
//        btn4.setBackground(getDrawable(android.R.color.darker_gray));
//        btn5.setBackground(getDrawable(android.R.color.darker_gray));
//        btn6.setBackground(getDrawable(android.R.color.darker_gray));
//        btn7.setBackground(getDrawable(android.R.color.darker_gray));
//        btn8.setBackground(getDrawable(android.R.color.darker_gray));
        isGameActive = true;
    }
}