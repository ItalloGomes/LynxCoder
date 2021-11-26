
    var cpu_canvas = document.getElementById('cpuCanvas');
    var disco_canvas = document.getElementById('discoCanvas');
    var memory_canvas = document.getElementById('memoriaCanvas');
    var test_bar = document.getElementById('testBar');
    var test_line = document.getElementById('testLine');
    
    var graficoCpu = null;
    var graficoDisco = null;
    var graficoMemoria = null;

    function init(){

        check_authentication();

        initChart();

        const userSession = JSON.parse(sessionStorage.getItem('userData'));

        initComponents(userSession);

    }

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

function getMaquinaUsuario(idUser) {
    fetch(`/maquinas/${idUser}`, {
        method: "GET"
    }).then( resultado => {
        if( resultado.ok ){

            resultado.json().then( maquina => {
   
                setInterval(() => {

                    fetch(`/maquinas/leiturasMaquina/${maquina.id_maquina}`, {
                        method: "GET"
                    }).then( resultado => {

                        if( resultado.ok ) {

                        resultado.json().then( leituraMaquina => {

                            graficoCpu.data.datasets[0].data[0] = leituraMaquina.porcentagem_uso_cpu;
                            graficoCpu.data.datasets[1].data[0] = (100-leituraMaquina.porcentagem_uso_cpu);

                            graficoDisco.data.datasets[0].data[0] = leituraMaquina.porcentagem_uso_disco;
                            graficoDisco.data.datasets[1].data[0] = (100-leituraMaquina.porcentagem_uso_disco);
                            
                            graficoMemoria.data.datasets[0].data[0] = leituraMaquina.porcentagem_uso_ram;
                            graficoMemoria.data.datasets[1].data[0] = (100-leituraMaquina.porcentagem_uso_ram);

                            graficoCpu.update();
                            graficoDisco.update();
                            graficoMemoria.update();

                            console.log('recuperado');
                        });

                        } else {

                            console.log("erro ao recuperar leituras de maquina");
                        
                        }

                    });

                }, 3000);
            
            });

        } else {

            console.log("erro ao recuperar maquina");

        }
    })
}

function initChart() {

    graficoCpu = new Chart(
        cpu_canvas, {
            type: 'doughnut',
            data: {
                labels: ['Usado', 'Livre'],
                datasets: [{
                    data: [0, 100],
                    backgroundColor: ['#43318f', '#d2befa'],
                    borderWidth: 0
                }]
            },
            options: {
                elements: {
                    center: {
                        text: '0%',
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
        }
    );

    graficoDisco = new Chart(
        disco_canvas, {
            type: 'doughnut',
            data: {
                labels: ['Usado', 'Livre'],
                datasets: [{
                    data: [0, 100],
                    backgroundColor: ['#43318f', '#d2befa'],
                    borderWidth: 0
                }]
            },
            options: {
                elements: {
                    center: {
                        text: '0%',
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
        }
    );

    graficoMemoria = new Chart(
        memory_canvas, {
            type: 'doughnut',
            data: {
                labels: ['Usado', 'Livre'],
                datasets: [{
                    data: [0, 100],
                    backgroundColor: ['#43318f', '#d2befa'],
                    borderWidth: 0
                }]
            },
            options: {
                elements: {
                    center: {
                        text: '0%',
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
        }
    );

    let graficoLine = new Chart(test_line, {
        type: 'line',
        data: {
            labels: ['ATVD1', 'ATVD2', 'ATVD3', 'ATVD4'],
            datasets: [ {
                data: [100, 0, 70, 70],
                backgroundColor: ['#322f5036', '#322f5036', '#322f5036', '#322f5036'],
                borderColor: ['#605ca1', '#605ca1', '#605ca1', '#605ca1'],
                borderWidth: 3
            }]
        },
        options: {
            title: {
                display: false
            },
            legend: {
                display: false
            }
        }
    });

    let graficoBar = new Chart(test_bar, {
        type: 'bar',
        data: {
            labels: ['ATVD1', 'ATVD2', 'ATVD3', 'ATVD4'],
            datasets: [ {
                data: [100, 0, 50, 80],
                backgroundColor: ['#52cc6852', '#322f5036', '#322f5036', '#322f5036'],
                borderColor: ['#52cc68', '#605ca1', '#605ca1', '#605ca1'],
                borderWidth: 2
            }]
        },
        options: {
            title: {
                display: false
            },
            legend: {
                display: false
            },
            scales: {
                xAxes: [{
                    barThickness : 50
                }]
            }
        }
    });

    Chart.defaults.global.defaultFontColor = "#303030";
    Chart.pluginService.register({
        beforeDraw: function (chart) {
            if (chart.config.options.elements.center) {
            var ctx = chart.chart.ctx;

            var centerConfig = chart.config.options.elements.center;
            var fontStyle = centerConfig.fontStyle || 'Arial';
            var txt = centerConfig.text;
            var color = centerConfig.color || '#000';
            var sidePadding = centerConfig.sidePadding || 20;
            var sidePaddingCalculated = (sidePadding/100) * (chart.innerRadius * 2)

            ctx.font = "30px " + fontStyle;

            var stringWidth = ctx.measureText(txt).width;
            var elementWidth = (chart.innerRadius * 2) - sidePaddingCalculated;

            var widthRatio = elementWidth / stringWidth;
            var newFontSize = Math.floor(30 * widthRatio);
            var elementHeight = (chart.innerRadius * 2);

            var fontSizeToUse = Math.min(newFontSize, elementHeight);

            ctx.textAlign = 'center';
            ctx.textBaseline = 'middle';
            var centerX = ((chart.chartArea.left + chart.chartArea.right) / 2);
            var centerY = ((chart.chartArea.top + chart.chartArea.bottom) / 2);
            ctx.font = fontSizeToUse+"px " + fontStyle;
            ctx.fillStyle = color;

            ctx.fillText(txt, centerX, centerY);
            }
        }
    });

}