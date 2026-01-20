package pokemon;

import java.util.Arrays;


public class Charmander extends Pokemon{
    /*
    move bank for ALL possible moves for a pokemon up to level 20(here for charmander)
    unlocks moves based on level requirements
    lvl 1 lvl 1 lvl 9 lvl 15
    */
    private Move[] possibleMoves = {this.getMoveList().getMove("Growl"), this.getMoveList().getMove("Scratch"), this.getMoveList().getMove("Ember"), this.getMoveList().getMove("Leer")};
    
    /* 
    base number of attack at level one, will add as they level up
    */    
    private Move[] currMoves = {this.getMoveList().getMove("Growl"), this.getMoveList().getMove("Scratch"), null, null};


    //constructor
    //init all the stats
    public Charmander(){
        super(39.0, 60.0, 43.0, 52.0, "charmander", "fire");
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
    GROWL: 
    Type: Normal Category: Status Power: -1 Accuracy: 100 PP: 40
    Lowers opponent's Attack.
    */
    public void growl(Pokemon other){
        other.setStageAtk(other.getStageAtk() - 1);
        other.setCurrAtk(other.getCurrAtk() * 2 /(other.getStageAtk() + 2));
    }
    /*
    SCRATCH: 
    Type: Normal Category: Physical Power: 40 Accuracy: 100 PP: 35
    N/A
    */
    public int scratch(Pokemon other){
      return atkCalc(currMoves[1], other);
    }
    /* 
    EMBER: 
    Type: Fire Category: Special Power: 40 Accuracy: 100 PP: 25
    May burn opponent.
    */
    public int ember(Pokemon other){
      return atkCalc(currMoves[2], other);
    }
    /*
    LEER: 
    Type: Normal Category: Status Power: -1 Accuracy: 100 PP: 30
    Lowers opponent's Defense. 
    */
    public void leer(Pokemon other){
      other.setCurrDefense(other.getCurrDefense() * 2 /(other.getStageDefense() + 2));
    }
    /*
    heres the atk method, calls every other method to do damage
    or supp sometimes to the other pokemons 
    */
    public void atk(int index, Pokemon other){
        if(index == 0 && currMoves[0].getPp() > 0){
          growl(other);
          currMoves[0].setCurrPp(currMoves[0].getPp() - 1);
          System.out.println(this.getName() + " used " + currMoves[0].getName() + "!" );
        }
        else if(index == 1 && currMoves[1].getPp() > 0){
          other.setCurrHp(other.getCurrHp() - scratch(other));
          currMoves[1].setCurrPp(currMoves[1].getPp() - 1);
          System.out.println(this.getName() + " used " + currMoves[1].getName() + "!" );
        }
        else if(index == 2){
          if(!(currMoves[2] == null) && currMoves[2].getPp() > 0){
            other.setCurrHp(other.getCurrHp() - ember(other));
            currMoves[2].setCurrPp(currMoves[2].getPp() - 1);
            System.out.println(this.getName() + " used " + currMoves[2].getName() + "!" );
          }
        }
        else if(index == 3){
          if(!(currMoves[3] == null) && currMoves[3].getPp() > 0){
            leer(other);
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
      if(getLevel() == 9){
        currMoves[2] = this.getMoveList().getMove("Ember");
        return true;
      }
      else if(getLevel() == 15){
        currMoves[3] = this.getMoveList().getMove("Leer");
        return true;
      }
      return false;
    }

    

    //makes noises, pretty silly and funny when printed out
    @Override
    public String makeNoise(){
      int key = (int) Math.random() * 4;
      if(key == 1){
        return "Char.. charr.. CHARRRMAANDERRR";
      }
      else if(key == 2){
        return "CHARRRRMANDER!";
      }
      else{
        return "charmander!";
      }
    }

}
