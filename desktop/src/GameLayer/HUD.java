package GameLayer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class HUD extends Actor {
    private int worldTimer;
    private float timeCount;
    private int lives;
    private int score;
    private Stage stage;
    private Label countdownLabel;
    private Label scoreLabel;
    private Label timeLabel;
    private Label livesLabel;

    private HUD hud;

    private Label backgroundLabel;


    public HUD() {
        worldTimer = 300;
        timeCount = 0;
        lives = 3;
        score = 0;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Create a BitmapFont instance for the labels
        BitmapFont font = new BitmapFont();

        // Create LabelStyle with the font
        LabelStyle labelStyle = new LabelStyle(font, Color.WHITE);

        stage.getViewport().setWorldSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Initialize the labels with appropriate text and style
        countdownLabel = new Label(String.format("%03d", worldTimer), labelStyle);
        scoreLabel = new Label(String.format("%06d", score), labelStyle);
        timeLabel = new Label("TIME", labelStyle);
        livesLabel = new Label("LIVES x " + lives, labelStyle);


        // Add labels to the stage
        stage.addActor(countdownLabel);
        stage.addActor(scoreLabel);
        stage.addActor(timeLabel);
        stage.addActor(livesLabel);

        // Position labels dynamically based on the stage size
        float labelPadding = 10f; // Padding between labels and screen edges
        float labelY = stage.getHeight() - labelPadding - 50; // Start position from the top of the stage

        countdownLabel.setPosition(labelPadding, labelY - countdownLabel.getHeight());
        scoreLabel.setPosition(stage.getWidth() / 2f - scoreLabel.getWidth() / 2f, labelY - scoreLabel.getHeight());
        timeLabel.setPosition(labelPadding, labelY - countdownLabel.getHeight() - timeLabel.getHeight() - labelPadding);
        livesLabel.setPosition(stage.getWidth() - livesLabel.getWidth() - labelPadding, labelY - livesLabel.getHeight());

        // Logging the position and size of HUD elements after setting positions
        System.out.println("Countdown Label: X=" + countdownLabel.getX() + ", Y=" + countdownLabel.getY() + ", Width=" + countdownLabel.getWidth() + ", Height=" + countdownLabel.getHeight());
        System.out.println("Score Label: X=" + scoreLabel.getX() + ", Y=" + scoreLabel.getY() + ", Width=" + scoreLabel.getWidth() + ", Height=" + scoreLabel.getHeight());
        System.out.println("Time Label: X=" + timeLabel.getX() + ", Y=" + timeLabel.getY() + ", Width=" + timeLabel.getWidth() + ", Height=" + timeLabel.getHeight());
        System.out.println("Lives Label: X=" + livesLabel.getX() + ", Y=" + livesLabel.getY() + ", Width=" + livesLabel.getWidth() + ", Height=" + livesLabel.getHeight());
    }


    public void setVisible(boolean visible) {
        stage.getRoot().setVisible(visible);
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    public void render() {
        float delta = Gdx.graphics.getDeltaTime();
        timeCount += delta;
        if (timeCount >= 1) {
            worldTimer--;
            countdownLabel.setText(String.format("%03d", worldTimer));
            timeCount = 0;
        }




        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    public void dispose() {
        stage.dispose();
    }
}
