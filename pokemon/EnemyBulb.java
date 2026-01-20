package pokemon;

public class EnemyBulb extends Bulbasaur implements Enemy {
    public EnemyBulb(){
        
    }
 
    public void atk(Pokemon other){
        int move = random(1, 0);
        super.atk(move, other);
    }
}
