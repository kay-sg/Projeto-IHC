package model;

public class Jogador {

    private int idJogador;
    private int nivel = 0;

    public Jogador(int id){
        this.idJogador = id;
    }

    public int getIdJogador(){
        return idJogador;
    }

    public void updateNivel(int n){
        this.nivel += n;
    }

    public int getNivel(){
        return this.nivel;
    }
}
