public abstract class Crafting {

    protected int idCrafting;
    protected Equipamento equipamento;

    public Crafting(int idCrafting) {
        this.idCrafting = idCrafting;
    }

    public abstract Equipamento executarCraft();

    public int obterIdCrafting() {
        return this.idCrafting;
    }

    public Equipamento obterEquipamento() {
        return this.equipamento;
    }

    public void definirEquipamento(Equipamento equipamento) {
        this.equipamento = equipamento;
    }
}
