package entity.Projeteis;

import java.awt.Graphics2D;


public class TiroForte extends Projeteis{

    public TiroForte(int x, int y, int largura, int tileSize, int velocidade) {
        super(x, y, largura, tileSize, velocidade, "/res/playerSprites/tiroForte.png", 2);
    }
      
    public void draw(Graphics2D g2) {

        g2.drawImage(getImagemTiro(), getX(), getY(), getTileSize(), getTileSize(), null); // "null" é para o imageObserver (notifica o aplicativo sobre atualizações em uma imagem carregada)
    }
    
    public void update() {

        plusX(getVelocidade());

        if (getX() > getLargura()){
            setIsVisivel(false);
        }
    }
}
