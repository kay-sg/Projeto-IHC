import java.util.List;

public abstract class Crafting {

    protected int idCrafting;
    protected Equipamento equipamento;

    public Crafting(int idCrafting) {
        this.idCrafting = idCrafting;
    }

    public final Equipamento executarCraft(TemplateEquipamento template, List<Material> materiais) {
        this.equipamento = calcularAtributos(template, materiais);
        return this.equipamento;
    }

    protected abstract Equipamento calcularAtributos(TemplateEquipamento template, List<Material> materiais);

    public int obterIdCrafting() {
        return this.idCrafting;
    }

    public Equipamento obterEquipamento() {
        return this.equipamento;
    }

}
