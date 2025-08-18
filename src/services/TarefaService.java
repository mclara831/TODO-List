package services;

import entities.Status;
import entities.Tarefa;
import persistence.TarefaPersistence;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TarefaService {

    private static List<Tarefa> tarefaList = new ArrayList<>();
    private static TarefaPersistence persistence = new TarefaPersistence();

    public List<Tarefa> getTarefaList() {
        return tarefaList;
    }

    public static void carregarTarefas() {
        tarefaList = persistence.lerTodasTarefas();

    }

    public void criarNovaTarefa(Tarefa tarefa) {
        tarefaList.add(tarefa);
        Collections.sort(tarefaList);
    }

    public void deletarTarefa(String tarefa) {
        for (Tarefa t : tarefaList) {
            if (t.getNome().equalsIgnoreCase(tarefa.trim().toLowerCase()))  {
                tarefaList.remove(t);
                break;
            }
        }

    }

    public List<Tarefa> listarTarefaPorCategoria(String categoria) {
        List<Tarefa> tarefas = new ArrayList<>();
        for (Tarefa t : tarefaList) {
            if (t.getCategoria().equalsIgnoreCase(categoria.toLowerCase())) {
                tarefas.add(t);
            }
        }
        return tarefas;
    }

    public List<Tarefa> listarTarefaPorPrioridade(Integer prioridade) {
        List<Tarefa> tarefas = new ArrayList<>();
        for (Tarefa t : tarefaList) {
            if (prioridade == t.getNivelPrioridade()) {
                tarefas.add(t);
            }
        }
        return tarefas;
    }

    public List<Tarefa> listarTarefaPorStatus(Status status) {
        List<Tarefa> tarefas = new ArrayList<>();
        for (Tarefa t : tarefaList) {
            if (t.getStatus().equals(status)) {
                tarefas.add(t);
            }
        }
        return tarefas;
    }

    public Integer listarNumeroDeTarefaPorStatus(Status status) {
        Integer numeroDeTarefa = 0;
        for (Tarefa t : tarefaList) {
            if (t.getStatus().equals(status)) {
                numeroDeTarefa++;
            }
        }
        return numeroDeTarefa;
    }

    public List<Tarefa> listarTarefasPorData(LocalDate dataInicial, LocalDate dataFinal) {
        List<Tarefa> tarefas = new ArrayList<>();
        for (Tarefa t : tarefaList) {
            if (t.getDataTermino().isAfter(dataInicial) && t.getDataTermino().isBefore(dataFinal)) {
                tarefas.add(t);
            }
        }
        return tarefas;
    }

    public static void salvarDados() {
        persistence.salvarDados(tarefaList);
    }

    public void atualizarTodaTarefaPorNome(String nome, Tarefa tarefa) {
        for (Tarefa t : tarefaList) {
            if (t.getNome().equalsIgnoreCase(nome.trim().toLowerCase()))  {
                int index = tarefaList.indexOf(t);
                tarefaList.set(index, tarefa);
            }
        }
    }

    public void atualizarStatusTarefaPorNome(String nome, Status status) {
        for (Tarefa t : tarefaList) {
            if (t.getNome().equalsIgnoreCase(nome.trim().toLowerCase())) {
                int index = tarefaList.indexOf(t);
                t.setStatus(status);
                tarefaList.set(index, t);
            }
        }
    }
}
