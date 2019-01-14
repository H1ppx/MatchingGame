/**
@author Nate Mettke
@author William Chu
show a board of cards(6x6)
Ask the user to select a card --> show it
Ask the user to select another card --> show it
if they are the same, remove the cards
if they are different turn them back over
when all cards are gone, show some stats(total flips, how many bad turns, time etc)
TODO:
Winning - If all the cards are gone
Select two at once(turns), then cover them up
removing the cards that match
scoreboard(timer - done, how many turns, stats)
Show the rules at the beginning - Done
Label the rows, or number the cards, or handle clicks
 */
import lattelib.ColorLatte;
import lattelib.WebLatte;
import org.dalton.Assignment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

@Assignment(assignment = "Memory")
public class Memory {

    static Random rand = new Random();
    static WebLatte frame = new WebLatte();
    static String[][] board = new String[6][6];
    static ArrayList<String> matchedCards = new ArrayList<String>();

    public static void main(String [] args){

        //Create Cards
        ArrayList<String> cards =  new ArrayList<String>();
        cards.addAll(Arrays.asList("Acorn", "Airport", "Award", "Baby", "Bike",
                "Binoculars", "Biohazard", "Boardwalk", "Boot", "Bug", "Cake",
                "Cactus", "Climbing", "Cloud", "Campfire", "Camera",
                "Car", "Defrost"));

        //Create Deck
        ArrayList<String> deck = new ArrayList<String>();
        deck = createDeck(deck, cards, false);

        // Display Instructions on Frame
        dispInstructions();

        //Fill the board
        deck = fischerYatesShuffle(cards, true);

        // Board filled
        for (int i=0; i<board.length; i++){
            for(int j=0; j<board.length; j++) {
                board [i][j] = deck.get(board.length*i+j);
                System.out.println("i: "+i +", j: "+j + ", x: " +board[i][j]);
            }
        }

        // Draw Board
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                frame.drawRectangle(200 + i * 60, 200 + j * 60, 50, 50, 0, ColorLatte.PapayaWhip);
            }
        }

        while(true) {

            frame.paint();
            //Ask the user to flip a card:
            frame.println("Pick 1st card");
            frame.println("Row?");
            int row = frame.nextLine().toInt();
            frame.println("Col?");
            int col = frame.nextLine().toInt();
            frame.drawNoun(board[row][col], 200 + row * 60, 200 + col * 60, 50, 50, 0);
            frame.paint();

            frame.println("Pick 2nd card");
            frame.println("Row?");
            int row2 = frame.nextLine().toInt();
            frame.println("Col?");
            int col2 = frame.nextLine().toInt();
            frame.drawNoun(board[row2][col2], 200 + row2 * 60, 200 + col2 * 60, 50, 50, 0);
            frame.paint();

            frame.clearConsole();
            dispInstructions();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(board[row][col] == board[row2][col2]){
                frame.drawRectangle(200 + row * 60, 200 + col * 60, 50, 50, 0, ColorLatte.WHITE);
                frame.drawRectangle(200 + row2 * 60, 200 + col2 * 60, 50, 50, 0, ColorLatte.WHITE);
                matchedCards.add(board[row][col]);
                frame.println("Matches: "+matchedCards.toString());
            }else{
                frame.drawRectangle(200 + row * 60, 200 + col * 60, 50, 50, 0, ColorLatte.PapayaWhip);
                frame.drawRectangle(200 + row2 * 60, 200 + col2 * 60, 50, 50, 0, ColorLatte.PapayaWhip);
            }

            frame.drawText("Matches: "+matchedCards.toString(),200,180,20,0,ColorLatte.BLACK);
        }

    }

    /**
     * Uses the Fischer-Yates shuffle to shuffle an String Array
     * @param deck Deck Array to be shuffled
     * @return Shuffled String Array
     */
    private static ArrayList<String> fischerYatesShuffle(ArrayList<String> deck, boolean debug) {
        for (int i = deck.size() - 1; i > 0; i--) {
            int index = rand.nextInt(i +1);
            String a = deck.get(index);
            deck.set(index, deck.get(i));
            deck.set(i, a);
        }
        if(debug){
            System.out.println(deck.toString());
        }
        return deck;
    }


    /**
     *
     * @param deck the arraylist that the cards will be added to
     * @param cards array of names of cards
     * @param debug if you want it debugged
     * @return
     */
    private static ArrayList createDeck(ArrayList deck, ArrayList cards, boolean debug){
        cards.addAll(cards);
        deck.addAll(cards);
        if(debug){
            System.out.println(deck.toString());
        }
        return deck;
    }

    private static void dispInstructions(){
        frame.println("Turn over any two cards. " +
                "If the two cards match, keep them. " +
                "If they don't match, turn them back over. " +
                "Remember what was on each card and where it was. " +
                "Game is won when all cards are off the board.");
    }
}

