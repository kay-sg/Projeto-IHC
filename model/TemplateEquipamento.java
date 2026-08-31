public class TemplateEquipamento {

    private int idEquipamento, tipo;
    private String nome;
    private boolean usado;

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

    public String getNome(){
        return this.nome;
    }

    
}
