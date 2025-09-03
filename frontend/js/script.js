var tarefas = [
  {
    nome: "Limpar a casa",
    descricao: "Lavar o banheiro",
    categoria: "Casa",
    dataTermino: "2025-09-03",
    prioridade: "2",
    status: "To do",
  },
  {
    nome: "Finalizar a trilha de JS",
    descricao: "Projeto prático",
    categoria: "Acelera ZG",
    dataTermino: "2025-09-03",
    prioridade: "1",
    status: "Doing",
  },
  {
    nome: "Ir a mercado",
    descricao: "Comprar pão de sal e manteiga",
    categoria: "Casa",
    dataTermino: "2025-09-02",
    prioridade: "2",
    status: "Done",
  }
];

carregartarefas();
let tarefaEditando = null;

document.querySelector("#current_year").innerHTML = new Date().getFullYear();
document.querySelector("#filtrar").value = 'all';


function carregartarefas() {
  var lista = document.querySelector("#lista");
  lista.innerHTML = "";

  tarefas.forEach((tarefa) => {
    var li = document.createElement("li");
    li.innerHTML = `
            <div class="title">
                <p id="titulo">${tarefa.nome}</p>
                <div id="tarefa-btn">
                    <button class="editar btn">Editar</button>
                    <button class="deletar btn">Deletar</button>
                    <button class="ver-detalhes btn">Ver detalhes</button>
                </div>
            </div>
            <div class="detalhes" style="display: none;">
                <p>Descrição: <span>${tarefa.descricao}</span></p>
                <p>Categoria: <span>${tarefa.categoria}</span></p>
                <p>Data término: <span>${tarefa.dataTermino}</span></p>
                <p>Prioridade: <span>${tarefa.prioridade}</span></p>
                <p>Status: <span>${tarefa.status}</span></p>
            </div>`;
    lista.appendChild(li);
  });
  deletarTarefa();
  atualizarTarefa();
  verDetalhes();
}

// botão de voltar
document.querySelector("#return-btn").onclick = function () {
  document.querySelector("#nova-tarefa-form").style.display = "none";
  document.querySelector("#tarefas-container").style.display = "initial";
};

// limpa o campos do formulário
function limparFormulario() {
  var form = document.querySelector("#nova-tarefa-form");

  form.elements["nome"].value = "";
  form.elements["descricao"].value = "";
  form.elements["categoria"].value = "";
  form.elements["data-termino"].value = "";
  form.elements["prioridade"].value = "";
  form.elements["status"].value = "";
}

//exibir formulário
document.querySelector("#nova_tarefa").onclick = function () {
  limparFormulario();
  document.querySelector("#nova-tarefa-form").style.display = "initial";
  document.querySelector("#tarefas-container").style.display = "none";
};

//adicionar tarefa
document.querySelector("#form-btn").onclick = function (event) {
  event.preventDefault();
  var form = document.querySelector("#nova-tarefa-form");

  const nome = form.elements["nome"].value;
  const descricao = form.elements["descricao"].value;
  const categoria = form.elements["categoria"].value;
  const dataTermino = form.elements["data-termino"].value;
  const prioridade = form.elements["prioridade"].value;
  const status = form.elements["status"].value;

  if (!nome || !categoria || !dataTermino || !prioridade || !status) {
    alert("Preencha todos os campos obrigatórios!");
    return;
  }

  const tarefa = {
    nome,
    descricao,
    categoria,
    dataTermino,
    prioridade,
    status,
  };

  if (tarefaEditando !== null) {
    tarefas[tarefaEditando] = tarefa;
    tarefaEditando = null;
    this.innerText = "Adicionar tarefa";
  } else {
    tarefas.push(tarefa);
  }

  form.style.display = "none";
  document.querySelector("#tarefas-container").style.display = "initial";
  carregartarefas();
};

function verDetalhes() {
  document.querySelectorAll(".ver-detalhes").forEach((btn) => {
    btn.onclick = function () {
      const detalhes = this.closest("li").querySelector(".detalhes");

      if (btn.innerHTML === "Ver detalhes") {
        btn.innerHTML = "Ocultar";
        detalhes.style.display = "block";
      } else {
        btn.innerHTML = "Ver detalhes";
        detalhes.style.display = "none";
      }
    };
  });
}

function deletarTarefa() {
  document.querySelectorAll(".deletar").forEach((element) => {
    element.onclick = function (event) {
      const index = event.target.dataset.index;
      tarefas.splice(index, 1);
      carregartarefas();
    };
  });
}

function atualizarTarefa() {
  var atualizar_btns = document.querySelectorAll(".editar");
  for (let i = 0; i < tarefas.length; i++) {
    atualizar_btns[i].onclick = function () {
      const tarefa = tarefas[i];

      const form = document.querySelector("#nova-tarefa-form");
      form.elements["nome"].value = tarefa.nome;
      form.elements["descricao"].value = tarefa.descricao;
      form.elements["categoria"].value = tarefa.categoria;
      form.elements["data-termino"].value = tarefa.dataTermino;
      form.elements["prioridade"].value = tarefa.prioridade;
      form.elements["status"].value = tarefa.status;

      form.style.display = "initial";
      document.querySelector("#tarefas-container").style.display = "none";
      tarefaEditando = i;

      const botao = document.querySelector("#form-btn");
      botao.innerText = "Salvar Alterações";
    };
  }
}

// filtrar tarefas
document.querySelector("#filter-btn").onclick = function () {
  var value = document.querySelector("#filtrar").value;
  var lista = document.querySelector("#lista");
  lista.innerHTML = "";

  if (value == "all") {
    carregartarefas();
    return;
  }

  tarefas.forEach((tarefa) => {
    if (value.toLocaleLowerCase() == tarefa.status.toLocaleLowerCase()) {
      var li = document.createElement("li");
      li.innerHTML = `
                <div class="title">
                    <p id="titulo">${tarefa.nome}</p>
                    <div id="tarefa-btn">
                        <button class="editar btn">Editar</button>
                        <button class="deletar btn">Deletar</button>
                        <button class="ver-detalhes btn">Ver detalhes</button>
                    </div>
                </div>
                <div class="detalhes" style="display: none;">
                    <p>Descrição: <span>${tarefa.descricao}</span></p>
                    <p>Categoria: <span>${tarefa.categoria}</span></p>
                    <p>Data término: <span>${tarefa.dataTermino}</span></p>
                    <p>Prioridade: <span>${tarefa.prioridade}</span></p>
                    <p>Status: <span>${tarefa.status}</span></p>
                </div>
            `;
      lista.appendChild(li);
    }
  });
};