package pokemon;
public class Move {
    //attributes
    private String type, name, category;
    private int pp, power, accuracy;
    private String description;

    //constructors
    public Move(String name, String type, String category, int power, int accuracy, int pp, String description){
        this.name = name;
        this.type = type;
        this.category = category;
        this.power = power;
        this.accuracy = accuracy;
        this.pp = pp;
        this.description = description;
    }

    //getters and setters
    public String getName(){
        return name;
    }

    public String getType(){
        return type;
    }

    public String category(){
        return category;
    }

    public int getPower(){
        return power;
    }

    public int getAccuracy(){
        return accuracy;
    }

    public int getPp(){
        return pp;
    }

    public int setPp(int newPp){
        pp = newPp;
        return pp;
    }
    public String getDescription(){
        return description;
    }

    public String toString(){
        return name.toUpperCase() + ": \n" + "Type: " + type + " Category: " + category + " Power: " + power + " Accuracy: " + accuracy + " PP: " + pp + "\n" + description;
    }
}
