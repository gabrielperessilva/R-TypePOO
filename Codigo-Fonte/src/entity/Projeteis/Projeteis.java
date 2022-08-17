package entity.Projeteis;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Rectangle;

public abstract class Projeteis {
    
    private BufferedImage ImagemTiro;
    private int x, y;
    private boolean isVisivel;
    private int velocidade;
    private int largura;
    private int tileSize;
    private int dano;

    public Projeteis(int x, int y, int largura, int tileSize, int velocidade, String path, int dano) {
        this.dano = dano;
        this.x = x;
        this.y = y;
        this.largura = largura;
        this.tileSize = tileSize;
        this.velocidade = velocidade;
        this.isVisivel = true;

        try{
            ImagemTiro = ImageIO.read(this.getClass().getResourceAsStream(path));     //Lê o arquivo de imagem e aloca em um BufferedImage
        }
        catch(IOException e){
            e.printStackTrace(); //imprime o throwable junto com outros detalhes como o número da linha e o nome da classe onde ocorreu a exceção.
        }
    }
      
    abstract public void draw(Graphics2D g2);
    abstract public void update();

    public Rectangle getBounds(){
        return new Rectangle(x,y,tileSize/3,tileSize/3);
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public void setIsVisivel(boolean isVisivel){
        this.isVisivel = isVisivel;
    }
    public boolean getIsVisivel() {
        return isVisivel;
    }  
    public BufferedImage getImagemTiro() {
        return ImagemTiro;
    }

    public int getVelocidade() {
        return velocidade;
    }
    public int getLargura() {
        return largura;
    }
    public int getTileSize(){
        return tileSize;
    }
    public void plusX(int s){
        x += s;
    }
    public void plusY(int s){
        y += s;
    }
    public int getDano(){
        return dano;
    }



}
