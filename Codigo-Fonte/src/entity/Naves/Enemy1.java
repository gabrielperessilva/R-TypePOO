package entity.Naves;

import entity.Projeteis.Tiro;
import game.Telas.Dimensoes;

public class Enemy1 extends Enemy{

    private Player player;
    public Enemy1(int x, int y, int speed, Player player,  Dimensoes dm) {
        super(x, y, speed, dm, 100);
        this.player = player;
    }

    public void update() {

        plusX(-getSpeed());//  x -= speed;

        if(getX() >= 1000) 
            plusX(-getSpeed());//  x -= speed;   

        if(getX() < -100)
            setIsVisivel(false);
        
        if(this.getY() > player.getY() + 20){
            plusY(-getSpeed()); // y -=  speed
            setDirection("up");
        }
        else if(this.getY() < player.getY() - 20){
            plusY(+getSpeed());//  y += speed;
            setDirection("down");
        }
        else{
            setDirection("right");
        } 
        
    
        float time2 = System.nanoTime();

        if (time2 > getNextFire()){
            setNextFire(time2+getFireRate());
            this.getTiros().add(new Tiro(getX(), getY() + 10, getDimensoes().getScreenWidth(), getDimensoes().getTileSize(), 10, "left"));
        }
    }
}