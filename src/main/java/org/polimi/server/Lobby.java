package org.polimi.server;

import org.polimi.server.controller.GameController;
import org.polimi.server.model.Player;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Lobby {
    private  ArrayList<Player> publicListOf2;
    private  ArrayList<Player> publicListOf3;
    private  ArrayList<Player> publicListOf4;
    private ArrayList<GameController> gcList;
    public Lobby(){
        this.publicListOf2 = new ArrayList<>();
        this.publicListOf3 = new ArrayList<>();
        this.publicListOf4 = new ArrayList<>();
        this.gcList = new ArrayList<>();
    }
    public void insertInList(Player player, int num){
        switch (num){
            case 2->{
                publicListOf2.add(player);
                if(publicListOf2.size()==2){
                    gcList.add(new GameController(publicListOf2));
                    publicListOf2.clear();
                }
            }
            case 3->{
                publicListOf3.add(player);
                if(publicListOf3.size()==3){
                    gcList.add(new GameController(publicListOf3));
                    publicListOf3.clear();
                }
            }
            case 4->{
                publicListOf4.add(player);
                if(publicListOf4.size()==4){
                    gcList.add(new GameController(publicListOf4));
                    publicListOf4.clear();
                }
            }

        }
    }

}
