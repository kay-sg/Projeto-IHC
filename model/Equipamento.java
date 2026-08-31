import java.util.ArrayList;
public class Equipamento {

    private int idEquipamento;
    private String nome;
    private float qualidade;
    private boolean metodoCrafting;
    private String tipo;
    private ArrayList<Atributo> atributos = new ArrayList<>();

    public Equipamento(int id, String n, float qd, boolean mc, String tp){
        this.idEquipamento = id;
        this.nome = n;
        this.qualidade = qd;
        this.metodoCrafting = mc;
        this.tipo = tp;
    }

    public void atribuicao(Atributo at){
        atributos.add(at);
    }

    public ArrayList<Atributo> getAtributos(){
        return this.atributos;
    }
    
    public int getIdEquipamento() {
        return this.idEquipamento;
    }

    public String getNome() {
        return this.nome;
    }

    public float getQualidade() {
        return this.qualidade;
    }

    public boolean isMetodoCrafting() {
        return this.metodoCrafting;
    }

    public String getTipo() {
        return this.tipo;
    }

    @Override
    public String toString(){
        String temp = "Id: "+this.idEquipamento+"\nNome: "+this.nome+"\nTipo: "+this.tipo+"\nQualidade:"+this.qualidade+"\nMétodo: ";
        if (this.metodoCrafting){
            temp += "Manual";
        } else {temp += "Automático";}
        temp += "\nAtributos: ";
        for (Atributo at: this.atributos){
            temp += at.toString();
        }
        return temp;
    }
}
