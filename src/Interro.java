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
        width = 1010; height = 1000;

        cL = new CardLayout();

        panel = new Panel();

        frame = new JFrame("Test");
        frame.setSize(width, height+frame.getInsets().top);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(panel);
        frame.setVisible(true);
    }
    class Panel extends JPanel{
        public Panel(){
            setLayout(cL);

            game = new Game();
            homePage = new Home();
            teamPage = new TeamPage();

            add(homePage, "home");
            add(teamPage, "team");
            add(game, "game");
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
    class Game extends JPanel{
        int alive;
        int time;
        int gLength, cLength;
        int[][] rocks;
        public Game(){
            setBackground(Color.decode("#329da8"));
            gLength = cLength = 10;
            rocks = new int[300][2];
            for(int i = 0; i < rocks.length; i++) for(int j = 0; j < rocks[0].length; j++) rocks[i][j] = (int)(Math.random()*965+15);
        }
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(Color.decode("#80af49"));
            g2.fillRect(10, 10, 980, 980);
            for(int i = 0; i < rocks.length; i++) for(int j = 0; j < rocks[0].length; j++){
                g2.setColor(Color.GRAY);
                g2.fillOval(rocks[i][0], rocks[i][1], 10, 10);
            }
        }
        class Player{
            int[] pos = new int[4];
            int health;
            public Player(){
                for(int i = 0; i < 4; i++){
                    int playerPosDetermine = (int)(Math.random()*10);
                    pos[i] = playerPosDetermine;
                }
                health = 100;
            }
        }
    }
}