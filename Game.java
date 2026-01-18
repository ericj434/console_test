import java.util.Scanner;
import java.io.*;

import pokemon.*;

public class Game{
  public static String[][] battle = new String[8][24];
  
  public static final String RED_BOX = "\u001B[41m   \u001B[0m";
  public static final String GREEN_BOX = "\u001B[42m   \u001B[0m";
  public static final String BLUE_BOX = "\u001B[44m   \u001B[0m";
  public static final String BLACK_BOX = "\u001B[40m   \u001B[0m";
  public static final String WHITE_BOX = "\u001B[47m   \u001B[0m";

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
          System.out.println(charm.getName() + " HP: " + charm.getCurrHp());
          System.out.println(enemy1.getName() + " HP: " + enemy1.getCurrHp());
          System.out.println("enter etk: ");
          String atk = input.nextLine();
          charm.atk(Integer.parseInt(atk), enemy1);
          enemy1.atk(charm);
        }
      }
    }
    //System.out.println(moves.getMove(73));
    //System.out.print("\033[H\033[2J");
  }

  public static void display(){
    for(String[] row : battle){
      for(String pixel : row){
        System.out.print(pixel);
      }
      System.out.println();
    }
  }
  
  //public static void displayHp(Pokemon pokemon){}

  //public static void displayName(Pokemon pokemon){}

  public static void displayImage(String pokemon){
    if(pokemon.equals("charmander")){
      File img = new File("./imgs/charmander.txt");
      enterIntoArray(img);
    }
    else if(pokemon.equals("bulbasaur")){
      return;
    }
    else{
      return;
    }
  }

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
    return null;
  }
  
  public static void update(){

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
