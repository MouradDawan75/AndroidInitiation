package fr.dawan.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckedTextView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import fr.dawan.myapplication.entities.Contact;
import fr.dawan.myapplication.entities.Question;
import fr.dawan.myapplication.entities.Quiz;
import fr.dawan.myapplication.entities.QuizTest;
import fr.dawan.myapplication.entities.Response;

public class QuizActivity extends BaseActivity {

    TextView tvQuizTitle, tvQuestionText, tvTimer;
    GridView gvResponses;
    ArrayAdapter<Response> adapter;
    QuizTest quizTest;
    CountDownTimer timer;
    int currentQuestion;
    Button btnSuivant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);

        setContentView(R.layout.activity_quiz);

        /*
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/

        tvQuizTitle = findViewById(R.id.tv_quiz_title);
        tvQuestionText = findViewById(R.id.tv_question_text);
        tvTimer = findViewById(R.id.tv_timer);
        gvResponses = findViewById(R.id.gv_response);
        btnSuivant = findViewById(R.id.btn_quiz_suivant);


        quizTest = new QuizTest(1, new Contact(1,"Mourad","mmahrane@dawan.fr"), Quiz.createQuiz(),0);

        tvQuizTitle.setText(quizTest.getQuiz().getTitle());

        currentQuestion = 0;

        //Temps en millisecondes

        timer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long temps) {
                tvTimer.setText("Temps restant: "+temps/1000);
            }

            @Override
            public void onFinish() {
                currentQuestion++;
                if(currentQuestion < quizTest.getQuiz().getQuestions().size()){
                    displayQuestion();
                    //RAZ du timer
                    timer.cancel();
                    timer.start();
                }else{
                    resultatFinal();
                }
            }
        }.start();

        displayQuestion();

    }

    private void displayQuestion() {
        //Tant qu'il reste des questions à afficher
        if(currentQuestion < quizTest.getQuiz().getQuestions().size()){

            //Récupérer la qst en cours
            Question qst = quizTest.getQuiz().getQuestions().get(currentQuestion);

            //Afficher le texte de la qst
            tvQuestionText.setText(qst.getText());

            //Remplir GridView responses selon le type de la qst
            if(qst.isMultiple()){
                adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_checked, qst.getResponses());
                gvResponses.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
            }
            else{
                adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, qst.getResponses());
                gvResponses.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
            }
            gvResponses.setAdapter(adapter);
        }else{
            //Afficher le score final - envoyer e score par mail
            resultatFinal();
        }

    }

    private void resultatFinal() {
        timer.cancel();
        tvQuestionText.setText("Votre score est de: "+quizTest.getScore());
        gvResponses.setVisibility(View.INVISIBLE);
        btnSuivant.setText("Reçevoir le résultat par email");
        tvTimer.setVisibility(View.INVISIBLE);
    }

    public void btnSuivantClick(View view) {

        if(btnSuivant.getText().toString().equals("Suivant")){
            //Traiter la qst en cours et passer à a qst suivante
            Question qstEnCours = quizTest.getQuiz().getQuestions().get(currentQuestion);

            //Récupérer les reponses
            if (qstEnCours.isMultiple()){

                for(int i = 0; i < gvResponses.getChildCount(); i++){
                    AppCompatCheckedTextView v = (AppCompatCheckedTextView) gvResponses.getChildAt(i);

                    if(v.isChecked()){
                        if(qstEnCours.getResponses().get(i).isCorrect()){
                            quizTest.setScore(quizTest.getScore() + 1);
                        }else{
                            quizTest.setScore(quizTest.getScore() - 1);
                        }
                    }
                }//fin du for
            }
            else{
                for(int i = 0; i < gvResponses.getChildCount(); i++){
                    AppCompatCheckedTextView v = (AppCompatCheckedTextView) gvResponses.getChildAt(i);

                    if(v.isChecked()){
                        if(qstEnCours.getResponses().get(i).isCorrect()){
                            quizTest.setScore(quizTest.getScore() + 1);
                            break;
                        }
                    }
                }//fin for
            }//fin else

            //Passer à la question suivante
            currentQuestion++;
            timer.cancel();
            timer.start();
            displayQuestion();
        }else{
            //Intent implicite pour l'envoi d'un mail
            Intent mailIntent = new Intent(Intent.ACTION_SENDTO,
                    Uri.parse("mailto:?subject"+quizTest.getQuiz().getTitle()+"&to="+quizTest.getContact().getEmail()
                            +"&body= Votre score est de: "+quizTest.getScore()));

            startActivity(mailIntent);
        }


    }
}