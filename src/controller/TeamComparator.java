package controller;

import java.util.Comparator;

import model.Team;

class TeamComparator implements Comparator<Team> {
	
	@Override
	public int compare(Team team1, Team team2) {

		int comparedPoints = Integer.compare(team1.getPoints(), team2.getPoints());
		if (comparedPoints != 0) {
			return comparedPoints;
		}
		
		int comparedGoalDifferences = Integer.compare(team1.getGoalDifference(), team2.getGoalDifference());
		if (comparedGoalDifferences != 0) {
			return comparedGoalDifferences;
		}
		
		int comparedGoals = Integer.compare(team1.getGoals(), team2.getGoals());
		return comparedGoals;
		
	}
	
}