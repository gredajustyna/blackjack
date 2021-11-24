package app.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Deck {
    List<Card> deck = new ArrayList<>();

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
            card = new Card("Król"+suits[0],10);
            deck.add(card);
            card = new Card("Dama"+suits[1],10);
            deck.add(card);
            card = new Card("Walet"+suits[2],10);
            deck.add(card);
            card = new Card("As"+suits[3],1);
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

    public List<Card> getDeck() {
        return deck;
    }

    public static void main(String[] args) {
        Deck deck= new Deck(2);
        deck.shuffle();
        System.out.println(deck.getDeck());
        System.out.println(deck.getDeck().size());

    }
}
