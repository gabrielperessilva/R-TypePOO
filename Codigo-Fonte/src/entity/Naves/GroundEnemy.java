package entity.Naves;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import game.Telas.Dimensoes;
import javax.imageio.ImageIO;

import entity.Projeteis.MisselTiro;
import entity.Projeteis.Projeteis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.Rectangle;

public class GroundEnemy{

    private Dimensoes dm;

    private Boolean hasFired;

    private int x, y;
    private int speed;
    private BufferedImage misselBase, misselEmpty;
    private List<Projeteis> misselTiro = new ArrayList<>();
    private boolean isVisivel;
    private int score;

    public GroundEnemy(int x, int y, int speed, Dimensoes dm) {
        this.x = x;
        this.y = y;
        this.dm = dm;
        this.speed = speed;
        this.isVisivel = true;
        hasFired = false;
        score = 300;

        try{
            misselBase = ImageIO.read(this.getClass().getResourceAsStream("/res/enemySprites/missel.png"));     //Lê o arquivo de imagem e aloca em um BufferedImage
            misselEmpty = ImageIO.read(this.getClass().getResourceAsStream("/res/enemySprites/missel_empty.png"));
        }
        catch(IOException e){
            e.printStackTrace(); //imprime o throwable junto com outros detalhes como o número da linha e o nome da classe onde ocorreu a exceção.
        }
    }

    public void update(){
        
        plusX(-speed);//  x -= speed;
        int pos = (int)(Math.random() * 1000 - 150);
        if(x < -100)
            isVisivel = false;
        if(x < pos && hasFired == false){
            hasFired = true;
            misselTiro.add(new MisselTiro(x, y, dm.getScreenWidth(), dm.getTileSize(), 8));
        }
        
    }
    public boolean getIsVisivel(){
        return isVisivel;
    }
    public Boolean getCondition(){
        return hasFired;
    }

    public List<Projeteis> getMissel() {
        return misselTiro;
    }
        
    public void plusX(int s){
        this.x += s;
    }

    public Rectangle getBounds(){
        return new Rectangle(x, y, dm.getTileSize(),dm.getTileSize());
    }

    public int getScore() {
        return score;
    }

    public int  getX() {
        return x;
    }
    
    public int  getY() {
        return y;
    }

    public void setIsVisivel(boolean isVisivel){
        this.isVisivel = isVisivel;
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        if(getCondition() == true)
            image = misselEmpty;

        else if(getCondition() == false)
            image = misselBase;

        g2.drawImage(image, x, y, dm.getTileSize(), dm.getTileSize(), null); // "null" é para o imageObserver (notifica o aplicativo sobre atualizações em uma imagem carregada)
    }
}