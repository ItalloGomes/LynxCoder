let squads_trello = [];
let squads_banco = [];
let squads_dif = [];
let usuarios_trello = [];
let usuarios_banco = [];
let usuarios_dif = [];
let usuarios_sem_trello = [];
let lista_tudo = [];

function open_trello_modal() {
    boxTrelloModal.classList.remove('ocult');
}

function close_trello_modal() {
    boxTrelloModal.classList.add('ocult');
}

function open_loading() {
    loading.classList.remove('ocult');
}

function close_loading() {
    loading.classList.add('ocult');
}

function open_remove(id, nome) {
    document.getElementById("modalRemoveContb").innerHTML = 
    `<h1>Deseja remover o contribuidor ${nome} ?</h1>
    <button onclick="remove_user(${id})" id="btnRemoveUser">Remover</button>
    <button onclick="cancel_remove()">Cancelar</button>`
    document.getElementById("boxRemoveContb").classList.remove("ocult");
}

function cancel_remove() {
    document.getElementById("boxRemoveContb").classList.add("ocult");
}

function remove_user(id) {
    fetch(`usuarios/removeUsuario/${id}`, {
        method: "DELETE"
    }).then(function (resultado) {
        if (resultado.ok) {
            console.log("Usuário removido")
            window.location.reload();
        } else {
            console.log("Erro ao remover usuário");
        }
    });
}

function open_add_user() {
    document.getElementById("inputFkEmpresa").value = fk_empresa;

    squads_banco.forEach(squad => {
        document.getElementById("slFkSquad").innerHTML +=
            `<option value="${squad.id}">${squad.nome}</option>`
    });

    document.getElementById("boxUser").classList.remove("ocult");
}

function close_add_user() {
    document.getElementById("boxUser").classList.add("ocult");
}

function submitUser() {
    if (inputSenha.value != inputConfSenha.value) {
        boxAlert.classList.remove('ocult');
        boxAlert.classList.add('alert-active');
        titleAlert.innerHTML = "Alerta!";
        descAlert.innerHTML = "As senhas não correspondem";

        setTimeout(() => {
            boxAlert.classList.add('ocult');
            boxAlert.classList.remove('alert-active');
        }, 5000);
    } else if (inputNome.value.length < 3 || inputLogin.value.length < 3) {
        boxAlert.classList.remove('ocult');
        boxAlert.classList.add('alert-active');
        titleAlert.innerHTML = "Alerta!";
        descAlert.innerHTML = "Nome e login devem ter pelo menos 3 letras";

        setTimeout(() => {
            boxAlert.classList.add('ocult');
            boxAlert.classList.remove('alert-active');
        }, 5000);
    } else {
        var form = new URLSearchParams(new FormData(formUser));
        fetch("/usuarios/addUsuario", {
            method: "POST",
            body: form
        }).then(function (response) {

            if (response.ok) {
                console.log("Usuario registrado!");
                window.location.reload();
            } else {
                console.log("Erro ao registrar usuário")
            }
        });
    }
    return false;
}

let callbackSquadsTrello = false;
let callbackSquadsBanco = false;

let countUsuariosTrello = 0;
let callbackusuariosBanco = false;

let countCadastroSquads = 0;

function init() {
    sincronizar_squads();
};

function sincronizar_squads() {
    buscar_squads_trello();
    buscar_squads_banco();

    let getCallbackInterval = setInterval(function () {
        if (callbackSquadsTrello && callbackSquadsBanco) {
            clearInterval(getCallbackInterval);
            for (let i = 0; i < squads_trello.length; i++) {
                const squad_trello = squads_trello[i];
                let exists = false;
                for (let j = 0; j < squads_banco.length; j++) {
                    const squad_banco = squads_banco[j];
                    if (squad_trello.id == squad_banco.id_trello) {
                        exists = true;
                    }
                }
                if (!exists) {
                    squads_dif.push(squad_trello);
                }
            }
            if (squads_dif.length > 0) {
                spanTrelloMsg.innerHTML = `${squads_dif.length} 
                nova(s) squad(s)`
                btnCadastrarSquads.style.display = 'inline';
                btnCadastrarUsuarios.style.display = 'none';
                open_trello_modal();
                close_loading();
                let insertCallbackInterval = setInterval(function () {
                    if (countCadastroSquads == squads_dif.length) {
                        clearInterval(insertCallbackInterval);
                        sincronizar_usuarios();
                    }
                }, 1000);
            } else {
                sincronizar_usuarios();
            }
        }
    }, 1000);
}

function buscar_squads_trello() {
    callbackSquadsTrello = false;
    fetch(`https://trello.com/1/members/${id_admin}/boards?key=${key}&token=${token}`, {
        method: "GET"
    }).then(function (resultado) {
        if (resultado.ok) {
            resultado.json().then(squadsTrello => {
                squads_trello = squadsTrello;
                callbackSquadsTrello = true;
            });
        } else {
            console.log("Erro ao cadastrar boards da empresa");
        }
    });
}

function buscar_squads_banco() {
    callbackSquadsBanco = false;

    fetch(`squads/${fk_empresa}`, {
        method: "GET"
    }).then(function (resultado) {
        if (resultado.ok) {
            resultado.json().then(squadsBanco => {
                squads_banco = squadsBanco;
                callbackSquadsBanco = true;
            });
        } else {
            console.log("Erro ao recuperar squads do banco");
        }
    });
}

function sincronizar_usuarios() {
    buscar_usuarios_trello();
    buscar_usuarios_banco();

    let getCallbackInterval = setInterval(function () {
        if (countUsuariosTrello == usuarios_trello.length + squads_trello.length && callbackusuariosBanco) {
            clearInterval(getCallbackInterval);
            usuarios_trello = uniq = [...new Set(usuarios_trello)];

            for (let i = 0; i < usuarios_trello.length; i++) {
                const usuario_trello = usuarios_trello[i];
                let exists = false;
                for (let j = 0; j < usuarios_banco.length; j++) {
                    const usuario_banco = usuarios_banco[j];
                    if (usuario_trello.id_trello == usuario_banco.id_trello) {
                        exists = true;
                    }
                }
                if (!exists) {
                    usuarios_dif.push(usuario_trello);
                }
            }

            for (let i = 0; i < usuarios_banco.length; i++) {
                const usuario_banco = usuarios_banco[i];
                let exists = false;
                for (let j = 0; j < usuarios_trello.length; j++) {
                    const usuario_trello = usuarios_trello[j];
                    if (usuario_banco.id_trello == usuario_trello.id_trello) {
                        exists = true;
                    }
                }
                if (!exists) {
                    usuarios_sem_trello.push(usuario_banco);
                }
            }
            if (usuarios_dif.length > 0) {
                spanTrelloMsg.innerHTML = `${usuarios_dif.length} 
                novo(s) usuário(s)`
                btnCadastrarSquads.style.display = 'none';
                btnCadastrarUsuarios.style.display = 'inline';
                open_trello_modal();
                close_loading();
            } else {
                exibir_informacoes_acesso();
            }
        }
    }, 1000);
}

function buscar_usuarios_trello() {
    squads_trello.forEach(squadTrello => {
        let callbackFkSquad = false;
        let fk_squad;
        fetch(`squads/squadComIdTrello/${squadTrello.id}`, {
            method: "GET"
        }).then(function (resultado) {
            if (resultado.ok) {
                resultado.json().then(squads => {
                    fk_squad = squads[0].id
                    callbackFkSquad = true;
                });
            } else {
                console.log("Erro ao buscar fk_squad com id_trello");
            }
        });

        let getCallbackInterval = setInterval(function () {
            if (callbackFkSquad) {
                clearInterval(getCallbackInterval);
                countUsuariosTrello += squadTrello.memberships.length;
                squadTrello.memberships.forEach(membership => {
                    fetch(`https://trello.com/1/members/${membership.idMember}?key=${key}&token=${token}`, {
                        method: "GET"
                    }).then(function (resultado) {

                        if (resultado.ok) {

                            resultado.json().then(member => {
                                if (member.id != id_admin) {
                                    let data = {
                                        id_trello: member.id,
                                        nome: member.fullName,
                                        foto: member.avatarUrl,
                                        login: member.username,
                                        senha: member.username + (Math.random() * (900_000 - 100_000) + 100_000),
                                        is_gestor: membership.memberType == "admin" ? 1 : 0,
                                        fk_supervisor: null,
                                        fk_squad: fk_squad,
                                        fk_empresa: fk_empresa
                                    }

                                    usuarios_trello.push(data);
                                    lista_tudo.push({
                                        usuario: data,
                                        squad: squadTrello
                                    })
                                }
                            });
                        } else {
                            console.log("Erro ao recuperar usuários do trello!");
                            resultado.text().then(texto => {
                                console.error(texto);
                                res.status(500).send(texto);
                            });
                        }

                    });
                });
            }
        }, 1000);
    });
}

function buscar_usuarios_banco() {
    callbackusuariosBanco = false;
    fetch(`usuarios/usuariosEmpresa/${fk_empresa}`, {
        method: "GET"
    }).then(function (resultado) {
        if (resultado.ok) {
            resultado.json().then(usuariosBanco => {
                usuarios_banco = usuariosBanco[0];
                callbackusuariosBanco = true;
            });
        } else {
            console.log("Erro ao recuperar usuários do banco");
        }
    });
}

function cadastrar_squads() {
    close_trello_modal();
    open_loading();
    console.log(squads_dif);
    countCadastroSquads = 0;

    squads_dif.forEach(squadTrello => {
        callbackCadastroSquads = false;
        var data = JSON.stringify({
            id_trello: squadTrello.id,
            nome: squadTrello.name,
            descricao: squadTrello.desc,
            fk_empresa: fk_empresa
        });

        fetch(`/squads/addSquad`, {
            method: "POST",
            headers: new Headers({ 'content-type': 'application/json' }),
            body: data
        }).then(response => {
            if (response.ok) {
                countCadastroSquads++;
                console.log("Squad cadastrada: " + squadTrello.name)
            } else {
                console.log('Erro ao cadastrar squad!');
            }
        });
    });
}

function cadastrar_usuarios() {
    close_trello_modal();
    open_loading();
    exibir_informacoes_acesso();
    console.log(usuarios_dif);

    usuarios_dif.forEach(member => {
        var data = JSON.stringify({
            id_trello: member.id_trello,
            nome: member.nome,
            foto: member.foto,
            login: member.login,
            senha: member.senha,
            is_gestor: member.is_gestor,
            fk_supervisor: member.fk_supervisor,
            fk_squad: member.fk_squad,
            fk_empresa: member.fk_empresa
        });

        fetch(`/usuarios/addUsuario`, {
            method: "POST",
            headers: new Headers({ 'content-type': 'application/json' }),
            body: data
        }).then(response => {
            if (response.ok) {
                console.log("Usuário cadastrado: " + member.nome)
            } else {
                console.log('Erro ao cadastrar usuário!');
            }
        });
    });
};

function exibir_informacoes_acesso() {
    let gestores = 0;

    lista_tudo.forEach(element => {
        document.getElementById("table-body-userlist").innerHTML +=
            `<tr>
            <td>${element.squad.name}</td>
            <td>${element.usuario.nome}</td>
            <td>${element.usuario.is_gestor == 1 ? "Gestor" : "Colaborador"}</td>
            <td><img src="assets/img/trello-icon.png"></td>
        </tr>`

        if (element.usuario.is_gestor == 1) {
            gestores++;
        }
    })

    usuarios_sem_trello.forEach(element => {
        document.getElementById("table-body-userlist").innerHTML +=
            `<tr>
            <td>${element.nome_squad}</td>
            <td>${element.nome_usuario}</td>
            <td>${element.is_gestor == 1 ? "Gestor" : "Colaborador"}</td>
            <td onclick="open_remove(${element.id_usuario}, '${element.nome_usuario}')"><img src="assets/img/trash-can.png"></td>
        </tr>`

        if (element.is_gestor == 1) {
            gestores++;
        }
    })

    b_gestores.innerHTML = gestores;
    b_colaboradores.innerHTML = usuarios_trello.length + usuarios_sem_trello.length - gestores;

    b_squads.innerHTML = squads_trello.length;

    fetch(`sprints/`, {
        method: "GET"
    }).then(function (resultado) {
        if (resultado.ok) {
            resultado.json().then(sprints => {
                b_sprints.innerHTML = sprints.length;
                close_loading();
            });
        } else {
            console.log("Erro ao recuperar quantidade de sprints");
        }
    });
}