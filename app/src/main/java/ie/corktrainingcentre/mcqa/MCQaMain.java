package ie.corktrainingcentre.mcqa;

import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MCQaMain extends Activity {

    private Button btnPrevious;
    private Button btnCheckAnswer;
    private Button btnNext;

    private TextView txtStatus;
    private TextView txtQuestion;

    private RadioButton rdbAnswerA;
    private RadioButton rdbAnswerB;
    private RadioButton rdbAnswerC;
    private RadioButton rdbAnswerD;

    private int questionNumber = 1;
    private final int MAX_QUESTION_NUMBER = 5;
    private boolean[] scoreCard = new boolean[this.MAX_QUESTION_NUMBER];

    private MCQ currentQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcqa_main);

        btnPrevious = (Button)findViewById(R.id.btnPrevious);
        btnCheckAnswer = (Button)findViewById(R.id.btnAnswer);
        btnNext = (Button)findViewById(R.id.btnNext);

        txtStatus = (TextView)findViewById(R.id.status);
        txtQuestion = (TextView)findViewById(R.id.question);

        rdbAnswerA = (RadioButton)findViewById(R.id.rdbA);
        rdbAnswerB = (RadioButton)findViewById(R.id.rdbB);
        rdbAnswerC = (RadioButton)findViewById(R.id.rdbC);
        rdbAnswerD = (RadioButton)findViewById(R.id.rdbD);

        this.getQuestion();
    }

    public void getQuestion() {
        this.currentQuestion = new MCQ(this, this.questionNumber);
        this.txtQuestion.setText( this.currentQuestion.getQuestion() );

        this.rdbAnswerA.setChecked(false);
        this.rdbAnswerB.setChecked(false);
        this.rdbAnswerC.setChecked(false);
        this.rdbAnswerD.setChecked(false);

        this.rdbAnswerA.setText( this.currentQuestion.getAnswerA() );
        this.rdbAnswerB.setText( this.currentQuestion.getAnswerB() );
        this.rdbAnswerC.setText( this.currentQuestion.getAnswerC() );
        this.rdbAnswerD.setText( this.currentQuestion.getAnswerD() );

        this.updateStatus();
    }

    public void getPreviousMCQ(View v) {
        if (--this.questionNumber < 1) {
            this.questionNumber = 1;
        }
        this.getQuestion();
    }

    public void checkAnswer(View v) {
        String message;
        char userAnswer = ' ';
        if ( this.rdbAnswerA.isChecked() ) {
            userAnswer = 'A';
        } else if ( this.rdbAnswerB.isChecked() ) {
            userAnswer = 'B';
        } else if ( this.rdbAnswerC.isChecked() ) {
            userAnswer = 'C';
        } else if ( this.rdbAnswerD.isChecked() ) {
            userAnswer = 'D';
        }
        if ( userAnswer == this.currentQuestion.getCorrectAnswer() ) {
            message = "Well done! Your answer is correct.";
            scoreCard[this.questionNumber - 1] = true;
        } else {
            message = "Too bad! Your answer is wrong. Try again.";
            scoreCard[this.questionNumber - 1] = false;
        }
        this.updateStatus();
        this.outputToastMessage(message);
    }

    private void updateStatus() {
        this.txtStatus.setText("Number Correct: " + this.getNumberCorrect() +
                " / " +
                this.MAX_QUESTION_NUMBER + " : Total Questions");
    }

    private int getNumberCorrect() {
        int numCorrect = 0;
        for (int i = 0; i < this.scoreCard.length; i++) {
            if ( this.scoreCard[i] ) {
                numCorrect++;
            }
        }
        return numCorrect;
    }

    private void outputToastMessage(String msg) {
        CharSequence text = msg;
        Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG);
        toast.show();
    }

    public void getNextMCQ(View v) {
        if (++this.questionNumber > this.MAX_QUESTION_NUMBER) {
            this.questionNumber = this.MAX_QUESTION_NUMBER;
        }
        this.getQuestion();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mcqa_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
