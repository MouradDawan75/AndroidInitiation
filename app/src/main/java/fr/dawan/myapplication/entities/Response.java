package fr.dawan.myapplication.entities;

public class Response {

    private int id;
    private String text;
    private boolean correct;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public Response(int id, String text, boolean correct) {
        this.id = id;
        this.text = text;
        this.correct = correct;
    }

    //Car pas de Base de données
    public Response(String text, boolean correct) {
        this.text = text;
        this.correct = correct;
    }

    public Response() {
    }

    @Override
    public String toString() {
        return text;
    }
}
