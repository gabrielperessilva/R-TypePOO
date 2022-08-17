package game.Telas;

import scenario.Background;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import game.Dados;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.imageio.ImageIO;

public class GameOver extends JPanel implements Runnable{

    private Dimensoes dm;
    private Thread gameThread;
    private Background background;
    private Tela tela;
    private Botao s;
    private int scr;
    private int c, TAM;
    private BufferedImage endgame;
    private Dados dados[];
    private Dados novo;
    private String nameFile;
    private javax.swing.JTextField text;
    private JLabel score;

    public GameOver(Dimensoes dm, int scr){

        this.scr = scr;
        this.dm = dm;
        this.c = 0;
        this.TAM = 10;
        this.dados = new Dados[TAM];
        this.nameFile = "scores.txt";

        this.background  = new Background(dm.getScreenWidth(),dm.getScreenHeight(), "ScenarioMountains.png", "ScenarioMountains2.png", "ScenarioSky.png", "ScenarioTrees.png");
        this.setPreferredSize(new Dimension(dm.getScreenWidth(), dm.getScreenHeight()));
        this.setBackground(new Color(0,0,0,0));
        this.setDoubleBuffered(true); // Para componentes que são repintados com muita frequência ou têm gráficos particularmente complexos para exibir.
        this.setFocusable(true);
        this.setLayout(null);
        this.tela = new Tela(this);
        
        try {
            endgame = ImageIO.read(this.getClass().getResourceAsStream("/res/scenarioImages/GameOver2.png"));  
        }
        catch(IOException e) {
                e.printStackTrace(); //imprime o throwable junto com outros detalhes como o número da linha e o nome da classe onde ocorreu a exceção.
        }

        initComponents();
        repaint();
    }

    public void initComponents() {

        score = new JLabel("Score: "+Integer.toString(scr));
        score.setFont(getFont().deriveFont(30f));
        
        s = new Botao(dm, "SAVE");
        s.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eActionPerformed(evt);
            }
        });

        text = new javax.swing.JTextField();
		text.setOpaque(false);
        text.setPreferredSize(new Dimension(200, 50));
        text.setFont(getFont().deriveFont(50f));
        text.setHorizontalAlignment(JTextField.CENTER);
        text.setBorder(new LineBorder(new Color(152, 194, 227), 2));
        
        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(this);
        this.setLayout(jPanel1Layout);

        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(132, Short.MAX_VALUE)
                .addComponent(text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(dm.getScreenWidth()-3*dm.getButtoWidth()+130, dm.getScreenWidth()-3*dm.getButtoWidth()+130, dm.getScreenWidth()-3*dm.getButtoWidth()+130))
                .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(dm.getScreenWidth()-3*dm.getButtoWidth()+130, dm.getScreenWidth()-3*dm.getButtoWidth()+130, dm.getScreenWidth()-3*dm.getButtoWidth()+130)
                .addComponent(score, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(600, Short.MAX_VALUE)
                .addComponent(score, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(100, 100, 100)
                .addContainerGap(50, Short.MAX_VALUE)
                .addComponent(text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(200, 200, 200))
        );

        this.add(s);
        this.add(score);

        s.setLocation((dm.getScreenWidth()-dm.getButtoWidth())/2-20, dm.getScreenWidth()/2);
    }

    private void dadosArq(){
        String linha;
        BufferedReader arquivo = null; //Objeto leitor
        try{
            arquivo = new BufferedReader(new FileReader(new File(nameFile)));
            //Instanciação do objeto leitor
            String nome = " ";
            while((linha = arquivo.readLine()) != null && c < TAM) {
                int i;
                for(i = 3; i < linha.length()  ;i++){
                    if (linha.charAt(i) == ' '){
                        nome = linha.substring(3, i);
                        break;
                    } 
                }
                for(; i < linha.length()  ;i++){
                    if (linha.charAt(i) <= 57 && linha.charAt(i) >= 48){
                        String number = linha.substring(i, linha.length());
                        
                        int n = Integer.parseInt(number);
                        dados[c] = new Dados(nome, n);
                        c++;
                    break;
                    }
                }

            }
            arquivo.close(); //fechamento do arquivo
            salvarArq();

           }
        catch(java.io.IOException e) {
                   System.out.println("File error: " + e.toString());
        }
    }
    
    private void salvarArq(){
        int i;
        for (i = 0; i < c;i++){
            if (dados[i].getScr() < novo.getScr()){
                
                if (c < TAM){
                    for(int j = c; j > i; j--){
                        dados[j] = dados[j-1];
                    }
                    c++;
                }else{
                    for(int j = c-1; j > i; j--){
                        dados[j] = dados[j-1];
                    }
                }
                dados[i] = novo;
                break;
            }
        }
        if (i == c && c < TAM){
            dados[i] = novo;
            c++;
        }
        
        try{
            Files.delete(Paths.get(nameFile));    
            File newFile = new File(nameFile);
            newFile.createNewFile();
            for(i = 0; i < c;i++){
                FileWriter fw = new FileWriter(nameFile, true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write((i+1)+". "+ dados[i].getNome());

                for (int j = dados[i].getNome().length(); j < 40;j++){
                    bw.write(" ");
                }

                bw.write(Integer.toString(dados[i].getScr()));
                bw.newLine();
                bw.close();
            }
                 
        }catch(java.io.IOException e){
            System.out.println("File error: "+e.toString());
        }
    }
    
    private void eActionPerformed(java.awt.event.ActionEvent evt) {   
        if (!text.getText().isEmpty()){
            this.novo = new Dados(text.getText(), scr);   
            dadosArq();                           
            tela.setVisible(false);
            new Score(dm);
            gameThread = null;
        }
        
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
            //System.out.println("is running");
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
        
        g2.drawImage(this.endgame, dm.getScreenWidth()/2-dm.getTileSize(), dm.getScreenHeight()/5-dm.getTileSize(), 2*dm.getTileSize(), 2*dm.getTileSize(), null);
    }

}