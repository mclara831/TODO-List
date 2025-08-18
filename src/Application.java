import CLI.UI;
import services.TarefaService;

import java.util.Scanner;

public class Application {
    public static void main(String[] args) {

        TarefaService.carregarTarefas();
        boolean continuar = true;
        Scanner sc = new Scanner(System.in);
        int opcao;
        while (continuar) {

            UI.menu();

            opcao = sc.nextInt();

            switch (opcao) {
                case 1:
                    UI.criarNovaTarefa();
                    break;
                case 2:
                    UI.deletarTarefa();
                    break;
                case 3:
                    UI.listarTarefasPorCategoria();
                    break;
                case 4:
                    UI.listarTarefasPorPrioridade();
                    break;
                case 5:
                    UI.listarTarefasPorStatus();
                    break;
                case 6:
                    UI.listarNumeroDeTarefasPorStatus();
                    break;
                case 7:
                    UI.listarTarefasPorData();
                    break;
                case 8:
                    UI.listarTodasTarefas();
                    break;
                case 9:
                    UI.atualizarTodaTarefaPorNome();
                    break;
                case 10:
                    UI.atualizarStatusTarefaPorNome();
                    break;
                case 0:
                    TarefaService.salvarDados();
                    continuar = false;
                    System.out.println("Encerrando o programa...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }
}
