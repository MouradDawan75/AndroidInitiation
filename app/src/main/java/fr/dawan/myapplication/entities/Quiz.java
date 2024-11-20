package fr.dawan.myapplication.entities;

import java.util.ArrayList;
import java.util.List;

public class Quiz {

    private int id;
    private String title;
    private List<Question> questions = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public Quiz(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public Quiz(String title) {
        this.title = title;
    }

    public Quiz() {
    }

    public static Quiz createQuiz(){
        Quiz androidQuiz = new Quiz("Android");

        //Question simple
        Question qst1 = new Question(1,"Comment lancer une Activity", false);
        qst1.getResponses().add(new Response(1,"startActivity", true));
        qst1.getResponses().add(new Response(2,"beginActivity", false));

        androidQuiz.getQuestions().add(qst1);

        //Question multiple
        Question qst2 = new Question(2,"Comment transférer des données d'une Activity à une autre?", true);
        qst2.getResponses().add(new Response(3,"Utilisation d'un Bundle", true));
        qst2.getResponses().add(new Response(4,"Déclarer les données dans le manifest.xml", false));
        qst2.getResponses().add(new Response(5,"Utilisation de la méthode putExtra de l'Intent", true));

        androidQuiz.getQuestions().add(qst2);



        return androidQuiz;
    }
}
