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
        return "Card{" +
                "name='" + name + '\'' +
                '}';
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
}
