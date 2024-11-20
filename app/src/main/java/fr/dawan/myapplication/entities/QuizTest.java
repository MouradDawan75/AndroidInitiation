package fr.dawan.myapplication.entities;

public class QuizTest {

    private int id;
    private Contact contact;
    private Quiz quiz;
    private int score;

    public QuizTest(int id, Contact contact, Quiz quiz, int score) {
        this.id = id;
        this.contact = contact;
        this.quiz = quiz;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
