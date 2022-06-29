import javax.swing.*;
import java.awt.*; import java.awt.event.*;
import java.util.ArrayList; import java.util.Scanner;
import java.io.FileWriter; import java.io.PrintWriter; import java.io.File; import java.io.FileNotFoundException; import java.io.IOException;
public class Interro {
    int width, height;
    CardLayout cL;
    JFrame frame;
    ArrayList<Game> privateGames = new ArrayList();
    ArrayList<Game> publicGames = new ArrayList();
    ArrayList<Team> teams = new ArrayList();
    Panel panel;
    Game game; Home homePage; TeamPage teamPage;
    public static void main(String[] args){new Interro();}
    public Interro(){
        width = 1016;
        height = 1039;

        cL = new CardLayout();

        panel = new Panel();

        frame = new JFrame("Test");
        frame.setSize(width, height);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(panel);
        frame.setVisible(true);
    }

    class Panel extends JPanel{
        public Panel(){
            setLayout(cL);

            game = new Game();
            game.newPlayer();
            homePage = new Home();
            teamPage = new TeamPage();

            add(homePage, "home");
            add(teamPage, "team");
            add(game.player, "game");
        }
    }

    class Home extends JPanel{
        Font font1, font2;
        JButton battleB, makeGameB, makeTeamB;
        JTextField joinGame, joinTeam;
        public Home(){
            setLayout(null);

            font1 = new Font("Serif", Font.PLAIN, 50);
            font2 = new Font("Serif", Font.PLAIN, 30);

            battleB = new JButton("Battle");
            battleB.setBackground(Color.decode("#b066bb"));
            battleB.setFont(font1);
            battleB.addActionListener(e -> cL.show(panel, "game"));
            add(battleB);
            battleB.setBounds(750, 300, 200, 70);

            makeGameB = new JButton("Make Game");
            makeGameB.setBackground(Color.decode("#1e90ff"));
            makeGameB.setFont(font2);
            makeGameB.addActionListener(e -> cL.show(panel, "gameCreate"));
            add(makeGameB);
            makeGameB.setBounds(750, 530, 200, 50);
    
            makeTeamB = new JButton("Make Team");
            makeTeamB.setBackground(Color.decode("#1e90ff"));
            makeTeamB.setFont(font2);
            makeTeamB.addActionListener(e -> cL.show(panel, "team"));
            add(makeTeamB);
            makeTeamB.setBounds(750, 460, 200, 50);
        }

        public void paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            setBackground(Color.decode("#80af49"));
            g2.setColor(Color.BLACK);
            g2.drawRect(750, 50, 200, 200);
        }
    }

    class TeamPage extends JPanel{
        JButton home;
        public TeamPage(){
            setBackground(Color.BLUE);
        }

        public void reset(){

        }

        public void paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
        }
    }

    class Team{
        int maxPlayers, players;
    }

    class Game{
        int alive;
        int time;
        final int margin = 20;
        final int mapWidth = 955;
        int gLength, cLength;
        final int scale = 15;
        int[] beachX;
        int[] beachY;
        int[][] rocks;
        int[][] trees;
        Player player;
        public Game(){
            gLength = cLength = 10;
            rocks = new int[300][2];
            trees = new int[450][2];
            for(int i = 0; i < rocks.length; i++) for(int j = 0; j < 2; j++) rocks[i][j] = (int)(Math.random()*mapWidth+margin);
            for(int i = 0; i < trees.length; i++) for(int j = 0; j < 2; j++) trees[i][j] = (int)(Math.random()*mapWidth+margin);
            beachX = new int[396];
            beachY = new int[396];
            beachX[0] = 10;
            beachY[0] = 10;
            beachX[98] = 990;
            beachY[98] = 10;
            beachX[196] = 990;
            beachY[196] = 990;
            beachX[304] = 10;
            beachY[304] = 990;
            for(int i = 1; i < 98; i++){
                beachX[i] = i*10;
                beachY[i] = (int)(Math.random()*5+10);
            }
            for(int i = 99; i < 196; i++){
                beachX[i] = (int)(Math.random()*5+985);
                beachY[i] = (i-98)*10;
            }
            for(int i = 197; i < 304; i++){
                beachX[i] = (i-196)*10;
                beachY[i] = (int)(Math.random()*5+985);
            }
            for(int i = 305; i < 396; i++){
                beachX[i] = (int)(Math.random()*5+10);
                beachY[i] = (i-304)*10;
            }
        }

        public void newPlayer(){
            player = new Player();
        }

        class Player extends JPanel implements KeyListener, MouseListener, MouseMotionListener{
            boolean map;
            boolean up, down, left, right;
            boolean mouseDown;
            boolean f3;
            int[] pos = new int[2];
            int move;
            int health;
            int speed;
            int scope;
            int mX, mY;
            int[] beachXScaled;
            int[] beachYScaled;
            BasicStroke normal, border;
            public Player(){
                PlayerMover playerMover = new PlayerMover();
                Timer playerTimer = new Timer(5, playerMover);
                playerTimer.start();

                Render render = new Render();
                Timer renderTimer = new Timer(3, render);
                renderTimer.start();

                setBackground(Color.decode("#28546c"));
                addKeyListener(this);
                for(int i = 0; i < 2; i++){
                    pos[i] = (int)(Math.random()*mapWidth+margin);
                }
                health = 100;
                scope = 1;
                speed = 1;
                up = down = left = right = false;
                map = false;
                f3 = false;
                move = 0;
                mouseDown = false;
                mX = getX();
                mY = getY();
                normal = new BasicStroke(1);
                border = new BasicStroke(5);
            }

            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                if(map) {
                    //sea
                    g2.setColor(Color.decode("#329da8"));
                    g2.fillRect(0, 0, 1000, 1000);
                    //reference corner squares
                    g2.setColor(Color.decode("#424242"));
                    g2.fillRect(0, 0, 10, 10);
                    g2.fillRect(0, 990, 10, 10);
                    g2.fillRect(990, 0, 10, 10);
                    g2.fillRect(990, 990, 10, 10);
                    //beach
                    g2.setColor(Color.decode("#d0b45c"));
                    g2.fillPolygon(beachX, beachY, 396);
                    //grass
                    g2.setColor(Color.decode("#80af49"));
                    g2.fillRect(20, 20, 960, 960);
                    //rocks
                    for (int i = 0; i < rocks.length; i++) {
                        for (int j = 0; j < 2; j++) {
                            g2.setColor(Color.GRAY);
                            g2.fillOval(rocks[i][0], rocks[i][1], 5, 5);
                        }
                    }
                    //tree trunks
                    for (int i = 0; i < trees.length; i++) {
                        for (int j = 0; j < 2; j++) {
                            g2.setColor(Color.decode("#734b00"));
                            g2.fillOval(trees[i][0], trees[i][1], 4, 4);
                        }
                    }
                    //tree leaves
                    for (int i = 0; i < trees.length; i++) {
                        for (int j = 0; j < 2; j++) {
                            g2.setColor(new Color(22 / 255f, 128 / 255f, 48 / 255f, 0.5f));
                            g2.fillOval(trees[i][0] - 3, trees[i][1] - 3, 12, 12);
                        }
                    }
                    //player
                    g2.setColor(Color.decode("#8d00cf"));
                    g2.fillOval(pos[0]-5, pos[1]-5, 10, 10);
                }
                else {
                    beachXScaled = new int[beachX.length];
                    beachYScaled = new int[beachY.length];
                    for(int i = 0; i < beachX.length; i++){
                        beachXScaled[i] = (beachX[i] - pos[0]) * scale / scope + 500;
                        beachYScaled[i] = (beachY[i] - pos[1]) * scale / scope + 500;
                    }
                    //sea
                    g2.setColor(Color.decode("#329da8"));
                    g2.fillRect((- pos[0] * scale / scope) + 500, (- pos[1] * scale / scope) + 500, 1000 * scale / scope, 1000 * scale / scope);
                    //beach
                    g2.setColor(Color.decode("#d0b45c"));
                    g2.fillPolygon(beachXScaled, beachYScaled, 396);
                    //grass
                    g2.setColor(Color.decode("#80af49"));
                    g2.fillRect((20 - pos[0]) * scale / scope + 500, (20 - pos[1]) * scale / scope + 500, 960 * scale / scope, 960 * scale / scope);
                    //rocks
                    for(int i = 0; i < rocks.length; i++) {
                        for (int j = 0; j < 2; j++) {
                            g2.setColor(Color.GRAY);
                            g2.fillOval((rocks[i][0] - pos[0]) * scale / scope + 500, (rocks[i][1] - pos[1]) * scale / scope + 500, 5 * scale / scope, 5 * scale / scope);
                        }
                    }
                    //player body
                    g2.setColor(Color.decode("#fae06b"));
                    g2.fillOval(500 - 50 / 2 / scope, 500 - 50 / 2 / scope, 50 / scope, 50 / scope);
                    g2.setColor(Color.BLACK);
                    g2.setStroke(border);
                    g2.drawOval(500 - 50 / 2 / scope, 500 - 50 / 2 / scope, 50 / scope, 50 / scope);
                    //player hands
                    g2.setColor(Color.decode("#fae06b"));
                    g2.setStroke(normal);
                    //g2.fillOval();
                    //tree trunks
                    for(int i = 0; i < trees.length; i++) {
                        for (int j = 0; j < 2; j++) {
                            g2.setColor(Color.decode("#734b00"));
                            g2.fillOval((trees[i][0] - pos[0]) * scale / scope + 500 + scale / scope / 2, (trees[i][1] - pos[1]) * scale / scope + 500 + scale / scope / 2, 4 * scale / scope, 4 * scale / scope);
                        }
                    }
                    //tree leaves
                    for(int i = 0; i < trees.length; i++) {
                        for (int j = 0; j < 2; j++) {
                            g2.setColor(new Color(22 / 255f, 128 / 255f, 48 / 255f, 0.5f));
                            g2.fillOval((trees[i][0] - 3 - pos[0]) * scale / scope + 500 - scale / scope / 2, (trees[i][1] - 3 - pos[1]) * scale / scope + 500 - scale / scope / 2, 12 * scale / scope, 12 * scale / scope);
                        }
                    }
                    //reference lines
                    g2.setColor(Color.BLACK);
                    g2.drawLine(500, 0, 500, 1000);
                    g2.drawLine(0, 500, 1000, 500);
                }
                if(f3){
                    g2.setColor(Color.BLACK);
                    g2.setFont(new Font("Serif", Font.PLAIN, 30));
                    g2.drawString("x: "+pos[0]+", y: "+pos[1], 10, 40);
                }
            }

            class PlayerMover implements ActionListener{
                public void actionPerformed(ActionEvent e){
                    if(move == 2){
                        move = 0;
                        if (up && pos[1] > 0) pos[1] -= speed;
                        if (down && pos[1] < 1000) pos[1] += speed;
                        if (left && pos[0] > 0) pos[0] -= speed;
                        if (right && pos[0] < 1000) pos[0] += speed;
                    }
                    else move++;
                }
            }

            class Render implements ActionListener{
                public void actionPerformed(ActionEvent e){
                    grabFocus();
                    repaint();
                }
            }

            public void keyTyped(KeyEvent e){
                char key = e.getKeyChar();
                switch (key){
                    case 'm': {
                        map = !map;
                        break;
                    }
                    case '/': {
                        f3 = !f3;
                        break;
                    }
                }
            }

            public void keyPressed(KeyEvent e){
                char key = e.getKeyChar();
                switch(key){
                    case 'w': {
                        up = true;
                        break;
                    }
                    case 's': {
                        down = true;
                        break;
                    }
                    case 'a': {
                        left = true;
                        break;
                    }
                    case 'd': {
                        right = true;
                        break;
                    }
                }
            }

            public void keyReleased(KeyEvent e){
                char key = e.getKeyChar();
                switch(key){
                    case 'w' : {
                        up = false;
                        break;
                    }
                    case 's' : {
                        down = false;
                        break;
                    }
                    case 'a' : {
                        left = false;
                        break;
                    }
                    case 'd' : {
                        right = false;
                        break;
                    }
                }
            }

            public void mouseClicked(MouseEvent e){}

            public void mousePressed(MouseEvent e){
                mouseDown = true;

            }

            public void mouseReleased(MouseEvent e){
                mouseDown = false;
            }

            public void mouseEntered(MouseEvent e){}

            public void mouseExited(MouseEvent e){}

            public void mouseMoved(MouseEvent e){
                mX = getX();
                mY = getY();
            }

            public void mouseDragged(MouseEvent e){}
        }
    }
}