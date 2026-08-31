package model;

import java.util.List;

public class Manual extends Crafting {

    private Minijogo minijogo;

    public Manual(int id, Minijogo minijogo) {
        super(id);
        this.minijogo = minijogo;
    }

    @Override
    protected Equipamento calcularAtributos(TemplateEquipamento template, List<Material> materiais) {
        float desempenho = minijogo.getPontuacao() / 100f;

        EquipamentoBuilder builder = new EquipamentoBuilder()
            .tipo(String.valueOf(template.getTipo()))
            .metodoCrafting(true);

        adicionarAtributosPadrao(builder, materiais, desempenho);
        adicionarAtributosDosMateriais(builder, materiais, desempenho);

        return builder.build();
    }

    private void adicionarAtributosPadrao(EquipamentoBuilder builder, List<Material> materiais, float fator) {
        float somaValorMagico = 0f;
        for (Material m : materiais) {
            somaValorMagico += m.obterValorMagico();
        }

        builder.comAtributo(new Atributo("Durabilidade", (50f + somaValorMagico * 2) * fator));
        builder.comAtributo(new Atributo("Ataque", (5f + somaValorMagico) * fator));
    }

    private void adicionarAtributosDosMateriais(EquipamentoBuilder builder, List<Material> materiais, float fator) {
        for (Material m : materiais) {
            builder.comAtributo(new Atributo(m.obterNome(), m.obterValorMagico() * fator));
        }
    }
}