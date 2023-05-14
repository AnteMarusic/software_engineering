package org.polimi.server.model.goal.shared_goal;

import org.polimi.server.model.Card;
import org.polimi.server.model.Coordinates;

import java.util.LinkedList;

public class SharedGoal3 extends AbstractSharedGoal{
    public SharedGoal3(int numOfPlayer) {
        super(numOfPlayer);
    }

    @Override
    protected boolean achieved(Card[][] tmpGrid) {
        LinkedList<Coordinates[]> matchList = new LinkedList<Coordinates[]>();
        Coordinates[] tmpArray;
        for(int i=0 ; i<ROW ; i++){
            for(int j=0 ; j<2 ; j++){
                if(tmpGrid[i][j]!=null){
                    if(tmpGrid[i][j+1]!=null && tmpGrid[i][j+2]!=null && tmpGrid[i][j+3]!=null){
                        if(     tmpGrid[i][j].getColor() == tmpGrid[i][j+1].getColor() &&
                                tmpGrid[i][j].getColor() == tmpGrid[i][j+2].getColor() &&
                                tmpGrid[i][j].getColor() == tmpGrid[i][j+3].getColor()){
                            tmpArray = new Coordinates[4];
                            tmpArray[0] = new Coordinates(i,j);
                            tmpArray[1] = new Coordinates(i,j+1);
                            tmpArray[2] = new Coordinates(i,j+2);
                            tmpArray[3] = new Coordinates(i,j+3);
                            matchList.add(tmpArray);
                        }
                    }
                }
            }
        }
        for(int j=0 ; j<COL ; j++){
            for(int i=0 ; i<3 ; i++){
                if(tmpGrid[i][j]!=null){
                    if(tmpGrid[i+1][j]!=null && tmpGrid[i+2][j]!=null && tmpGrid[i+3][j]!=null){
                        if(     tmpGrid[i][j].getColor() == tmpGrid[i+1][j].getColor() &&
                                tmpGrid[i][j].getColor() == tmpGrid[i+2][j].getColor() &&
                                tmpGrid[i][j].getColor() == tmpGrid[i+3][j].getColor()){
                            tmpArray = new Coordinates[4];
                            tmpArray[0] = new Coordinates(i,j);
                            tmpArray[1] = new Coordinates(i+1,j);
                            tmpArray[2] = new Coordinates(i+2,j);
                            tmpArray[3] = new Coordinates(i+3,j);
                            matchList.add(tmpArray);
                        }
                    }
                }
            }
        }
        for(int i=0 ; i<matchList.size() ; i++){
            System.out.print(matchList.get(i)[0]+ " ");
            System.out.print(matchList.get(i)[1]+ " ");
            System.out.print(matchList.get(i)[2]+ " ");
            System.out.print(matchList.get(i)[3]);
            System.out.println();
        }

        for(int i=0 ; i < matchList.size() ; i++){
            for(int j=0 ; j < matchList.size() ; j++){
                for(int k=0 ; k < matchList.size() ; k++){
                    for(int s=0 ; s < matchList.size() ; s++){
                        if(i!=j && i!=k && i!=s && j!=k && j!=s && k!=s){
                            if(     !doesOverlap(matchList.get(i), matchList.get(j)) &&
                                    !doesOverlap(matchList.get(i), matchList.get(k)) &&
                                    !doesOverlap(matchList.get(i), matchList.get(s)) &&
                                    !doesOverlap(matchList.get(j), matchList.get(k)) &&
                                    !doesOverlap(matchList.get(j), matchList.get(s)) &&
                                    !doesOverlap(matchList.get(k), matchList.get(s))
                            ){
                                return true;
                            }
                        }
                    }
                }
            }
        }
    return false;
    }


    private boolean doesOverlap(Coordinates[] firstBlock, Coordinates[] newBlock){
        for(int i=0 ; i<firstBlock.length ; i++){
            for(int j=0 ; j<newBlock.length ; j++){
                if(firstBlock[i].equals(newBlock[j])){
                    return true;
                }
            }
        }
        return false;
    }
}
