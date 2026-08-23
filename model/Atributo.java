public class Atributo {
    private String nome;
    private float valor;

    public Atributo(String n, float v){
        this.nome = n;
        this.valor = v;
    }

    @Override
    public String toString(){
        return "\n"+this.nome+": "+this.valor;
    }

}
