

async function initComponents(user) {

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

    getTarefasPendentes(user.id_usuario);

    getMaquinaUsuario(user.id_usuario);

};

function getTarefasPendentes(idUser) {
    fetch(`/tarefas/pendentes/${idUser}`, {
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