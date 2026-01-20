package pokemon;

import java.util.Arrays;


public class Squirtle extends Pokemon{
    /*
    move bank for ALL possible moves for a pokemon up to level 20(here for charmander)
    unlocks moves based on level requirements
    lvl 1 lvl 1 lvl 8 lvl 15
    */
    private Move[] possibleMoves = {this.getMoveList().getMove("Tackle"), this.getMoveList().getMove("Tail Whip"), this.getMoveList().getMove("Bubble"), this.getMoveList().getMove("Water Gun")};
    
    /* 
    base number of attack at level one, will add as they level up
    */    
    private Move[] currMoves = {this.getMoveList().getMove("Tackle"), this.getMoveList().getMove("Tail Whip"), null, null};


    //constructor
    //init all the stats
    public Squirtle(){
        super(39.0, 60.0, 43.0, 52.0, "squirtle", "water");
    }
    
    /*
    LIST OF METHODS:
    1) atk(int index, Pokemon other): this method is used for calculating the attack of all the moves in currMoves
       atk() --> String (move name to print)
    2) the skill based methods are used to do something to another pokemon, we made a lot of them

    */
    /*
    restore pp for all current moves 
    */
    public void restore(){
      for(int i = 0; i < 4; i++){
        if(currMoves[i] != null){
          currMoves[i].setCurrPp(currMoves[i].getPp());
        }
      }
    }
    /*
    TACKLE: 
    Type: Normal Category: Physical Power: 40 Accuracy: 100 PP: 35
    N/A
    */
    public int tackle(Pokemon other){
        return atkCalc(currMoves[0], other);
    }
    /*
    TAIL WHIP: 
    Type: Normal Category: Status Power: -1 Accuracy: 100 PP: 30
    Lowers opponent's Defense.
    */
    public void tailWhip(Pokemon other){
      other.setStageDefense(other.getStageDefense() - 1);
      other.setCurrDefense(other.getCurrDefense() * 2 /(Math.abs(other.getStageDefense()) + 2));;
    }
    /* 
    BUBBLE: 
    Type: Water Category: Special Power: 40 Accuracy: 100 PP: 30
    May lower opponent's Speed.
    */
    public int bubble(Pokemon other){
      return atkCalc(currMoves[2], other);
    }
    /*
    WATER GUN: 
    Type: Water Category: Special Power: 40 Accuracy: 100 PP: 25
    N/A 
    */
    public int waterGun(Pokemon other){
      return atkCalc(currMoves[3], other);
    }
    /*
    heres the atk method, calls every other method to do damage
    or supp sometimes to the other pokemons 
    */
    public void atk(int index, Pokemon other){
        if(index == 0 && currMoves[0].getPp() > 0){
          other.setCurrHp(other.getCurrHp() - tackle(other));
          currMoves[0].setCurrPp(currMoves[0].getPp() - 1);
          System.out.println(this.getName() + " used " + currMoves[0].getName() + "!" );
        }
        else if(index == 1 && currMoves[1].getPp() > 0){
          tailWhip(other);
          currMoves[1].setCurrPp(currMoves[1].getPp() - 1);
          System.out.println(this.getName() + " used " + currMoves[1].getName() + "!" );
        }
        else if(index == 2){
          if(!(currMoves[2] == null) && currMoves[2].getPp() > 0){
            other.setCurrHp(other.getCurrHp() - bubble(other));
            currMoves[2].setCurrPp(currMoves[2].getPp() - 1);
            System.out.println(this.getName() + " used " + currMoves[2].getName() + "!" );
          }
        }
        else if(index == 3){
          if(!(currMoves[3] == null) && currMoves[3].getPp() > 0){
            other.setCurrHp(other.getCurrHp() - waterGun(other));
            currMoves[3].setCurrPp(currMoves[3].getPp() - 1);
            System.out.println(this.getName() + " used " + currMoves[3].getName() + "!" );
          }
        }
        else{
          System.out.println("You can't use that move");
        }
    }
    /* 
    used to display moves and pp levels in the battle screen
    */

    public String displayMove(int index){
      if(currMoves[index] != null)
        return currMoves[index].getName().toUpperCase() + " (" + currMoves[index].getPp() + " pp)";
      return null; 
    }

    /*
    new moves so cool, you get new moves when u reach the level req 
    */
    public boolean addMoves(){
      if(getLevel() == 8){
        currMoves[2] = this.getMoveList().getMove("Bubble");
        return true;
      }
      else if(getLevel() == 15){
        currMoves[3] = this.getMoveList().getMove("Water Gun");
        return true;
      }
      return false;
    }

    

    //makes noises, pretty silly and funny when printed out
    @Override
    public String makeNoise(){
      int key = (int) Math.random() * 4;
      if(key == 1){
        return "sqUirtle";
      }
      else if(key == 2){
        return "squirtle";
      }
      else{
        return "SQIETRLLEEE!";
      }
    }

}
