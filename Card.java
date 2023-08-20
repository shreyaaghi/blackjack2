/*
color: red or black; 
suit: spades, hearts, diamonds, clubs
value: 1-13 (A, J, Q, K, 2-10)
*/
class Card {
  // Field Variables / Members
  private String suit;
  private String value; // --> "3" -- "A"
  private int cardValue; // --> 3 -- 1

  // Constructor
  public Card(String suit, String value) {
    this.suit = suit;
    this.value = value;
    this.cardValue = this.calculateCardValue();
  }

  // Methods
  // Getter
  public String getSuit() {
    return this.suit;
  }
  public String getValue() {
    return this.value;
  }
  public int getCardValue() {
    return this.cardValue;
  }
  
  // Setter
  public void setSuit(String suit) {
    this.suit = suit;
  }
  public void setValue(String value) {
    this.value = value;
    this.cardValue = this.calculateCardValue();
  }

  // toString
  @Override
  public String toString(){
    return this.value + this.suit;
  }

  // Other methods
  private int calculateCardValue(){
    String val = this.value;
    if (val.equalsIgnoreCase("K") || val.equalsIgnoreCase("Q") || val.equalsIgnoreCase("J")) {
      return 10;
    }
    else if(val.equalsIgnoreCase("A")) {
      return 11;
    }
    else {
      int parsedVal = Integer.parseInt(val);
      return parsedVal;
    }
    /*
      Get the value of the card
      Check if it's an K, Q, J
        Return 10 if any of these are true.
      Check if it's an A
        Return 1 or 11 if this is true (your choice, we'll do something about this later with our "Hand" class)
      Otherwise
        Call Integer.parseInt and pass in the value
        Return the parsed value
    */
  } 
}