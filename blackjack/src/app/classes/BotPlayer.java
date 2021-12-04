package app.classes;

public class BotPlayer {
    private int difficulty;
    private Deck deck;
    private int playerNr;

    public BotPlayer(int difficulty, Deck deck, int playerNr) {
        this.difficulty = difficulty;
        this.deck = deck;
        this.playerNr = playerNr;
    }

    public boolean play(){ // zwraca true jeśli bot dobrał i false jeśli stand

        if(difficulty==0){ // dobiera albo wstrzymuje 50/50
            if(Math.random()>0.5){
                deck.draw(playerNr);
                return true;
            }else{
                return false;
            }

        }
        if(difficulty==1){//działa jak krupier
            if(deck.getScore(playerNr)<17){
                deck.draw(playerNr);
                return true;
            }else{
                return false;
            }

        }
        if(difficulty==2){//przewiduje przyszłość tzn zna następną kartę
            if(deck.getScore(playerNr)+deck.getDeck().get(0).getValue()<22){
                deck.draw(playerNr);
                return true;
            }else{
                if(deck.getDeck().get(0).getValue()==11){
                    if(deck.getScore(playerNr)+1<22){
                        deck.draw(playerNr);
                        return true;
                    }
                }
                return false;
            }
        }
        return true; // return dodany bo inaczej krzyczy :v
    }
}
