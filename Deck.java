import java.util.ArrayList;

class Deck {
  private ArrayList<Card> cards;

  public Deck() {
    this.cards = this.generateDeck();  
  }

  private ArrayList<Card> generateDeck(){
    ArrayList<Card> deck = new ArrayList<Card>();
    String[] suits = {"♠", "♥", "♣", "♦"};
    String[] values = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
    for (String suit: suits) {
      for (String value: values) {
        Card card = new Card(suit, value);
        deck.add(card);
      }
    }
    return deck;
  }

  public Card getCard(){
    int i = (int)(Math.random() * this.cards.size());
    return this.cards.remove(i);
  }

  public ArrayList<Card> getDeck(){
    return this.cards;
  }

  @Override
  public String toString(){
    return this.cards.toString();
  }

  public int size(){
    return this.cards.size();
  }
  
}