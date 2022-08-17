package game.Telas;

import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JPanel;
import entity.Naves.Enemy;
import entity.Naves.Enemy1;
import entity.Naves.Enemy2;
import entity.Naves.GroundEnemy;
import entity.Naves.Player;
import entity.Projeteis.Explosion;
import entity.Projeteis.PowerUp;
import entity.Projeteis.Projeteis;
import entity.Projeteis.Tiro;
import game.KeyHandle;
import java.awt.Rectangle;
import scenario.Background;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GamePanel extends JPanel implements Runnable{
    
    private Dimensoes dm;
    private int FPS;
    private KeyHandle keyH = new KeyHandle();
    private Thread gameThread; // Quando essa thread iniciar, vai continuar o programa rodando até você fechar.

    private Player player;
    private Background background;
    private List<Enemy> enemy = new ArrayList<>();
    private List<Projeteis> tirosP;
    private List<List<Projeteis>> tirosE = new ArrayList<>();
    private List<GroundEnemy> groundEnemy = new ArrayList<>();
    private List<Explosion> explo = new ArrayList<>();
    private PowerUp powerUp;

    private long spawnRateEnemy;
    private long nextSpawnEnemy;
    private long spawnRatePowerUp;
    private long nextSpawnPowerUp;

    private boolean emJogo;
    private JLabel vida, score;
    private Tela tela;
    private int scr = 0;
    private boolean GamerOverStatus = false;
    private int gameOverDelay = 0;

    public GamePanel(Dimensoes dm) {
        this.vida = new JLabel("Life: ");
        this.score = new JLabel("Score: ");
        this.dm = dm;
        this.background  = new Background(dm.getScreenWidth(),dm.getScreenHeight(), "ScenarioMountains.png", "ScenarioMountains2.png", "ScenarioSky.png", "ScenarioTrees.png");
        this.spawnRateEnemy = 1500000000;
        this.nextSpawnEnemy = 0;
        this.spawnRatePowerUp = 2000000000;
        this.nextSpawnPowerUp = 0;
        this.FPS = 60;
        this.player = new Player(this, keyH, 100, 100, 5,dm);
        this.tirosP = player.getTiros();
        this.setPreferredSize(new Dimension(dm.getScreenWidth(), dm.getScreenHeight()));
        this.setBackground(new Color(0,0,0,0));
        this.setDoubleBuffered(true); // Para componentes que são repintados com muita frequência ou têm gráficos particularmente complexos para exibir.
        this.addKeyListener(keyH);
        this.setFocusable(true);
        emJogo = true;
        this.tela = new Tela(this);
        this.add(vida);
        this.add(score);
        
        repaint();
    }

    public void spawnEnemies() {
        
        int posX = (int)(Math.random() * 2000 + 1512);
        int posY = (int)(Math.random() * 370 + 40);

        if (enemy.size() < 6){
            Enemy1 aux1 = new Enemy1(posX, posY, 5, player,dm);
            Enemy2 aux2 = new Enemy2(posX, posY, 4,dm);
            enemy.add(aux1);
            enemy.add(aux2);
            tirosE.add(aux1.getTiros());
            tirosE.add(aux2.getTiros());
        }

        if (groundEnemy.size() < 2){
            GroundEnemy aux3 = new GroundEnemy(posX, 660, 5, dm);
            groundEnemy.add(aux3);
            tirosE.add(aux3.getMissel());
        }
    }

    public void spawnPowerUp() {
        
        int posX = (int)(Math.random() * 2000 + 1512);
        int posY = (int)(Math.random() * 370 + 40);
        powerUp = new PowerUp(posX, posY, 5, dm);
    }

    public void startGameThread() {

        gameThread = new Thread(this); // Passando o GamePanel para a thread
        gameThread.start();
    }

    /*
        Usando para criar uma thread. Essa thread vai manter o GamePanel Rodando, 
        mesmo quer outros threas se iniciam durante a execução
    */
    @Override
    public void run() {
       
        double drawInterval = 1000000000/FPS; // 0.01666666sec (tempo em que a função vai ser chamada novamente)
        double delta = 0;
        long lastTime = System.nanoTime(); // retorna um valor da máquina em nanosegundo
        long currentTime;

        while(gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if(delta >= 1) {

                if (player.getLife() > 0)
                    this.vida.setText("Life: "+Integer.toString(player.getLife()));
                else
                    this.vida.setText("Life: "+0);

                this.score.setText("Score: "+Integer.toString(scr));

                update();
                repaint();
                delta--;
            }
        }
    }

    public void verificaTiros(Iterator<Projeteis> iter){

        while(iter.hasNext()){

            Projeteis aux = iter.next();

            if (aux.getIsVisivel()){
                aux.update();
            }
            else
                iter.remove();
        }
    }

    public void update() {

        if(emJogo == false) {

            gameOverDelay++;

            if(gameOverDelay >= 30) {
                gameOverDelay = 0;
                GamerOverStatus = true;
            }
        }

        if (emJogo == false && GamerOverStatus == true){

            tela.setVisible(false);
            new GameOver(dm,scr);
            gameThread = null;
        }
        else{

            if(player.isVisivel() == true)
                player.update();
    
            // Update tiros dos inimigos
            Iterator<List<Projeteis>> iter = tirosE.iterator();
            while(iter.hasNext()){
                verificaTiros(iter.next().iterator());
            }

            // Update tiros do jogador
            verificaTiros(tirosP.iterator());

            // Update explosoes
            Iterator<Explosion> it = explo.iterator();
            while(it.hasNext()){
                Explosion aux = it.next();
                if (aux.getIsVisivel()){
                    aux.update();
                }
                else
                    it.remove();
            }

            // Spawnar inimigos
            long currentTime = System.nanoTime();

            if(currentTime > nextSpawnEnemy) {
                nextSpawnEnemy = currentTime + spawnRateEnemy;
                spawnEnemies();
            }

            if(powerUp == null) {
                if(currentTime > nextSpawnPowerUp) {
                    nextSpawnPowerUp = currentTime + spawnRatePowerUp;
                    spawnPowerUp();    
                }
            }

            //Update do powerup
            if(powerUp != null) {
                if(powerUp.getIsVisivel() == true)
                    powerUp.update();
                else
                    powerUp = null;
            }

            // Update dos inimigos
            Iterator<Enemy> itEnemy = enemy.iterator();

            while(itEnemy.hasNext()){
                Enemy temp = itEnemy.next();
                if (temp.getIsVisivel() == true){
                    temp.update();
                }else{
                    explo.add(new Explosion(temp.getX(), temp.getY(), dm));
                    itEnemy.remove();
                }
            }

            Iterator<GroundEnemy> itGEnemy = groundEnemy.iterator();

            while(itGEnemy.hasNext()){
                GroundEnemy temp = itGEnemy.next();
                if (temp.getIsVisivel() == true){
                    temp.update();
                }else{
                    itGEnemy.remove();
                }
            }
            
            colisao();
        }
    }

    public void colisao(){
        Rectangle formaEnemy;
        Rectangle formaTiro;
        Rectangle formaGroundEnemy;
        Rectangle formaNave = player.getBounds();
        
        for (int i = 0; i < enemy.size(); i++){

            Enemy tempEnemy = enemy.get(i);
            formaEnemy = tempEnemy.getBounds();

            if(formaNave.intersects(formaEnemy)){

                player.DiminuirVida(player.getLife());
                player.setIsVisivel(false);
                tempEnemy.setIsVisivel(false);
                scr+=tempEnemy.getScore();
                explo.add(new Explosion(player.getX(), player.getY(), dm));

                if (player.getLife() == 0)
                    emJogo = false;
            }
        }

        for (int i = 0; i < tirosP.size();i++){
            formaTiro = tirosP.get(i).getBounds();

            for (int j = 0; j < enemy.size();j++){

                formaEnemy = enemy.get(j).getBounds();
                if (formaTiro.intersects(formaEnemy)){

                    if(tirosP.get(i) instanceof Tiro)
                        tirosP.get(i).setIsVisivel(false);

                    scr+= enemy.get(j).getScore();
                    enemy.get(j).setIsVisivel(false);
                }
            }

            for (int j = 0; j < groundEnemy.size();j++){

                formaGroundEnemy = groundEnemy.get(j).getBounds();
                if (formaTiro.intersects(formaGroundEnemy)){
                    
                    if(tirosP.get(i) instanceof Tiro)
                        tirosP.get(i).setIsVisivel(false);

                    scr+= groundEnemy.get(j).getScore();
                    explo.add(new Explosion(groundEnemy.get(j).getX(), groundEnemy.get(j).getY(), dm));
                    groundEnemy.get(j).setIsVisivel(false);
                }
            }
        }

        Iterator<List<Projeteis>>itListTiros = tirosE.iterator();

        while(itListTiros.hasNext()){
            Iterator <Projeteis> itTiros = itListTiros.next().iterator();
            while(itTiros.hasNext()){
                Projeteis temp = itTiros.next();
                if (temp.getBounds().intersects(formaNave)){

                    player.DiminuirVida(temp.getDano());
                    itTiros.remove();
                    
                    if (player.getLife() <= 0) {

                        player.setIsVisivel(false);
                        explo.add(new Explosion(player.getX(), player.getY(), dm));
                        emJogo = false;
                    }
                }
            }

        }

        if(powerUp != null) {
            if(powerUp.getBounds().intersects(formaNave)){
                player.setTipoTiro(true);
                powerUp.setIsVisivel(false);
            }
        }

    }

    public void paintComponent(Graphics g) {
        vida.setLocation(0, 0);
        
        super.paintComponent(g); // imprime o objeto na tela

        Graphics2D g2 = (Graphics2D) g; //mais controle sobre a geometria, coordenada e cor.
        
        //Desenhando o cenário
        background.draw(g2);
        
        //Desenhando powerUp
        if(powerUp != null && powerUp.getIsVisivel()) {
            powerUp.draw(g2);
        }

        // Desenhar inimigos
        for(int i = 0; i < enemy.size(); i++) {
            enemy.get(i).draw(g2);
        }

        // Desenhar inimigos terrestres
        for(int i = 0; i < groundEnemy.size(); i++) {
            groundEnemy.get(i).draw(g2);
        }

        // Desenhar tiros do player
        for (int i = 0; i < tirosP.size(); i++){
            tirosP.get(i).draw(g2);
        }
        
        Iterator<List<Projeteis>>itListTiros = tirosE.iterator();

        while(itListTiros.hasNext()){
            Iterator <Projeteis> itTiros = itListTiros.next().iterator();
            while(itTiros.hasNext()){
                Projeteis temp = itTiros.next();
                temp.draw(g2);
            }
            for (int i = 0;i < explo.size();i++){
                explo.get(i).draw(g2);
            }

            if(player.isVisivel() == true)
                player.draw(g2);
        }

    }
}
