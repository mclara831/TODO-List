let tarefaEditando = null;

function getLocalStorage() {
  return JSON.parse(localStorage.getItem("tarefas"));
}

console.log(getLocalStorage());

function setNewObjectToLocalStorage(obj) {
  var tarefas = getLocalStorage();
  tarefas.push(obj);
  localStorage.setItem("tarefas", JSON.stringify(tarefas));
}

function setArrayToLocalStorage(arr) {
  localStorage.setItem("tarefas", JSON.stringify(arr));
}

document.querySelector("#current_year").innerHTML = new Date().getFullYear();
document.querySelector("#filtrar").value = "all";

function carregartarefas() {
  var tarefas = getLocalStorage();
  var lista = document.querySelector("#lista");
  lista.innerHTML = "";

  tarefas.forEach((tarefa) => {
    var li = document.createElement("li");
    li.className = "tarefa-container";
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

  var tarefas = getLocalStorage();

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

  setArrayToLocalStorage(tarefas);

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
  var tarefas = getLocalStorage();
  document.querySelectorAll(".deletar").forEach((element) => {
    element.onclick = function (event) {
      const index = event.target.dataset.index;
      tarefas.splice(index, 1);
      setArrayToLocalStorage(tarefas);
      carregartarefas();
    };
  });
}

function atualizarTarefa() {
  var tarefas = getLocalStorage();
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
document.querySelector("#filter-btn").onclick = filtrar();

function filtrar() {
  var tarefas = getLocalStorage();

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
      li.className = "tarefa-container";
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
}

function renderizarParaMudarStatus() {
  let lista = document.querySelector("#lista");
  lista.innerHTML = "";
  const tarefas = getLocalStorage();

  tarefas.forEach((tarefa) => {
    const li = document.createElement("li");
    li.className = "tarefa-item";

    const input = document.createElement("input");
    input.type = "checkbox";
    input.className = "btn-check";
    input.id = `tarefa-${tarefa.nome}-${tarefas.indexOf(tarefa)}`;
    input.value = tarefa.nome;
    input.autocomplete = "off";

    const label = document.createElement("label");
    label.className = "tarefa-name";
    label.htmlFor = input.id;
    label.textContent = tarefa.nome;

    li.appendChild(input);
    li.appendChild(label);
    lista.appendChild(li);
  });
  mudarStatusDeMultiplasTarefas();
}

document.querySelector("#mudar-status-btn").onclick = function (event) {
  event.preventDefault();
  renderizarParaMudarStatus();
};

function mudarStatusDeMultiplasTarefas() {

  let label = document.querySelector("#filtrar-label");
  label.innerHTML = "Mudar status para: ";

  let filtrar_btn = document.querySelector("#filter-btn");
  filtrar_btn.innerHTML = "Aplicar";
  
  var select = document.querySelector("#filtrar").value;

  let all_option = document.querySelector("[value='all']");
  let todo_option = document.querySelector("[value='To do']");

  if (all_option) {
    all_option.style.display = "none";
    all_option.selected = false;
  }

  if (todo_option) {
    todo_option.selected = true;
    select.value = "To do";
  }

  var tarefas = getLocalStorage();

  filtrar_btn.onclick = function () {
    const inputs = document.querySelectorAll("input[type=checkbox]:checked");
    select = document.querySelector("#filtrar").value;
    console.log(select)

    inputs.forEach((i) => {
      var t = tarefas.find(
        (t) => `tarefa-${t.nome}-${tarefas.indexOf(t)}` == i.id
      );
      var index = tarefas.indexOf(t);
      if (t) {
        t.status = select;
        tarefas[index] = t;
      }
    });

    setArrayToLocalStorage(tarefas);
    label.innerHTML = "Filtrar por: ";
    filtrar_btn.innerHTML = "Filtrar";
    filtrar_btn.onclick = filtrar;

    if (all_option) {
      all_option.style.display = "initial"; 
      all_option.selected = true;
    }

    if (todo_option) {
      todo_option.selected = false;
    }

    carregartarefas();
  };
}

carregartarefas();
