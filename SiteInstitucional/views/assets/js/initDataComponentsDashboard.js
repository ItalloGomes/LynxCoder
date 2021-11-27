var sprintAtiva;

var usuarioId;
var grafico;

var backgroundColor = ['#322f5036', '#322f5036', '#322f5036', '#322f5036', '#62958D', '#7EBC89', '#C3706B', '#9E66A2'];
var borderColor = ['#605ca1', '#605ca1', '#605ca1', '#605ca1', '#23B59F', '#33C44B', '#B83E35', '#911A9A'];
                

async function initComponents(user, graficoBar) {

    usuarioId = user.id_usuario;
    grafico = graficoBar;

    // INIT COMPONENTES ------------------------------------------------------------------
    if(user.foto_usuario != null){
        document.getElementById('foto_perfil_usuario_log_out').src = user.foto_usuario;
    } else {
        document.getElementById('foto_perfil_usuario_log_out').src = "https://www.pinpng.com/pngs/m/226-2269397_gnar-bot-is-a-hd-png-download.png";
    }

    let nameSplit = user.nome_usuario.split(' ');

    // NOME E TIPO USUÁRIO
    document.getElementById('nome_usuario_log_out').innerHTML = `${nameSplit[0]} ${nameSplit[nameSplit.length-1]}`;
    
    document.getElementById('tipo_usuario_log_out').innerHTML = !user.is_gestor ? "Colaborador" : "Gestor";
    
    getMaquinaUsuario(user.id_usuario);
    
    getSprintAtiva(user.id_usuario);

};

function getSprintAtiva(idUser){
    fetch(`/sprints/sprintAtivaUsuario/${idUser}`, {
        method: 'GET'
    }).then(response => {

        if (response.ok) {

            response.json().then( sprint => {
              
                sprintAtiva = sprint[0].id_sprint;

                setAllTarefasChartUser();
                
            });

        }else{
            response.text().then(function (resposta) {
                console.log("Erro: " + resposta);
            });
        }

    });
};

function setAllTarefasChartUser() {
    fetch(`/tarefas/allOfSprint/${usuarioId}/${sprintAtiva}`, {
        method: 'GET'
    }).then(response => {

        if (response.ok) {

            response.json().then( tarefas => {
              
                let count = 1;
                tarefas.forEach( element => {

                    grafico.config.data.labels.push(`ATVD ${count}`);
                    grafico.config.data.datasets[0].data.push(element.total_concluido);
                    grafico.config.data.datasets[0].backgroundColor.push(backgroundColor[count]);
                    grafico.config.data.datasets[0].borderColor.push(borderColor[count]);

                    grafico.update();

                    count++;
                })

            });

            getTarefasPendentes(usuarioId, sprintAtiva);

        }else{
            response.text().then(function (resposta) {
                console.log("Erro: " + resposta);
            });
        }

    });
}

function getTarefasPendentes(idUser, sprintId) {
    fetch(`/tarefas/pendentes/${idUser}/${sprintId}`, {
        method: "GET"
    }).then( response => {
                    
        if (response.ok) {
            response.json().then( tarefas => {
                var count = 1;
                
                if(tarefas.length > 0) {
                    tarefas.forEach(element => {
    
                        let container = document.getElementById('atividadesPends');
    
                        let div = document.createElement('div');
                        div.className = 'card_atvd';
    
                        let h2 = document.createElement('h2');
    
                        let b = document.createElement('b');
                        b.textContent = `Nº${count}`;
    
                        let txt = document.createTextNode(` - ${element.nome_tarefa}`);
    
                        h2.appendChild(b);
                        h2.appendChild(txt);
    
                        let p = document.createElement('p');
                        p.textContent = `${element.total_concluido}%`;
    
                        div.appendChild(h2);
                        div.appendChild(p);
    
                        container.appendChild(div);
    
                    });
                } else {

                    let container = document.getElementById('atividadesPends');

                    let div = document.createElement('div');

                    let b = document.createElement('b');

                    let txt = document.createTextNode(`Sem Tarefas pendentes`);

                    b.appendChild(txt);

                    let center = document.createElement('center');

                    center.appendChild(b);

                    div.appendChild(center);

                    container.appendChild(div);
                
                }

            });
        } else {
            response.text().then(function (resposta) {
                console.log("Erro: " + resposta);
            });
        }
                
    });
}