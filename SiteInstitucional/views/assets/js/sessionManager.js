var userSession = JSON.parse(sessionStorage.getItem('userData'));

function redirect_login() {
    window.location.href = 'http://localhost:8081/index.html';
}

function check_authentication() {

    if (userSession.login == undefined)  {
        redirect_login();
    } else {
        session_validate();
    }
    
}

function logoff() {
    close_session();
    sessionStorage.clear();
    redirect_login();
}

function session_validate() {
    fetch(`/login/sessao/${userSession.login}`, {cache:'no-store'})
    .then(resposta => {
        if (resposta.ok) {
            resposta.text().then(texto => {
                console.log('Sessão :) ', texto);    
            });
        } else {
            console.error('Sessão :.(');
            logoff();
        } 
    });    
}

function close_session() {
    fetch(`/login/sair/${userSession.login}`, {cache:'no-store'});
}