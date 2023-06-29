package org.polimi.client;

import org.polimi.GameRules;
import org.polimi.client.view.Cli;
import org.polimi.client.view.gui.sceneControllers.SceneController;
import org.polimi.messages.*;
import org.polimi.servernetwork.model.Card;
import org.polimi.servernetwork.model.Coordinates;

import java.util.*;

public class CliClientController implements ClientControllerInterface {
    private Cli cli;
    private Scanner scanner;
    private final Client client;
    private String username;

    public CliClientController(Client client) {
        this.scanner = new Scanner(System.in);
        this.username = "unknown";
        cli = new Cli();
        this.client = client;
    }
    @Override
    public void setUsername (String username) {this.username = username;}


    /**
     * Handles incoming messages received from the server.
     *
     * @param message The message received from the server.
     * @return The message to send back to the server, or null if no message needs to be sent.
     */
    @Override
    public Message handleMessage (Message message) {
        switch (message.getMessageType()) {
            case ERROR_MESSAGE -> {
                ErrorMessage errorMessage = (ErrorMessage) message;
                switch (errorMessage.getErrorType()){
                    case ALREADY_TAKEN_USERNAME -> {
                        System.out.println("already taken username, choose another");
                        return chooseUsername();
                    }
                }
            }

            //this is the message sent if the username was not taken by anybody
            //if you are reconnecting you should expect a Model_status_all message
            case CHOOSE_GAME_MODE -> {
                cli.setMyUsername(username);
                return chooseGameMode();
            }

            case GAME_CODE_MESSAGE -> {
                GameCodeMessage gameCodeMessage = (GameCodeMessage) message;
                cli.printGameCode(gameCodeMessage.getGameCode());
            }

            // nuovo messaggio aggiunto
            case USERNAME -> {
                System.out.println("il nome è: " + username);
                cli.setMyUsername(username);
            }

            case WAITING_IN_LOBBY -> {
                //in case of update message the client doesn't have to send any message
                System.out.println("waiting in lobby...");
                return null;
            }

            //this message is sent
            //if the server recognises that this client is reconnecting, so it has to send the whole model status,
            //otherwise, it is sent at the beginning of the match
            case MODEL_STATUS_ALL -> {
                //in case of status all message the client doesn't have to send any message
                ModelStatusAllMessage m = (ModelStatusAllMessage) message;
                Map<Coordinates, Card> board = m.getBoard();
                List<Card[][]> bookshelves = m.getBookshelves();
                int sharedGoal1 = m.getSharedGoal1();
                int sharedGoal2 = m.getSharedGoal2();
                int currPlayer = m.getCurrentPlayer();
                Coordinates[] personalGoalCoordinates = m.getPersonalGoalCoordinates();
                Card.Color[] personalGoalColors = m.getPersonalGoalColors();
                List <String> usernames = m.getUsernames();
                int personalGoal = m.getPersonalGoalIndex();
                modelAllMessage(board, bookshelves, sharedGoal1, sharedGoal2, personalGoalCoordinates, personalGoalColors, usernames, personalGoal, currPlayer);
                cli.printRoutine();
                return null;
            }


            // nuovo messaggio aggiunto
            case BOARDMESSAGE -> {
                BoardMessage m = (BoardMessage) message;
                System.out.println("ho ricevuto il messaggio di riempire di nuovo la board");
                Map<Coordinates, Card> board = m.getBoard();
                newBoardRefill(board);
                cli.printRoutine();
            }

            case CARD_TO_REMOVE -> {
                CardToRemoveMessage m = (CardToRemoveMessage) message;
                if(!cli.ifCurrentPlayer())
                    cli.removeOtherPlayerCards(m.getCoordinates());
                cli.printRoutine();
                return null;
            }
            case CHOSEN_COLUMN_REPLY -> {
                ChosenColumnMessage m = (ChosenColumnMessage) message;
                cli.insertInOtherPlayerBookshelf(m.getColumn());
                cli.printRoutine();
                return null;
            }

            //first message that is sent when is your turn
            case CHOOSE_CARDS_REQUEST -> {
                return chooseCards();
            }

            //message that should be received subsequently to the choice and the sorting of the cards.
            case CHOOSE_COLUMN_REQUEST -> {
                return chooseColumn();
            }

            //message received when is not your turn and the server notifies you of the next client playing
            case NOTIFY_NEXT_PLAYER -> {
                NotifyNextPlayerMessage m = (NotifyNextPlayerMessage) message;
                System.out.println(m.getNextPlayer() + " is now playing");
                cli.setCurrentPlayer(m.getNextPlayerInt(), m.getNextPlayer());
                return null;
            }
            case ALREADYTAKENGAMECODEMESSAGE -> {
                cli.alreadyTakenGameCode();
                return null;
            }
            case AREALONE -> {
                cli.youAreAlone();
            }
            case DISCONNECTION_ALLERT -> {
                DisconnectionAlert m = (DisconnectionAlert) message;
                cli.disconnectionAlert(m.getUsernameDisconnected());
            }
            case NOTIFY_GOAL_COMPLETION -> {
            }
            case NOTIFY_GAME_END -> {
            }
            case RANKING_MESSAGE -> {
                RankingMessage m = (RankingMessage) message;
                Map<String,Integer> ranking = m.getRanking();
                cli.printRanking(ranking);
            }
            case CURRENT_SCORE -> {
                CurrentScore m = (CurrentScore) message;
                cli.printCurrentScore(m.getCurrentScore());
            }
            case SHAREDSCOREACHIEVE_MESSAGE -> {
                SharedScoreAchieveMessage m = (SharedScoreAchieveMessage) message;
                cli.printAchieveMessage(m.getIndex(), m.getNewPoints());
            }
        }
        return null;
    }

    /**
     * asks to type in stdin the username and sends a message of type username to the server.
     */
    @Override
    public Message chooseUsername () {
        boolean flag = false;
        do {
            cli.askForUsername();
            if (scanner.hasNextInt()) {
                scanner.nextInt();
                scanner.nextLine();
                System.out.println("integers aren't valid usernames");
            }
            else {
                setUsername(scanner.next());
                flag = true;
            }
        } while (!flag);
        return new Message(username, MessageType.USERNAME);
    }

    /**
     * writes an informative message that the username is already in use and calls chooseUsername method
     */
    @Override
    public void alreadyTakenUsername () {
        cli.alreadyTakenUsername();
    }

    /**
     * Allows the player to choose the game mode.
     *
     * @return The chosen game mode as a ChosenGameModeMessage.
     */
    @Override
    public Message chooseGameMode () {
        ChosenGameModeMessage message = null;
        int input=0;
        int gameCode;
        boolean valid=true;
        do {
            try {
                cli.chooseGameMode();
                input = scanner.nextInt();
                if (input < 1 || input > 4) {
                    cli.invalid();
                }
                valid = true;
            }
            catch(NumberFormatException | InputMismatchException e){
                cli.invalid();
                valid = false;
                this.scanner = new Scanner(System.in);
            }


        } while (input < 1 || input > 4 || valid == false);
        switch (input) {
            case 1 -> {
                do {
                    cli.joinOrCreateGame();
                    input = scanner.nextInt();
                    if (input < 1 || input > 2) {
                        cli.invalid();
                    }
                } while (input < 1 || input > 2);
                if (input == 1) {
                    do {
                        cli.chooseNumberOfPlayer();
                        input = scanner.nextInt();
                        if (input < 0) {
                            cli.invalid();
                        }
                    } while (input < 2 || input >4);
                    message = new ChosenGameModeMessage(username, GameMode.CREATE_PRIVATE_GAME,-1, input);
                    cli.waitForTheOtherPlayer();
                }
                else {
                    cli.insertGameCode();
                    input = scanner.nextInt();
                    message = new ChosenGameModeMessage(username, GameMode.JOIN_PRIVATE_GAME, input);

                }
            }
            case 2 -> {
                message = new ChosenGameModeMessage(username, GameMode.JOIN_RANDOM_GAME_2_PLAYER, -1);
            }
            case 3 -> {
                message = new ChosenGameModeMessage(username, GameMode.JOIN_RANDOM_GAME_3_PLAYER, -1);
            }
            case 4 -> {
                message = new ChosenGameModeMessage(username, GameMode.JOIN_RANDOM_GAME_4_PLAYER, -1);
            }
        }
        return message;
    }

    /**
     * gets from std input one to three coordinates
     * ensures that the arrayList containing these coordinates is dim one to three and contains valid coordinates
     * ensures that the dimension of the arrayList isn't greater than maxInsertable in bookshelf
     * ensures that the cards are picked in a line from the board
     */

    @Override
    public Message chooseCards() {
        //since the cards have to be picked in a line, each card picked has to have one constant coordinate
        int counter = 0;
        Coordinates c1 = null, c2 = null, c3 = null;
        int row, col;
        int maxInsertable = cli.getMaxInsertable();
        boolean normalFlag;
        boolean actionFlag;
        String action;

        LinkedList<Coordinates> chosenCoordinates = new LinkedList<>();
        System.out.println("choose a card");
        while (counter < maxInsertable) {
            switch (counter) {
                //you necessarily have to choose at least a card
                case 0 -> {
                    do {
                        normalFlag = false;
                        actionFlag = false;
                        cli.typeRow();
                        if (scanner.hasNextInt()) {
                            // If the next input is an integer
                            row = scanner.nextInt();
                            scanner.nextLine(); //removes carriage return
                            cli.typeCol();
                            if (scanner.hasNextInt()) {
                                col = scanner.nextInt();
                                scanner.nextLine(); //removes carriage return
                                if (!GameRules.boardRowColInBound(row, col, cli.getNumOfPlayers())) {
                                    cli.notInBoundError();
                                } else {
                                    c1 = new Coordinates(row, col);
                                    if (cli.boardSeeCardAtCoordinates(new Coordinates(row, col)) == null) {
                                        cli.notValidCard();
                                    } else if (cli.isCardPickable(c1)) {
                                        System.out.println("ok, great choice");
                                        chosenCoordinates.add(c1);
                                        normalFlag = true;
                                    } else {
                                        System.out.println("this card is not pickable yet");
                                    }
                                }
                            }
                            else {
                                action = scanner.next();
                                if (Objects.equals(action, "stop")) {
                                    System.out.println("you can't stop here");
                                }
                                else if (Objects.equals(action, "redo")) {
                                    System.out.println("you can't redo here");
                                }
                                else {
                                    System.out.println("invalid input");
                                }
                            }
                        }
                        else {
                            action = scanner.next();
                            if (Objects.equals(action, "stop")) {
                                System.out.println("you can't stop here");
                            }
                            else if (Objects.equals(action, "redo")) {
                                System.out.println("you can't redo here");
                            }
                            else {
                                System.out.println("invalid input");
                            }
                        }
                    } while (!normalFlag);
                    counter++;
                }
                case 1 -> {
                    normalFlag = false;
                    actionFlag = false;
                    do {
                        System.out.println("Choose your next Card or type 'undo' to undo previous choice or 'stop' to terminate");
                        System.out.println("Type row number (0 to 8)");
                        if (scanner.hasNextInt()) {
                            row = scanner.nextInt();
                            scanner.nextLine();
                            System.out.println("Type col number (0 to 8)");
                            if (scanner.hasNextInt()) {
                                col = scanner.nextInt();
                                scanner.nextLine();
                                if (!GameRules.boardRowColInBound(row, col, cli.getNumOfPlayers())) {
                                    System.out.println("coordinates not in bound");
                                } else {
                                    c2 = new Coordinates(row, col);
                                    if (cli.boardSeeCardAtCoordinates(new Coordinates(row, col)) == null) {
                                        System.out.println("the card has already been taken! please choose another one");
                                    } else if (cli.isCardPickable(c2)) {
                                        if (GameRules.areCoordinatesAligned(c1, c2)) {
                                            System.out.println("ok, great choice");
                                            chosenCoordinates.add(c2);
                                            normalFlag = true;
                                        } else {
                                            System.out.println("this card isn't aligned with the first one");
                                        }
                                    } else {
                                        System.out.println("this card is not pickable yet");
                                    }
                                }
                            } else {
                                action = scanner.next();
                                if (Objects.equals(action, "stop")) {
                                    System.out.println("you can't stop here");
                                } else if (Objects.equals(action, "redo")) {
                                    System.out.println("you can't redo here");
                                } else {
                                    System.out.println("invalid input");
                                }
                            }
                        }
                        else {
                            action = scanner.next();
                            if (action.equalsIgnoreCase("stop")) {
                                System.out.println("ok, you stopped card selection");
                                counter = maxInsertable;
                                actionFlag = true;
                            } else if (Objects.equals(action, "redo")) {
                                System.out.println("ok, going back to previous step");
                                chosenCoordinates.removeLast();
                                counter--;
                                actionFlag = true;
                            } else {
                                System.out.println("invalid input");
                            }
                        }
                    }while (!normalFlag && !actionFlag) ;
                    if (normalFlag) {
                        counter++;
                    }
                }
                case 2 -> {
                    normalFlag = false;
                    actionFlag = false;
                    do {
                        System.out.println("Choose your next Card or type 'undo' to undo previous choice or 'stop' to terminate");
                        System.out.println("Type row number (0 to 8)");
                        if (scanner.hasNextInt()) {
                            row = scanner.nextInt();
                            scanner.nextLine();
                            System.out.println("Type col number (0 to 8)");
                            if (scanner.hasNextInt()) {
                                col = scanner.nextInt();
                                scanner.nextLine();
                                if (!GameRules.boardRowColInBound(row, col, cli.getNumOfPlayers())) {
                                    System.out.println("coordinates not in bound");
                                } else {
                                    c3 = new Coordinates(row, col);
                                    if (cli.boardSeeCardAtCoordinates(new Coordinates(row, col)) == null) {
                                        System.out.println("the card has already been taken! please choose another one");
                                    } else if (cli.isCardPickable(c3)) {
                                        if (GameRules.areCoordinatesAligned(c1, c2, c3)) {
                                            System.out.println("ok");
                                            chosenCoordinates.add(c3);
                                            normalFlag = true;
                                        } else {
                                            System.out.println("this card isn't aligned with the first one");
                                        }
                                    } else {
                                        System.out.println("this card is not pickable yet");
                                    }
                                }
                            }
                            else {
                                action = scanner.next();
                                if (Objects.equals(action, "stop")) {
                                    System.out.println("you can't stop here");
                                } else if (Objects.equals(action, "redo")) {
                                    System.out.println("you can't redo here");
                                } else {
                                    System.out.println("invalid input");
                                }
                            }
                        }
                        else {
                            action = scanner.next();
                            if (action.equalsIgnoreCase("stop")) {
                                System.out.println("ok, you stopped card selection");
                                counter = maxInsertable;
                                actionFlag = true;
                            } else if (Objects.equals(action, "redo")) {
                                System.out.println("ok, going back to previous step");
                                chosenCoordinates.removeLast();
                                counter--;
                                actionFlag = true;
                            } else {
                                System.out.println("invalid input");
                            }
                        }

                    } while (!normalFlag && !actionFlag);
                    if (normalFlag) {
                        counter++;
                    }
                }
            }
        }
        System.out.println(chosenCoordinates);
        cli.removeCards(chosenCoordinates);
        if (chosenCoordinates.size() > 1) {
            System.out.println("order");
            chosenCoordinates = orderChosenCards(chosenCoordinates);
        }
        return new ChosenCardsMessage(username, chosenCoordinates);
    }

    /**
     * Orders the chosen cards based on the specified positions.
     *
     * @param chosenCoordinates The list of chosen coordinates.
     * @return The ordered coordinates as a linked list.
     */
    @Override
    public LinkedList<Coordinates> orderChosenCards(List<Coordinates> chosenCoordinates) {
        int position;
        int i = 0;
        LinkedList<Coordinates>  orderedCoordinates = new LinkedList<Coordinates>();
        for(int k=0; k<chosenCoordinates.size(); k++){
            orderedCoordinates.add(null);
        }
        List<Card> toOrder = cli.getChosenCards();
        List<Card> ordered = new ArrayList<Card>(toOrder.size());
        for (int j = 0; j < toOrder.size(); j++) {
            ordered.add(null);
        }
        cli.setOrderedChosenCards(ordered);
        cli.setChosenCards(toOrder);
        System.out.println("initialization of ordered " + ordered);

        while (i < toOrder.size()) {
            int size = toOrder.size();
            do {
                System.out.println("Where do you want to put the card in position " + i + " ?\n type the index (indexing start from 0)");
                position = scanner.nextInt();
                if (position > toOrder.size() - 1 || position < 0) {
                    System.out.println("position not in bound, choose again");
                }
                else if (ordered.get(position) != null) {
                    System.out.println("There's already a card in position " + position + ", choose another...");
                }
            } while (position > size - 1 || position < 0 || (position <= size - 1 && position >= 0 && ordered.get(position) != null));

            orderedCoordinates.set(position, chosenCoordinates.get(i));
            ordered.set(position, toOrder.get(i));
            toOrder.set(i, null);

            cli.printRoutine();
            i++;
        }
        cli.setChosenCards(ordered);
        return orderedCoordinates;
    }
    @Override
    public void newPlayerJoinedLobby (String newPlayer) {
        cli.addNewPlayer(newPlayer);
    }

    /**
     * Allows the player to choose a column where to insert the cards.
     *
     * @return The chosen column as a ChosenColumnMessage.
     */
    @Override
    public Message chooseColumn () {
        int input = -1;
        boolean flag = false;
        do {
            System.out.println("choose the column where to insert the cards");
            if (scanner.hasNextInt()) {
                // If the next input is an integer
                input = scanner.nextInt();
                if (!GameRules.bookshelfColInBound(input)) {
                    System.out.println("invalid input");
                }
                else {
                    if (cli.getInsertable(input) >=  cli.getChosenCardsSize()) {
                        System.out.println("insert in Bookshelf");
                        flag = true;
                        cli.insertInBookshelf(input);
                    }
                    else {
                        System.out.println("not enough space to store cards in the column you chose");
                    }
                }
            }
            else {
                System.out.println("invalid input");
                scanner.nextLine();
            }
        } while (!GameRules.bookshelfColInBound(input) || !flag);
        cli.printRoutine();
        cli.clearChosenCard();
        return new ChosenColumnMessage(username, input);

    }

    /**
     * calls the correspondent method of CLI
     */
    @Override
    public void errorMessage () {

    }

    /**
     * should call the correspondent method of CLI
     */
    @Override
    public void loginSuccessful () {
        System.out.println("login successful");
    }

    /**
     * should call the correspondent method of CLI
     */
    @Override
    public void reconnectionSuccessful () {
        System.out.println("reconnection successful");
    }

    /**
     * calls the correspondent method of CLI
     */

    public void startGameMessage () {}

    /**
     * handles the message modelStatusMessage
     */
    public void modelUpdateMessage () {
    }

    /**
     * handles ModelStatusAllMessage
     */

    @Override
    public void modelAllMessage (Map<Coordinates, Card> board, List<Card[][]> bookshelves, int sharedGoal1, int sharedGoal2, Coordinates[] personalGoalCoordinates, Card.Color[] personalGoalColors, List<String> usernames, int personalGoal, int currentPlayer) {
        List <ClientBookshelf> l = new ArrayList<>(bookshelves.size());
        if (this.cli == null)
            throw new NullPointerException();
        for(int i=0 ; i<usernames.size() ; i++) {
            l.add (new ClientBookshelf(bookshelves.get(i)));
        }
        cli.setIntCurrentPlayer(currentPlayer);
        cli.setPlayers(usernames);
        cli.setBookshelves(l);
        cli.setBoard(board);
        cli.setPersonalGoal(personalGoalCoordinates, personalGoalColors);
        cli.setSharedGoal1(sharedGoal1);
        cli.setSharedGoal2(sharedGoal2);
    }
    public void newBoardRefill(Map<Coordinates, Card> board){
        cli.setBoard(board);
    }

    @Override
    public void disconnect() {
        cli.disconnect();
    }

}