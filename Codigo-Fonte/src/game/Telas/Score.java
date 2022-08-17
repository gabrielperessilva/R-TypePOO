package game.Telas;

import scenario.Background;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Score extends JPanel implements Runnable{

    private Dimensoes dm;
    private Thread gameThread;
    private Background background;
    private Tela tela;
    private Botao e;
    private JLabel[] names = new JLabel[10];
    private int cont;
    
    public Score(Dimensoes dm){
        
        this.dm = dm;
        this.background  = new Background(dm.getScreenWidth(),dm.getScreenHeight(), "ScenarioMountains.png", "ScenarioMountains2.png", "ScenarioSky.png", "ScenarioTrees.png");
        this.setPreferredSize(new Dimension(dm.getScreenWidth(), dm.getScreenHeight()));
        this.setBackground(new Color(0,0,0,0));
        this.setDoubleBuffered(true); // Para componentes que são repintados com muita frequência ou têm gráficos particularmente complexos para exibir.
        this.setFocusable(true);
        
        this.tela = new Tela(this);
   
        e = new Botao(dm, "MENU");
        e.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eActionPerformed(evt);
            }
        });

        this.add(e);
        addTela();
        repaint();
    }
    
    private void addTela() {

        this.cont = 0;
        int width = 100, height = 100;
        String linha;
        BufferedReader arquivo = null; //Objeto leitor

        try{
            arquivo = new BufferedReader(new FileReader(new File("scores.txt")));

            //Instanciação do objeto leitor
            while((linha = arquivo.readLine()) != null && cont < 10) {
                names[cont] = new JLabel(linha);
                names[cont].setFont(getFont().deriveFont(20f));
                width+=width;
                height+=height;
                this.add(names[cont]);
                cont++;
            }
            arquivo.close(); //fechamento do arquivo
        }
        catch(java.io.IOException e) {
            System.out.println("File error: " + e.toString());
        } 
    }
    
    private void eActionPerformed(java.awt.event.ActionEvent evt) {                                         
        tela.setVisible(false);
        new Menu(dm);
        gameThread = null;
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
                delta--;
            }
        }
    }
     
    public void paintComponent(Graphics g) {

        super.paintComponent(g); // imprime o objeto na tela
        Graphics2D g2 = (Graphics2D) g; //mais controle sobre a geometria, coordenada e cor.

        background.draw(g2);
        e.setLocation(dm.getScreenWidth()/2-dm.getButtoWidth()/3, dm.getScreenHeight()-2*dm.getButtoHeight());

        int width = dm.getScreenWidth()/2-150;
        int height = 50;

        for(int i = 0;i < cont;i++) {
            names[i].setLocation(width,height);
            height+=50;
        }
    }
}