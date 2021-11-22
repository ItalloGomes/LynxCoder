let squads_trello = [];
let squads_banco = [];
let squads_dif = [];
let usuarios_trello = [];
let usuarios_banco = [];
let usuarios_dif = [];
let lista_tudo = [];

let callbackSquadsTrello = false;
let callbackSquadsBanco = false;
let callbackUsuariosTrello = false;
let callbackusuariosBanco = false;
let callbackCadastroSquads = false;

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
                cadastrar_squads();
                let insertCallbackInterval = setInterval(function () {
                    if (callbackCadastroSquads) {
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
            console.log("Erro ao buscar squads do banco");
        }
    });
}

function sincronizar_usuarios() {
    buscar_usuarios_trello();
    buscar_usuarios_banco();

    let getCallbackInterval = setInterval(function () {
        if (callbackUsuariosTrello && callbackusuariosBanco) {
            clearInterval(getCallbackInterval);
            exibir_informacoes_acesso();
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
            if (usuarios_dif.length > 0) {
                cadastrar_usuarios();
            }
        }
    }, 1000);
}

function buscar_usuarios_trello() {
    squads_trello.forEach(squadTrello => {
        let callbackFkSquad = false;
        callbackUsuariosTrello = false;

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
                                callbackUsuariosTrello = true;
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
    fk_empresa = fk_empresa;

    fetch(`usuarios/usuariosEmpresa/${fk_empresa}`, {
        method: "GET"
    }).then(function (resultado) {
        if (resultado.ok) {
            resultado.json().then(usuariosBanco => {
                usuarios_banco = usuariosBanco;
                callbackusuariosBanco = true;
            });
        } else {
            console.log("Erro ao recuperar usuários do banco");
        }
    });
}

function cadastrar_squads() {
    console.log(squads_dif);
    fk_empresa = fk_empresa;

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
                callbackCadastroSquads = true;
                console.log("Squad cadastrada: " + squadTrello.name)
            } else {
                console.log('Erro ao cadastrar squad!');
            }
        });
    });
}

function cadastrar_usuarios() {
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
            <td>
                <select id="select-role-${element.usuario.id_trello}" class="select-role">
                    <option value="0">Role: Colaborador</option>
                    <option value="1">Role: Gerente</option>
                </select>
            </td>
        </tr>`
        
        document.getElementById(`select-role-${element.usuario.id_trello}`).value = element.usuario.is_gestor;
        if (element.usuario.is_gestor == 1) {
            gestores++;
        } 
    })

    b_gestores.innerHTML = gestores;
    b_colaboradores.innerHTML = usuarios_trello.length - gestores;

    b_squads.innerHTML = squads_trello.length;

    fetch(`sprints/`, {
        method: "GET"
    }).then(function (resultado) {
        if (resultado.ok) {
            resultado.json().then(sprints => {
                b_sprints.innerHTML = sprints.length;
                closeLoading();
            });
        } else {
            console.log("Erro ao recuperar quantidade de sprints");
        }
    });
}