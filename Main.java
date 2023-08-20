import java.util.Scanner;
class Main {
  public static void playBlackJack(){
    Scanner userInput = new Scanner(System.in);
    Playable game;
    try { 
      System.out.println("Type sp for single player or mp for multiplayer: ");
      String userChoice = userInput.nextLine();
      if (userChoice.equalsIgnoreCase("sp")) {
        game = new SinglePlayerGame();
      }
      else if (userChoice.equalsIgnoreCase("mp")) {
        System.out.println("How many players: ");
        int userChoice2 = userInput.nextInt();
        while (userChoice2 < 2 || userChoice2 > 20){
          System.out.println("invalid number. please reenter: ");
          userChoice2 = userInput.nextInt();
        }
        game = new BlackJack(userChoice2);
      } else {
        System.out.println("No mode input, defaulting to single player");
        game = new SinglePlayerGame();
      }
      game.setup();
      game.play();
    } 
    catch (IllegalArgumentException e){
      System.out.printf("Errored: %s\n", e.getMessage());
    }
  }

  public static void playGoFish(){
    Scanner userInput = new Scanner(System.in);
    Playable game;
    try { 
      System.out.println("How many players?");
      int userChoice = userInput.nextInt();
      while (userChoice < 2 || userChoice > 10){
          System.out.println("invalid number. please reenter: ");
          userChoice = userInput.nextInt();
      }
      game = new GoFish(userChoice);
      
      game.setup();
      game.play();
    } 
    catch (IllegalArgumentException e){
      System.out.printf("Errored: %s\n", e.getMessage());
    }
    // System.out.println("Hello World");
    // try {
    //   Thread.sleep(1000);
    // } catch(InterruptedException e){
    //   System.out.printf("Errored: %s\n", e.getMessage());
    // }
    // System.out.print("\033[H\033[2J");  
  }
  
  public static void main(String[] args) {
    playGoFish();
  }
}
/*
TODO
- Inheritance / Interface discussion
- Exception handling (Run v Checked)
*/