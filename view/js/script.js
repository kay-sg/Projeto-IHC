let items = [];
let filtro = "Todos";
let busca = "";
let selectedItem = null;

function carregarItens() {
    fetch("http://localhost:8000/itens.php")
        .then(response => {
            if (!response.ok) {
                throw new Error("Erro ao buscar os itens.");
            }
            return response.json();
        })
        .then(data => {
            items = data;
            selectedItem = null;
            atualizarItens();
        })
        .catch(error => {
            console.error("Erro:", error);
        });
}

function descartarItem(id) {
    if (!id) {
        return;
    }

    fetch(`http://localhost:8000/excluirItem.php?id=${id}`)
        .then(response => {
            if (!response.ok) {
                throw new Error("Erro ao excluir o item.");
            }
            return response.json();
        })
        .then(() => {
            selectedItem = null;
            carregarItens();
        })
        .catch(error => {
            console.error("Erro ao excluir:", error);
        });
}

function obterItensFiltrados() {
    return items
        .filter(item => {
            if (filtro === "Todos") return true;
            if (filtro === "Espadas") return item.tipo === "Espada";
            if (filtro === "Armaduras") return item.tipo === "Armadura";
            return true;
        })
        .filter(item => {
            return item.nome.toLowerCase().includes(busca.toLowerCase());
        });
}

function atualizarItens() {
    const itemsFiltrados = obterItensFiltrados();
    const container = document.getElementById("itemsAreaContainer");
    const count = document.getElementById("itemsCount");

    count.textContent = `Itens armazenados: (${itemsFiltrados.length}/12)`;
    container.innerHTML = "";

    if (
        selectedItem &&
        !itemsFiltrados.some(item => item.id === selectedItem.id)
    ) {
        selectedItem = null;
    }

    for (let index = 0; index < 12; index++) {
        const item = itemsFiltrados[index];

        if (!item) {
            const emptyArea = document.createElement("div");

            emptyArea.className = "itemArea";

            emptyArea.addEventListener("click", () => {
                selectedItem = null;

                document.querySelectorAll(".itemArea").forEach(btn => {
                    btn.classList.remove("selected");
                });

                mostrarAtributos();
            });

            container.appendChild(emptyArea);
            continue;
        }

        const button = document.createElement("button");

        button.type = "button";
        button.className = "itemArea";
        button.dataset.id = item.id;

        const img = document.createElement("img");

        img.src = item.imagem;
        img.alt = item.nome;

        button.appendChild(img);

        if (selectedItem && selectedItem.id === item.id) {
            button.classList.add("selected");
        }

        button.addEventListener("click", event => {
            event.preventDefault();
            event.stopPropagation();

            selectedItem = item;

            document.querySelectorAll(".itemArea").forEach(btn => {
                btn.classList.remove("selected");
            });

            button.classList.add("selected");

            mostrarAtributos();
        });

        container.appendChild(button);
    }

    mostrarAtributos();
}

function mostrarAtributos() {
    const attributes = document.getElementById("itemAttributes");

    attributes.innerHTML = "";

    if (!selectedItem) {
        return;
    }

    const item = selectedItem;

    const div = document.createElement("div");

    div.className = "itemAttributes";

    div.innerHTML = `
        <img src="${item.imagem}" class="itemImage" alt="${item.nome}">

        <div class="itemInfo">

            <div class="attributeGroup">
                <span class="label">nome do equipamento</span>
                <span class="value">${item.nome}</span>
            </div>

            <div class="attributeGroup">
                <span class="label">tipo</span>
                <span class="value">${item.tipo}</span>
            </div>

            <div class="attributeGroup">
                <span class="label">método de criação</span>
                <span class="value">
                    ${item.metodo_utilizado == 0 ? "Automático" : "Manual"}
                </span>
            </div>

            <div class="attributeGroup">
                <span class="label">atributos</span>
                <span class="value">${item.qualidade}</span>
                <span class="value">${item.peso} kg</span>
            </div>

        </div>

        <button type="button" class="discardButton">
            Descartar
        </button>
    `;

    const deleteButton = div.querySelector(".discardButton");

    deleteButton.addEventListener("click", event => {
        event.preventDefault();
        event.stopPropagation();

        descartarItem(item.id);
    });

    attributes.appendChild(div);
}

const menuButtons = document.querySelectorAll(".menuOption");

menuButtons.forEach(button => {
    button.type = "button";

    button.addEventListener("click", event => {
        event.preventDefault();

        filtro = button.dataset.filter;

        menuButtons.forEach(btn => {
            btn.classList.remove("buttonPressed");
        });

        button.classList.add("buttonPressed");

        atualizarItens();
    });
});

const searchBar = document.getElementById("searchBar");

searchBar.addEventListener("input", event => {
    busca = event.target.value;
    atualizarItens();
});

carregarItens();