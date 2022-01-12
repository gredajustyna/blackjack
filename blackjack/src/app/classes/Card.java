package app.classes;

public class Card {
    private String name;
    private int value;

    public Card(String name, int value){
        this.name =name;
        this.value = value;

    }

    @Override
    public String toString() {
        return name;
    }


    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }


    public static String getCardImage(String cardName){
        switch (cardName){
            case "2 Hearts":
                return "/cardHearts2.png";
            case "3 Hearts":
                return "/cardHearts3.png";
            case "4 Hearts":
                return "/cardHearts4.png";
            case "5 Hearts":
                return "/cardHearts5.png";
            case "6 Hearts":
                return "/cardHearts6.png";
            case "7 Hearts":
                return "/cardHearts7.png";
            case "8 Hearts":
                return "/cardHearts8.png";
            case "9 Hearts":
                return "/cardHearts9.png";
            case "10 Hearts":
                return "/cardHearts10.png";
            case "Ace Hearts":
                return "/cardHeartsA.png";
            case "Jack Hearts":
                return "/cardHeartsJ.png";
            case "King Hearts":
                return "/cardHeartsK.png";
            case "Queen Hearts":
                return "/cardHeartsQ.png";
            case "2 Diamonds":
                return "/cardDiamonds2.png";
            case "3 Diamonds":
                return "/cardDiamonds3.png";
            case "4 Diamonds":
                return "/cardDiamonds4.png";
            case "5 Diamonds":
                return "/cardDiamonds5.png";
            case "6 Diamonds":
                return "/cardDiamonds6.png";
            case "7 Diamonds":
                return "/cardDiamonds7.png";
            case "8 Diamonds":
                return "/cardDiamonds8.png";
            case "9 Diamonds":
                return "/cardDiamonds9.png";
            case "10 Diamonds":
                return "/cardDiamonds10.png";
            case "Ace Diamonds":
                return "/cardDiamondsA.png";
            case "Jack Diamonds":
                return "/cardDiamondsJ.png";
            case "King Diamonds":
                return "/cardDiamondsK.png";
            case "Queen Diamonds":
                return "/cardDiamondsQ.png";
            case "2 Clubs":
                return "/cardClubs2.png";
            case "3 Clubs":
                return "/cardClubs3.png";
            case "4 Clubs":
                return "/cardClubs4.png";
            case "5 Clubs":
                return "/cardClubs5.png";
            case "6 Clubs":
                return "/cardClubs6.png";
            case "7 Clubs":
                return "/cardClubs7.png";
            case "8 Clubs":
                return "/cardClubs8.png";
            case "9 Clubs":
                return "/cardClubs9.png";
            case "10 Clubs":
                return "/cardClubs10.png";
            case "Ace Clubs":
                return "/cardClubsA.png";
            case "Jack Clubs":
                return "/cardClubsJ.png";
            case "King Clubs":
                return "/cardClubsK.png";
            case "Queen Clubs":
                return "/cardClubsQ.png";
            case "2 Spades":
                return "/cardSpades2.png";
            case "3 Spades":
                return "/cardSpades3.png";
            case "4 Spades":
                return "/cardSpades4.png";
            case "5 Spades":
                return "/cardSpades5.png";
            case "6 Spades":
                return "/cardSpades6.png";
            case "7 Spades":
                return "/cardSpades7.png";
            case "8 Spades":
                return "/cardSpades8.png";
            case "9 Spades":
                return "/cardSpades9.png";
            case "10 Spades":
                return "/cardSpades10.png";
            case "Ace Spades":
                return "/cardSpadesA.png";
            case "Jack Spades":
                return "/cardSpadesJ.png";
            case "King Spades":
                return "/cardSpadesK.png";
            case "Queen Spades":
                return "/cardSpadesQ.png";
            default:
                return "/cardBack_green5.png";
        }
    }
}
