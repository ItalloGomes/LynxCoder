function closeLoading() {
    document.getElementById("loading").classList.add("ocult");
}

function openLoading() {
    document.getElementById("loading").classList.remove("ocult");
}

let gestor;
let squad;
let usuarios_squad;
let sprintAtual;
let ultimasSprints;

function init() {
    get_gestor();
};

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

function get_usuarios_squad() {
    fetch(`/usuarios/usuariosSquad/${squad.id}`, {
        method: "GET"
    }).then(function (resultado) {
        if (resultado.ok) {
            resultado.json().then(usuarios => {
                usuarios_squad = usuarios;
                get_sprints();
            })
        } else {
            console.log("Erro ao recuperar usuários da squad");
        }
    });
}

function get_sprints() {
    fetch(`sprints/sprintsSquad/${gestor.fk_squad}`, {
        method: "GET"
    }).then(function (resultado) {
        if (resultado.ok) {
            resultado.json().then(sprints => {
                if (sprints.length == 0) {
                    sprint_data.style.display = `none`;
                    no_sprint.style.display = `flex`;
                    sprint_header.innerHTML = `<h1>Sprint Atual</h1>
                        <button onclick="iniciar_sprint()">+</button>`
                    squad_canvas.innerHTML += `Ainda não temos dados de sprints da sua squad!`
                } else {
                    sprintAtual = sprints[0];
                    ultimasSprints = sprints;
                    if (sprintAtual.ativa == 1) {
                        sprint_data.style.display = `flex`;
                        no_sprint.style.display = `none`;
                        sprint_header.innerHTML = `<h1>${sprints[0].descricao}</h1>
                        <button onclick="fechar_sprint()">x</button>`
                    } else {
                        sprint_data.style.display = `none`;
                        no_sprint.style.display = `flex`;
                        sprint_header.innerHTML = `<h1>Sprint Atual</h1>
                        <button onclick="iniciar_sprint()">+</button>`
                    }
                    atualizar_progresso_tarefas();
                    if (sprints.length > 1 || sprints[0].ativa == 0) {
                        buscar_feedbacks(sprintAtual.ativa == 1 ? sprints[1] : sprints[0]);
                    }
                }
            });
        } else {
            console.log("Erro ao recuperar sprints");
        }
    });
}

function atualizar_progresso_tarefas() {
    fetch(`https://trello.com/1/lists/${sprintAtual.id_trello}/cards?key=${key}&token=${token}`, {
        method: "GET"
    }).then(function (resultado) {
        if (resultado.ok) {
            resultado.json().then(tarefasTrello => {

                fetch(`tarefas/allOfSprint/${sprintAtual.id}`, {
                    method: "GET"
                }).then(function (resultado) {
                    if (resultado.ok) {
                        resultado.json().then(tarefasBanco => {

                            tarefasTrello.forEach(tarefaTrello => {
                                let concluidoTrello = ((tarefaTrello.badges.checkItemsChecked / tarefaTrello.badges.checkItems) * 100).toFixed(2);
                                tarefasBanco.forEach(tarefaBanco => {
                                    if (tarefaTrello.id == tarefaBanco.id_trello) {
                                        if (concluidoTrello != tarefaBanco.total_concluido) {
                                            tarefaBanco.total_concluido = concluidoTrello;
                                            fetch(`tarefas/atualizarProgresso/${concluidoTrello}/${tarefaBanco.id_tarefa}`, {
                                                method: "POST"
                                            }).then(function (resultado) {
                                                if (resultado.ok) {
                                                    console.log("Tarefa atualizada com sucesso!")
                                                } else {
                                                    console.log("Erro ao atualizar tarefa");
                                                }
                                            });
                                        }
                                    }
                                })
                            });

                            gerar_charts_sprint(tarefasBanco);
                            gerar_chart_squad();

                        });
                    } else {
                        console.log("Erro ao recuperar tarefas");
                    }
                });
            });
        } else {
            console.log("Erro ao recuperar sprint");
        }
    });
}

function gerar_charts_sprint(tarefas) {
    let total_tarefas = 0;
    let entregues = 0;

    let total_pontos = 0;
    let pontuacao = 0;

    let participantes = 0;

    let ultimo = 0;

    tarefas.forEach(tarefa => {
        total_tarefas++;
        total_pontos += tarefa.pontos;
        if (tarefa.total_concluido >= 100.00) {
            entregues++;
            pontuacao += tarefa.pontos;
        }
        if (tarefa.fk_usuario != ultimo) {
            participantes++;
        }
        ultimo = tarefa.fk_usuario;
    })

    var ctx_entregas = document.getElementById('ctxEntregas');

    var doughnut_entregas = new Chart(ctx_entregas, {
        type: 'doughnut',
        data: {
            labels: ['Entregue', 'Pendente'],
            datasets: [{
                data: [entregues, total_tarefas - entregues],
                backgroundColor: ['#43318f', '#d2befa'],
                borderWidth: 0
            }]
        },
        options: {
            elements: {
                center: {
                    text: entregues < 10 ? '0' + entregues : entregues,
                    color: '#43318f',
                    sidePadding: 50,
                    minFontSize: 18,
                }
            },
            cutoutPercentage: 85,
            legend: {
                display: false,
            },
        }
    });

    var ctx_pontuacao = document.getElementById('ctxPontuacao');

    var doughnut_pontuacao = new Chart(ctx_pontuacao, {
        type: 'doughnut',
        data: {
            labels: ['Pontuado', 'Pendente'],
            datasets: [{
                data: [pontuacao, total_pontos - pontuacao],
                backgroundColor: ['#43318f', '#d2befa'],
                borderWidth: 0
            }]
        },
        options: {
            elements: {
                center: {
                    text: pontuacao < 10 ? '0' + pontuacao : pontuacao,
                    color: '#43318f',
                    sidePadding: 50,
                    minFontSize: 18,
                }
            },
            cutoutPercentage: 85,
            legend: {
                display: false,
            },
        }
    });

    var ctx_participantes = document.getElementById('ctxParticipantes');

    var doughnut_participantes = new Chart(ctx_participantes, {
        type: 'doughnut',
        data: {
            labels: ['Participantes', 'Não Participantes'],
            datasets: [{
                data: [participantes, usuarios_squad.length - participantes],
                backgroundColor: ['#43318f', '#d2befa'],
                borderWidth: 0
            }]
        },
        options: {
            elements: {
                center: {
                    text: participantes < 10 ? '0' + participantes : participantes,
                    color: '#43318f',
                    sidePadding: 50,
                    minFontSize: 18,
                }
            },
            cutoutPercentage: 85,
            legend: {
                display: false,
            },
        }
    });
}

function gerar_chart_squad() {
    let datasets = [];
    let count = 0;
    ultimasSprints.forEach(sprint => {
        fetch(`tarefas/allOfSprint/${sprint.id}`, {
            method: "GET"
        }).then(function (resultado) {
            if (resultado.ok) {
                resultado.json().then(tarefasSprint => {
                    let entregues = 0;
                    let pontuacao = 0;
                    let participantes = 0;

                    let ultimo = 0;

                    tarefasSprint.forEach(tarefa => {
                        if (tarefa.total_concluido >= 100.00) {
                            entregues++;
                            pontuacao += tarefa.pontos;
                        }
                        if (tarefa.fk_usuario != ultimo) {
                            participantes++;
                        }
                        ultimo = tarefa.fk_usuario;
                    })

                    datasets.unshift({
                        data: [entregues, pontuacao, participantes],
                        label: sprint.descricao,
                        backgroundColor: "#43318f"
                    });

                    count++;
                });
            } else {
                console.log("Erro ao recuperar tarefas");
            }
        });
    });

    let getInterval = setInterval(() => {
        if (count == ultimasSprints.length) {
            clearInterval(getInterval)
            var ctx_squad = document.getElementById('ctxSquad');

            var bar_squad = new Chart(ctx_squad, {
                type: 'bar',
                data: {
                    labels: ["Entregas", "Pontuação", "Participantes"],
                    datasets: datasets
                },
                options: {
                    scales: {
                        yAxes: [{
                            display: true,
                            ticks: {
                                beginAtZero: true,
                            }
                        }]
                    },
                    title: {
                        display: true,
                        text: 'Desempenho por sprint'
                    }
                }
            });
            closeLoading();
        }
    }, 1000);
}

function fechar_sprint() {
    openLoading();
    fetch(`sprints/fecharSprint/${sprintAtual.id}`, {
        method: "POST"
    }).then(function (resultado) {
        if (resultado.ok) {
            console.log("Sprint fechada com sucesso!")
            gerar_feedbacks();
        } else {
            console.log("Erro ao fechar sprint");
        }
    });
}

function gerar_feedbacks() {
    let count = 0;
    usuarios_squad.forEach(usuario => {
        var data = JSON.stringify({
            mensagem: null,
            aproveitamento: null,
            facilidade: null,
            fk_usuario: usuario.id,
            fk_sprint: sprintAtual.id
        });

        fetch(`/feedbacks/addFeedback`, {
            method: "POST",
            headers: new Headers({ 'content-type': 'application/json' }),
            body: data
        }).then(response => {
            if (response.ok) {
                count++;
                console.log("Feedback gerado: " + usuario.nome)
            } else {
                console.log('Erro ao criar feedback!');
            }
        });
    });
    let interval = setInterval(() => {
        if (count == usuarios_squad.length) {
            clearInterval(interval);
            window.location.reload();
        }
    }, 1000);
}

function buscar_feedbacks(sprint) {
    fetch(`feedbacks/allFromSprint/${sprint.id}`, {
        method: "GET"
    }).then(function (resultado) {
        if (resultado.ok) {
            resultado.json().then(feedbacks => {
                feedbacks[0].forEach(feedback => {
                    if (feedback.aproveitamento_feedback != null) {
                        span_nofeedback.style.display = `none`;
                        div_feedbacks.innerHTML += `
                        <div class="card_atvd">
                            <h2><b>${feedback.nome_usuario}</b></h2>

                            <h3>Aproveitamento: <b>${feedback.aproveitamento_feedback}</b></h3>
                            <h3>Facilidade: <b>${feedback.facilidade_feedback}</b></h3>
                            <h3><b>></b> ${feedback.mensagem_feedback}</h3>
                        </div>`
                    }
                });
            })
        } else {
            console.log("Erro ao recuperar feedbacks da última sprint");
        }
    });
}

function iniciar_sprint() {
    openLoading();
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
                                fetch(`sprints/sprintsSquad/${gestor.fk_squad}`, {
                                    method: "GET"
                                }).then(function (resultado) {
                                    if (resultado.ok) {
                                        resultado.json().then(sprintsBanco => {
                                            sprintAtual = sprintsBanco[0];
                                            cadastrar_tarefas_sprint(sprint.id);
                                        });
                                    } else {
                                        console.log("Erro ao recuperar sprints");
                                    }
                                });
                                console.log("Sprint cadastrada: " + sprint.name)
                            } else {
                                console.log('Erro ao cadastrar sprint!');
                            }
                        });
                    }
                })
            });
        } else {
            console.log("Erro ao recuperar sprint");
        }
    });
}

function cadastrar_tarefas_sprint(id_sprint_trello) {
    fetch(`https://trello.com/1/lists/${id_sprint_trello}/cards?key=${key}&token=${token}`, {
        method: "GET"
    }).then(function (resultado) {
        if (resultado.ok) {
            resultado.json().then(tarefas => {
                let count = 0;
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
                                                fk_sprint: sprintAtual.id
                                            });

                                            fetch(`/tarefas/addTarefa`, {
                                                method: "POST",
                                                headers: new Headers({ 'content-type': 'application/json' }),
                                                body: data
                                            }).then(response => {
                                                if (response.ok) {
                                                    count++;
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

                let interval = setInterval(() => {
                    if (count == tarefas.length) {
                        clearInterval(interval);
                        window.location.reload();
                    }
                }, 1000);
            });
        } else {
            console.log("Erro ao recuperar tarefas");
        }
    });
}