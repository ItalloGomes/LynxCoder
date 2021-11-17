window.onload = function() {
    if (sprint_ativa()) {
        // desabilitar botão de iniciar sprint
    } else {
        // desabilitar botão de fechar sprint
    };
};

function sprint_ativa() {
    fetch('/sprints/status_sprint', {
        method: "GET"
    }).then(response => {
       if (response[0].ativa) {
           return true;
       } else {
           return false;
       }
    });
}

function iniciar_sprint() {
    
}

function fechar_sprint() {
    
}
