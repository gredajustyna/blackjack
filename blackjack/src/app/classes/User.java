package app.classes;

public class User {
    private String Login;
    private String Password;
    private int Matches;
    private int Wins;
    private int CardsUsed;
    private int TimePlayed;

    public String getLogin() {
        return Login;
    }

    public void setLogin(String login) {
        Login = login;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public int getMatches() {
        return Matches;
    }

    public void setMatches(int matches) {
        Matches = matches;
    }

    public int getWins() {
        return Wins;
    }

    public void setWins(int wins) {
        Wins = wins;
    }

    public int getCardsUsed() {
        return CardsUsed;
    }

    public void setCardsUsed(int cardsUsed) {
        CardsUsed = cardsUsed;
    }

    public int getTimePlayed() {
        return TimePlayed;
    }

    public void setTimePlayed(int timePlayed) {
        TimePlayed = timePlayed;
    }
}
