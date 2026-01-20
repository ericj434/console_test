package pokemon;

public class EnemyChar extends Charmander implements Enemy{
    public EnemyChar(){
        
    }
 
    public void atk(Pokemon other){
        int move = random(1, 0);
        super.atk(move, other);
    }
}
