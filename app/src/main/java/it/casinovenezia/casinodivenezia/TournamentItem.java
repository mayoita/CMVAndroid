package it.casinovenezia.casinodivenezia;

import com.parse.ParseFile;

import java.util.ArrayList;

/**
 * Created by massimomoro on 31/07/15.
 */
public class TournamentItem {
    private String type;

    private String tournamentDescription;
    private ArrayList tournamentEvent;
    private String startDate;
    private String endDate;
    private ArrayList tournamentsRules;
    private String tournamentsName;
    private String office;
    private String tournamentUrl;
    private ParseFile imageTournament;

    public String getTournamentUrl() {
        return tournamentUrl;
    }

    public void setTournamentUrl(String tournamentUrl) {
        this.tournamentUrl = tournamentUrl;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getTournamentsName() {
        return tournamentsName;
    }

    public void setTournamentsName(String tournamentsName) {
        this.tournamentsName = tournamentsName;
    }

    public ArrayList getTournamentsRules() {
        return tournamentsRules;
    }

    public void setTournamentsRules(ArrayList tournamentsRules) {
        this.tournamentsRules = tournamentsRules;
    }

    public ParseFile getImageTournament() {
        return imageTournament;
    }

    public void setImageTournament(ParseFile imageTournament) {
        this.imageTournament = imageTournament;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }


    public String getTournamentDescription() {
        return tournamentDescription;
    }

    public void setTournamentDescription(String tournamentDescription) {
        this.tournamentDescription = tournamentDescription;
    }

    public ArrayList getTournamentEvent() {
        return tournamentEvent;
    }

    public void setTournamentEvent(ArrayList tournamentEvent) {
        this.tournamentEvent = tournamentEvent;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
