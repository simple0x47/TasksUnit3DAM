package android.example.geoquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    /*variables miembro para capturar los diferentes View desde Java*/
    private ImageButton mTrueButton;
    private ImageButton mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mPreviousButton;
    private TextView mQuestionTextView;
    private TextView mCorrectAnswersTextView;
    private TextView mQuestionIndicatorTextView;

    /*indice para saber que pregunta del array estamos leyendo*/
    private int mCurrentIndex = 0;

    /*indicador de respuestas correctas*/
    private int mCorrectAnswers = 0;

    /*Array de objetos Question*/
    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_one, true),
            new Question(R.string.question_two, false),
            new Question(R.string.question_three, false),
            new Question(R.string.question_four, true),
            new Question(R.string.question_five, true)
    };

    /*Metodo que nos devuelve el ID de Recurso de la pregunta que queremos mostrar*/
    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
        mQuestionIndicatorTextView.setText(String.format("%s %d/%d",
                getString(R.string.question_indicator), mCurrentIndex + 1, mQuestionBank.length));

        mCorrectAnswersTextView.setText(String.format("%s %d",
                getString(R.string.correct_answers), mCorrectAnswers));
    }

    /*Metodo para comprobar si la respuesta es correcta o no*/
    /*Le pasamos un booleano cuando lo llamamos desde cada boton*/
    /*Hacer notar que trabajamos con los ID de los recursos String no con el String directamente*/
    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId = 0;
        if (userPressedTrue == answerIsTrue) {
            messageResId = R.string.correct_toast;
            ++mCorrectAnswers;
        } else {
            messageResId = R.string.incorrect_toast;
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
        mCorrectAnswersTextView.setText(String.format("%s %d",
                getString(R.string.correct_answers), mCorrectAnswers));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mCorrectAnswersTextView = (TextView) findViewById(R.id.correct_answers);
        mQuestionIndicatorTextView = (TextView) findViewById(R.id.question_id);

        mTrueButton = (ImageButton) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });
        mFalseButton = (ImageButton) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);

            }
        });
        mNextButton = (ImageButton) findViewById(R.id.next_button);

        /*Gestion del boton siguiente*/
        /*Calculamos el indice de pregunta en el que nos encontramos*/
        /*Aumentamos en 1 la pregunta, con la % nos aseguramos que no pasamos de las preguntas existentes*/
        View.OnClickListener nextListener = v -> {
            mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
            updateQuestion();
        };

        mNextButton.setOnClickListener(nextListener);
        mQuestionTextView.setOnClickListener(nextListener);

        mPreviousButton = (ImageButton) findViewById(R.id.previous_button);
        mPreviousButton.setOnClickListener(v -> {
            mCurrentIndex = (mCurrentIndex == 0)
                    ? mQuestionBank.length - 1
                    : Math.max(mCurrentIndex - 1, 0);
            updateQuestion();
        });

        updateQuestion();
    }
}