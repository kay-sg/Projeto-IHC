public class CraftingFactory {
    public static Crafting criar(String tipo, int idCrafting) {
        return criar(tipo, idCrafting, null);
    }
    
    public static Crafting criar(String tipo, int idCrafting, Minijogo minijogo) {
        switch(tipo.toLowerCase()) {
            case "automatico":
                return new Automatico(idCrafting);
            case "manual":
                return new Manual(idCrafting, minijogo);
            default:
                throw new IllegalArgumentException("Tipo inválido");
        }
    }
}
