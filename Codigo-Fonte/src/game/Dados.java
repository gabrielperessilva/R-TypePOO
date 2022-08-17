package game;

public class Dados {
    private int scr;
    private String nome;

    public Dados(String nome, int scr){
        this.scr = scr;
        this.nome = nome;
    }

    public int getScr(){
        return scr;
    }

    public String getNome(){
        return nome;
    }
}
