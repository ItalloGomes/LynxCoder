const user = JSON.parse(sessionStorage.getItem('userData'));

class InitAppDataUser{

    constructor(user){
        
        this.user = user;
        this.initComponents();

        this.supervisor = JSON.parse(sessionStorage.getItem('userSuper'));
        this.squad = JSON.parse(sessionStorage.getItem('userSquad'));
        this.empresa = JSON.parse(sessionStorage.getItem('userEmpresa'));

    }

    initComponents() {
        getDataSupervisor(this.user.fk_supervisor);
        getDataSquad(this.user.fk_squad);
        getDataEmpresa(this.user.fk_empresa);
    }

    logComponentsReceived() {
        console.log(this.squad);
        console.log("--");
        console.log(this.empresa);
    }

}

function getDataSupervisor(id) {

    if(id == null) return null;
    
    fetch(`/usuarios/getUsuarioById/${id}`, {
        method: "GET"
    }).then( response => {

        if (response.ok) {

            response.json().then( supervisor => {
                
                // console.log(supervisor);

                let texto = JSON.stringify(supervisor);
                sessionStorage.setItem('userSuper', texto);

            });

        } else {
            response.text().then(function (resposta) {
                console.log("ERRO: " + resposta);
            });
        }
    }).catch( () => {
        console.log("erro supervisor");
        return null;
    });

}

function getDataSquad(id) {
    
    if(id == null) return null;

    fetch(`/squads/getSquadById/${id}`, {
        method: "GET"
    }).then( response => {

        if (response.ok) {

            response.json().then( squad => {
                
                // console.log(squad);
                
                let texto = JSON.stringify(squad);
                sessionStorage.setItem('userSquad', texto);

            });

        } else {
            response.text().then(function (resposta) {
                console.log("ERRO: " + resposta);
            });
        }
    }).catch( () => {
        console.log("erro squad");
        return null;
    });;

}

function getDataEmpresa(id) {
    
    if(id == null) return null;

    fetch(`/empresas/getEmpresaById/${id}`, {
        method: "GET"
    }).then( response => {

        if (response.ok) {

            response.json().then( empresa => {
                
                // console.log(empresa);

                let texto = JSON.stringify(empresa);
                sessionStorage.setItem('userEmpresa', texto);
            
            });

        } else {
            response.text().then(function (resposta) {
                console.log("ERRO: " + resposta);
            });
        }
    }).catch( () => {
        console.log("erro empresa");
        return null;
    });;

}

new InitAppDataUser(user);