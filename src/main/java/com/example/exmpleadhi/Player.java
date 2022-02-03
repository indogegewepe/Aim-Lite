package com.example.exmpleadhi;

public class Player {
    private String name;
    private String difficulty;
    private int level;
    private int score;

    Player(String name, String difficulty,
        int level, int score) {
        this.name = name;
        this.difficulty = difficulty;
        this.level = level;
        this.score = score;
    }

    Player(String name, String difficulty, int score) {
        this.name = name;
        this.difficulty = difficulty;
        this.score = score;
    }

    public String getName() {
        return name;
    }
    public String getDifficulty() {
        return difficulty;
    }
    public int getLevel() {
        return level;
    }
    public int getScore() {
        return score;
    }
}