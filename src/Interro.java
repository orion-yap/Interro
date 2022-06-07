import javax.swing.*;
import java.awt.*; import java.awt.event.*;
import java.util.ArrayList; import java.util.Scanner;
import java.io.FileWriter; import java.io.PrintWriter; import java.io.File; import java.io.FileNotFoundException; import java.io.IOException;
public class Interro {
    int width, height;
    JFrame frame;
    ArrayList<Game> privateGames = new ArrayList();
    ArrayList<Game> publicGames = new ArrayList();
    ArrayList<Team> teams = new ArrayList();
    Home homePage;
    TeamPage teamPage;
    public static void main(String[] args){new Interro();}
    public Interro(){
        width = 1000; height = 1000;

        homePage = new Home();
        teamPage = new TeamPage();

        frame = new JFrame("Test");
        frame.setSize(width, height);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(homePage);
        frame.setVisible(true);
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
            battleB.addActionListener(e -> {

            });
            add(battleB);
            battleB.setBounds(750, 300, 200, 70);

            makeGameB = new JButton("Make Game");
            makeGameB.setBackground(Color.decode("#1e90ff"));
            makeGameB.setFont(font2);
            makeGameB.addActionListener(e -> {

            });
            add(makeGameB);
            makeGameB.setBounds(750, 530, 200, 50);
    
            makeTeamB = new JButton("Make Team");
            makeTeamB.setBackground(Color.decode("#1e90ff"));
            makeTeamB.setFont(font2);
            makeTeamB.addActionListener(e -> {
                teamPage.reset();
                frame.setContentPane(teamPage);
            });
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
        Grid[][] grid;
        public Game(){
            grid = new Grid[10][10];
        }
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            for(int i = 0; i < grid.length; i++) for(int j = 0; j < grid[0].length; j++)
                for(int k = 0; k < grid[i][j].coords.length; k++) for(int l = 0; l < grid[i][j].coords[0].length; l++){
                    int terrainDetermine = (int)(Math.random()*100);
                    if(terrainDetermine < 52) grid[i][j].coords[k][l] = 0;
                    else if(terrainDetermine < 68) grid[i][j].coords[k][l] = 1;
                    else if(terrainDetermine < 84) grid[i][j].coords[k][l] = 2;
                    else if(terrainDetermine < 100) grid[i][j].coords[k][l] = 3;
                }
        }
        class Grid{
            int[][] coords = new int[10][10];
            /*
            0; nothing
            1; rock
            2; tree
            3; barrel
             */
            public Grid(){
                for(int i = 0; i < grid.length; i++) for(int j = 0; j < grid[0].length; j++)
                    for(int k = 0; k < grid[i][j].coords.length; k++) for(int l = 0; l < grid[i][j].coords[0].length; l++){
                        int terrainDetermine = (int)(Math.random()*100);
                        if(terrainDetermine < 52) grid[i][j].coords[k][l] = 0;
                        else if(terrainDetermine < 68) grid[i][j].coords[k][l] = 1;
                        else if(terrainDetermine < 84) grid[i][j].coords[k][l] = 2;
                        else if(terrainDetermine < 100) grid[i][j].coords[k][l] = 3;
                    }
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