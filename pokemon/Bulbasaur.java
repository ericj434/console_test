package pokemon;

import java.util.Arrays;


public class Bulbasaur extends Pokemon{
    /*
    move bank for ALL possible moves for a pokemon up to level 20(here for bulbasaur)
    unlocks moves based on level requirements
    lvl 1 lvl 1 lvl 7 lvl 13
    */
    private Move[] possibleMoves = {this.getMoveList().getMove("Growl"), this.getMoveList().getMove("Tackle"), this.getMoveList().getMove("Leech Seed"), this.getMoveList().getMove("Vine Whip")};
    
    /* 
    base number of attack at level one, will add as they level up
    */    
    private Move[] currMoves = {this.getMoveList().getMove("Growl"), this.getMoveList().getMove("Tackle"), null, null};


    //constructor
    //init all the stats
    public Bulbasaur(){
        super(39.0, 60.0, 43.0, 52.0, "bulbasaur", "grass");
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
        other.setCurrAtk(other.getCurrAtk() * 2 /(other.getStageAtk() + 2));
    }

    /*
    TACKLE: 
    Type: Normal Category: Physical Power: 40 Accuracy: 100 PP: 35
    N/A
    */
    public int tackle(Pokemon other){
        return atkCalc(currMoves[1], other);
    }
    /* 
    LEECH SEED: 
    Type: Grass Category: Status Power: -1 Accuracy: 90 PP: 10
    Drains HP from opponent each turn. FOR OUR GAME ONLY DRAINS HP (9.81% of opponent hp)
    */
    public void leechSeed(Pokemon other){
      this.setCurrHp((int) (this.getCurrHp() + other.getHp() * 0.0981));
      other.setCurrHp((int) (0.9019 * other.getHp()));
    }
    /*
    VINE WHIP: 
    Type: Grass Category: Physical Power: 45 Accuracy: 100 PP: 25
    N/A
    */
    public int vineWhip(Pokemon other){
      return atkCalc(currMoves[3], other);
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
          other.setCurrHp(other.getCurrHp() - tackle(other));
          currMoves[1].setCurrPp(currMoves[1].getPp() - 1);
          System.out.println(this.getName() + " used " + currMoves[1].getName() + "!" );
        }
        else if(index == 2){
          if(!(currMoves[2] == null) && currMoves[2].getPp() > 0){
            leechSeed(other);
            currMoves[2].setCurrPp(currMoves[2].getPp() - 1);
            System.out.println(this.getName() + " used " + currMoves[2].getName() + "!" );
          }
        }
        else if(index == 3){
          if(!(currMoves[3] == null) && currMoves[3].getPp() > 0){
            other.setCurrHp(other.getCurrHp() - vineWhip(other));
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
      if(getLevel() == 7){
        currMoves[2] = this.getMoveList().getMove("Leech Seed");
        return true;
      }
      else if(getLevel() == 13){
        currMoves[3] = this.getMoveList().getMove("Vine Whip");
        return true;
      }
      return false;
    }

    

    //makes noises, pretty silly and funny when printed out
    @Override
    public String makeNoise(){
      int key = (int) Math.random() * 4;
      if(key == 1){
        return "BULbasaurrr....";
      }
      else if(key == 2){
        return "BULBASUARRRRR";
      }
      else{
        return "bulbaa saurrrrr";
      }
    }

}
