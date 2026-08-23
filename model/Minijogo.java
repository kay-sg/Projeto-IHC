public class Minijogo {

    private int idMinijogo;
    private String nome, descricao;
    private float recorde, pontuacao;

    public Minijogo(int id, String n, String d, float rc, float pt){
        this.idMinijogo = id;
        this.nome = n;
        this.descricao = d;
        this.recorde = rc;
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

    public float getRecorde() {
        return this.recorde;
    }

    public float getPontuacao() {
        return this.pontuacao;
    }

}
