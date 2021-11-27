var userSession = JSON.parse(sessionStorage.getItem('userData'));

function redirect_login() {
    window.location.href = 'http://localhost:8081/index.html';
}

function check_authentication() {

<<<<<<< HEAD
    if (userSession.login == undefined || userSession.login_admin == undefined)  {
        redirect_login();
=======
    if (userSession.tipoLogin != 0) {
        if (userSession.login == undefined) {
            redirect_login();
        }
    } else if (userSession.tipoLogin == 0) {
        if (userSession.login_admin == undefined) {
            redirect_login();
        }
>>>>>>> refs/remotes/origin/main
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
    fetch(`/login/sessao/${userSession.login}`, { cache: 'no-store' })
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
    fetch(`/login/sair/${userSession.login}`, { cache: 'no-store' });
}