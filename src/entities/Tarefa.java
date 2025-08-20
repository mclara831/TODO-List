package entities;

import java.time.LocalDate;

public class Tarefa implements Comparable<Tarefa> {
    private String nome;
    private String descricao;
    private LocalDate dataTermino;
    private Integer nivelPrioridade;
    private String categoria;
    private Status status;

    public Tarefa(String nome, String descricao, LocalDate dataTermino, Integer nivelPrioridade, String categoria, Status status) {
        this.nome = nome;
        this.descricao = descricao;
        this.dataTermino = dataTermino;
        this.nivelPrioridade = nivelPrioridade;
        this.categoria = categoria;
        this.status = status;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataTermino() {
        return dataTermino;
    }

    public void setDataTermino(LocalDate dataTermino) {
        this.dataTermino = dataTermino;
    }

    public Integer getNivelPrioridade() {
        return nivelPrioridade;
    }

    public void setNivelPrioridade(Integer nivelPrioridade) {
        this.nivelPrioridade = nivelPrioridade;
    }

    public String  getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String tarefaToString() {
        return "\n\nNome: " + nome
                + "\nDescricao: " + descricao
                + "\nDataTermino: " + dataTermino
                + "\nNivelPrioridade: " + nivelPrioridade
                + "\nCategoria: " + categoria
                + "\nStatus: " + status;
    }

    @Override
    public String toString() {
        return nome + "$$"
                + descricao + "$$"
                + dataTermino + "$$"
                + nivelPrioridade + "$$"
                + categoria + "$$"
                + status;
    }

    @Override
    public int compareTo(Tarefa tarefa) {
        if (this.nivelPrioridade > tarefa.nivelPrioridade) {
            return 1;
        } else  if (this.nivelPrioridade == tarefa.nivelPrioridade) {
            return 0;
        }  else {
            return -1;
        }
    }
}
