import java.util.ArrayList;
import java.util.Scanner;

class GoFish implements Playable {
  private ArrayList<Hand> hands;
  private Deck deck;
  private int playerCount;
  
  public GoFish(int playerCount) throws IllegalArgumentException {
    if (playerCount < 2) {
      throw new IllegalArgumentException("not enough players");
    }
    else if (playerCount > 10) {
      throw new IllegalArgumentException("too many players");
    }
    this.hands = new ArrayList<Hand>();
    this.deck = new Deck();
    this.playerCount = playerCount;
  }

  public void setup(){
    int numCards = playerCount == 2 ? 7 : 5;
    for(int i = 0; i < playerCount; i++) {
      Hand hand = new Hand();
      for (int j = 0; j < numCards; j++) {
        hand.dealCard(deck.getCard());
      }
      hands.add(hand);
    }
  }


  public ArrayList<Integer> getNumBooks() {
    ArrayList<Integer> numBooks = new ArrayList<Integer>();
    for (Hand hand : hands) {
      numBooks.add(hand.getBookCount());
    }
    return numBooks;
  }
  /*
  AL<I> nB = getNumBooks();
  [1, 3, 5] (P1 had 1 book, P2 had 3 books, P3 had 5 books)

  How many books were found?
  
  */
  /*
    HOMEWORK:
      - Create a method to handle whether the game is still in play or not (canPlay should be a good guide) [return a boolean]
  */
  public boolean canPlay() {
    if(deck.size() != 0) { // still has cards
      return true;
    }
    // No more cards in deck
    ArrayList<Integer> numBooks = getNumBooks();
    int bookCounter = 0;
    for (int i = 0; i < numBooks.size(); i++) {
      bookCounter += numBooks.get(i);
    }
    if (bookCounter == 13) { // no more books to find
      return false;
    }
    // no more cards left, not all books have been found yet
    for(int i = 0; i < hands.size(); i++) {
      Hand hand = hands.get(i);
      if(hand.size() == 0) {
        hand.bust(); // bust == no more cards
      }
      if(hand.size() % (numBooks.get(i) * 4) == 0) {
        hand.hold(); // no incomplete books left
      }
      // Check if any player is still active; if any is, return true.
      if(hand.getPlayerState() == PlayerState.ACTIVE) {
        return true;
      }
    }
    
    return false;
  }

  private void sleep(long ms){
    try {
      Thread.sleep(ms);
    } catch(InterruptedException e){
      
    }
  }

  public void clear() {
    System.out.print("\033[H\033[2J");
    System.out.println();
  }

  
  public void play(){
    Scanner input = new Scanner(System.in);
    int playerNumber = 0;
    while (canPlay()) {
      Hand currentPlayer = hands.get(playerNumber);
      System.out.printf("Player %d, your hand is %s. What player do you want to ask? ", playerNumber + 1, currentPlayer.toString());
      int playerToAsk = input.nextInt() - 1; // Handle offset
      while (playerToAsk >= hands.size()) {
        System.out.println("That's not a player, please try again: ");
        playerToAsk = input.nextInt() - 1;
      }
      while (hands.get(playerToAsk).getPlayerState() != PlayerState.ACTIVE) {
        System.out.println("That player is not active, please try another player: ");
        playerToAsk = input.nextInt() - 1;
      }
      while (playerNumber == playerToAsk) {
        System.out.println("you can't ask yourself for a card, please pick a different player");
        playerToAsk = input.nextInt() - 1;
      }
      System.out.print("What card would you like to ask for? ");
      String wantedCard = (input.next()).toUpperCase();
      while(! currentPlayer.containsCardValue(wantedCard)) {
        System.out.println("you don't have that card, please try a different one: ");
        wantedCard = (input.next()).toUpperCase();
      }
        Hand wantedHand = hands.get(playerToAsk);
      ArrayList<Card> cards = wantedHand.getCardsByValue(wantedCard);
      if (cards.size() > 0) {
        for (Card card : cards) {
          currentPlayer.addCard(card);
          wantedHand.removeCard(card);
        }
        System.out.printf("Player %d had %d of that card\n", playerToAsk + 1, cards.size());
      } else {
        System.out.println("Go fish");
        currentPlayer.dealCard(deck.getCard());
      }
      sleep(1500);
      clear();

      // go to the next player
      if (currentPlayer.getPlayerState() == PlayerState.ACTIVE) { // check if current player is still active
        if (currentPlayer.getBookCount() > 0) {
          if (currentPlayer.size() % (currentPlayer.getBookCount() * 4) == 0) { // if player has no more incomplete books remaining
              currentPlayer.hold(); // hold player
            }
        }    
      }
      int tempPN = playerNumber;
      playerNumber = (playerNumber + 1) % playerCount;
      while (hands.get(playerNumber).getPlayerState() != PlayerState.ACTIVE) {
        // what happens if no one is active
        playerNumber = (playerNumber + 1) % playerCount;
      }
      if(tempPN == playerNumber){
        System.out.println("Only 1 active player remaining");
        break;
      }
        // check if on last player
        /*
        if (hands.size() - 1 == playerNumber || (hands.size() - 2 == playerNumber && (hands.get(hands.size() - 1).getPlayerState() != PlayerState.ACTIVE) )){

          // if your last player is active OR (second last player is active AND last player is inactive)
          for (int i = 0; i < hands.size() - 2; i++) {
            if(hands.get(i).getPlayerState() == PlayerState.ACTIVE) {
              playerNumber = i;
              break;
            }
          }
        }
        // if not on last player
        else {
          if (hands.get(playerNumber + 1).getPlayerState() != PlayerState.ACTIVE) {//  check if next player is inactive; if they are, find the next active player and move the current player to that player
            int originalPlayerNumber = playerNumber;
            for (int i = playerNumber + 2; i < hands.size() - 1; i++) { // following player to last player
              if (hands.get(i).getPlayerState() == PlayerState.ACTIVE) {
                playerNumber = i;
                break;
              }
            }
            if(playerNumber == originalPlayerNumber) {
              // player number never changed, for loop naturally ended
              // no player after the current player is still active
              for (int i = 0; i < playerNumber; i++) {
                if (hands.get(i).getPlayerState() == PlayerState.ACTIVE) {
                  playerNumber = i;
                  break;
                }
              }
              // Check if a previous player is active still, change playerNumber to that.
              // If that for loop doesn't break and naturally ends, it means the only active player is itself.
              // Go from 0 to playerNumber
              if (playerNumber == originalPlayerNumber){
                System.out.println("Somehow you're the only player left... Game over buddy.");
                break;
                // game over, only one player left and their missing card is in the deck somehow
              }
            }
            
          } else {
            playerNumber += 1;
            // If the next player is active, just go to the next player
          }*/
      }
    }
  
}

