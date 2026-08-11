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
            selectedItem = items[0] || null;

            atualizarItens();

        })
        .catch(error => {

            console.error("Erro:", error);

        });

}

function descartarItem(id) {

    fetch(`http://localhost:8000/excluirItem.php?id=${id}`)
        .then(response => {

            if (!response.ok) {
                throw new Error("Erro ao excluir o item.");
            }

            return response;

        })
        .then(() => {

            carregarItens();

        })
        .catch(error => {

            console.error("Erro ao excluir:", error);

        });

}

function obterItensFiltrados() {

    return items
        .filter(item => {

            if (filtro === "Todos") {
                return true;
            }

            if (filtro === "Espadas") {
                return item.tipo === "Arma";
            }

            if (filtro === "Armaduras") {
                return item.tipo === "Armadura";
            }

            return true;

        })
        .filter(item => {

            return item.nome
                .toLowerCase()
                .includes(busca.toLowerCase());

        });

}

function atualizarItens() {

    const itemsFiltrados = obterItensFiltrados();

    const container =
        document.getElementById("itemsAreaContainer");

    const count =
        document.getElementById("itemsCount");

    count.textContent =
        `Itens armazenados: (${itemsFiltrados.length}/12)`;

    container.innerHTML = "";

    if (
        !selectedItem ||
        !itemsFiltrados.some(
            item => item.id === selectedItem.id
        )
    ) {

        selectedItem =
            itemsFiltrados[0] || null;

    }

    for (let index = 0; index < 12; index++) {

        const item = itemsFiltrados[index];

        const button =
            document.createElement("button");

        button.className = "itemArea";

        if (item) {

            button.dataset.id = item.id;

            const img =
                document.createElement("img");

            img.src = item.imagem;
            img.alt = item.nome;

            button.appendChild(img);

            button.addEventListener(
                "click",
                () => {

                    selectedItem = item;

                    mostrarAtributos();

                }
            );

        }

        container.appendChild(button);

    }

    mostrarAtributos();

}

function mostrarAtributos() {

    const attributes =
        document.getElementById("itemAttributes");

    attributes.innerHTML = "";

    if (!selectedItem) {
        return;
    }

    const item = selectedItem;

    const div =
        document.createElement("div");

    div.className =
        "itemAttributes";

    div.innerHTML = `

        <img
            src="${item.imagem}"
            class="itemImage"
            alt="${item.nome}"
        >

        <div class="itemInfo">

            <div class="attributeGroup">

                <span class="label">
                    nome do equipamento
                </span>

                <span class="value">
                    ${item.nome}
                </span>

            </div>

            <div class="attributeGroup">

                <span class="label">
                    tipo
                </span>

                <span class="value">
                    ${item.tipo}
                </span>

            </div>

            <div class="attributeGroup">

                <span class="label">
                    método de criação
                </span>

                <span class="value">
                    ${item.metodoUtilizado}
                </span>

            </div>

            <div class="attributeGroup">

                <span class="label">
                    atributos
                </span>

                <span class="value">
                    ${item.qualidade}
                </span>

                <span class="value">
                    ${item.peso} kg
                </span>

            </div>

        </div>

        <button class="discardButton">
            Descartar
        </button>
    `;

    const deleteButton =
        div.querySelector(".discardButton");

    deleteButton.addEventListener(
        "click",
        () => {

            descartarItem(item.id);

        }
    );

    attributes.appendChild(div);

}

const menuButtons =
    document.querySelectorAll(".menuOption");

menuButtons.forEach(button => {

    button.addEventListener(
        "click",
        () => {

            filtro =
                button.dataset.filter;

            menuButtons.forEach(btn => {

                btn.classList.remove(
                    "buttonPressed"
                );

            });

            button.classList.add(
                "buttonPressed"
            );

            atualizarItens();

        }
    );

});

const searchBar =
    document.getElementById("searchBar");

searchBar.addEventListener(
    "input",
    event => {

        busca =
            event.target.value;

        atualizarItens();

    }
);

carregarItens();
