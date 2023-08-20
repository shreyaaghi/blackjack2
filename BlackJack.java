import java.util.Scanner;
import java.util.ArrayList;
class BlackJack implements Playable {
  // Declare global/field variables here
  private Deck deck;
  private ArrayList<Hand> hands;
  private int playerCount;
  
  public BlackJack(int playerCount) throws IllegalArgumentException {
    if (playerCount < 2) {
      throw new IllegalArgumentException("Not enough players");
    }
    else if (playerCount > 20) {
      throw new IllegalArgumentException("Too many players");
    }
    // Initialize in Constructor/Initializer
    this.hands = new ArrayList<Hand>();
    this.deck = new Deck();
    this.playerCount = playerCount;
  }
  
  public void setup(){
    for (int i = 0; i < playerCount; i++) {
      Hand hand = new Hand();
      hand.dealCard(deck.getCard());
      hand.dealCard(deck.getCard());
      this.hands.add(hand);
    }
  }

  protected ArrayList<Hand> getHands(){
    return this.hands;
  }
  
  protected Deck getDeck(){
    return this.deck;
  }

  private boolean canPlay(){
    for (int i = 0; i < hands.size(); i++) {
      Hand hand = hands.get(i);
      if (hand.getPlayerState() == PlayerState.ACTIVE) {
        return true;
      }
    }
    return false;
  }

  protected ArrayList<Integer> getHeldHands() {
    ArrayList<Integer> heldHands = new ArrayList<Integer>();
    for (int i = 0; i < hands.size(); i++) {
      Hand hand = hands.get(i);
      if (hand.getPlayerState() == PlayerState.HELD) {
        heldHands.add(i);
      }
    }
    return heldHands;
  }

  protected boolean hasWinner(){
    return getHeldHands().size() != 0;
  }

  protected int winningHand(){
    ArrayList<Integer> heldHands = this.getHeldHands();
    int index = 0;
    int handValue = 0;
    for (int i: heldHands) {
      Hand hand = hands.get(i);
      if (hand.getHandValue() > handValue) {
        index = i; 
        handValue = hand.getHandValue();
      }
    }

    int secondIndex = 0;
    int secondHandValue = 0;
    heldHands.remove(heldHands.indexOf(index));
    for (int i: heldHands) {
      Hand hand = hands.get(i);
      if (hand.getHandValue() > secondHandValue) {
        secondIndex = i;
        secondHandValue = hand.getHandValue();
      }
    }
    if (handValue == secondHandValue) {
      return -1;
    }
    return index;
  }
  protected ArrayList<Integer> tiedHands() {
    ArrayList<Integer> heldHands = this.getHeldHands();
    ArrayList<Integer> tiedHands = new ArrayList<>();
    int winningHandValue = this.hands.get(winningHand()).getHandValue();
    for (int i: heldHands) {
      if (winningHandValue == hands.get(i).getHandValue()) {
        tiedHands.add(i);
      }
    }  
    return tiedHands;
  }

  public void play(){
    int playerNumber = 0;
    Scanner input = new Scanner(System.in);
    while (canPlay()) {
      System.out.printf("Player %d, your hand is %s. Do you want to hit or hold? ", playerNumber + 1, hands.get(playerNumber).toString());
      String playerInput = input.next();
      Hand hand = hands.get(playerNumber);
      if (playerInput.equalsIgnoreCase("hold")) {
        hand.hold();
        playerNumber += 1;
      }
      else if(playerInput.equalsIgnoreCase("hit")) {
        hand.dealCard(deck.getCard());
        hand.updateState();
        if (hand.getPlayerState() == PlayerState.BUSTED) {
          System.out.printf("Player %d, you busted! L\n", playerNumber + 1);
          playerNumber += 1;
        }
      }
    }
    if(this.hasWinner()){
      int winningHandIdx = winningHand();
      if (winningHandIdx != -1) {
        System.out.printf("Player %d has won.\n", winningHandIdx + 1);
      }
      else {
        System.out.println("tie game");
        ArrayList<Integer> tiedHandIdx = this.tiedHands();
        StringBuilder str = new StringBuilder();
        str.append("Tied Hands: ");
        for (int i: tiedHandIdx) {
          str.append(i + 1);
          str.append(" ");
        }
        str.deleteCharAt(str.length() - 1);
        System.out.println(str);
      }
    }
    else {
      System.out.println("All hands busted, no winner");
    }
    
  }

  @Override
  public String toString(){
    return hands.toString();
  }
}
/*
Collections:

Array: Indexed, set only, no adding or removing

Map: Key-Value Pairs, addable, removable
List: Indexed, addable, removable
Set: Indexed, addable, removable, unique values - no repeats

HashSet: Uses a hash to check
TreeSet: Sorted

HashMap: Hashes the keys and stores it that way
TreeMap: Sorted

LinkedList: Specific implementation of List, uses Nodes (We'll come back to this much much much later)
ArrayList: Basically an Array, but extendable


*/