public class Atributo {
    private String nome;
    private float valor;

    public Atributo(String n, float v){
        this.nome = n;
        this.valor = v;
    }

    public String getNome() {
        return this.nome;
    }

    public float getValor() {
        return this.valor;
    }

    @Override
    public String toString(){
        return "\n"+this.nome+": "+this.valor;
    }

}
