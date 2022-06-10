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

        class Player extends JPanel implements KeyListener{
            boolean map;
            boolean up, down, left, right;
            int[] pos = new int[2];
            int health;
            int speed;
            int scope;
            int[] beachXScaled;
            int[] beachYScaled;
            public Player(){
                PlayerMover playerMover = new PlayerMover();
                Timer playerTimer = new Timer(3, playerMover);
                playerTimer.start();
                setBackground(Color.decode("#329da8"));
                addKeyListener(this);
                for(int i = 0; i < 2; i++){
                    pos[i] = (int)(Math.random()*mapWidth+margin);
                }
                health = 100;
                scope = 1;
                speed = 1;
                up = down = left = right = false;
                map = true;
            }

            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                if(map) {
                    //reference corner squares
                    g2.setColor(Color.BLACK);
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
                            g2.fillOval(trees[i][0], trees[i][1], 5, 5);
                        }
                    }
                    //tree leaves
                    for (int i = 0; i < trees.length; i++) {
                        for (int j = 0; j < 2; j++) {
                            g2.setColor(new Color(22 / 255f, 128 / 255f, 48 / 255f, 0.5f));
                            g2.fillOval(trees[i][0] - 2, trees[i][1] - 2, 9, 9);
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
                        beachXScaled[i] = (beachX[i]-pos[0]) * scale / scope;
                        beachYScaled[i] = (beachY[i]-pos[1]) * scale / scope;
                    }
                    //grass
                    g2.setColor(Color.decode("#80af49"));
                    g2.fillRect((20 - pos[0]) * scale / scope, (20 - pos[1]) / scope, 960 * scale / scope, 960 * scale / scope);
                    //player
                    g2.setColor(Color.decode("#fae06b"));
                    g2.fillOval(500-50/2/scope, 500-50/2/scope, 50/scope, 50/scope);
                    //rocks
                    g2.setColor(Color.GRAY);
                    for (int i = 0; i < rocks.length; i++) {
                        g2.fillOval((rocks[i][0] - pos[0]) * scale / scope, (rocks[i][1] - pos[1]) * scale / scope, 5 * scale / scope, 5 * scale / scope);
                    }
                    //reference lines
                    g2.setColor(Color.BLACK);
                    g2.drawLine(500, 0, 500, 1000);
                    g2.drawLine(0, 500, 1000, 500);
                }
            }

            class PlayerMover implements ActionListener{
                public void actionPerformed(ActionEvent e){
                    if(up) pos[1] -= speed;
                    if(down) pos[1] += speed;
                    if(left) pos[0] -= speed;
                    if(right) pos[0] += speed;
                    grabFocus();
                    repaint();
                }
            }

            public void keyTyped(KeyEvent e){
                if(e.getKeyChar() == 'm'){
                    map = !map;
                    repaint();
                }
            }

            public void keyPressed(KeyEvent e){
                char key = e.getKeyChar();
                switch(key){
                    case 'w': up = true;
                    case 's': down = true;
                    case 'a': left = true;
                    case 'd': right = true;
                }
            }

            public void keyReleased(KeyEvent e){
                char key = e.getKeyChar();
                switch(key){
                    case 'w' : up = false;
                    case 's' : down = false;
                    case 'a' : left = false;
                    case 'd' : right = false;
                }
            }
        }
    }
}