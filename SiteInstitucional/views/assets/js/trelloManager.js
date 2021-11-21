var key;
var token;
var id_admin;
var fk_empresa;

window.onload = function () {
    console.log("Buscando dados do admin da empresa")
    fk_empresa = 1;
    // fk_empresa = sessionStorage.getItem("user").fk_empresa;

    fetch(`usuarios/adminEmpresa/${fk_empresa}`, {
        method: "GET"
    }).then(function (resultado) {
        if (resultado.ok) {
            resultado.json().then(admins => {
                if (admins.length > 0) {
                    key = admins[0].key_trello;
                    token = admins[0].token_trello;
                    id_admin = admins[0].id_trello;
                    console.log("Dados do admin recuperados")
                    init();
                }
            });
        } else {
            console.log("Erro ao recuperar dados do admin");
        }
    });
}