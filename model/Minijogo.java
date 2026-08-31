package model;

public class Minijogo {

    private int idMinijogo;
    private String nome, descricao;
    private float  pontuacao;

    public Minijogo(int id, String n, String d, float pt){
        this.idMinijogo = id;
        this.nome = n;
        this.descricao = d;
        this.pontuacao = pt;
    }

    public int getIdMinijogo() {
        return this.idMinijogo;
    }

    public String getNome() {
        return this.nome;
    }

    public String getDescricao() {
        return this.descricao;
    }


    public float getPontuacao() {
        return this.pontuacao;
    }

}
