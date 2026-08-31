package controller;

import java.util.List;
import model.*;


import model.repository.ConexaoJava;
import model.repository.ConexaoRepository;
import model.repository.JavaInstructionsRepository;
import model.repository.MaterialRepository;
import model.repository.TemplateRepository;
import model.repository.MinijogoRepository;
import model.repository.InventarioRepository;

public class CraftingController {

    private static boolean ativo = true;

    private final JavaInstructionsRepository
            instructionRepository;

    private final MaterialRepository
            materialRepository;

    private final TemplateRepository
            templateRepository;

    private final MinijogoRepository
            minijogoRepository;
    
    private final InventarioRepository
        inventarioRepository;

    public CraftingController(
            JavaInstructionsRepository instructionRepository,
            MaterialRepository materialRepository,
            TemplateRepository templateRepository,
            MinijogoRepository minijogoRepository,
            InventarioRepository inventarioRepository) {

        this.instructionRepository =
                instructionRepository;

        this.materialRepository =
                materialRepository;

        this.templateRepository =
                templateRepository;

        this.minijogoRepository =
                minijogoRepository;

        this.inventarioRepository =
                inventarioRepository;
    }

    public void executar() {

        while (ativo) {

            processarInstrucoesPendentes();

            try {

                Thread.sleep(3000);

            } catch (InterruptedException e) {

                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private void processarInstrucoesPendentes() {

        List<JavaInstruction> instrucoes =
                instructionRepository.buscarPendentes();

        if (instrucoes.isEmpty()) {

            System.out.println(
                    "Nenhum registro encontrado"
            );

            return;
        }

        for (JavaInstruction instrucao : instrucoes) {

            System.out.println(
                    "Nova instrução encontrada! ID: "
                    + instrucao.getId()
            );

            switch (
                    instrucao.getTipo().toUpperCase()
            ) {

                case "CREATE":

                    processarCreate(instrucao);
                    break;

                case "END":

                    processarEnd(instrucao);
                    break;

                default:

                    System.out.println(
                            "Tipo de instrução inválido."
                    );
                    break;
            }
        }
    }

    private void processarCreate(
            JavaInstruction instrucao) {

        int idUsuario =
                instrucao.getIdUsuario();

        int idTemplate =
                instrucao.getIdTemplate();

        List<Material> materiais =
                materialRepository.buscarPorTemplate(
                        idTemplate
                );

        TemplateEquipamento template =
                templateRepository.buscarPorId(
                        idTemplate
                );

        if (template == null) {

            System.out.println(
                    "Template não encontrado"
            );

            return;
        }

        Crafting sistemaCrafting =
                criarSistemaCrafting(
                        instrucao.getInstruction(),
                        idUsuario
                );

        if (sistemaCrafting == null) {
            return;
        }

        Equipamento equipamentoCriado =
                sistemaCrafting.executarCraft(
                        template,
                        materiais
                );

        if (equipamentoCriado != null) {
            // Salva o equipamento gerado no banco de dados através do repositório
            inventarioRepository.salvarEquipamento(equipamentoCriado, idUsuario);
        }

        instructionRepository.marcarComoProcessada(
                instrucao.getId()
        );

        System.out.println(
                "Equipamento criado com sucesso"
        );
    }

    private void processarEnd(
            JavaInstruction instrucao) {

        instructionRepository.marcarComoProcessada(
                instrucao.getId()
        );

        ativo = false;
    }

    private Crafting criarSistemaCrafting(
            String metodo,
            int idUsuario) {

        switch (metodo.toUpperCase()) {

            case "AUTOMATICO":

                return new Automatico(idUsuario);

            case "MANUAL":

                Minijogo minijogo =
                        minijogoRepository
                                .buscarDisponivel(
                                        idUsuario
                                );

                if (minijogo == null) {

                    System.out.println(
                            "Minijogo não encontrado"
                    );

                    return null;
                }

                return new Manual(
                        idUsuario,
                        minijogo
                );

            default:

                return null;
        }
    }

    public static void main(String[] args) {

        ConexaoRepository conexao =
                ConexaoJava.obterInstancia();

        JavaInstructionsRepository
                instructionRepository =
                new JavaInstructionsRepository(
                        conexao
                );

        MaterialRepository materialRepository =
                new MaterialRepository(
                        conexao
                );

        TemplateRepository templateRepository =
                new TemplateRepository(
                        conexao
                );

        MinijogoRepository minijogoRepository =
                new MinijogoRepository(
                        conexao
                );
        
        InventarioRepository inventarioRepository=
                new InventarioRepository(
                    conexao
                );

        CraftingController controller =
                new CraftingController(
                        instructionRepository,
                        materialRepository,
                        templateRepository,
                        minijogoRepository,
                        inventarioRepository
                );

        controller.executar();
    }
}