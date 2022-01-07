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
            case "2 Kier":
                return "/cardHearts2.png";
            case "3 Kier":
                return "/cardHearts3.png";
            case "4 Kier":
                return "/cardHearts4.png";
            case "5 Kier":
                return "/cardHearts5.png";
            case "6 Kier":
                return "/cardHearts6.png";
            case "7 Kier":
                return "/cardHearts7.png";
            case "8 Kier":
                return "/cardHearts8.png";
            case "9 Kier":
                return "/cardHearts9.png";
            case "10 Kier":
                return "/cardHearts10.png";
            case "As Kier":
                return "/cardHeartsA.png";
            case "Walet Kier":
                return "/cardHeartsJ.png";
            case "Król Kier":
                return "/cardHeartsK.png";
            case "Dama Kier":
                return "/cardHeartsQ.png";
            case "2 Karo":
                return "/cardDiamonds2.png";
            case "3 Karo":
                return "/cardDiamonds3.png";
            case "4 Karo":
                return "/cardDiamonds4.png";
            case "5 Karo":
                return "/cardDiamonds5.png";
            case "6 Karo":
                return "/cardDiamonds6.png";
            case "7 Karo":
                return "/cardDiamonds7.png";
            case "8 Karo":
                return "/cardDiamonds8.png";
            case "9 Karo":
                return "/cardDiamonds9.png";
            case "10 Karo":
                return "/cardDiamonds10.png";
            case "As Karo":
                return "/cardDiamondsA.png";
            case "Walet Karo":
                return "/cardDiamondsJ.png";
            case "Król Karo":
                return "/cardDiamondsK.png";
            case "Dama Karo":
                return "/cardDiamondsQ.png";
            case "2 Trefl":
                return "/cardClubs2.png";
            case "3 Trefl":
                return "/cardClubs3.png";
            case "4 Trefl":
                return "/cardClubs4.png";
            case "5 Trefl":
                return "/cardClubs5.png";
            case "6 Trefl":
                return "/cardClubs6.png";
            case "7 Trefl":
                return "/cardClubs7.png";
            case "8 Trefl":
                return "/cardClubs8.png";
            case "9 Trefl":
                return "/cardClubs9.png";
            case "10 Trefl":
                return "/cardClubs10.png";
            case "As Trefl":
                return "/cardClubsA.png";
            case "Walet Trefl":
                return "/cardClubsJ.png";
            case "Król Trefl":
                return "/cardClubsK.png";
            case "Dama Trefl":
                return "/cardClubsQ.png";
            case "2 Pik":
                return "/cardSpades2.png";
            case "3 Pik":
                return "/cardSpades3.png";
            case "4 Pik":
                return "/cardSpades4.png";
            case "5 Pik":
                return "/cardSpades5.png";
            case "6 Pik":
                return "/cardSpades6.png";
            case "7 Pik":
                return "/cardSpades7.png";
            case "8 Pik":
                return "/cardSpades8.png";
            case "9 Pik":
                return "/cardSpades9.png";
            case "10 Pik":
                return "/cardSpades10.png";
            case "As Pik":
                return "/cardSpadesA.png";
            case "Walet Pik":
                return "/cardSpadesJ.png";
            case "Król Pik":
                return "/cardSpadesK.png";
            case "Dama Pik":
                return "/cardSpadesQ.png";
            default:
                return "/cardBack_green5.png";
        }
    }
}
