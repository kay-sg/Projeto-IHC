public abstract class Crafting {

    private int idCrafting;
    protected Equipamento equipamento;

    public Crafting(int idCrafting) {
        this.idCrafting = idCrafting;
    }

    public abstract String executarCraft();

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
