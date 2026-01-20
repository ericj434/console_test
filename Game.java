import java.util.Arrays;
import java.util.Scanner;
import java.io.*;

import pokemon.*;

public class Game{
  public static String[][] world = new String[16][24];
  public static String[][] currWorld = new String[16][24];
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
  
  public static Pokemon starter = new Charmander();
  public static boolean playerIn, mapIn, pickedPokemon;
  public static boolean adventureMode = true;
  public static int pxcor, pycor;
  public static int currWidth, currHeight;
  public static boolean showMoves = false;
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
      Pokemon charm = new Charmander();
      boolean game = true;
      while(game){
        starterMenu();
        currHeight = 0;
        currWidth = 0;
        if(adventureMode){
          System.out.print("\033[H\033[2J");
          worldCreation();
        }
      }
    }
    //System.out.println(moves.getMove(73));
    //System.out.print("\033[H\033[2J");
  }
  /*
  pick yo starter 
  */
  public static void starterMenu(){
    while(!pickedPokemon){
      Scanner input = new Scanner(System.in);
      System.out.println("PICK YOUR STARTER! (1, 2, 3)");
      enterIntoArray(new File("./imgs/charmander.txt"));
      enterIntoArray(new File("./imgs/squirtle.txt"));
      enterIntoArray(new File("./imgs/bulbasaur.txt"));
      display(battle);
      String ans = input.nextLine();
      if(Integer.parseInt(ans) == 1){
        starter = new Charmander();
        pickedPokemon = true;
      }
      else if(Integer.parseInt(ans) == 2){
        starter = new Squirtle();
        pickedPokemon = true;
      }
      else if(Integer.parseInt(ans) == 3){
        starter = new Bulbasaur();
        pickedPokemon = true;
      }
      else{
        currHeight = 0;
        currWidth = 0;
        System.out.print("\033[H\033[2J");
        System.out.println("Please pick a valid pokemon");
      }
    }
  }
  /*
  map stuff 
  */
  public static void worldCreation(){
    Scanner input = new Scanner(System.in);
    while(true){
      if(!mapIn){
        importMap(world);
        importMap(currWorld);
        mapIn = true;
      }
          
      System.out.println(" __      __            .__       .___\r\n" + //
                  "/  \\    /  \\___________|  |    __| _/\r\n" + //
                  "\\   \\/\\/   /  _ \\_  __ \\  |   / __ | \r\n" + //
                  " \\        (  <_> )  | \\/  |__/ /_/ | \r\n" + //
                  "  \\__/\\  / \\____/|__|  |____/\\____ | \r\n" + //
                  "       \\/                         \\/ ");
      System.out.println("------------------------------------------------------------------------");
      if(!playerIn){
        spawnPlayer();
        playerIn = true;
      }
      display(currWorld);
      checkBlue();
      checkRed();
      System.out.println("Enter (w: up, a: left, s: down, d: right): ");
      String move = input.nextLine();
      System.out.print("\033[H\033[2J");
      movePlayer(move);
    }
  }
  /*
  checks to see if you are on the blue square for healing 
  */
  public static void checkBlue(){
    if(world[pycor][pxcor].equals(BLUE_BOX))
      starter.heal();
  }
  /* 
  red for restoring pp to moves
  */
  public static void checkRed(){
    if(world[pycor][pxcor].equals(RED_BOX)){
      starter.restore();
    }
  }
  /*
  random encounter 
  */
  public static void randEncounter(){
    int key = (int) (Math.random() * 101);
    int whichOne = (int) (Math.random() * 4);
    Enemy opp;
    if(whichOne == 1){
      opp = new EnemyChar();
    }
    else if(whichOne == 1){
      opp = new EnemyBulb();
    }
    else{
      opp = new EnemyBulb();
    }
    if(key > 70){
      battle(starter, opp);
    }
  }
  /*
  moves the player 
  */
  public static void movePlayer(String dir){
    if(dir.equals("w")){
      if(!(pycor - 1 < 0) && !currWorld[pycor - 1][pxcor].equals(GREY_BOX)){
        currWorld[pycor][pxcor] = world[pycor][pxcor];
        currWorld[pycor - 1][pxcor] = WHITE_BOX;
        pycor--;
        randEncounter();
      }
      else{
        System.out.println("You can't go up!");
      }
    }
    else if(dir.equals("a")){
      if(!(pxcor - 1 < 0) && !currWorld[pycor][pxcor - 1].equals(GREY_BOX)){
        currWorld[pycor][pxcor] = world[pycor][pxcor];
        currWorld[pycor][pxcor - 1] = WHITE_BOX;
        pxcor--;
        randEncounter();
      }
      else{
        System.out.println("You can't go left!");
      }
    }
    else if(dir.equals("s")){
      if(!(pycor + 1 > 15) && !currWorld[pycor + 1][pxcor].equals(GREY_BOX)){
        currWorld[pycor][pxcor] = world[pycor][pxcor];
        currWorld[pycor + 1][pxcor] = WHITE_BOX;
        pycor++;
        randEncounter();
      }
      else{
        System.out.println("You can't go down!");
      }
    }
    else if(dir.equals("d")){
      if(!(pxcor + 1 > 23) && !currWorld[pycor][pxcor + 1].equals(GREY_BOX)){
        currWorld[pycor][pxcor] = world[pycor][pxcor];
        currWorld[pycor][pxcor + 1] = WHITE_BOX;
        pxcor++;
        randEncounter();
      }
      else{
        System.out.println("You can't go left!");
      }
    }
    else{
      System.out.println("Invalid movement!");
    }
  }

  /*
  spawns the player 
  */
  public static void spawnPlayer(){
    currWorld[8][12] = WHITE_BOX;
    pxcor = 12;
    pycor = 8;
  }
  /*
  importing mapppppp 
  */
  public static void importMap(String[][] arr){
    File mapFile = new File("./imgs/map.txt");
    int currRow = 0;
    try{
      Scanner scansMap = new Scanner(mapFile);
      while(scansMap.hasNextLine()){
        String line = scansMap.nextLine();
        String[] lineSplit = line.split(" ");
        for(int i = 0; i < arr[currRow].length; i++){
          arr[currRow][i] = letterToBlock(lineSplit[i]);
        }
        currRow++;
      }
      scansMap.close();
    }catch(FileNotFoundException e){
      System.out.println("There's no file");
    }
  }




  /*
  CAUTION BELOW THIS POINT IS ALL MY WEIRD BATTLE RELATED STUFF + THE TRANSLATIONS FOR BLOCKS
  REALLY BAD CODE  
  */


  /*
  battle game loop 
  */
  public static void battle(Pokemon you, Enemy enemy){
    Scanner input = new Scanner(System.in);
    adventureMode = false;
    Pokemon enemyPokemon = (Pokemon) enemy;
    while(!checkAlive(you, enemyPokemon)){
      System.out.print("\033[H\033[2J");
      displayBattleScreen(you, enemy);
      if(adventureMode == true){
        System.out.print("\033[H\033[2J");
        currHeight = 0;
        currWidth = 0;
        break;
      }
      if(showMoves){
        System.out.println("Input the move (number) (type \"back\" to go back): ");
        String move = input.nextLine();
        try{
          if(you.getSpeed() > enemyPokemon.getSpeed()){
            you.atk(Integer.parseInt(move) - 1, enemyPokemon);
            enemy.atk(you);
          }
          else{
            enemy.atk(you);
            you.atk(Integer.parseInt(move) - 1, enemyPokemon);
          }
        }catch(NumberFormatException e){
          act(move, enemyPokemon);
        }
      }
      else{
        System.out.println("Enter the word of your action (all lowercase): ");
        String action = input.nextLine();
        act(action, you);
      }

      currHeight = 0;
      currWidth = 0;
      status = new String[2][72];
      //System.out.print("\033[H\033[2J");
    }
    showMoves = false;
    adventureMode = true;
  }

  /*
  general display for all the 'display' functions 
  */

  public static void displayBattleScreen(Pokemon home, Enemy away){
    displayBattle(away);
    System.out.println("\u001b[48;5;46m------------------------------------------------------------------------\u001b[0m");
    displayName(home,(Pokemon) away);
    displayHp(home, (Pokemon) away);
    if(!showMoves)
      displayAction(home);
    else
      displayMoves(home);
          
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
  public static void displayBattle(Enemy opp){
    displayImage(starter.getName());
    enterIntoArray(new File("./imgs/VS.txt"));
    displayImage(opp.getName());
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
  public static void displayMoves(Pokemon pokemon){
    String move0, move1, move2, move3;
    showMoves = true;
    if(pokemon.displayMove(0) != null){
      move0 = "1. " + pokemon.displayMove(0);
    }
    else{
      move0 = "1. NO MOVE";
    }

    if(pokemon.displayMove(1) != null){
      move1 = "2. " + pokemon.displayMove(1);
    }
    else{
      move1 = "2. NO MOVE";
    }

    if(pokemon.displayMove(2) != null){
      move2 = "3. " + pokemon.displayMove(2);
    }
    else{
      move2 = "3. NO MOVE";
    }

    if(pokemon.displayMove(3) != null){
      move3 = "4. " + pokemon.displayMove(3);
    }
    else{
      move3 = "4. NO MOVE";
    }

    for(int i = 0; i < action[1].length; i++){
      action[1][i] = " ";
    }
    for(int i = 0; i < action[2].length; i++){
      action[2][i] = " ";
    }
    action[1][1] = move0;
    action[1][4] = move1;
    action[2][1] = move2;
    action[2][4] = move3;
  }


  /*
  takes in the choices the player makes at the action menu of the battle 
  */
  public static void act(String action, Pokemon other){
    if(action.equals("fight")){
      displayMoves(starter);
    }
    else if(action.equals("run")){
      int key = (int) (Math.random() * 101);
      int target = (int) (starter.getSpeed() / other.getSpeed());
      if(starter.getSpeed() > other.getSpeed()){
        showMoves = false;
        adventureMode = true;
        System.out.println("You escaped!");
      }
      else{
        int chance = (((int) (starter.getSpeed() * 128 / other.getSpeed()) + 30) % 256) * 100;
        if(key < chance){
          showMoves = false;
          adventureMode = true;
          System.out.println("You escaped!");
        }
        else{
          System.out.println("You failed to escape!");
        }
      }
    }
    else if(action.equals("back")){
      showMoves = false;
      displayAction(starter);
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
    else if(letter.equals("gr")){
      return GREY_BOX;
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
      gainExp(you);
      you.levelUp(you.getExp());
      return true;   
    }
    else{
      return false;
    }
  }

  /*
  real pokemon logic is hard, i made a super simple exp gain method, really easy to understand
  follows formula level * 5 
  */
  public static void gainExp(Pokemon home){
    //home.setExp(home.getExp() + home.getLevel() * 5);
    home.setExp(home.getExp() + home.getLevel() * 5);
  }
}
