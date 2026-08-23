import java.util.ArrayList;
public class Equipamento {

    private int idEquipamento;
    private String nome;
    private float qualidade;
    private boolean metodoCrafting;
    private int tipo;
    private ArrayList<Float> atributos = new ArrayList<>();

    public Equipamento(int id, String n, float qd, boolean mc, int tp){
        this.idEquipamento = id;
        this.nome = n;
        this.qualidade = qd;
        this.metodoCrafting = mc;
        this.tipo = tp;
    }

    public void atribuicao(float at){
        atributos.add(at);
    }

    @Override
    public String toString(){
        return "";
    }
}
