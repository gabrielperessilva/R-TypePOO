package entity.Projeteis;

import java.awt.Graphics2D;

public class Tiro extends Projeteis{
    private String direction;
    public Tiro(int x, int y, int largura, int tileSize, int velocidade, String direction) {
        super(x, y, largura, tileSize, velocidade, "/res/playerSprites/tiro.png", 1);
        this.direction = direction;
    }
      
    public void draw(Graphics2D g2) {

        if (direction.equals("right")){
            g2.drawImage(getImagemTiro(), getX(), getY(), getTileSize(), getTileSize(), null); // "null" é para o imageObserver (notifica o aplicativo sobre atualizações em uma imagem carregada)
        }else{
            g2.drawImage(getImagemTiro(), getX(), getY(), -getTileSize(), getTileSize(), null); 
        }
        
    }
    
    public void update() {

        if(direction == "right")
            plusX(getVelocidade());

        else if(direction == "left")
        plusX(-getVelocidade());

        if (getX() > getLargura()){
            setIsVisivel(false);
        }
    }

}
