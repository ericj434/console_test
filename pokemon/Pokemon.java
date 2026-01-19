package pokemon;
public abstract class Pokemon{
    /*
    keeps track of all the levels and exp;
    */

    private int level = 1;
    private int exp = 0;

    //attributes, base stats that will remain constant in battle
    private double hp, speed, defense, atk;
    private String name, type;

    private MoveList moveSet = new MoveList();

    /*
    we will have these variables being mutable to adjust for in battle when we are decreasing the variables
    but we want to keep the base atk defense speed.
    tldr: mutable stats for game
    */
    private double currSpeed, currDefense, currAtk, currHp;

    /*
    stages for debuffs, used to calculate the percentage of def, atk, speed etc after 
    a use of a status skill. 
    */
    private int stageSpeed, stageDefense, stageAtk;


    //constructors
    public Pokemon(double initialHp, double initialSpeed, double initialDefense, double initialAtk, String name, String type){
        hp = initialHp;
        currHp = initialHp;
        speed = initialSpeed;
        currSpeed = initialSpeed;
        defense = initialDefense;
        currDefense = initialDefense;
        atk = initialAtk;
        this.type = type;
        this.name = name;
    }
    

    //getters and setters
    
    //hp getters and setters
    public double getHp(){
        return hp;
    }

    public void setHp(int hp){
        this.hp = hp;
    }

    //speed getters and setters
    public double getSpeed(){
        return speed;
    }

    public void setSpeed(int speed){
        this.speed = speed;
    }

    //defense getter and setters
    public double getDefense(){
        return defense;
    }

    public void setDefense(int defense){
        this.defense = defense;
    }

    //atk getters and setters
    public double getAtk(){
        return atk;
    }

    public void setAtk(int atk){
        this.atk = atk;
    }
    
    //name getter, should be immutable thus no setter
    public String getName(){
        return name;
    }

    //levels getter and setters
    public int getLevel(){
        return level;
    }

    public void setLevel(int level){
        this.level = level;
    }

    //exp getters and setters
    public int getExp(){
        return exp;
    }

    public void setExp(int exp){
        this.exp = exp;
    }

    public Move getMoves(String name){
        for(int i = 0; i < moveSet.length(); i++){
            if(moveSet.getMove(i).getName().equals(name)){
                return moveSet.getMove(i);
            }
        }
        return null;
    }

    public MoveList getMoveList(){
        return moveSet;
    }

    /*
    these are going to be used for keeping track of debuffs  
    */
    public double setCurrDefense(double newDefense){
      currDefense = newDefense;
      return newDefense;
    }

    public double getCurrDefense(){
        return currDefense;
    }

    public double setCurrAtk(double newAtk){
        currAtk = newAtk;
        return currAtk;
    }

    public double getCurrAtk(){
        return currAtk;
    }

    public double setCurrSpeed(double newSpeed){
        currSpeed = newSpeed;
        return currSpeed;
    }

    public double getCurrSpeed(){
        return currSpeed;
    }

    public double setCurrHp(double newHp){
        currHp = newHp;
        return currHp;
    }

    public double getCurrHp(){
        return currHp;
    }

    /*
    last few getters for these attributes, i keep adding more and more but
    itll make the code more robust for when i need more pokemons 
    */
    
    public int getStageSpeed(){
        return stageSpeed;
    }

    public int setStageSpeed(int newStageSpeed){
        stageSpeed = newStageSpeed;
        return stageSpeed;
    }

    public int getStageDefense(){
        return stageDefense;
    }

    public int setStageDefense(int newStageDefense){
        stageDefense = newStageDefense;
        return stageDefense;
    }

    public int getStageAtk(){
        return stageAtk;
    }

    public int setStageAtk(int newStageAtk){
        stageAtk = newStageAtk;
        return stageAtk;
    }
    /*
    simple toString method to return lvl and name 
    */

    public String toString(){
        return name + " LVL " + level;
    }
    /*
    exp things, level up, calculates exp based on the fast equation from
    bulbapedia (linked in readme).
    */
    public int calculateExp(int level){
        return (int) (4 * Math.pow(level, 3) / 5);
    }

    public int levelUp(int exp){
        if(calculateExp(this.getLevel()) == this.exp){
            level++;
            exp = 0;
            return level;
        }
        return -1;
    }
    /* 
    critical calculations
    */
    public boolean critical(){
        int key = (int) (Math.random() * 101);
        if(key >= 70){
            System.out.println("Critical hit by " + name);
            return true;
        }
        else{
            return false;
        }
    }

    public int random(int max, int min){
        return (int) (Math.random() * (max - min + 1) + min);
    }

    /*
    doesnt factor in type effectiveness, too lazy
    */
    public int atkCalc(Move move, Pokemon other){
        int damage = 2 * level;
        if(critical()){
            damage *= 2;
        }
        damage = ( (damage / 5 + 2) * move.getPower() * (int) (this.atk / other.defense) ) / 50 + 2;
        if(this.type.equals(move.getType())){
            damage *= 1.5;
        }
        damage *= random(217, 255);
        damage = (int) (damage / 255);
        return damage;

    }
    
    public abstract void atk(int index, Pokemon other);

    public abstract String makeNoise();

}
