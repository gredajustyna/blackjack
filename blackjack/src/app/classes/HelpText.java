package app.classes;

public class HelpText {
    public HelpText(){

    }
    static String helpText1 = "Equally well known as Twenty-One. The rules are simple, the play is thrilling, and there is opportunity for high strategy. " +
            "In fact, for the expert player who mathematically plays a perfect game and is able to count cards, " +
            "the odds are sometimes in that player's favor to win.\n";
    static String thePack = "The standard 52-card pack is used, but in most casinos several decks of cards are shuffled together. " +
            "Here the players are able to choose between 1, 2 or 3 decks. ";
    static String objectOfTheGame = "Each participant attempts to beat the dealer by getting a count as close to 21 as possible, without going over 21.";
    static String cardValuesScoring = "2-10 - points are the same as card number\n" +
            "Jack, Queen, King - 10 points\nAce -It is up to each individual player if an ace is worth 1 or 11.";
    static String thePlay = "The player to the left goes first and must decide whether to \"stand\" (not ask for another card) or \"hit\" " +
            "(ask for another card in an attempt to get closer to a count of 21, or even hit 21 exactly). " +
            "Thus, a player may stand on the two cards originally dealt to them, or they may ask the dealer for additional cards, " +
            "one at a time, until deciding to stand on the total (if it is 21 or under), or goes \"bust\" (if it is over 21)." +
            " In the latter case, the player loses and the dealer collects the bet wagered. The dealer then turns to the next player to" +
            " their left and serves them in the same manner.\n";

    public static String getHelpText1() {
        return helpText1;
    }

    public static String getThePack() {
        return thePack;
    }

    public static String getObjectOfTheGame() {
        return objectOfTheGame;
    }

    public static String getCardValuesScoring() {
        return cardValuesScoring;
    }

    public static String getThePlay() {
        return thePlay;
    }
}
