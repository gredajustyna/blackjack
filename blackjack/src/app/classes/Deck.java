package app.classes;

import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Deck {
    private List<Card> deck = new ArrayList<>();

    private List<Card> krupier = new ArrayList<>();
    private List<Card> player1 = new ArrayList<>();
    private List<Card> player2 = new ArrayList<>();
    private List<Card> player3 = new ArrayList<>();
    private List<Card> player4 = new ArrayList<>();
    public int[] score= {0,0,0,0,0};
    private List<Card> discard = new ArrayList<>();


    public Deck(int decksNumber){
        String[] suits = {" Pik"," Kier"," Trefl"," Karo"};
        Card card;
        for(int i =2; i<11; i++){
            card = new Card(i+suits[0],i);
            deck.add(card);
            card = new Card(i+suits[1],i);
            deck.add(card);
            card = new Card(i+suits[2],i);
            deck.add(card);
            card = new Card(i+suits[3],i);
            deck.add(card);
        }
        for(int i =0; i<4;i++){
            card = new Card("Król"+suits[i],10);
            deck.add(card);
            card = new Card("Dama"+suits[i],10);
            deck.add(card);
            card = new Card("Walet"+suits[i],10);
            deck.add(card);
            card = new Card("As"+suits[i],11);
            deck.add(card);
        }

        for(int i=1; i<decksNumber;i++){
            deck.addAll(deck);
        }

    }

    public void shuffle(){
        Random rnd = ThreadLocalRandom.current();
        for (int i = deck.size() - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            Card a = deck.get(index);
            deck.set(index, deck.get(i));
            deck.set(i, a);
        }
    }

    public int draw(int user){

        score[user] += deck.get(0).getValue();
        int value = deck.get(0).getValue();
        switch (user) {
            case 0:
                krupier.add(deck.get(0));
                if(score[user]>21){
                    for(int i = 0; i < krupier.size(); i++){
                        if(krupier.get(i).getValue()==11){
                            krupier.get(i).setValue(1);
                            score[user] -= 10;
                            break;
                        }
                    }
                }
                break;
            case 1:
                player1.add(deck.get(0));
                if(score[user]>21){
                    for(int i = 0; i < player1.size(); i++){
                        if(player1.get(i).getValue()==11){
                            player1.get(i).setValue(1);
                            score[user] -= 10;
                            break;
                        }
                    }
                }
                break;
            case 2:
                player2.add(deck.get(0));
                if(score[user]>21){
                    for(int i = 0; i < player2.size(); i++){
                        if(player2.get(i).getValue()==11){
                            player2.get(i).setValue(1);
                            score[user] -= 10;
                            break;
                        }
                    }
                }
                break;
            case 3:
                player3.add(deck.get(0));
                if(score[user]>21){
                    for(int i = 0; i <player3.size(); i++){
                        if(player3.get(i).getValue()==11){
                            player3.get(i).setValue(1);
                            score[user] -= 10;
                            break;
                        }
                    }
                }
                break;
            case 4:
                player4.add(deck.get(0));
                if(score[user]>21){
                    for(int i = 0; i < player4.size(); i++){
                        if(player4.get(i).getValue()==11){
                            player4.get(i).setValue(1);
                            score[user] -= 10;
                            break;
                        }
                    }
                }
                break;
        }

        deck.remove(0);
        if(deck.size()==0){
            reShuffle();
        }
        return value;
    }

    public void discard(){
        discard.addAll(krupier);
        krupier.clear();
        discard.addAll(player1);
        player1.clear();
        discard.addAll(player2);
        player2.clear();
        discard.addAll(player3);
        player3.clear();
        discard.addAll(player4);
        player4.clear();
    }

    public void reShuffle(){
        for(int i = 0; i < discard.size(); i++){
            if(discard.get(i).getValue()==1){
                discard.get(i).setValue(11);
            }
        }

        deck.addAll(discard);
        discard.clear();
        shuffle();
    }


    public List<Card> getDeck() {
        return deck;
    }

    public int getScore(int nr) {
        return score[nr];
    }

    public static void main(String[] args) {
        Deck deck= new Deck(1);
        deck.shuffle();

        System.out.println(deck.getDeck().size());

        deck.draw(0);
        deck.draw(0);
        deck.draw(0);
        deck.draw(0);
        deck.draw(0);
        System.out.println(deck.krupier);
        System.out.println(deck.score[0]);

        System.out.println(deck.getDeck());
        System.out.println(deck.getDeck().size());

        BotPlayer bot = new BotPlayer(3,deck,4);
        System.out.println("test bota");
        System.out.println(bot.play());
        System.out.println(deck.score[4]);
        System.out.println(bot.play());
        System.out.println(deck.score[4]);
        System.out.println(bot.play());
        System.out.println(deck.score[4]);
        System.out.println(deck.getDeck());
        System.out.println(deck.getDeck().size());





    }
}
