# 📝 ToDo List

Esse projeto foi desenvolvido como proposta de resolução de ZG-Hero Project da trilha de Java do Acelera ZG.

Um simples projeto de **lista de tarefas** que permite criar, listar e gerenciar tarefas de forma prática.



## 📌 Funcionalidades Backend
1. Criar nova tarefa
2. Deletar tarefa
3. Listar tarefas por categoria
4. Listar tarefas por prioridade
5. Listar tarefas por status
6. Listar número de tarefas por status
7. Listar tarefas por data
8. Listar todas as tarefas
9. Atualizar todos os dados de uma tarefa
10. Atualizar apenas o status de uma tarefa

## 📌 Funcionalidades Frontend

- CRUD de tarefas

- Filtragem de tarefas

-  *Persistência em LocalStorage*: para implementar o salvamento de dados em LocalStorage, foram definidos dois métodos que realizam a definição: `setArrayToLocalStorage(arr)` e outra para recuperar os dados: `getLocalStorage()`. Dessa forma, ao utilizar as funcionalidade do CRUD, esses métodos são utilizados para auxiliar na manutenção dos dados.

- *Atualização de múltiplas tarefas*: para que essa implementação fosse possível, foi adicionado um botão "Mudar status de múltiplas tarefas" que ao ser clicado transforma a lista de tarefas em uma lista de opções com checkbox, onde cada opção possui um id definido pelo: tarefa-NOME DA TAREFA-INDICE NO ARRAY DE DADOS. Com isso, ao definir o status a ser atualizado e clicar em "Aplicar", com base no ids dos inputs, as tarefas selecionadas são atualizadas com sucesso.



## 🛠️ Tecnologias utilizadas

### Front-end:
    - CSS
    - HTML
    - Javascript

### Back-end:
    - Java



## 🚀 Como executar

1. Clone este repositório ou copie o arquivo `todo_list.jar` para sua máquina.
2. Certifique-se que esteja dentro da pasta: `TODO-List`
3. No seu terminal, execute o comando: 
```bash
java -jar todo_list.jar
```

## 📂 Estrutura do projeto

`src/Application.java` → Classe principal para executar a aplicação.


## 👩‍💻 Créditos

- Maria Clara Barbosa Fernandes