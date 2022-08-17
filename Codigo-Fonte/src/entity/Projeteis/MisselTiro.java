package entity.Projeteis;

import java.awt.Graphics2D;

public class MisselTiro extends Projeteis{
    

    public MisselTiro(int x, int y, int largura, int tileSize, int velocidade) {
        super(x, y, largura, tileSize, velocidade, "/res/enemySprites/missel_projectile.png", 4);
    }
     
    public void draw(Graphics2D g2) {
        g2.drawImage(getImagemTiro(), getX(), getY(), getTileSize(), getTileSize(), null);
    }
    
    public void update() {

        plusX(-getVelocidade());
        plusY(-getVelocidade());

        if (getX() > getLargura()){
            setIsVisivel(false);
        }
    }
}