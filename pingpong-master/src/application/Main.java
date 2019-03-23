package application;
	


import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;


public class Main extends Application {
	
	// размер пол¤
	private static final int width = 800;
	private static final int height = 600;
	
	// ширина и высота ракетка
	private static final int RACKET_WIDTH = 10;
	private static final int RACKET_HEIGHT = 90;
	
	// радиус м¤ча
	private static final int BALL_RAD = 30;
	
	// начальные координаты ракетки игрока
	double playerX=0;
	double playerY = height/2;
	
	// начальные координаты ракетки компа
	double compX = width - RACKET_WIDTH;
	double compY = height/2;
	
	// координаты м¤ча
	double ballX = width/2;
	double ballY = height/2;
	
	// инструмент рисовани¤
	GraphicsContext gc;
	
	// скорость м¤ча
	double ballYSpeed = 3;
	double ballXSpeed = 3;
	
	// игровой цикл
	boolean gameStarted;
	
	int score = 0;
	
	private void drawTable() {
		// рисуем поле
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, width, height);			
		// рисуем разделительную линию
		gc.setFill(Color.WHITE);
		gc.fillRect(width/2, 0, 2, height);		
		// рисуем м¤ч
		if(gameStarted) {
			ballX+=ballXSpeed;
			ballY+=ballYSpeed;
			// логика - комп отбивает м¤ч
			if(ballX > width-width/4) {
				compY = ballY - RACKET_HEIGHT/2;
			}
			gc.fillOval(ballX, ballY, BALL_RAD, BALL_RAD);
		} else {
			gc.setStroke(Color.WHITE);
			gc.setTextAlign(TextAlignment.CENTER);
			gc.strokeText("Click to start", width/2, height/2);			
		}
		
		
		if(ballY==600-BALL_RAD)
		{
			ballYSpeed = -3;
		}
		if(ballY==0)
		{
			ballYSpeed = 3;
		}
		if(ballX>=800-BALL_RAD-RACKET_WIDTH)
		{
			ballXSpeed = -3;
		}
		if(ballX<=RACKET_WIDTH)
		{
			ballXSpeed = 3;
		}
		if(ballX<=RACKET_WIDTH && (ballY<playerY || ballY>playerY+RACKET_HEIGHT))
		{
			score = 0;
			ballX=width/2;
			ballY=height/2;
			gameStarted = false;
		}
		if(ballX<=RACKET_WIDTH && (ballY>playerY && ballY<playerY+RACKET_HEIGHT))
		{
			score++;				
		}
			gc.setStroke(Color.WHITE);
			gc.setTextAlign(TextAlignment.LEFT);
			gc.strokeText("score: "+score, width/2+30, height-10);
		
	
		// рисуем ракетки
		gc.fillRect(playerX, playerY, RACKET_WIDTH, RACKET_HEIGHT);
		gc.fillRect(compX, compY, RACKET_WIDTH, RACKET_HEIGHT);	
	}
	
	@Override
	public void start(Stage root) {
		Canvas canvas = new Canvas(width,height);
		gc = canvas.getGraphicsContext2D();
		drawTable();		
		Timeline t1 = new Timeline(new KeyFrame(Duration.millis(10), e->drawTable()));
		t1.setCycleCount(Timeline.INDEFINITE);
		
		canvas.setOnMouseClicked(e -> gameStarted = true);
		canvas.setOnMouseMoved(e -> playerY = e.getY());
		
		root.setScene(new Scene(new StackPane(canvas)));
		root.setTitle("Ping-pong");
		root.show();
		t1.play();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
