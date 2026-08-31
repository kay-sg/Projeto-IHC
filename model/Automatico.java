import java.util.List;

public class Automatico extends Crafting {

    public Automatico(int id) {
        super(id);
    }

    @Override
    protected Equipamento calcularAtributos(TemplateEquipamento template, List<Material> materiais) {
        EquipamentoBuilder builder = new EquipamentoBuilder()
            .tipo(String.valueOf(template.getTipo()))
            .metodoCrafting(false);

        adicionarAtributosPadrao(builder, materiais);
        adicionarAtributosDosMateriais(builder, materiais);

        return builder.build();
    }

    private void adicionarAtributosPadrao(EquipamentoBuilder builder, List<Material> materiais) {
        float somaValorMagico = 0f;
        for (Material m : materiais) {
            somaValorMagico += m.obterValorMagico();
        }

        builder.comAtributo(new Atributo("Durabilidade", 50f + somaValorMagico * 2));
        builder.comAtributo(new Atributo("Ataque", 5f + somaValorMagico));
    }

    private void adicionarAtributosDosMateriais(EquipamentoBuilder builder, List<Material> materiais) {
        for (Material m : materiais) {
            builder.comAtributo(new Atributo(m.obterNome(), m.obterValorMagico() * (float) Math.random()));
        }
    }
}