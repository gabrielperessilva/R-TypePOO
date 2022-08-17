package game.Telas;

import scenario.Background;
import java.awt.Dimension;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Menu extends JPanel implements Runnable{

    private Dimensoes dm;
    private int playWidth;
    private int playHeight;
    private Thread gameThread;
    private Background background;
    private Tela tela;
    private Botao p,s,e;

    public Menu(Dimensoes dm) {
        
        this.dm = dm;
        this.playWidth = (dm.getScreenWidth()-dm.getButtoWidth())/2;
        this.playHeight = dm.getScreenWidth()/8;
        this.background  = new Background(dm.getScreenWidth(),dm.getScreenHeight(), "ScenarioMountains.png", "ScenarioMountains2.png", "ScenarioSky.png", "ScenarioTrees.png");
        this.setPreferredSize(new Dimension(dm.getScreenWidth(), dm.getScreenHeight()));
        this.setBackground(new Color(0,0,0,0));
        this.setDoubleBuffered(true);     // Para componentes que são repintados com muita frequência ou têm gráficos particularmente complexos para exibir.
        this.setFocusable(true);
        this.setLayout(null);
        this.tela = new Tela(this);

        p = new Botao(dm, "PLAY");
        p.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pActionPerformed(evt);
            }
        });

        s= new Botao(dm, "SCORES");
        s.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sActionPerformed(evt);
            }
        });

        e = new Botao(dm, "EXIT");
        e.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eActionPerformed(evt);
            }
        });

		this.add(p);
        this.add(s);
        this.add(e); 
    }

    private void pActionPerformed(java.awt.event.ActionEvent evt) { 

        tela.setVisible(false);
        new GamePanel(dm);
        gameThread = null;
    }   

    private void sActionPerformed(java.awt.event.ActionEvent evt) {                                         
        tela.setVisible(false);
        new Score(dm);
        gameThread = null;
    } 

    private void eActionPerformed(java.awt.event.ActionEvent evt) {                                         
        System.exit(0);
    }  

    public void startGameThread() {

        gameThread = new Thread(this); // Passando o GamePanel para a thread
        gameThread.start();
    }

    public void run(){

        double drawInterval = 1000000000/60; // 0.01666666sec (tempo em que a função vai ser chamada novamente)
        double delta = 0;
        long lastTime = System.nanoTime(); // retorna um valor da máquina em nanosegundo
        long currentTime;

        while(gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if(delta >= 1) {
                //Draw: desenhar na tela as atualizações informações
                repaint(); // Chamando a paintComponent function
                p.setLocation(playWidth, playHeight);
                s.setLocation(playWidth, playHeight+2*dm.getButtoHeight());
                e.setLocation(playWidth, playHeight+4*dm.getButtoHeight());

                delta--;
            }
        }    
    }
    
    public void paintComponent(Graphics g) {
        
        Graphics2D g2 = (Graphics2D) g; //mais controle sobre a geometria, coordenada e cor.
        background.draw(g2);
        super.paintComponent(g); // imprime o objeto na tela   
    }
}