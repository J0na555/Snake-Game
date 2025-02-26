import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;//store the segments of the snake body
import java.util.Random; // getting random x and y values to place our food 
import javax.swing.*;

    public class Snake extends JPanel implements ActionListener , KeyListener{
        private class Tile{  // a clas to control the tile size on the window
            int x;
            int y;

            Tile( int x , int y){
                this.x = x;
                this.y = y;
            }
        }


        int boardWidth;
        int boardheight;
        int tileSize = 25;


        //snake
        Tile snakeHead; 
        ArrayList<Tile> snakeBody;

        //food
        Tile food;

        Random random;

        //game logic
        Timer gameLoop;
        int velocityX;
        int velocityY;
        boolean gameOver = false;
       


        Snake(int boardWidth ,int boardheight){
            this.boardWidth = boardWidth;
            this.boardheight = boardheight;
            setPreferredSize(new Dimension(this.boardWidth,this.boardheight));
            setBackground(Color.BLACK);
            addKeyListener(this);
            setFocusable(true);

            snakeHead = new Tile(5, 5);
            snakeBody = new ArrayList<Tile>();

            food = new Tile(10, 10);
            random = new Random();
            placefood();

            velocityX = 0;
            velocityY = 0;

            gameLoop = new Timer(100, this);
            gameLoop.start();


        }

        public void paintComponent(Graphics g){
            super.paintComponent(g);
            draw(g);
        }
        public void draw(Graphics g) {
            // Drawing grid to make it visible enough to show the tile size
            for (int i = 0; i < boardWidth / tileSize; i++) { // 600/25 = 24 rows and 24 columns of squares
                g.drawLine(i * tileSize, 0, i * tileSize, boardheight); // Vertical lines
                g.drawLine(0, i * tileSize, boardWidth, i * tileSize); // Horizontal lines
            }
        
            // Food
            g.setColor(Color.red);
            g.fillRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize);
        
            // Snake head
            g.setColor(Color.green);
            g.fillRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize); // To move the snake head to the desired pixels
        
            // Snake body
            for (Tile snakePart : snakeBody) {
                g.fillRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize);
            }
        }
        public void placefood(){
            food.x = random.nextInt(boardWidth/tileSize);// 600/25 = 24 that means the x position is gonna be a random num b/n 1 and 24
            food.y = random.nextInt(boardheight/tileSize); // same for y too
        }

        public boolean collision(Tile tile1 ,Tile tile2){
            return tile1.x == tile2.x && tile1.y == tile2.y;
        }

        public void move() {
            // Move the snake body
            if (!snakeBody.isEmpty()) {
                for (int i = snakeBody.size() - 1; i > 0; i--) {
                    snakeBody.set(i, snakeBody.get(i - 1));
                }
                snakeBody.set(0, new Tile(snakeHead.x, snakeHead.y));
            }
        
            // Move the snake head
            snakeHead.x += velocityX;
            snakeHead.y += velocityY;
        
            // Check for eating the food
            if (collision(snakeHead, food)) {
                // Add a new tile at the position of the previous tail
                Tile tail = snakeBody.isEmpty() ? new Tile(snakeHead.x - velocityX, snakeHead.y - velocityY) : snakeBody.get(snakeBody.size() - 1);
                snakeBody.add(new Tile(tail.x, tail.y));
                placefood();
            }
                // Check for collision with the snake body
                for (int i = 1; i < snakeBody.size(); i++) {
                    Tile snakePart = snakeBody.get(i);
                    if (collision(snakeHead, snakePart)) {
                        gameOver = true;
                    }
                }

                // Check for collision with the walls
                if (snakeHead.x < 0 || snakeHead.x >= boardWidth / tileSize || snakeHead.y < 0 || snakeHead.y >= boardheight / tileSize) {
                    gameOver = true;
                }
        }
        @Override
        public void actionPerformed(ActionEvent e){
            move();
            repaint();// calls draw over and over again
            if (gameOver){
                gameLoop.stop();//stop if game over is true 
            }

        }

        @Override
        public void keyPressed(KeyEvent e) {
           if (e.getKeyCode()==KeyEvent.VK_UP && velocityY != 1){ // to make sure its not going backwards cuz if it did
            velocityX = 0;                                        // it will be going back to his tail(his tail will be the head)
            velocityY = -1; // cuz 1 moves downward for some weird reason
           }else if (e.getKeyCode()== KeyEvent.VK_DOWN && velocityY != -1){
            velocityX = 0;
            velocityY = 1;
           }else if (e.getKeyCode()== KeyEvent.VK_LEFT && velocityX !=1){
            velocityX = -1;
            velocityY = 0;
            }else if(e.getKeyCode()== KeyEvent.VK_RIGHT && velocityX !=-1){
                velocityX = 1;
                velocityY = 0;
            }

           

        }

        // we dont need the rest of these override methods
        @Override
        public void keyTyped(KeyEvent e) {          
        }
       @Override
        public void keyReleased(KeyEvent e) {
        }


    }
