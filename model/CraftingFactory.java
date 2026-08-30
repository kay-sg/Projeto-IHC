public class CraftingFactory {
    public static Crafting criar(String tipo, int idCrafting) {
        switch(tipo.toLowerCase()) {
            case "automatico":
                return new Automatico(idCrafting);
            case "manual":
                return new Manual(idCrafting);
            default:
                throw new IllegalArgumentException("Tipo inválido");
        }
    }
}
