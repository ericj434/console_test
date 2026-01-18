package pokemon;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
some status skills will not work properly, those needed to be written 
in. Only the required moves for charmander, squirtle, bulbasaur (and more
if i decide to add more pokemons) up to level 20 will be fully functional) 
*/
public class MoveList {
    private Move[] moveSet = new Move[165];
    public int length = 0;
    public MoveList(){
        try{
            File moveText = new File("pokemon/moves.txt");
            Scanner scansMoves = new Scanner(moveText);
            while(scansMoves.hasNextLine()){
                String move = scansMoves.nextLine();
                String[] moveArr = move.split("\t");
                if(moveArr.length == 7){
                    moveSet[length] = new Move(moveArr[0], moveArr[1], moveArr[2], Integer.parseInt(moveArr[3]), Integer.parseInt(moveArr[4]), Integer.parseInt(moveArr[5]), moveArr[6]);
                }
                else if(moveArr.length == 6){
                    moveSet[length] = new Move(moveArr[0], moveArr[1], moveArr[2], Integer.parseInt(moveArr[3]), Integer.parseInt(moveArr[4]), Integer.parseInt(moveArr[5]), "N/A");
                }
                length++;
            }
            scansMoves.close();
        }catch(FileNotFoundException e){
            e.printStackTrace();
            System.out.println("ERROR, CANT FIND \"moves.txt\"");
        }
    }
    public Move getMove(int index){
        return moveSet[index];
    }

    public Move getMove(String name){
        for(int i = 0; i < length; i++){
            if(moveSet[i].getName().equals(name)){
                return moveSet[i];
            }
        }
        return null;
    }

    public int getIndex(String move){
        for(int i = 0; i < length; i++){
            if(moveSet[i].getName().equals(move)){
                return 1;
            }
        }
        return -1;
    }
    public int length(){
        return length;
    }

    
    
}
