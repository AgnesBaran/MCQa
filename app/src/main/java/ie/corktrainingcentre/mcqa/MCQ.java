package ie.corktrainingcentre.mcqa;

import android.content.Context;
import android.content.res.Resources;
import java.io.IOException;
import java.io.InputStream;

public class MCQ {

    Context context;

    private final String QUESTION_FILE_PREFIX = "mcq";
    private final String QUESTION_FILE_EXTENSION = ".txt";

    private int questionNumber;
    private String question;
    private String answerA;
    private String answerB;
    private String answerC;
    private String answerD;
    private char correctAnswer;

    public MCQ(Context con, int num) {
        this.context = con;
        this.questionNumber = num;
        readQuestion();
    }

    public void readQuestion() {
        String questionText = null;
        Resources myRes = this.context.getResources();
        String fileName = getFileName();
        String resourceType = "raw";
        String packageName = this.context.getPackageName();
        int identifier = myRes.getIdentifier( fileName, resourceType, packageName );
        InputStream myFile = myRes.openRawResource( identifier );
        try {
            byte[] buffer = new byte[myFile.available()];
            myFile.read(buffer, 0, buffer.length);
            questionText = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // split the text from the file on "\n"
        String split[] = questionText.split("\\n");
        StringBuilder quest = new StringBuilder();
        int len = split.length;
        if (len > 0) {
            for (int i = 0; i < (len - 5); i++ ) {
                quest.append(split[i]);
                if (i != (len - 5)) {
                    quest.append( "\n" );
                }
            }
            this.question = quest.toString();
            this.answerA = split[len - 5];
            this.answerB = split[len - 4];
            this.answerC = split[len - 3];
            this.answerD = split[len - 2];
            this.correctAnswer = split[len - 1].charAt(0);
        }
    }

    public String getFileName() {
        StringBuilder str = new StringBuilder();
        str.append(this.QUESTION_FILE_PREFIX);
        str.append(this.questionNumberToString());
        //str.append(this.QUESTION_FILE_EXTENSION);
        return str.toString();
    }

    public String questionNumberToString() {
        String str;
        if (this.questionNumber < 10) {
            str = "00" + this.questionNumber;
        } else if (this.questionNumber < 100) {
            str = "0" + this.questionNumber;
        } else {
            str = new Integer(this.questionNumber).toString();
        }
        return str;
    }

    public String getQuestion() {
        return this.question;
    }

    public String getAnswerA() {
        return this.answerA;
    }

    public String getAnswerB() {
        return this.answerB;
    }

    public String getAnswerC() {
        return this.answerC;
    }

    public String getAnswerD() {
        return this.answerD;
    }

    public char getCorrectAnswer() {
        return this.correctAnswer;
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append( this.getQuestion() + "\n\n");
        str.append( "A: " + this.getAnswerA() + "\n");
        str.append( "B: " + this.getAnswerB() + "\n");
        str.append( "C: " + this.getAnswerC() + "\n");
        str.append( "D: " + this.getAnswerD() + "\n");
        str.append( "\nAnswer: " + this.getCorrectAnswer() + "\n");
        return str.toString();
    }

} // end of class MCQ