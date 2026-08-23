import java.util.ArrayList;
public class TemplateEquipamento {

    private int idEquipamento, tipo;
    private boolean usado;
    private ArrayList<Atributo> atributos;
    private ArrayList<Material> materiais = new ArrayList<>();

    public TemplateEquipamento(int id, int tp, ArrayList<Atributo> at){
        this.idEquipamento = id;
        this.tipo = tp;
        this.atributos = at;
    }
}
