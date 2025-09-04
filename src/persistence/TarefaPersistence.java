package persistence;

import entities.Status;
import entities.Tarefa;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class TarefaPersistence {

    File file;
    Scanner scanner;

    public TarefaPersistence() {
        file = new File("tarefas.txt");
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Tarefa> lerTodasTarefas() {
        List<Tarefa> tarefas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file));) {

            String line;
            while ((line = br.readLine()) != null) {

                String[] dados = line.split("\\$\\$");

                LocalDateTime alarme = null;
                if (!dados[7].equalsIgnoreCase("null")) {
                    alarme = LocalDateTime.parse(dados[7]);
                }

                Tarefa t = new Tarefa(dados[0],
                        dados[1],
                        LocalDate.parse(dados[2]),
                        Integer.parseInt(dados[3]),
                        dados[4],
                        Status.parseFromString(dados[5]),
                        Boolean.parseBoolean(dados[6]),
                        alarme);
                tarefas.add(t);
            }
            Collections.sort(tarefas);
            return tarefas;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void salvarDados(List<Tarefa> tarefas) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, false))) {
            for (Tarefa tarefa : tarefas) {
                bw.write(tarefa.toString() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
