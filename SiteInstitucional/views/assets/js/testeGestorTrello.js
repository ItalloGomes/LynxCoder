let gestor;
let squad;
let usuarios_squad;

let sprint_ativa;
let id_sprint;

function init() {
    get_gestor();
    is_sprint_ativa();
};

function is_sprint_ativa() {
    fetch(`sprints/isSprintAtiva/${gestor.fk_squad}`, {
        method: "GET"
    }).then(function (resultado) {
        if (resultado.ok) {
            resultado.json().then(sprints => {
                if (sprints.length > 0) {
                    sprint_ativa = sprints[0].ativa == 1 ? true : false;
                    id_sprint = sprints[0].id;
                } else {
                    sprint_ativa = false;
                }

                if (sprint_ativa) {
                    div_buttons.innerHTML = `<button onclick="fechar_sprint()">Fechar Sprint</button>`;
                } else {
                    div_buttons.innerHTML = `<button onclick="iniciar_sprint()">Iniciar Sprint</button>`;
                }
            });
        } else {
            console.log("Erro ao recuperar status da sprint");
        }
    });
}

function get_gestor() {
    gestor = JSON.parse(sessionStorage.getItem("userData"));
    get_squad();
}

function get_squad() {
    fetch(`squads/getSquadById/${gestor.fk_squad}`, {
        method: "GET"
    }).then(function (resultado) {
        if (resultado.ok) {
            resultado.json().then(squadGestor => {
                if (squadGestor != null) {
                    squad = squadGestor;
                    get_usuarios_squad();
                    console.log("Squad recuperada!")
                }
            });
        } else {
            console.log("Erro ao recuperar squad do gestor");
        }
    });
}

function iniciar_sprint() {
    if (squad != null) {
        fetch(`https://trello.com/1/boards/${squad.id_trello}/lists?key=${key}&token=${token}`, {
            method: "GET"
        }).then(function (resultado) {
            if (resultado.ok) {
                resultado.json().then(sprints => {
                    sprints.forEach(sprint => {
                        if (sprint.name.indexOf("Sprint") > -1) {
                            var data = JSON.stringify({
                                id_trello: sprint.id,
                                descricao: sprint.name,
                                ativa: true,
                                fk_squad: squad.id
                            });

                            fetch(`/sprints/addSprint`, {
                                method: "POST",
                                headers: new Headers({ 'content-type': 'application/json' }),
                                body: data
                            }).then(response => {
                                if (response.ok) {
                                    is_sprint_ativa();
                                    buscar_tarefas_sprint(sprint.id);
                                    console.log("Sprint cadastrada: " + sprint.name)
                                } else {
                                    console.log('Erro ao cadastrar sprint!');
                                }
                            });
                        }
                    });
                });
            } else {
                console.log("Erro ao recuperar sprint");
            }
        });
    }
}

function fechar_sprint() {
    fetch(`sprints/fecharSprint/${id_sprint}`, {
        method: "POST"
    }).then(function (resultado) {
        if (resultado.ok) {
            console.log("Sprint fechada com sucesso!")
            is_sprint_ativa();
            gerar_feedbacks();
        } else {
            console.log("Erro ao fechar sprint");
        }
    });
}

function get_usuarios_squad() {
    fetch(`usuarios/usuariosSquad/${squad.id}`, {
        method: "GET"
    }).then(function (resultado) {
        if (resultado.ok) {
            resultado.json().then(usuarios => {
                usuarios_squad = usuarios;
            })
        } else {
            console.log("Erro ao recuperar usuários da squad");
        }
    });
}

function gerar_feedbacks() {
    usuarios_squad.forEach(usuario => {
        var data = JSON.stringify({
            mensagem: null,
            aproveitamento: null,
            facilidade: null,
            fk_usuario: usuario.id,
            fk_sprint: id_sprint
        });

        fetch(`/feedbacks/addFeedback`, {
            method: "POST",
            headers: new Headers({ 'content-type': 'application/json' }),
            body: data
        }).then(response => {
            if (response.ok) {
                is_sprint_ativa();
                console.log("Feedback gerado: " + usuario.nome)
            } else {
                console.log('Erro ao criar feedback!');
            }
        });

    });
}

function buscar_tarefas_sprint(id_sprint_trello) {
    fetch(`https://trello.com/1/lists/${id_sprint_trello}/cards?key=${key}&token=${token}`, {
        method: "GET"
    }).then(function (resultado) {
        if (resultado.ok) {
            resultado.json().then(tarefas => {
                tarefas.forEach(tarefa => {

                    fetch(`https://trello.com/1/cards/${tarefa.id}/pluginData?key=${key}&token=${token}`, {
                        method: "GET"
                    }).then(function (resultado) {
                        if (resultado.ok) {
                            resultado.json().then(plugins => {

                                fetch(`/usuarios/usuarioIdTrello/${tarefa.idMembers[0]}`, {
                                    method: "GET"
                                }).then(function (resultado) {
                                    if (resultado.ok) {
                                        resultado.json().then(usuarios => {
                                            var data = JSON.stringify({
                                                id_trello: tarefa.id,
                                                nome: tarefa.name,
                                                pontos: JSON.parse(plugins[0].value).points,
                                                total_concluido: (tarefa.badges.checkItemsChecked / tarefa.badges.checkItems) * 100,
                                                prazo: tarefa.due,
                                                fk_usuario: usuarios[0].id,
                                                fk_sprint: id_sprint
                                            });
                                            console.log(data)
                        
                                            fetch(`/tarefas/addTarefa`, {
                                                method: "POST",
                                                headers: new Headers({ 'content-type': 'application/json' }),
                                                body: data
                                            }).then(response => {
                                                if (response.ok) {
                                                    console.log("Tarefa cadastrada: " + tarefa.name)
                                                } else {
                                                    console.log('Erro ao cadastrar tarefa!');
                                                }
                                            });
                                        });
                                    } else {
                                        console.log("Erro ao recuperar usuário responsável");
                                    }
                                });
                            });
                        } else {
                            console.log("Erro ao recuperar plugins");
                        }
                    });

                });
            });
        } else {
            console.log("Erro ao recuperar sprint");
        }
    });
}