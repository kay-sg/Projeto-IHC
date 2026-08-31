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
            .metodoCrafting(true).nome(String.valueOf(template.getNome()));

        adicionarAtributosPadrao(builder, materiais, desempenho);
        adicionarMateriaisUtilizados(builder, materiais);

        return builder.build();
    }

    private void adicionarAtributosPadrao(EquipamentoBuilder builder, List<Material> materiais, float fator) {
        float somaValorMagico = 0f;
        float qualidade = 10f;
        for (Material m : materiais) {
            somaValorMagico += m.obterValorMagico();
        }

        builder.comAtributo(new Atributo("Durabilidade", (50f + somaValorMagico * 2) * fator));
        builder.comAtributo(new Atributo("Ataque", (5f + somaValorMagico) * fator));
        builder.qualidade(qualidade*somaValorMagico*fator);
    }

}