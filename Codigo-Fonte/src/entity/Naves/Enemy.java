package entity.Naves;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import game.Telas.Dimensoes;

public class Enemy extends Entity{

    private float fireRate;
    private float nextFire;
    private float actionRate;
    private float nextAction;
    private int score;

    public Enemy(int x, int y, int speed, Dimensoes dm, int score) {
        super(x, y, speed, "idle","/res/enemySprites/enemy_up.png", "/res/enemySprites/enemy_down.png", "/res/enemySprites/enemy_idle.png", "/res/enemySprites/enemy_right.png", dm);
        
        this.fireRate = 1000000000f;
        this.fireRate = 1500000000f;
        this.nextFire = 0.5f;

        this.actionRate = 400000000f;
        this.nextAction = 0;

        this.score = score;
    }

    public void update(){

    }
    
    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        switch(getDirection()) {
            case "up":
                image = getUp();
                break;
            
            case "down":
                image = getDown();
                break;

            case "idle":
                image = getIdle();
                break;

            case "right":
                image = getRight();
                break;
        }
        g2.drawImage(image, getX(), getY(), getDimensoes().getTileSize(),getDimensoes().getTileSize(), null); // "null" é para o imageObserver (notifica o aplicativo sobre atualizações em uma imagem carregada)
    }
    public float getFireRate() {
        return fireRate;
    }

    public float getNextFire() {
        return nextFire;
    }

    public void setNextFire(float nextFire) {
        this.nextFire = nextFire;
    }

    
    public float getActionRate() {
        return actionRate;
    }
    
    public float getNextAction() {
        return nextAction;
    }

    public void setNextAction(float s) {
        this.nextAction = s;
    }

    public int getScore() {
        return score;
    }
}