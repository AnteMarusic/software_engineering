package org.polimi.client;

import org.polimi.GameRules;
import org.polimi.messages.*;
import org.polimi.server.model.Card;
import org.polimi.server.model.Coordinates;

import java.util.*;

public class ClientController {
    private Cli cli;
    private Scanner scanner;
    private final Client client;
    private String username;

    public ClientController(Client client) {
        this.scanner = new Scanner(System.in);
        this.username = "unknown";
        cli = new Cli();
        this.client = client;
    }
    public void setUsername (String username) {this.username = username;}

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
                return chooseGameMode();
            }

            case WAITING_IN_LOBBY -> {
                //in case of update message the client doesn't have to send any message
                System.out.println("waiting in lobby...");
                return null;
            }
            case START_GAME_MESSAGE -> {
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
                int personalGoal = m.getPersonalGoal();
                String[] usernames = m.getUsernames();
                modelAllMessage(board, bookshelves, sharedGoal1, sharedGoal2, personalGoal, Arrays.stream(usernames).toList());
                for(int i=0 ; i<usernames.length ; i++){
                    cli.createBookshelf(usernames[i] , bookshelves.get(i));
                }
                cli.printRoutine();
                return null;
            }

            //this message is sent during the match to notify changes that happened to the model
            case MODEL_STATUS_UPDATE -> {
                //in case of update message the client doesn't have to send any message
                modelUpdateMessage();
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
            }
            case NOTIFY_GOAL_COMPLETION -> {
            }
            case NOTIFY_GAME_END -> {
            }
            case RANKING_MESSAGE -> {
            }
        }
        return null;
    }

    /**
     * asks to type in stdin the username and sends a message of type username to the server.
     */
    public Message chooseUsername () {
        cli.askForUsername();
        setUsername(scanner.nextLine());
        return new Message(username, MessageType.USERNAME);
    }

    /**
     * writes an informative message that the username is already in use and calls chooseUsername method
     */
    public void alreadyTakenUsername () {
        cli.alreadyTakenUsername();
        chooseUsername();
    }


    public Message chooseGameMode () {
        ChosenGameModeMessage message = null;
        int input;
        do {
            cli.chooseGameMode();
            input = scanner.nextInt();
            if (input < 1 || input > 4) {
                cli.invalid();
            }
        } while (input < 1 || input > 4);
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
                        input = scanner.nextInt();
                        if (input < 0) {
                            cli.invalid();
                        }
                    } while (input < 0);
                    message = new ChosenGameModeMessage(username, GameMode.JOIN_PRIVATE_GAME, input);
                }
                else message = new ChosenGameModeMessage(username, GameMode.CREATE_PRIVATE_GAME, -1);
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
    public Message chooseCards() {
        //since the cards have to be picked in a line, each card picked has to have one constant coordinate
        int counter = 0;
        Coordinates c1 = null, c2 = null, c3 = null;
        int row, col;
        int numberToPick;
        int maxInsertable = cli.getMaxInsertable();
        boolean flag;

        LinkedList<Coordinates> chosenCoordinates = new LinkedList<>();

        do {
            cli.numberOfCards();
            numberToPick = scanner.nextInt();
            if (numberToPick >= 3) {
                cli.moreThan3Cards();
            }
            if (numberToPick < 0) {
                cli.lessThan1Card();
            }
        } while (numberToPick >= 3 || numberToPick < 0);

        while (counter < numberToPick) {
            switch (counter) {
                case 0 -> {
                    do {
                        flag = false;

                        cli.typeRow();
                        row = scanner.nextInt();
                        cli.typeCol();
                        col = scanner.nextInt();
                        if (!GameRules.boardRowColInBound(row, col, cli.getNumOfPlayers())) {
                            cli.notInBoundError();
                        } else {
                            c1 = new Coordinates(row, col);
                            if (cli.boardSeeCardAtCoordinates(new Coordinates(row, col)) == null) {
                                cli.notValidCard();
                            } else if (cli.isCardPickable(c1)) {
                                System.out.println("ok");
                                chosenCoordinates.add(c1);
                                flag = true;
                            } else {
                                System.out.println("this card is not pickable yet");
                            }
                        }
                    } while (!flag);
                    counter++;
                }
                case 1 -> {
                    flag = false;
                    do {
                        System.out.println("Choose your next Card");
                        System.out.println("Type row number (0 to 8)");
                        row = scanner.nextInt();
                        System.out.println("Type col number (0 to 8)");
                        col = scanner.nextInt();
                        if (!GameRules.boardRowColInBound(row, col, cli.getNumOfPlayers())) {
                            System.out.println("coordinates not in bound");
                        } else {
                            c2 = new Coordinates(row, col);
                            if (cli.boardSeeCardAtCoordinates(new Coordinates(row, col)) == null) {
                                System.out.println("the card has already been taken! please choose another one");
                            } else if (cli.isCardPickable(c2)) {
                                if (GameRules.areCoordinatesAligned(c1, c2)) {
                                    System.out.println("ok");
                                    chosenCoordinates.add(c2);
                                    flag = true;
                                } else {
                                    System.out.println("this card isn't aligned with the first one");
                                }
                            } else {
                                System.out.println("this card is not pickable yet");
                            }
                        }
                    } while (!flag);
                    counter++;
                }
                case 2 -> {
                    flag = false;
                    do {
                        System.out.println("Choose your next Card");
                        System.out.println("Type row number (0 to 8)");
                        row = scanner.nextInt();
                        System.out.println("Type col number (0 to 8)");
                        col = scanner.nextInt();
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
                                    flag = true;
                                } else {
                                    System.out.println("this card isn't aligned with the first one");
                                }
                            } else {
                                System.out.println("this card is not pickable yet");
                            }
                        }
                    } while (!flag);
                    counter++;
                }
            }
        }
        if (chosenCoordinates.size() > 1) {
            orderChosenCards(chosenCoordinates);
        }
        return new ChosenCardsMessage(username, chosenCoordinates);
    }

    //the array contains coordinates, so CLI has to show the changes during this procedure
    private List<Coordinates> orderChosenCards(List<Coordinates> toOrder) {
        ArrayList<Coordinates> temp = new ArrayList<>(toOrder.size());
        int position;
        int i = 0;

        while (i < toOrder.size()) {

            do {
                System.out.println("Where do you want to put the card in position " + i + " ?\n");
                position = scanner.nextInt();
                if (position > toOrder.size() - 1 || position < 0) {
                    System.out.println("position not in bound, choose again");
                }
            }while (position > toOrder.size() - 1 || position < 0);

            if (temp.get(position) != null) {
                if (position >= 0 && position < toOrder.size()) {
                    temp.add(toOrder.get(i));
                    i++;
                }
                else {
                    System.out.println(position + "is not in the interval [0, toInsert.length]...\n Please choose again\n");
                }
            }
            else
                System.out.println("There's already a card in position "+position+", choose another...");
        }
        cli.removeCards(temp);
        return temp;
    }
    public void newPlayerJoinedLobby (String newPlayer) {
        cli.addNewPlayer(newPlayer);
    }

    public Message chooseColumn () {
        int input;
        boolean flag = false;
        do {
            System.out.println("choose the column where to insert the cards");
            input = scanner.nextInt();
            if (!GameRules.bookshelfColInBound(input)) {
                System.out.println("invalid input");
            }
            else {
                if (cli.getInsertable(input) < cli.getChosenCardsSize()) {
                    flag = true;
                    cli.insert(input);
                }
                else {
                    System.out.println("not enough space to store cards in the column you chose");
                }
            }
        } while (!GameRules.bookshelfColInBound(input) && flag);
        return new ChosenColumnMessage(username, input);
    }

    /**
     * calls the correspondent method of CLI
     */
    public void errorMessage () {

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
    public void modelAllMessage (Map<Coordinates, Card> board, List<Card[][]> bookshelves, int sharedGoal1, int sharedGoal2, int personalGoal, List<String> usernames) {
        if (this.cli == null)
            this.cli = new Cli();
        cli.setPlayers(usernames);
        cli.setBoard(board);
        cli.setBookshelves(bookshelves); // come faccio a sapere di chi é la bookshelf???
        cli.setPersonalGoal(personalGoal);
        cli.setSharedGoal1(sharedGoal1);
        cli.setSharedGoal2(sharedGoal2);
    }

    public void handleDisconnection() {
        System.out.println("an error occurred, you disconnected from the server");
    }
}