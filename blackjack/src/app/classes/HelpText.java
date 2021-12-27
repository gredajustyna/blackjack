package app.classes;

public class HelpText {
    public HelpText(){

    }
    static String helpText1 = "Equally well known as Twenty-One. The rules are simple, the play is thrilling, and there is opportunity for high strategy. " +
            "In fact, for the expert player who mathematically plays a perfect game and is able to count cards, " +
            "the odds are sometimes in that player's favor to win.;\n";
    String thePack = "The standard 52-card pack is used, but in most casinos several decks of cards are shuffled together. " +
            "Here the players are able to choose between 1, 2 or 3 decks. In addition, in a casino the dealer uses a blank plastic card," +
            " which is never dealt, but is placed toward the bottom of the pack to indicate when it will be time for the cards to be reshuffled. " +
            "When four or more decks are used, they are dealt from a shoe (a box that allows the dealer to remove cards one at a time, " +
            "face down, without actually holding one or more packs).";
    String objectOfTheGame = "Each participant attempts to beat the dealer by getting a count as close to 21 as possible, without going over 21.";
    String cardValuesScoring = "It is up to each individual player if an ace is worth 1 or 11. Here, we stick to the value of " +
            "Face cards are 10 and any other card is its pip value.";
    String thePlay = "The player to the left goes first and must decide whether to \"stand\" (not ask for another card) or \"hit\" " +
            "(ask for another card in an attempt to get closer to a count of 21, or even hit 21 exactly). " +
            "Thus, a player may stand on the two cards originally dealt to them, or they may ask the dealer for additional cards, " +
            "one at a time, until deciding to stand on the total (if it is 21 or under), or goes \"bust\" (if it is over 21)." +
            " In the latter case, the player loses and the dealer collects the bet wagered. The dealer then turns to the next player to" +
            " their left and serves them in the same manner.\n" +
            "\n" +
            "The combination of an ace with a card other than a ten-card is known as a \"soft hand,\" " +
            "because the player can count the ace as a 1 or 11, and either draw cards or not. " +
            "For example with a \"soft 17\" (an ace and a 6), the total is 7 or 17. " +
            "While a count of 17 is a good hand, the player may wish to draw for a higher total. " +
            "If the draw creates a bust hand by counting the ace as an 11, the player simply counts the ace as a 1 and " +
            "continues playing by standing or \"hitting\" (asking the dealer for additional cards, one at a time).";

    public static String getHelpText1() {
        return helpText1;
    }

    public String getThePack() {
        return thePack;
    }

    public String getObjectOfTheGame() {
        return objectOfTheGame;
    }

    public String getCardValuesScoring() {
        return cardValuesScoring;
    }

    public String getThePlay() {
        return thePlay;
    }
}
