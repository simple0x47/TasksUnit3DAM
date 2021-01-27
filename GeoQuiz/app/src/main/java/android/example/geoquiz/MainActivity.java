package android.example.geoquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    /*variables miembro para capturar los diferentes View desde Java*/
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private TextView mQuestionTextView;
    /*indice para saber que pregunta del array estamos leyendo*/
    private int mCurrentIndex=0;
    /*Array de objetos Question*/
    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_one,true),
            new Question(R.string.question_two,false),
            new Question(R.string.question_three,false),
            new Question(R.string.question_four,true),
            new Question(R.string.question_five,true)
    };

    /*Metodo que nos devuelve el ID de Recurso de la pregunta que queremos mostrar*/
    private void updateQuestion(){
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    /*Metodo para comprobar si la respuesta es correcta o no*/
    /*Le pasamos un booleano cuando lo llamamos desde cada boton*/
    /*Hacer notar que trabajamos con los ID de los recursos String no con el String directamente*/
    private void checkAnswer(boolean userPressedTrue){
        boolean answerIsTrue=mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId=0;
        if(userPressedTrue==answerIsTrue){
            messageResId=R.string.correct_toast;
        }else{
            messageResId=R.string.incorrect_toast;
        }
        Toast.makeText(this,messageResId, Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mQuestionTextView=(TextView)findViewById(R.id.question_text_view);

        mTrueButton = (Button)findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });
        mFalseButton = (Button)findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);

            }
        });
        mNextButton=(Button)findViewById(R.id.next_button);
        /*Gestion del boton siguiente*/
        /*Calculamos el indice de pregunta en el que nos encontramos*/
        /*Aumentamos en 1 la pregunta, con la % nos aseguramos que no pasamos de las preguntas existentes*/
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex=(mCurrentIndex+1)%mQuestionBank.length;
                updateQuestion();
            }
        });
        updateQuestion();

    }
}