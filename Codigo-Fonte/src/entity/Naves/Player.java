package entity.Naves;

import game.Telas.Dimensoes;
import game.KeyHandle;
import game.Telas.GamePanel;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Projeteis.Tiro;
import entity.Projeteis.TiroForte;

public class Player extends Entity{

    private KeyHandle keyH;
    private float fireRate;
    private float nextFire;
    private Boolean isVisivel;
    private Boolean tipoTiro = false;
    private BufferedImage upDano, downDano, idleDano, rightDano; // para guardar arquivos de imagem
    public Player(GamePanel gp, KeyHandle keyH, int x, int y, int speed,  Dimensoes dm) {

        super(x, y, speed, "idle", "/res/playerSprites/player_up.png",  "/res/playerSprites/player_down.png",  "/res/playerSprites/player_idle.png",  "/res/playerSprites/player_right.png", dm);
        this.fireRate = 150000000f;
        this.nextFire = 0.5f;
        this.keyH = keyH;
        isVisivel = true;

        try{
            upDano = ImageIO.read(this.getClass().getResourceAsStream("/res/playerSprites/player_upDano.png"));     //Lê o arquivo de imagem e aloca em um BufferedImage
            downDano = ImageIO.read(this.getClass().getResourceAsStream("/res/playerSprites/player_downDano.png"));
            idleDano = ImageIO.read(this.getClass().getResourceAsStream("/res/playerSprites/player_idleDano.png"));
            rightDano = ImageIO.read(this.getClass().getResourceAsStream("/res/playerSprites/player_idleDano.png"));
        }
        catch(IOException e){
            e.printStackTrace(); //imprime o throwable junto com outros detalhes como o número da linha e o nome da classe onde ocorreu a exceção.
        }


    }
    
    public void update() {
        if (keyH.getEscPressed() == true)
            System.exit(0);
            
        if(keyH.getUpPressed() == true && getY() >= -getDimensoes().getOriginalTileSize()){
                setDirection("up");
                plusY(-getSpeed()); // y -=  speed
        }

        if(keyH.getDownPressed() == true && getY() <= getDimensoes().getScreenHeight() - 2*getDimensoes().getOriginalTileSize()){
                setDirection("down");
                plusY(getSpeed()); // y +=  speed
            
        }
        
        if(keyH.getLeftPressed() == true && getX() >= -getDimensoes().getOriginalTileSize()){
            
            if(keyH.getDownPressed() == true && getY() <= getDimensoes().getScreenHeight() - 2*getDimensoes().getOriginalTileSize())  {
                setDirection("down");
                plusY(getSpeed()/2);  // y += speed/2;
                plusX(-getSpeed()/2); // x -= speed/2;
            }
            else if(keyH.getUpPressed() == true && getY() >= -getDimensoes().getOriginalTileSize()) {
                setDirection("up");
                plusY(-getSpeed()/2);  // y -= speed/2;
                plusX(-getSpeed()/2); // x -= speed/2;
            }
            else if(keyH.getRightPressed() == true && getX() <= getDimensoes().getScreenWidth() - 2*getDimensoes().getOriginalTileSize()) {
                setDirection("idle");
            }
            else{
                setDirection("idle");
                plusX(-getSpeed());//x -= speed;
            }
        }

        if(keyH.getRightPressed() == true && getX() <= getDimensoes().getScreenWidth() - 2*getDimensoes().getOriginalTileSize()){

            if(keyH.getDownPressed() == true && getY() <= getDimensoes().getScreenHeight() - 2*getDimensoes().getOriginalTileSize()) {
                setDirection("down");  //  direction = "down";
                plusY(getSpeed()/2); // y += speed/2;
                plusX(getSpeed()/2); // x += speed/2;
            }
            else if(keyH.getUpPressed() == true && getY() >= -getDimensoes().getOriginalTileSize()) {
                setDirection("up");  //  direction = "up";
                plusY(-getSpeed()/2); // y -= speed/2;
                plusX(getSpeed()/2); // x += speed/2;
            }
            else{
                setDirection("right");  //  direction = "right";
                plusX(getSpeed()); // x += speed;
            }
        }
        if(keyH.getUpPressed() == false && keyH.getDownPressed() == false && keyH.getLeftPressed() == false && keyH.getRightPressed() == false)
            setDirection("idle"); // direction = "idle";
        
        if(keyH.getJPressed() == true){
        
            float time = System.nanoTime();

            if (time > nextFire){
                nextFire = time + fireRate;

                if(tipoTiro == true) {
                    getTiros().add(new TiroForte(getX()+74, getY() + 10, getDimensoes().getScreenWidth(), getDimensoes().getTileSize(),12));
                    tipoTiro = false;
                }
                else    
                    getTiros().add(new Tiro(getX()+74, getY() + 10, getDimensoes().getScreenWidth(), getDimensoes().getTileSize(),12, "right"));
            }
        }
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        if (getPerdeuVida() == true){

            switch(getDirection()) {
                case "up":
                    image = getUpDano();
                    break;
                
                case "down":
                    image = getDownDano();
                    break;
    
                case "idle":
                    image = getIdleDano();
                    break;
    
                case "right":
                    image = getRightDano();
                    break;
            }
            
        }else{

            switch(getDirection()) {
                case "up":
                    image = getUp();
                    break;
                
                case "down":                  
                    image = getDown();
                    break;
    
                case "idle":
                    image = getIdle();
                    break;
    
                case "right":
                    image = getRight();
                    break;
            }

        }
        
        
        g2.drawImage(image, getX(), getY(), getDimensoes().getTileSize(), getDimensoes().getTileSize(), null); // "null" é para o imageObserver (notifica o aplicativo sobre atualizações em uma imagem carregada)
    }
    
    public boolean isVisivel(){
        return isVisivel;
    }
    public void setIsVisivel (boolean isVisivel){
        this.isVisivel = isVisivel;
    }

    public Boolean getTipoTiro () {
        return tipoTiro;
    }

    public void setTipoTiro(Boolean tipo) {
        this.tipoTiro = tipo;
    }

    public BufferedImage getUpDano (){
        return this.upDano;
    }
    public BufferedImage getDownDano (){
        return this.downDano;
    }
    public BufferedImage getIdleDano (){
        return this.idleDano;
    }
    public BufferedImage getRightDano (){
        return this.rightDano;
    }
    
}
