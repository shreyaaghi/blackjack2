import java.util.ArrayList;
import java.util.HashMap;
class Hand {
  private PlayerState state;
  private ArrayList<Card> hand;
  
  public Hand() {
    this.hand = new ArrayList<Card>();
    this.state = PlayerState.ACTIVE;
  }
  
  public void dealCard(Card card) {
    hand.add(card);
  }

  // public List<Card> getHand(){
  //   return Collections.unmodifiableList(hand);
  // }

  
  private boolean containsAce(){
    for (int i = 0; i < hand.size(); i++) {
      Card card = hand.get(i);
      if (card.getValue().equals("A")) {
        return true;
      }
    }
    return false;
  }

  public int size() {
    return hand.size();
  }
  
  public int getHandValue(){
    int cardSum = 0;
    for (int i = 0; i < hand.size(); i++) {
      Card card = hand.get(i);
      cardSum += card.getCardValue();
    }
    if (cardSum > 21 && containsAce()) {
      return cardSum - 10;
    }
    return cardSum;
  }

  
  public int getBookCount() {
    // https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/HashMap.html
    int bookCount = 0;
    HashMap<String, Integer> cardCounter = new HashMap<String, Integer>();
    for (Card card : hand) {
      String cardValue = card.getValue();
      cardCounter.putIfAbsent(cardValue, 0);
      cardCounter.put(cardValue, cardCounter.get(cardValue) + 1);
    }
    for (int count : cardCounter.values()) {
      if (count == 4) {
        bookCount += 1;
      }
    }
    return bookCount;
  }

  public ArrayList<Card> getCardsByValue(String value){
    ArrayList<Card> cards = new ArrayList<>();
    for (Card card: hand) {
      if (card.getValue().equalsIgnoreCase(value)) {
        cards.add(card);
      }
    }
    return cards;
  }

  public boolean containsCardValue(String value){
    // TODO check if the card value is found in the hand
    for (Card card: hand) {
      if (card.getValue().equalsIgnoreCase(value)) {
        return true;
      }
    }
    return false;
  }

  public boolean removeCard(Card card) {
    if(hand.contains(card)) {
      hand.remove(card);
      return true;
    }
    return false;
  }

  public void addCard(Card card){
    this.dealCard(card);
  }

  private void updateState(boolean held){
    int check = getHandValue();
    if (check > 21) {
      state = PlayerState.BUSTED;
    }
    else if (held) {
      state = PlayerState.HELD;
    }
  }
  
  public void updateState(){
    this.updateState(false);
  }

  public void hold(){
    this.updateState(true);
  }

  public void bust(){
    state = PlayerState.BUSTED;
  }

  public PlayerState getPlayerState(){
    return this.state;
  }
  
  @Override
  public String toString() {
    StringBuilder str = new StringBuilder();
    for(Card card : hand){
      str.append(card);
      str.append(" ");
    }
    str.deleteCharAt(str.length() - 1);
    return str.toString();
  }
}