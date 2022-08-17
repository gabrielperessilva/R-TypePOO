package entity.Projeteis;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import game.Telas.Dimensoes;

public class Explosion {

    private int x, y;
    private BufferedImage[] animation = new BufferedImage[5];
    private Dimensoes dm;
    private int index;
    private int exploTick = 0;
    private boolean isVisivel;
    
    public Explosion(int x, int y, Dimensoes dm) {
        this.dm = dm;
        this.x = x; 
        this.y = y;
        this.isVisivel = true;
        this.index = 0;

        try{
            animation[0] = ImageIO.read(this.getClass().getResourceAsStream("/res/enemySprites/explosion_1.png"));     //Lê o arquivo de imagem e aloca em um BufferedImage
            animation[1] = ImageIO.read(this.getClass().getResourceAsStream("/res/enemySprites/explosion_2.png"));
            animation[2] = ImageIO.read(this.getClass().getResourceAsStream("/res/enemySprites/explosion_3.png"));
            animation[3] = ImageIO.read(this.getClass().getResourceAsStream("/res/enemySprites/explosion_4.png"));
            animation[4] = ImageIO.read(this.getClass().getResourceAsStream("/res/enemySprites/explosion_5.png"));
        }
        catch(IOException e){
            e.printStackTrace(); //imprime o throwable junto com outros detalhes como o número da linha e o nome da classe onde ocorreu a exceção.
        }
    }

    public void update() {
        if(isVisivel == true) {
            exploTick++;
            if(exploTick >= 10) {
                exploTick = 0;
                index++;
                if(index >= 5) {
                    index = 0;
                    isVisivel = false;
                }
            }
        }
    }

    public int getIndex() {
        return index;
    }

    public void draw(Graphics2D g2) {

        if (index < 5) {
            g2.drawImage(animation[index], x, y, dm.getTileSize(), dm.getTileSize(), null); // "null" é para o imageObserver (notifica o aplicativo sobre atualizações em uma imagem carregada)
        }
    }

    public int  getX() {
        return x;
    }

    public int  getY() {
        return y;
    }

    public boolean getIsVisivel(){
        return this.isVisivel;
    }

}