import java.util.ArrayList;
import java.util.Scanner;

class SinglePlayerGame extends BlackJack {
  public SinglePlayerGame() {
    super(2);
  }
  private boolean canPlay(Hand hand) {
    return hand.getPlayerState() == PlayerState.ACTIVE;
  }

  @Override
  public void play() {
    System.out.println("From Single Player");
    ArrayList<Hand> hands = super.getHands();
    Deck deck = super.getDeck();
    Scanner input = new Scanner(System.in);
    while (this.canPlay(hands.get(0))) {
      hands = super.getHands();
      deck = super.getDeck();
      System.out.printf("Player 1, your hand is %s. Do you want to hit or hold? ", hands.get(0).toString());
      String playerInput = input.next();
      Hand hand = hands.get(0);
      if (playerInput.equalsIgnoreCase("hold")) {
        hand.hold();
        break;
      }
      else if(playerInput.equalsIgnoreCase("hit")) {
        hand.dealCard(deck.getCard());
        hand.updateState();
        if (hand.getPlayerState() == PlayerState.BUSTED) {
          System.out.println("Player 1, you busted! L");
          break;
        }
      }
    }
    Hand hand = hands.get(0);
    Hand cpu = hands.get(1);
    if (hand.getPlayerState() == PlayerState.BUSTED) {
      cpu.hold();
    }
    while (cpu.getPlayerState() == PlayerState.ACTIVE) {
      if (cpu.getHandValue() < 16) {
        cpu.dealCard(deck.getCard());
        cpu.updateState();
        if (cpu.getPlayerState() == PlayerState.BUSTED) {
          break;
        }
      }
      else if (cpu.getHandValue() > 17) {
        cpu.hold();
      }
    }
    if(this.hasWinner()) {
      int winningHandIdx = winningHand();
      if (winningHandIdx != -1) {
        System.out.println(winningHandIdx == 0 ? "Player wins" : "CPU wins");
      }
      else {
        System.out.println("tie game");
        System.out.printf("player hand: %s\nCPU hand: %s\n", hands.get(0), hands.get(1));
      }
    }
  }
}