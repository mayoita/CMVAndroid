package it.casinovenezia.casinodivenezia;

import com.parse.ParseFile;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by massimomoro on 30/07/15.
 */
public class PokerItem {
    private String myId;
    private String tournamentDate;
    private String tournamentDescription;
    private String tournamentEvent;
    private String startDate;
    private String endDate;
    private ArrayList tournamentsRules;
    private String tournamentsName;
    private String office;
    private String tournamentUrl;
    private ArrayList pokerData;

    public String getMyId () {return myId;}
    public void setMyId(String name) {this.myId = name;}

    public String getTournamentDate() {
        return tournamentDate;
    }

    public void setTournamentDate(String tournamentDate) {
        this.tournamentDate = tournamentDate;
    }

    public String getTournamentDescription() {
        return tournamentDescription;
    }

    public void setTournamentDescription(String tournamentDescription) {
        this.tournamentDescription = tournamentDescription;
    }

    public String getTournamentEvent() {
        return tournamentEvent;
    }

    public void setTournamentEvent(String tournamentEvent) {
        this.tournamentEvent = tournamentEvent;
    }



    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String name) {
        this.startDate = name;
    }
    public String getEndDate() {
        return endDate;
    }
    public void setEndDate(String name) {
        this.endDate = name;
    }

    public ArrayList getTournamentsRules() {
        return tournamentsRules;
    }

    public void setTournamentsRules(ArrayList tournamentsRules) {
        this.tournamentsRules = tournamentsRules;
    }

    public String getTournamentsName() {
        return tournamentsName;
    }

    public void setTournamentsName(String tournamentsName) {
        this.tournamentsName = tournamentsName;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getTournamentUrl() {
        return tournamentUrl;
    }

    public void setTournamentUrl(String tournamentUrl) {
        this.tournamentUrl = tournamentUrl;
    }

    public ArrayList getPokerData() {
        return pokerData;
    }

    public void setPokerData(ArrayList pokerData) {
        this.pokerData = pokerData;
    }
}
