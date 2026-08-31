package model;

public class JavaInstruction {

    private int id;
    private String tipo;
    private String instruction;
    private int idUsuario;
    private int idTemplate;

    public JavaInstruction(
            int id,
            String tipo,
            String instruction,
            int idUsuario,
            int idTemplate) {

        this.id = id;
        this.tipo = tipo;
        this.instruction = instruction;
        this.idUsuario = idUsuario;
        this.idTemplate = idTemplate;
    }

    public int getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public String getInstruction() {
        return instruction;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public int getIdTemplate() {
        return idTemplate;
    }
}