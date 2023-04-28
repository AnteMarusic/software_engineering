package org.polimi.client;

public class ClientController {}

    /**
     * gets from std input one to three coordinates
     * ensures that the arrayList containing these coordinates is dim one to three and contains valid coordinates
     * ensures that the dimension of the arrayList isn't greater than maxInsertable in bookshelf
     * ensures that the cards are picked in a line from the board
     */
   /* public void chooseCards(Board board){
        //since the cards have to be picked in a line, each card picked has to have one constant coordinate
        int x=0, y=0;
        int previousX=0, previousY=0;
        Card tempCard = null;
        Coordinates coordinates = new Coordinates(0,0);
        Scanner scanner = new Scanner(System.in);
        int maxInsertable = bookshelf.getMaxInsertable();
        ArrayList<Card> chosenCards = new ArrayList<>(maxInsertable);
        int counter=0;
        String answer;
        while(counter < maxInsertable){
            if(counter > 0) {
                System.out.println("Do you want to choose another? You can choose another" + (maxInsertable - counter) + "cards\nType 'yes' or 'no'");
                answer=scanner.nextLine().toLowerCase();
                if(answer.equals("no"))
                    break;
                else if(!answer.equals("yes")){
                    System.out.println("Input is not valid, type again...\n");
                    continue;
                }
            }

            switch(counter){
                case 0 -> {
                    do {
                        System.out.println("Type row number (0 to 8)");
                        previousX = scanner.nextInt();
                        System.out.println("Type col number (0 to 8)");
                        previousY = scanner.nextInt();
                        coordinates.setRowCol(previousX,previousY);
                        if(coordinates.CoordinatesAreValid()){
                            tempCard = board.seeCardAtCoordinates(coordinates);
                            if (tempCard == null)
                                System.out.println("There's no card at that position, please choose another...");
                            else {
                                if(tempCard.getState() == Card.State.NOT_PICKABLE)
                                    System.out.println("That card cannot be picked, please choose another...");
                                else if(tempCard.getState() == Card.State.PICKABLE){
                                    chosenCards.add(board.getCardAtCoordinates(coordinates));
                                    counter++;
                                    System.out.println("You chose a card in position (" + previousX + "," + previousY);
                                }
                            }
                        }
                        else
                            System.out.println("These coordinates are not valid, choose again...");
                    }while(!coordinates.CoordinatesAreValid() || tempCard == null || tempCard.getState() != Card.State.PICKABLE);//first card
                    previousX = x;
                    previousY = y;
                }

                case 1 -> {
                    do {
                        System.out.println("Choose your next Card");
                        System.out.println("Type row number (0 to 8)");
                        x = scanner.nextInt();
                        System.out.println("Type col number (0 to 8)");
                        y = scanner.nextInt();
                        coordinates.setRowCol(x, y);
                        if (coordinates.CoordinatesAreValid() && ((x == previousX && (y == previousY+1 ||y == previousY-1)) || ((x == previousX+1 ||x == previousX-1) && y == previousY))) {
                            tempCard = board.seeCardAtCoordinates(coordinates);
                            if (tempCard == null)
                                System.out.println("There's no card at that position, please choose another...");
                            else {
                                chosenCards.add(board.getCardAtCoordinates(coordinates));
                                System.out.println("You chose a card in position (" + x + "," + y);
                            }
                        } else
                            System.out.println("These coordinates are not valid, choose again...");
                    } while (!coordinates.CoordinatesAreValid() || !((x == previousX && (y == previousY+1 ||y == previousY-1)) || ((x == previousX+1 ||x == previousX-1) && y == previousY)) || tempCard == null); //eventual second card
                    previousX = x;
                    previousY = y;
                }
                case 2 -> {
                    if (x == previousX) {
                        do {
                            System.out.println("Choose your next Card");
                            System.out.println("Type row number (0 to 8)");
                            x = scanner.nextInt();
                            System.out.println("Type col number (0 to 8)");
                            y = scanner.nextInt();
                            coordinates.setRowCol(x, y);
                            if (coordinates.CoordinatesAreValid() && ((x == previousX && y==previousY-1) || (x == previousX && y==previousY+1))) {
                                tempCard = board.getCardAtCoordinates(coordinates);
                                if (tempCard == null)
                                    System.out.println("There's no card at that position, please choose another...");
                                else {
                                    chosenCards.add(board.getCardAtCoordinates(coordinates));
                                    System.out.println("You chose a card in position (" + x + "," + y);
                                }
                            } else
                                System.out.println("These coordinates are not valid, choose again... ");
                        } while (!coordinates.CoordinatesAreValid() || !((x == previousX && y==previousY-1) || (x == previousX && y==previousY+1)) || tempCard == null);
                    }
                    if (y == previousY) {
                        do {
                            System.out.println("Choose your next Card");
                            System.out.println("Type row number (0 to 8)");
                            x = scanner.nextInt();
                            System.out.println("Type col number (0 to 8)");
                            y = scanner.nextInt();
                            coordinates.setRowCol(x, y);
                            if (coordinates.CoordinatesAreValid() && ((y == previousY && x==previousX-1) || (y == previousY && x==previousX+1))) {
                                tempCard = board.getCardAtCoordinates(coordinates);
                                if (tempCard == null)
                                    System.out.println("There's no card at that position, please choose another...");
                                else {
                                    chosenCards.add(board.getCardAtCoordinates(coordinates));
                                    System.out.println("You chose a card in position (" + x + "," + y);
                                }
                            } else
                                System.out.println("These coordinates are not valid, choose again...");
                        } while (!coordinates.CoordinatesAreValid() || !((y == previousY && x==previousX-1) || (y == previousY && x==previousX+1)) || tempCard == null);
                    }
                }
            }
            counter++;
        }
        if(counter>1)
            orderChosenCards(chosenCards);
    }

    private void orderChosenCards(ArrayList<Card> toInsert) {
        ArrayList<Card> temp = new ArrayList<>(toInsert.size());
        Scanner scanner = new Scanner(System.in);
        int position;
        int i = 0;

        printChosenCards(toInsert);

        while (i < toInsert.size()) {
            System.out.println("Where do you want to put the card in position " + i +"?\n");
            position = scanner.nextInt();
            if (temp.get(position) != null) {
                if (position >= 0 && position < toInsert.size()) {
                    temp.add(toInsert.get(i));
                    i++;
                }
                else {
                    System.out.println(position + "is not in the interval [0, toInsert.length]...\n Please choose again\n");
                }
            }
            else
                System.out.println("There's already a card in position "+position+", choose another...");
        }
        printChosenCards(temp);
        insertInBookshelf(temp);
    }


    /**
     *
     * @param chosenCards requires that no card inside it is null
     */
   /* private void printChosenCards (ArrayList<Card> chosenCards) {
        chosenCards.forEach(System.out::println);
    }
}*/
