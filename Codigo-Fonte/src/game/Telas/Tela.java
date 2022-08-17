package game.Telas;

import javax.swing.JFrame;

public class Tela extends JFrame{

    public Tela(GameOver over){
      
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("R-Type");
        add(over); 
        pack();
        setVisible(true);
        setLocationRelativeTo(null); 

        over.startGameThread();
    }

    public Tela(Score score){
      
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("R-Type");
        add(score); 
        pack();
        setVisible(true);
        setLocationRelativeTo(null); 

        score.startGameThread();
    }

    public Tela(Menu menu){
      
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("R-Type");
        add(menu); 
        pack();
        setVisible(true);
        setLocationRelativeTo(null);  

        menu.startGameThread();
    }
    
    public Tela(GamePanel gp){
       
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("R-Type");
        add(gp);
        pack();
        setVisible(true);
        setLocationRelativeTo(null);  

        gp.startGameThread();
    }

    public static void main(String[] args) {
        Dimensoes dm = new Dimensoes();
        new Menu(dm);
    }
}