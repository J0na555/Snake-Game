import javax.swing.*;


    public class Snakegame{
        public static void main(String[] args) {
            int boardWidth = 600;
            int boardheight = boardWidth;

            JFrame frame = new JFrame("Snake Game");
            frame.setVisible(true);
            frame.setSize(boardWidth , boardheight);
            // make the window 600*600
            frame.setLocationRelativeTo(null); 
            //open the window at the center of the window
            frame.setResizable(false); 
            // make it not resizable in size
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
            // the program will terminate when the user clicks the x button on the window


            Snake snake = new Snake(boardWidth, boardheight);
            frame.add(snake);
            frame.pack();
            snake.requestFocus();
            //the snake game is gonna be the one listening t0 the key presses           

        }

    }

