package blog.tech.prasenjeet.scarnesdie;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity
{
    private int userOverallScore=0;
    private int userTurnScore=0;
    private int computerOverallScore=0;
    private int computerTurnScore=0;

    private Button roll, reset, hold;
    private ImageView diceImage ;
    private TextView playerTextView , scoreTextView;





    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerTextView = (TextView) findViewById(R.id.playerTextView);
        scoreTextView = (TextView) findViewById(R.id.scoreTextView);

        diceImage = (ImageView) findViewById(R.id.diceImage);



        reset = (Button) findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                reset();
            }
        });


        roll = (Button) findViewById(R.id.roll);
        roll.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                rollDice();
            }
        });


        hold = (Button) findViewById(R.id.hold);
        hold.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                hold();
            }
        });



    }



    public void reset()
    {
        userOverallScore = userTurnScore = computerOverallScore =computerTurnScore = 0;
        playerTextView.setText("User's turn!");
        scoreTextView.setText("Start the game by rolling the dice");
    }


    public void hold()
    {
        userOverallScore += userTurnScore;
        userTurnScore = 0;


        updateScoreTextView("user");

        handler.postDelayed(r, 500);


    }



    public void updateScoreTextView(String type)
    {
        if(type.equals("user"))
            scoreTextView.setText("Your score: "+userOverallScore+" Computer score: "+computerOverallScore+" \nYour turn score: "+userTurnScore);
        else if(type.equals("computer"))
            scoreTextView.setText("Your score: "+userOverallScore+" Computer score: "+computerOverallScore+" \nComputer turn score: "+computerTurnScore);


    }




    public void rollDice()
    {
        int max = 6, min = 1;
        Random random = new Random();
        int randomNum = random.nextInt(max - min + 1) + min;

        if(randomNum!=1)
        {
            changeDiceImage(randomNum);
            userTurnScore += randomNum;
            updateScoreTextView("user");
            if(userTurnScore + userOverallScore >= 100)
            {
                Toast.makeText(this, "User won the game", Toast.LENGTH_LONG).show();
                reset();

            }


        }
        else
        {
            changeDiceImage(1);
            userTurnScore = 0;
            hold();

        }


    }



    final Handler handler = new Handler();
    final Runnable r = new Runnable()
    {
        public void run()
        {
            computerTurn();

        }
    };








    public void computerTurn()
    {
        roll.setEnabled(false);
        hold.setEnabled(false);

        playerTextView.setText("Computer's turn!");

        int max = 6, min = 1;


        Random random = new Random();
        int randomNum = random.nextInt(max - min + 1) + min;

        if(randomNum!=1)
        {
            changeDiceImage(randomNum);
            computerTurnScore += randomNum;
            updateScoreTextView("computer");
            if(computerTurnScore + computerOverallScore >= 100)
            {
                Toast.makeText(this, "Computer won the game", Toast.LENGTH_LONG).show();
                roll.setEnabled(true);
                hold.setEnabled(true);
                reset();
                return ;

            }
        }
        else
        {
            changeDiceImage(1);
            computerTurnScore = 0;
            updateScoreTextView("computer");

        }

        Log.d("ComputerTurn", String.valueOf(randomNum));

        if(computerTurnScore != 0  && computerTurnScore <=20)
        {
            handler.postDelayed(r, 500);
        }
        else
        {
            resumeUser();
        }




    }



    public void  resumeUser()
    {
        computerOverallScore+=computerTurnScore;
        computerTurnScore=0;
        playerTextView.setText("User's turn!");

        //updateScoreTextView("computer");
        updateScoreTextView("user");

        roll.setEnabled(true);
        hold.setEnabled(true);
        Log.d("ComputerTurn","_______________");
    }

    public void changeDiceImage(int diceNumber)
    {
        switch (diceNumber)
        {
            case 1:
                diceImage.setImageResource(R.drawable.dice1);
                break;

            case 2:
                diceImage.setImageResource(R.drawable.dice2);
                break;

            case 3:
                diceImage.setImageResource(R.drawable.dice3);
                break;

            case 4:
                diceImage.setImageResource(R.drawable.dice4);
                break;

            case 5:
                diceImage.setImageResource(R.drawable.dice5);
                break;

            case 6:
                diceImage.setImageResource(R.drawable.dice6);
                break;

        }
    }




}





