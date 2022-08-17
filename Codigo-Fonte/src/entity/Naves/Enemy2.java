package entity.Naves;

import entity.Projeteis.Tiro;
import game.Telas.Dimensoes;

public class Enemy2 extends Enemy{

    private int posY;

    public Enemy2(int x, int y, int speed, Dimensoes dm) {
        super(x, y, speed,dm, 200);
    }

    public void update() {

        if(getX() < -100)
            setIsVisivel(false);  

        if(getX() >= 1000) {
            plusX(-getSpeed());//  x -= speed; 
            setDirection("right");
        }

        if(getX() <= 1000) {

            float time = System.nanoTime();

            if(time > getNextAction()){
                setNextAction( time + getActionRate());
                posY = (int)(Math.random() * 520 + 40);
            }

            if((this.getY() - 100) > posY){
                plusY(-getSpeed()); // y -=  speed
                setDirection("up");
            }
            else if((this.getY() + 100) < posY){
                plusY(+getSpeed());//  y += speed;
                setDirection("down");
            }
            else{
                setDirection("idle");
            }
        }

        float time = System.nanoTime();

        if (time > getNextFire()){
            setNextFire(time+getFireRate());
            this.getTiros().add(new Tiro(getX(), getY() + 10, getDimensoes().getScreenWidth(), getDimensoes().getTileSize(), 10, "left"));
        }
    }
}