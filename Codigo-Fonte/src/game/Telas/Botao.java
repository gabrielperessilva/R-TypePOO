package game.Telas;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;
import java.awt.Cursor;

public class Botao extends JButton{
    
    public Botao(Dimensoes dm, String text){
        this.setText(text);
        this.setFont(getFont().deriveFont(50f));
        this.setForeground(Color.black);
        this.setSize(new Dimension(dm.getButtoWidth(), dm.getButtoHeight()));
        this.setBackground(new Color(0,0,0,0));
		this.setOpaque(false);
		this.setContentAreaFilled(false);
		this.setBorderPainted(false);
        this.setFocusPainted(false);
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bMouseExited(evt);
            }
        });
    }

    private void bMouseEntered(java.awt.event.MouseEvent evt) {  
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));                                   
        this.setForeground(new Color(99, 148, 168));
    }   

    private void bMouseExited(java.awt.event.MouseEvent evt) {                                     
        this.setForeground(new Color(0,0,0));
    }   
    
}
