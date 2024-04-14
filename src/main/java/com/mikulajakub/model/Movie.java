package com.mikulajakub.model;

import lombok.Getter;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

@Getter
public class Movie {
    String title;
    Set<String> actors = new HashSet<>();

    public Movie(String title) {
        this.title = title;
    }

    public void addActor(String actor) {
        actors.add(actor);
    }

    @Override
    public String toString() {
        return "Movie [title=" + title + ", actors=" + actors + "]";
    }
}
