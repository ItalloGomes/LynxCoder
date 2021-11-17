var all_squads = [];

function closedForm () {
    boxModal.classList.add('ocult');
}

function openForm () {
    boxModal.classList.remove('ocult');
}

function cadastrar_squads() {
    fetch('/trello/cadastrar_squads', {
        method: "POST"
    }).then( response => {
        if (response.ok){

            response.json().then(squads => {

                squads.forEach(squad => {
                    add_squad(squad)
                });

            });

        } else {

            console.log('Erro ao cadastrar squads!');

            response.text().then(texto => {
                console.error(texto);
            });

        }

        finalizar_aguardar();
    });
}

function add_info_squad(squad) {
    fetch(`trello/listar_usuarios_squad/${squad}`, {
        method: "GET"
    }).then(response => {
        if (response.ok){

            response.json().then(usuarios => {
                all_squads.push({
                    idTrello: squad.id,
                    nome: squad.name,
                    desc: squad.desc,
                    membros: usuarios
                });
            });

        } else {
            console.log('Erro ao cadastrar squad!');
            response.text().then(texto => {
                console.error(texto);
            });
        }
    });
}

function cadastrar_usuarios() {
    all_squads.forEach(squad => {
        squad.usuarios.forEach(usuario => {
            fetch('/addUsuario', {
                method: "POST",
                body: usuario
            }).then(response => {
                if (response.ok) {
                    console.log("Usuário cadastrado: " + usuario.nome)
                } else {
                    console.log('Erro ao cadastrar usuário!');
                    console.error(texto);
                }
            });
        });
    });
}
