package services;

import entities.Status;

import java.time.LocalDateTime;

public class AlarmeService implements Runnable{

    private final TarefaService service;

    public AlarmeService(TarefaService service) {
        this.service = service;
    }

    @Override
    public void run() {
        while (true) {
            try {
                LocalDateTime dataHoraAtual = LocalDateTime.now().withSecond(0).withNano(0);

                service.getTarefaList().forEach(tarefa -> {
                    if (tarefa.getAlarmeAtivo() && !tarefa.getStatus().equals(Status.DONE)) {
                        if (tarefa.getAlarme().withSecond(0).withNano(0).isEqual(dataHoraAtual) &&
                            tarefa.getDataTermino().isAfter(tarefa.getAlarme().toLocalDate())) {
                            System.out.println("\n\n==================== ATENÇÃO ====================");
                            System.out.println("O prazo para a tarefa \"" + tarefa.getNome() + "\" está encerrando!");
                            System.out.println("Data de término: " + tarefa.getDataTermino());
                            System.out.println("===================================================");
                        }
                    }
                });

                Thread.sleep(60000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

}
