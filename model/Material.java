public class Material {

    private int idMaterial;
    private String nome;
    private float durabilidade;
    private float peso;
    private float valorMagico;

    public Material(
            int idMaterial,
            String nome,
            float durabilidade,
            float peso,
            float valorMagico) {

        this.idMaterial = idMaterial;
        this.nome = nome;
        this.durabilidade = durabilidade;
        this.peso = peso;
        this.valorMagico = valorMagico;
    }

    public String retornarValores() {
        return "Material: " + this.nome
                + ", Durabilidade: " + this.durabilidade
                + ", Peso: " + this.peso
                + ", Valor mágico: " + this.valorMagico;
    }

    public int obterIdMaterial() {
        return this.idMaterial;
    }

    public String obterNome() {
        return this.nome;
    }

    public float obterDurabilidade() {
        return this.durabilidade;
    }

    public float obterPeso() {
        return this.peso;
    }

    public float obterValorMagico() {
        return this.valorMagico;
    }
}