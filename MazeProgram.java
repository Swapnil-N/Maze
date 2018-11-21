// Swapnil Napuri 
//Add ons: Music, multiple start mazes, stopwatch

import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Color;
import java.awt.Font;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import javax.sound.sampled.*;

public class MazeProgram extends JPanel implements KeyListener, MouseListener{

	JFrame frame;
	private Thread t;
	private boolean gameOn;
	private int frameX = 1200;
	private int frameY = 1000;

	//declare an array to store the maze

	String[][] maze = new String[13][1];
	int y=3,x=1;
	int endX = 0, endY = 0;

	private int dir=1;
	private int shiftY = 50;
	private int shiftX = 110;
	private int jump=20;
	private int steps = 0;
	private boolean gameOver = false;

	private ArrayList<Integer> wallNums = new ArrayList<Integer>();
	private ArrayList<Polygon> allPolys = new ArrayList<Polygon>();

	long startTime;
	Clip audioClip;

	public MazeProgram(){

		setBoard();
		frame=new JFrame();
		frame.add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(frameX, frameY);
		frame.setVisible(true);
		frame.addKeyListener(this);
		setWalls();
		soundStuff();
		//this.addMouseListener(this);

		//f=new Font("ARIAL",Font.BOLD,22);


	}
	public void paintComponent(Graphics g){

		super.paintComponent(g);
		g.setColor(Color.BLACK);	//this will set the background color
		g.fillRect(0,0,frameX,frameY);

		//drawBoard here!
		g.setColor(Color.GREEN);

		for (int i=0;i<maze.length;i++){
			for (int j=0;j<maze[0].length;j++){

				if (maze[i][j].equals("+"))
					g.drawRect(j*jump+shiftX, i*jump+shiftY, jump, jump);

			}
		}
		g.setColor(Color.YELLOW);
		g.fillOval(endX*jump+shiftX, endY*jump+shiftY, jump, jump);

		g.setColor(Color.WHITE);
		g.fillRect(x*jump+shiftX,y*jump+shiftY,jump-1,jump-1);	

		g.setFont(new Font("ARIAL",Font.BOLD,22));
		g.setColor(Color.RED);
		g.drawString("Steps: "+steps, frameX/2-50,340);


		g.setColor(Color.BLUE);
		g.fillRect(348, 348, 494, 494);

		g.setColor(Color.GREEN);
		g.drawRect(350, 350, 490, 490);
		for (int i=350+70;i<=770;i+=70){
			g.drawLine(350, i, 350+490, i);
		}

		g.setColor(Color.RED);
		for (int i=1;i<4;i++){
			g.fillRect(350+70*(i-1), 350+70*i, 70, 350-70*2*(i-1));
		}
		for (int i=1;i<4;i++){
			g.fillRect(770-70*(i-1), 350+70*i, 70, 350-70*2*(i-1));
		}
		g.fillRect(560, 560, 70, 70);
		g.setColor(Color.GREEN);
		g.drawRect(560, 560, 70, 70);

		for (Polygon item: allPolys){
			g.setColor(Color.RED);
			g.fillPolygon(item);
			g.setColor(Color.GREEN);
			g.drawPolygon(item);
		}
		
		//other commands that might come in handy


		//you can also use Font.BOLD, Font.ITALIC, Font.BOLD|Font.Italic
		//g.drawOval(x,y,10,10);
		//g.fillRect(x,y,100,100);
		//g.fillOval(x,y,10,10);

		if (gameOver){
			g.setFont(new Font("ARIAL",Font.BOLD,50));
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(0, 0, frameX, frameY);
			g.setColor(Color.CYAN);
			g.drawString("You a WINNER !!!", frameX/2-200, frameY/2-50);
			g.drawString("It took you "+steps + " Steps", frameX/2-220, frameY/2);
			g.drawString("It took you "+(System.currentTimeMillis()-startTime)/1000.0 + " seconds", frameX/2-300, frameY/2+50);

		}

	}
	public void setBoard()
	{
		//choose your maze design

		//pre-fill maze array here

		int rando = (int)(Math.random()*2)+1;

		String inputName = "maze";
		if(rando == 1)
			inputName+="1.txt";
		else inputName+="2.txt";

		System.out.println(inputName);

		File name = new File(inputName);
		int r=0;
		try
		{
			BufferedReader input = new BufferedReader(new FileReader(name));
			String text;
			while( (text=input.readLine())!= null){

				//System.out.println(text);
				maze[r] = text.split("");
				r++;
				//your code goes in here to chop up the maze design
				//fill maze array with actual maze stored in text file
			}

			/*for (String[] printarr: maze){
				for (String item: printarr){
					System.out.print(item);
				}
				System.out.println();
			}*/

			for (int i=0;i<maze.length;i++){
				for (int j=0;j<maze[0].length;j++){
					if (maze[i][j].equals("S")){
						y = i;
						x = j;
					}
					if (maze[i][j].equals("E")){
						endX = j-1;
						endY = i;
					}
				}
			}

		}
		catch (IOException io)
		{
			System.err.println("File error");
		}
		startTime = System.currentTimeMillis();
		//System.out.println(startTime);
		setWalls();
	}

	public void soundStuff(){

       try{
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File("SickoMode.wav"));
            audioClip = AudioSystem.getClip();
            audioClip.open(audioStream);
        }catch(Exception e){
			System.out.println("Null");
		}
        audioClip.loop(Clip.LOOP_CONTINUOUSLY);
    }

	public void setWalls(){
		
		//0=up, 1=right, 2=down, 3=left

		wallNums = new ArrayList<Integer>();

		if (dir == 1){
			if (x+3 < maze[0].length){
				for (int i = 1;i<=3;i++){
					//System.out.println(maze[y+1][x+i]);
					if (maze[y-1][x+i].equals("+"))
						wallNums.add(i);
					if (maze[y+1][x+i].equals("+"))
						wallNums.add(i+3);
				}
				if (maze[y][x+3].equals("+"))
					wallNums.add(7);
				if (maze[y][x+2].equals("+"))	
					wallNums.add(8);
				if (maze[y][x+1].equals("+"))
					wallNums.add(9);
			}
			else if (x+2 < maze[0].length){
				for (int i = 1;i<=2;i++){
					//System.out.println(maze[y+1][x+i]);
					if (maze[y-1][x+i].equals("+"))
						wallNums.add(i);
					if (maze[y+1][x+i].equals("+"))
						wallNums.add(i+3);
				}
				if (maze[y][x+2].equals("+"))	
					wallNums.add(8);
				if (maze[y][x+1].equals("+"))
					wallNums.add(9);
			}
			else{
				if (maze[y-1][x+1].equals("+"))
					wallNums.add(1);
				if (maze[y+1][x+1].equals("+"))
					wallNums.add(1+3);	
				if (maze[y][x+1].equals("+"))
					wallNums.add(9);
			}
		}
		if (dir == 2){ //down
			if (y+3 < maze.length){
				for (int i = 1;i<=3;i++){  
					if (maze[y+i][x+1].equals("+"))
						wallNums.add(i);
					if (maze[y+i][x-1].equals("+"))
						wallNums.add(i+3);
				}
				if (maze[y+3][x].equals("+"))
					wallNums.add(7);
				if (maze[y+2][x].equals("+"))
					wallNums.add(8);
				if (maze[y+1][x].equals("+"))
					wallNums.add(9);
			}
			else if (y+2 < maze.length){
				for (int i = 1;i<=2;i++){  
					if (maze[y+i][x+1].equals("+"))
						wallNums.add(i);
					if (maze[y+i][x-1].equals("+"))
						wallNums.add(i+3);
				}
				if (maze[y+2][x].equals("+"))
					wallNums.add(8);
				if (maze[y+1][x].equals("+"))
					wallNums.add(9);
			}
			else{
				if (maze[y+1][x+1].equals("+"))
					wallNums.add(1);
				if (maze[y+1][x-1].equals("+"))
					wallNums.add(1+3);
				if (maze[y+1][x].equals("+"))
					wallNums.add(9);
			}
		}
		if (dir == 3){ //left
			if (x-3 >= 0){
				for (int i = 1;i<=3;i++){
					if (maze[y+1][x-i].equals("+"))
						wallNums.add(i);
					if (maze[y-1][x-i].equals("+"))
						wallNums.add(i+3);
				}
				if (maze[y][x-3].equals("+"))
					wallNums.add(7);
				if (maze[y][x-2].equals("+"))
					wallNums.add(8);
				if (maze[y][x-1].equals("+"))
					wallNums.add(9);
			}
			else if (x-2 >= 0){
				for (int i = 1;i<=2;i++){
					if (maze[y+1][x-i].equals("+"))
						wallNums.add(i);
					if (maze[y-1][x-i].equals("+"))
						wallNums.add(i+3);
				}
				if (maze[y][x-2].equals("+"))
					wallNums.add(8);
				if (maze[y][x-1].equals("+"))
					wallNums.add(9);
			}
			else{
				if (maze[y+1][x-1].equals("+"))
					wallNums.add(1);
				if (maze[y-1][x-1].equals("+"))
						wallNums.add(1+3);
				if (maze[y][x-1].equals("+"))
					wallNums.add(9);
			}
		}
		if (dir == 0){ //up
			if (y-3 >= 0){
				for (int i = 1;i<=3;i++){
					if (maze[y-i][x-1].equals("+"))
						wallNums.add(i);
					if (maze[y-i][x+1].equals("+"))
						wallNums.add(i+3);
				}
				if (maze[y-3][x].equals("+"))
					wallNums.add(7);
				if (maze[y-2][x].equals("+"))
					wallNums.add(8);
				if (maze[y-1][x].equals("+"))
					wallNums.add(9);
			}
			else if (y-2 >= 0){
				for (int i = 1;i<=2;i++){
					if (maze[y-i][x-1].equals("+"))
						wallNums.add(i);
					if (maze[y-i][x+1].equals("+"))
						wallNums.add(i+3);
				}
				if (maze[y-2][x].equals("+"))
					wallNums.add(8);
				if (maze[y-1][x].equals("+"))
					wallNums.add(9);
			}
			else{
				if (maze[y-1][x-1].equals("+"))
					wallNums.add(1);
				if (maze[y-1][x+1].equals("+"))
					wallNums.add(1+3);
				if (maze[y-1][x].equals("+"))
					wallNums.add(9);
			}
		}

		allPolys = new ArrayList<Polygon>();
		for (Integer thing: wallNums){
			allPolys.add((new Wall(thing)).getPoly());
		}
		//allPolys.add((new Wall(1)).getPoly());

		//System.out.println(wallNums);

	}


	public void keyPressed(KeyEvent ke){

		if(ke.getKeyCode()==39){ // right   		0=up, 1=right, 2=down, 3=left
			if(dir==3)
				dir=0;		//dir=(dir+1)%4;
			else dir++;

		}
		if(ke.getKeyCode()==37){ // left
			if(dir==0)
				dir=3;
			else dir--;
		}

		if(ke.getKeyCode()==38){ // up
			if (dir==1){
				if (maze[y][x+1].equals(" ")){
					x++;
					steps++;
				}
				if (maze[y][x+1].equals("E")){
					x++;
					gameOver = true;
				}
			}
			else if(dir==2){
				if (maze[y+1][x].equals(" ")){
					y++;
					steps++;
				}
				if (maze[y+1][x].equals("E")){
					y++;
					gameOver = true;
				}
			}
			else if(dir==3){
				if (maze[y][x-1].equals(" ")){
					x--;
					steps++;
				}
				if (maze[y][x-1].equals("E")){
					x--;
					gameOver = true;
				}
			}
			else if(dir==0){
				if (maze[y-1][x].equals(" ")){
					y--;
					steps++;
				}
				if (maze[y-1][x].equals("E")){
					y--;
					gameOver = true;
				}
			}
		}
		/*if(ke.getKeyCode()==40){ // down
			if (dir==1)
				y--;
			else if(dir==2)
				x--;
			else if(dir==3)
				y++;
			else if(dir==0)
				x++;
		}*/
		
		setWalls();
		repaint();

	}
	public void keyReleased(KeyEvent ke){

	}
	public void keyTyped(KeyEvent e)
	{
	}
	public void mouseClicked(MouseEvent e)
	{
	}
	public void mousePressed(MouseEvent e)
	{
	}
	public void mouseReleased(MouseEvent e)
	{
	}
	public void mouseEntered(MouseEvent e)
	{
	}
	public void mouseExited(MouseEvent e)
	{
	}
	public static void main(String args[])
	{
		MazeProgram app=new MazeProgram();
	}
}