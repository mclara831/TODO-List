package CLI;

import entities.Status;
import entities.Tarefa;
import services.TarefaService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class UI {

    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final TarefaService service;
    private final Scanner input;

    public UI(TarefaService tarefaService) {
        this.service = tarefaService;
        input = new Scanner(System.in);
    }

    private LocalDate lerData() {
        boolean continuar = true;
        LocalDate dataFormatada = null;
        while (continuar) {
            try {
                String data = input.next();
                dataFormatada = LocalDate.parse(data, dtf);
                continuar = false;
            } catch (DateTimeParseException e) {
                System.out.println("Data está em formato inválido! Digite novamente: ");
            }
        }
        return dataFormatada;
    }

    private LocalTime lerHorario() {
        input.nextLine();
        String horaStr = input.nextLine(); // lê string
        DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("HH:mm");
        return LocalTime.parse(horaStr, formatterHora);
    }

    private Status lerStatus() {
        System.out.println("\n1. TODO (para fazer)");
        System.out.println("2. DOING (fazendo)");
        System.out.println("3. DONE (feita)");
        System.out.println("Qual o status do tarefa: ");
        int statusTarefa = input.nextInt();

        while (statusTarefa < 1 || statusTarefa > 3) {
            System.out.println("\n[AVISO]: Entrada inválida! Digite novamente!");
            System.out.println("\n1. TODO (para fazer)");
            System.out.println("2. DOING (fazendo)");
            System.out.println("3. DONE (feita)");
            System.out.println("Qual o status do tarefa: ");
            statusTarefa = input.nextInt();
        }

        Status status = switch (statusTarefa) {
            case 1 -> Status.TODO;
            case 2 -> Status.DOING;
            case 3 -> Status.DONE;
            default -> null;
        };

        return status;
    }

    public void menu() {
        System.out.println("\n============================== MENU DE OPÇÕES ==============================");
        System.out.println("1.  Criar nova tarefa");
        System.out.println("2.  Deletar tarefa");
        System.out.println("3.  Listar tarefas por categoria");
        System.out.println("4.  Listar tarefas por prioridade");
        System.out.println("5.  Listar tarefas por status");
        System.out.println("6.  Listar número de tarefas por status");
        System.out.println("7.  Listar tarefas por data");
        System.out.println("8.  Listar todas as tarefas");
        System.out.println("9.  Atualizar todos os dados de uma tarefa");
        System.out.println("10. Atualizar status de uma tarefa");
        System.out.println("0.  Sair");
        System.out.println("==============================================================================");
        System.out.print("Escolha uma opção: ");
    }


    public Tarefa inserirDadosTarefa() {
        System.out.println("Digite o nome do tarefa: ");
        String nome = input.nextLine();
        input.nextLine();
        System.out.println("Digite a descricao da tarefa: ");
        String descricao = input.nextLine();
        System.out.println("Digite a data de termino da tarefa (dd/mm/yyyy): ");
        LocalDate data = lerData();

        System.out.println("Em uma escala de 1 (mais alto) a 5 (mais baixo): qual é a prioridade desse tarefa? ");
        int prioridade = input.nextInt();
        while (prioridade < 1 || prioridade > 5) {
            System.out.println("Prioridade inválida! Digite novamente!");
            System.out.println("Em uma escala de 1 a 5: qual é a prioridade desse tarefa? ");
            prioridade = input.nextInt();
        }

        input.nextLine();
        System.out.println("Digite a categoria da tarefa: ");
        String categoria = input.nextLine();

        Status statusTarefa = lerStatus();

        System.out.println("\nGostaria de definir um alarme de lembrete para essa tarefa (s/n)? ");
        char alarme = input.next().charAt(0);
        while (alarme != 's' && alarme != 'n') {
            System.out.println("Digite novamente! Gostaria de definir um alarme de lembrete para essa tarefa (s/n)? ");
            alarme = input.next().charAt(0);
        }

        boolean alarmeAtivo;
        LocalDate dataAlarme;
        LocalTime timeAlarme;
        LocalDateTime alarmeCompleto = null;
        if (alarme == 's') {

            alarmeAtivo = true;

            System.out.println("Digite o dia do alarme(dd/mm/yyyy): ");
            dataAlarme = lerData();
            System.out.println("Digite o horário do alarme(hh:mm): ");
            timeAlarme = lerHorario();
            alarmeCompleto = LocalDateTime.of(dataAlarme, timeAlarme);

            while (alarmeCompleto.toLocalDate().isAfter(data)) {
                System.out.println("\n[AVISO]: A data digitada está para depois da data de término da tarefa: " + data + ". Digite novamente!");
                System.out.println("Digite o dia do alarme(dd/mm/yyyy): ");
                dataAlarme = lerData();
                alarmeCompleto = LocalDateTime.of(dataAlarme, timeAlarme);
            }

        } else {
            alarmeAtivo = false;
        }
        return new Tarefa(nome, descricao, data, prioridade, categoria, statusTarefa, alarmeAtivo, alarmeCompleto);
    }

    public void listarTodasTarefas() {
        var tarefas = service.getTarefaList();
        System.out.println("\nTodas Tarefas:");
        for (Tarefa t : tarefas) {
            System.out.println(t.tarefaToString());
        }
    }

    public void criarNovaTarefa() {
        input.nextLine();
        service.criarNovaTarefa(inserirDadosTarefa());
        System.out.println("\n[AVISO]: Tarefa criada com sucesso!");
    }

    public void deletarTarefa() {
        input.nextLine();
        System.out.println("Digite o nome do tarefa: ");
        String nome = input.nextLine();
        service.deletarTarefa(nome);
        System.out.println("\n[AVISO]: Operação concluida com sucesso!");
    }

    public void listarTarefasPorCategoria() {
        input.nextLine();
        System.out.println("Digite o nome da categoria: ");
        String categoria = input.nextLine();
        var tarefas = service.listarTarefaPorCategoria(categoria);

        System.out.println("Lista de tarefas na categoria: " + categoria);
        tarefas.forEach(tarefa -> System.out.println(tarefa.tarefaToString()));
    }

    public void listarTarefasPorPrioridade() {
        System.out.println("Digite o número da prioridade: ");
        Integer prioridade = input.nextInt();
        var tarefas = service.listarTarefaPorPrioridade(prioridade);

        System.out.println("Lista de tarefas em prioridade: " + prioridade);
        tarefas.forEach(tarefa -> System.out.println(tarefa.tarefaToString()));
    }

    public void listarTarefasPorStatus() {

        Status status = lerStatus();
        var tarefas = service.listarTarefaPorStatus(status);

        System.out.println("Lista de tarefas em status: " + status);
        tarefas.forEach(tarefa -> System.out.println(tarefa.tarefaToString()));
    }

    public void listarNumeroDeTarefasPorStatus() {
        Status status = lerStatus();

        Integer numeroTarefa = service.listarNumeroDeTarefaPorStatus(status);
        System.out.println("Total de tarefas em status " + status + ": " + numeroTarefa);
    }

    public void listarTarefasPorData() {
        input.nextLine();
        System.out.println("Digite o inicio do periodo de busca (dd/mm/yyyy) ");
        LocalDate inicio = lerData();
        input.nextLine();
        System.out.println("Digite o final do periodo de busca (dd/mm/yyyy) ");
        LocalDate fim = lerData();

        var tarefas = service.listarTarefasPorData(inicio, fim);
        System.out.println("Lista de tarefas para concluir de " + inicio + " até " + fim + ": ");
        tarefas.forEach(tarefa -> System.out.println(tarefa.tarefaToString()));
    }

    public void atualizarTodaTarefaPorNome() {
        input.nextLine();
        System.out.println("Digite o nome do tarefa que deseja atualizar: ");
        String nome = input.nextLine();

        System.out.println("Digite os dados da tarefa atualizado: ");
        Tarefa t = inserirDadosTarefa();
        service.atualizarTodaTarefaPorNome(nome, t);
    }

    public void atualizarStatusTarefaPorNome() {
        input.nextLine();
        System.out.println("Digite o nome do tarefa que deseja atualizar: ");
        String nome = input.nextLine();

        Status status = lerStatus();
        service.atualizarStatusTarefaPorNome(nome, status);
    }
}
