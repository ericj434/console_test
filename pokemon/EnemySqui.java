package pokemon;

public class EnemySqui extends Squirtle implements Enemy{
    public EnemySqui(){
        
    }
 
    public void atk(Pokemon other){
        int move = random(1, 0);
        super.atk(move, other);
    }
}
