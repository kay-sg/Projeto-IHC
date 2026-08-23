import java.util.ArrayList;
import java.util.List;

public class Inventario {

    private int idInventario;
    private int quantidadeMax;
    private int quantidadeAtual;
    private List<Equipamento> equipamentos;

    public Inventario(int idInventario, int quantidadeMax) {
        this.idInventario = idInventario;
        this.quantidadeMax = quantidadeMax;
        this.quantidadeAtual = 0;
        this.equipamentos = new ArrayList<>();
    }

    public void adicionarEquipamento(Equipamento e) {
        if (verificarCapacidade()) {
            this.equipamentos.add(e);
        }
    }

    public void removerEquipamento(Equipamento e) {
        if (equipamentos.remove(e)) {
            this.quantidadeAtual--;
        }
    }

    public List<Equipamento> listarEquipamentos() {
        return this.equipamentos;
    }

    public boolean verificarCapacidade() {
        if (this.quantidadeAtual < this.quantidadeMax) {
            return true;
        }

        return false;
    }

    public int getIdInventario() {
        return this.idInventario;
    }

    public int getQuantidadeMax() {
        return this.quantidadeMax;
    }

    public int getQuantidadeAtual() {
        return this.quantidadeAtual;
    }
}
