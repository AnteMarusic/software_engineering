package org.polimi.client.view.gui.sceneControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.LinkedList;
import java.util.List;

public class FinalSceneController {
    @FXML
    private Label user0;
    @FXML
    private Label user1;
    @FXML
    private Label user2;
    @FXML
    private Label user3;


    @FXML
    public void initialize(){
        List<RankCouple> classifier = new LinkedList<>();
        List<String> usernames = SceneController.getInstance().getPlayers();
        for (String username : usernames) {
            RankCouple currCouple = new RankCouple();
            currCouple.setName(username);
            currCouple.setPoints(SceneController.getInstance().getRanking().get(username));
            classifier.add(currCouple);
        }
        classifier.sort((couple1, couple2) -> couple2.getPoints() - couple1.getPoints());
        user0.setText("1. "+ classifier.get(0).getName()+" scored "+ classifier.get(0).getPoints()+" points");
        user1.setText("2. "+ classifier.get(1).getName()+" scored "+ classifier.get(1).getPoints()+" points");
        if(usernames.size()==3) {
            user2.setText("3. "+ classifier.get(2).getName()+" scored "+ classifier.get(2).getPoints()+" points");
        }else if(usernames.size()==4){
            user2.setText("3. "+ classifier.get(2).getName()+" scored "+ classifier.get(2).getPoints()+" points");
            user3.setText("4. "+ classifier.get(3).getName()+" scored "+ classifier.get(3).getPoints()+" points");
        }
    }




    public void quitGame(){
        SceneController.getInstance().getStage().close();
    }

    public static class RankCouple{
        private String name;

        private int points;


        public String getName() {
            return name;
        }

        public int getPoints() {
            return points;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setPoints(int points) {
            this.points = points;
        }
    }
}
