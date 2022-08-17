package entity.Naves;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

import entity.Projeteis.Projeteis;
import game.Telas.Dimensoes;
import java.awt.Rectangle;

public abstract class Entity {

    private Dimensoes dm;
    private int frames;
    private int x, y;
    private int speed;
    private int Life;
    private boolean perdeuVida;
    private BufferedImage up, down, idle, right; // para guardar arquivos de imagem
    private String direction;
    private boolean isVisivel;
    private List <Projeteis> tiros = new ArrayList<>();

    public Entity(int x, int y, int speed, String direction, String str1, String str2, String str3, String str4, Dimensoes dm ) {
        Life = 5;
        perdeuVida = false;
        this.frames = 400;
        this.dm = dm;
        this.x = x; 
        this.y = y;
        this.speed = speed;
        this.direction = direction;
        this.isVisivel = true;

        try{
            up = ImageIO.read(this.getClass().getResourceAsStream(str1));     //Lê o arquivo de imagem e aloca em um BufferedImage
            down = ImageIO.read(this.getClass().getResourceAsStream(str2));
            idle = ImageIO.read(this.getClass().getResourceAsStream(str3));
            right = ImageIO.read(this.getClass().getResourceAsStream(str4));
        }
        catch(IOException e){
            e.printStackTrace(); //imprime o throwable junto com outros detalhes como o número da linha e o nome da classe onde ocorreu a exceção.
        }
    }

    public abstract void update();

    public Rectangle getBounds(){
        return new Rectangle(getX(),getY()+7,dm.getTileSize()-30,20);
    }

    public void setIsVisivel(boolean isVisivel){
        this.isVisivel = isVisivel;
    }
    public boolean getIsVisivel() {
        return isVisivel;
    }  
    
    public void plusX(int s){
        this.x += s;
    }

    public int  getX() {
        return x;
    }
    
    public int  getY() {
        return y;
    }
    
    public void plusY(int s){
        this.y += s;
    }


    public int getSpeed() {
        return speed;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public BufferedImage getUp (){
        return this.up;
    }
    public BufferedImage getDown (){
        return this.down;
    }
    public BufferedImage getIdle (){
        return this.idle;
    }
    public BufferedImage getRight (){
        return this.right;
    }

    public Dimensoes getDimensoes(){
        return this.dm;
    }

    public void DiminuirVida(int dano){
        Life -= dano;
        perdeuVida = true;
        frames = 400;
    }

    public int getLife(){
        return this.Life;
    }

    public boolean getPerdeuVida(){
        if (perdeuVida == true){
            if (frames == 0){
                perdeuVida = false;
            }else{
                frames--;
            }
            
            return true;
        }
        return false;
    }

    public List <Projeteis> getTiros(){
        return tiros;
    }

}

