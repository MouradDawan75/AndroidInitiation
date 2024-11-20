package fr.dawan.myapplication.entities;

import java.util.ArrayList;
import java.util.List;

public class Question {

    private int id;
    private String text;
    private boolean multiple;
    private List<Response> responses = new ArrayList<>();

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

    public boolean isMultiple() {
        return multiple;
    }

    public void setMultiple(boolean multiple) {
        this.multiple = multiple;
    }

    public List<Response> getResponses() {
        return responses;
    }

    public void setResponses(List<Response> responses) {
        this.responses = responses;
    }

    public Question(int id, String text, boolean multiple) {
        this.id = id;
        this.text = text;
        this.multiple = multiple;
    }

    public Question(String text, boolean multiple) {
        this.text = text;
        this.multiple = multiple;
    }

    public Question() {
    }
}
