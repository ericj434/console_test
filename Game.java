import java.util.Scanner;
import java.io.*;

import pokemon.*;

public class Game{
  public static String[][] battle = new String[8][24]; 
  public static String[][] status = new String[2][72];
  public static String[][] action = new String[4][24];
  
  public static final String RED_BOX = "\u001B[41m   \u001B[0m";
  public static final String GREEN_BOX = "\u001B[42m   \u001B[0m";
  public static final String BLUE_BOX = "\u001B[44m   \u001B[0m";
  public static final String BLACK_BOX = "\u001B[40m   \u001B[0m";
  public static final String WHITE_BOX = "\u001B[47m   \u001B[0m";
  public static final String DARK_GREEN_BOX = "\u001B[48;5;28m   \u001B[0m";
  public static final String GREY_BOX = "\u001b[48;5;239m   \u001b[0m";
  

  public static int currWidth, currHeight;

  public static final int IMG_WIDTH = 7;
  public static final int IMG_HEIGHT = 7;
  public static void main(String[] args){
    //displayImage("charmander");
    //enterIntoArray(new File("./imgs/VS.txt"));
    //displayImage("charmander");
    //display();
    System.out.print("\033[H\033[2J");
    System.out.println("____ ____ __ _ ____ _    ____ __     ____ ____ ____ _    ____ __   ___  __   \r\n" + //
            "| . \\|   || V \\| __\\|\\/\\ |   || \\|\\  |_ _\\| __\\| . \\|\\/\\ |___\\| \\|\\|  \\ | |  \r\n" + //
            "| __/| . ||  <_|  ]_|   \\| . ||  \\|    || |  ]_|  <_|   \\| /  |  \\|| . \\| |__\r\n" + //
            "|/   |___/|/\\_/|___/|/v\\/|___/|/\\_/    |/ |___/|/\\_/|/v\\/|/   |/\\_/|/\\_/|___/");
    MoveList moves = new MoveList();
    Scanner input = new Scanner(System.in);
    System.out.println("Press 1 to start");
    String number = input.nextLine();
    if(Integer.parseInt(number) == 1){
      System.out.print("\033[H\033[2J");
      EnemyChar enemy1 = new EnemyChar();
      Pokemon charm = new Charmander();
      boolean game = true;
      while(game){
        if(checkAlive(charm, enemy1)){
          if(enemy1.getCurrHp() <= 0){
            System.out.println("YOU WIN");
            game = false;
          }
          else{
            System.out.println("YOU LOSE");
            game = false;
          }
        }
        else{


          displayBattleScreen(charm, enemy1);


          System.out.println("Select an action: ");
          String num = input.nextLine();
          charm.atk(Integer.parseInt(num), enemy1);
          enemy1.atk(charm);
          currHeight = 0;
          currWidth = 0;
          status = new String[2][72];
          System.out.print("\033[H\033[2J");
        }
        
      }
    }
    //System.out.println(moves.getMove(73));
    //System.out.print("\033[H\033[2J");
  }

  /*
  general display for all the 'display' functions 
  */

  public static void displayBattleScreen(Pokemon home, Pokemon away){
    displayBattle();
    System.out.println("\u001b[48;5;46m------------------------------------------------------------------------\u001b[0m");
    displayName(home, away);
    displayHp(home, away);
    displayAction(home);
          
    display(status);
    display(action);
  }

  /*
  prints out the elements of all the possible "pixels" inside of any 2d array
  tldr simple print function for 2d arrs 
  */
  public static void display(String[][] arr){
    for(String[] row : arr){
      for(String pixel : row){
        System.out.print(pixel);
      }
      System.out.println();
    }
  }

  /*
  code used to display the image of the pokemon and also the vs symbol inbetween
  two pokemonss 
  */
  public static void displayBattle(){
    displayImage("charmander");
    enterIntoArray(new File("./imgs/VS.txt"));
    displayImage("charmander");
    display(battle);
  }
  /*
  display the status of the pokemon into the array declared as status
  used for only the first row of the status array
  second row will be used for hp 
  */
  public static void displayName(Pokemon home, Pokemon away){
    // first name is added here, we loop through the array to input chars
    for(int i = 0; i < home.toString().length(); i++){
      status[0][i] = String.valueOf(home.toString().toUpperCase().charAt(i));
    }


    /*
    we decided to create the variable so that we can have a proper indexing
    from the back to the end. We can't use the i in the for loop because that would make 
    it so that we have index out of bounds error 
    */

    int nameLength = away.toString().length() - 1;
    // same but for opp, we start from last index because we want it to be backwards
    for(int i = status[0].length - 1; i > status[0].length - away.toString().length() - 1; i--){
      status[0][i] = String.valueOf(away.toString().toUpperCase().charAt(nameLength));
      nameLength--;
    }
    //replaces the remaining 'null' with spaces, looks prettier
    for(int i = 0; i < status[0].length; i++){
      if(status[0][i] == null){
        status[0][i] = " ";
      }
    }
  }
  /*
  this will add to the second row of the array, displays the health status of the pokemon 
  */
  /*
                                |
                                |
  {GGGGGGGGGGGBBBBBBBBB} 
  */
  public static void displayHp(Pokemon home, Pokemon away){
    int lengthHealthHome = calculatePercentage(home);
    status[1][0] = "{";
    for(int i = 1; i < 11; i++){
      if(lengthHealthHome != 0){
        status[1][i] = GREEN_BOX;
        lengthHealthHome--;
      }
      else{
        status[1][i] = RED_BOX;
      }
    }
    status[1][11] = "}";

    int lengthHealthAway = calculatePercentage(away);
    status[1][status[1].length - 12] = "{";
    for(int i = status[1].length - 11; i < status[1].length - 1; i++){
      if(lengthHealthAway != 0){
        status[1][i] = GREEN_BOX;
        lengthHealthAway--;
      }
      else{
        status[1][i] = RED_BOX;
      }
    }
    status[1][status[1].length - 1] = "}";
    for(int i = 0; i < status[1].length; i++){
      if(status[1][i] == null && i % 5 == 0){
        status[1][i] = " ";
      }
      else if(status[1][i] == null){
        status[1][i] = "";
      }
    }
  }

  /*
  helper function for our displayHp() method, calculates hp based on percentages 
  */
  public static int calculatePercentage(Pokemon pokemon){
    return (int) (pokemon.getCurrHp() / pokemon.getHp() * 10);
  }

  public static void displayImage(String pokemon){
    if(pokemon.equals("charmander")){
      File img = new File("./imgs/charmander.txt");
      enterIntoArray(img);
    }
    else if(pokemon.equals("bulbasaur")){
      File img = new File("./imgs/bulbasaur.txt");
      enterIntoArray(img);
    }
    else if(pokemon.equals("squirtle")){
      File img = new File("./imgs/squirtle.txt");
      enterIntoArray(img);
    }
  }

  /*
  displays the actions that you can do in a pokemon batle (fight or run) 
  */
  public static void displayAction(Pokemon pokemon){
   for(int i = 0; i < action[0].length; i++){
    action[0][i] = GREY_BOX;
   }
   String fight = "| 1. FIGHT";
   String run = "| 2. RUN";
   for(int i = 0; i < fight.length(); i++){
    action[1][i] = fight.substring(i, i + 1);
   }
   for(int i = 0; i < run.length(); i++){
    action[2][i] = run.substring(i, i + 1);
   }
   for(int i = 0; i < action[0].length; i++){
    action[3][i] = GREY_BOX;
   }
   for(int i = 0; i < action.length; i++){
    for(int j = 0; j < action[i].length; j++){
      if(action[i][j] == null){
        action[i][j] = "";
      }
    }
   }
  }

  /*
  when the player enters 1 during the action menu, we display the possible moves 
  */
  public void displayMoves(Pokemon pokemon){
    Pokemon clonePokemon = null;
    try {
      clonePokemon = (Pokemon) pokemon.clone();
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
    }
    Pokemon testingMoves = new Charmander();
    if(clonePokemon.atk(0, testingMoves)){

    }
  }


  /*
  takes in the choices the player makes at the action menu of the battle 
  */
  public void act(String action){
    if(action.equals("fight")){

    }
    else if(action.equals("run")){

    }
    else if(action.equals("back")){
      
    }
    else{
      System.out.println("Please enter a valid action");
    }
  }
  /*
  this code is strictly used for the battle array
  which is 8x24 and seperates into 3 8x8 
  */
  public static void enterIntoArray(File img){
    int origWidth = currWidth;
    try{
      Scanner scansImg = new Scanner(img);
      while(scansImg.hasNext()){
        String pixel = scansImg.next();
        battle[currHeight][currWidth] = letterToBlock(pixel);
        if(currHeight == IMG_HEIGHT && currWidth == origWidth + IMG_WIDTH){
          currHeight = 0;
          currWidth++;
        }
        else if(currWidth == origWidth + IMG_WIDTH){
          currWidth = origWidth;
          currHeight++;
        }
        else{
          currWidth++;
        }
        
      }
      scansImg.close();
    } catch(IOException e){
      System.out.println("No such files found");
    }
 
  }
  



  public static String letterToBlock(String letter){
    if(letter.equals("R")){
      return RED_BOX;
    }
    else if(letter.equals("B")){
      return BLACK_BOX;
    }
    else if(letter.equals("b")){
      return BLUE_BOX;
    }
    else if(letter.equals("W")){
      return WHITE_BOX;
    }
    else if(letter.equals("G")){
      return GREEN_BOX;
    }
    else if(letter.equals("g")){
      return DARK_GREEN_BOX;
    }
    return null;
  }
  

  public static boolean checkAlive(Pokemon you, Pokemon opp){
    if(you.getCurrHp() <= 0){
      System.out.println("Your " + you.getName() + " has fainted");
      return true;     
    }
    else if(opp.getCurrHp() <= 0){
      System.out.println("The opposing " + opp.getName() + " has fainted");
      return true;   
    }
    else{
      return false;
    }
  }
}
