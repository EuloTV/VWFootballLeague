package model;

import java.util.Objects;

public class Team {

    private String name;
    private int wins = 0;
    private int losses = 0;
    private int draws = 0;
    private int goals = 0;
    private int goalsConceded = 0;
    private int points = 0;
    private int matches = 0;
    private int goalDifference = 0;
    private String goalsComparison = "0:0";

    public Team(String name) {
        this.setName(name);
    }

    public String getName() {
        return this.name;

    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWins() {
        return this.wins;
    }

    public void addWin() {
        this.wins++;
        updateMatches();
        updatePoints();
    }

    public int getLosses() {
        return this.losses;
    }

    public void addLoss() {
        this.losses++;
        updateMatches();
    }

    public int getDraws() {
        return this.draws;
    }

    public void addDraw() {
        this.draws++;
        updateMatches();
        updatePoints();
    }

    public int getMatches() {
        return this.matches;
    }

    private void updateMatches() {

        // 1. Aufgabe

        this.matches = getDraws() + getLosses() + getWins();
    }

    public int getPoints() {
        return this.points;
    }

    private void updatePoints() {

        // 1. Aufgabe

        this.points = getDraws() + getWins() * 3;

    }

    public int getGoals() {
        return this.goals;
    }

    public void addGoals(int goals) {
        this.goals += goals;
        updateGoalDifference();
        updateGoalsComparison();
    }

    public int getGoalsConceded() {
        return this.goalsConceded;
    }

    public void addGoalsConceded(int goalsConceded) {
        this.goalsConceded += goalsConceded;
        updateGoalDifference();
        updateGoalsComparison();
    }

    public int getGoalDifference() {
        return this.goalDifference;
    }

    public void updateGoalDifference() {
        // 2. Aufgabe
        this.goalDifference = getGoals() - getGoalsConceded();

    }

    public String getGoalsComparison() {
        return this.goalsComparison;
    }

    private void updateGoalsComparison() {
        // 2. Aufgabe
        this.goalsComparison = getGoals() + ":" + getGoalsConceded();
    }

    // 3. Aufgabe
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    // 3. Aufgabe
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Team other = (Team) obj;
        return Objects.equals(name, other.name);
    }

}
