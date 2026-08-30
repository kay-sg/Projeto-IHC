import java.util.ArrayList;

public class EquipamentoBuilder {
    private int idEquipamento;
    private String nome;
    private float qualidade;
    private boolean metodoCrafting;
    private String tipo;
    private ArrayList<Atributo> atributos = new ArrayList<>();

    public EquipamentoBuilder id(int id) {
        this.idEquipamento = id;
        return this;
    }

    public EquipamentoBuilder nome(String nome) {
        this.nome = nome;
        return this;
    }

    public EquipamentoBuilder id(String tipo) {
        this.tipo = tipo;
        return this;
    }

    public EquipamentoBuilder id(float qualidade) {
        this.qualidade = qualidade;
        return this;
    }

    public EquipamentoBuilder metodoCrafting(boolean metodoCrafting) {
        this.metodoCrafting = metodoCrafting;
        return this;
    }

    public Equipamento build() {
        Equipamento equipamento = new Equipamento(
            this.idEquipamento, 
            this.nome, 
            this.idEquipamento, 
            this.metodoCrafting, 
            this.tipo
        );

        for (Atributo at : this.atributos) {
            equipamento.atribuicao(at);
        }

        return equipamento;
    }
}
