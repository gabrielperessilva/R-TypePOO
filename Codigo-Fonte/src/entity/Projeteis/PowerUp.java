package entity.Projeteis;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import game.Telas.Dimensoes;
import java.awt.Rectangle;

public class PowerUp {

    private Dimensoes dm;
    private int x, y;
    private int speed;
    private BufferedImage image;
    private boolean isVisivel;
    
    public PowerUp(int x, int y, int speed, Dimensoes dm) {

        this.dm = dm;
        this.x = x; 
        this.y = y;
        this.speed = speed;
        this.isVisivel = true;

        try{
            image = ImageIO.read(this.getClass().getResourceAsStream("/res/playerSprites/tiro_PowerUp.png")); //Lê o arquivo de imagem e aloca em um BufferedImage
        }
        catch(IOException e){
            e.printStackTrace(); //imprime o throwable junto com outros detalhes como o número da linha e o nome da classe onde ocorreu a exceção.
        }
    }

    public void update(){
        plusX(-getSpeed());

        if(x < -100)
            setIsVisivel(false);  
    }

    public Rectangle getBounds(){
        return new Rectangle(x, y, dm.getTileSize(), dm.getTileSize());
    }

    public int getSpeed() {
        return speed;
    }
    
    public void plusX(int s){
        this.x += s;
    }

    public void setIsVisivel(boolean isVisivel){
        this.isVisivel = isVisivel;
    }

    public boolean getIsVisivel() {
        return isVisivel;
    }  

    public void draw(Graphics2D g2) {
    
        g2.drawImage(image, x, y, dm.getTileSize(), dm.getTileSize(), null); 
    }

}



