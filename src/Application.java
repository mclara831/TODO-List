import CLI.UI;
import services.AlarmeService;
import services.TarefaService;

import java.util.Scanner;

public class Application {
    public static void main(String[] args) {

        TarefaService tarefaService = new TarefaService();
        UI ui = new UI(tarefaService);
        AlarmeService alarmeService = new AlarmeService(tarefaService);

        Thread alarme = new Thread(alarmeService);
        alarme.start();

        boolean continuar = true;
        Scanner sc = new Scanner(System.in);
        int opcao;
        while (continuar) {

            ui.menu();

            opcao = sc.nextInt();

            switch (opcao) {
                case 1:
                    ui.criarNovaTarefa();
                    break;
                case 2:
                    ui.deletarTarefa();
                    break;
                case 3:
                    ui.listarTarefasPorCategoria();
                    break;
                case 4:
                    ui.listarTarefasPorPrioridade();
                    break;
                case 5:
                    ui.listarTarefasPorStatus();
                    break;
                case 6:
                    ui.listarNumeroDeTarefasPorStatus();
                    break;
                case 7:
                    ui.listarTarefasPorData();
                    break;
                case 8:
                    ui.listarTodasTarefas();
                    break;
                case 9:
                    ui.atualizarTodaTarefaPorNome();
                    break;
                case 10:
                    ui.atualizarStatusTarefaPorNome();
                    break;
                case 0:
                    tarefaService.salvarDados();
                    alarme.interrupt();
                    continuar = false;
                    System.out.println("Encerrando o programa...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }
}
