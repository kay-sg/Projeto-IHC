import java.util.ArrayList;
public class TemplateEquipamento {

    private int idEquipamento, tipo;
    private boolean usado;
    private ArrayList<Material> materiais = new ArrayList<>();

    public TemplateEquipamento(int id, int tp, boolean us){
        this.idEquipamento = id;
        this.tipo = tp;
        this.usado = us;
    }

     public int getIdEquipamento() {
        return this.idEquipamento;
    }

    public int getTipo() {
        return this.tipo;
    }

    public boolean getUsado(){
        return this.usado;
    }

    public ArrayList<Material> getMateriais() {
        return this.materiais;
    }

    public void setMateriais(ArrayList<Material> m) {
        this.materiais = m;
    }
    
}
