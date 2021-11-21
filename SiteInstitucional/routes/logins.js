const router = require("express").Router();
const db = require('../config/connectDatabase');
const Usuario = require("../models/Usuario");

var sessao = [];

router.post('/logar', (req, res) => {

    console.log("usuário logando...");

    const user = {
        login: req.body.login,
        senha: req.body.senha,
        tipo: req.body.tipoUsuario
    };

    console.log(user);

    var sql;
    switch(user.tipo) {
        case '0':
            sql = `select * from tb_administrador where login_admin='${user.login}' and senha_admin='${user.senha}'`;
            break;
        case '1':
            sql = `select * from tb_usuario where login='${user.login}' and senha='${user.senha}' and is_gestor='1'`;
            break;
        case '2':
            sql = `select * from tb_usuario where login='${user.login}' and senha='${user.senha}' and is_gestor='0'`;
            break;
        default:
            break;
    }

    console.log(`query: ${sql}`)

    db.sequelizeConnection.query(sql, {
        model: Usuario
    }).then(resultado => {

        console.log(`Encontrados: ${resultado.length}`);
        
        if (resultado.length == 1) {
            
            console.log(`Usuario: ${resultado[0]}`);
            
            sessao.push(resultado[0].dataValues.login);
            
            res.json( resultado[0] );

        } else {
            res.status(403).send('Login e/ou senha inválido(s)');
        }
        
    }).catch(erro => {
		console.error(erro);
		res.status(500).send(erro.message);
  	});
       
    
});

router.get('/sessao/:login', function(req, res, next) {
	
    let login = req.params.login;
	
    console.log(`Verificando se o usuário ${login} tem sessão`);
	
	let tem_sessao = false;
	for (let u=0; u < sessao.length; u++) {
		if (sessao[u] == login) {
			tem_sessao = true;
			break;
		}
	}

	if (tem_sessao) {
		let mensagem = `Usuário ${login} possui sessão ativa!`;
		console.log(mensagem);
		res.sendStatus(200).send(mensagem);
	} else {
		res.sendStatus(403);
	}
	
});

router.get('/sair/:login', function(req, res, next) {
	
    let login = req.params.login;
	
    console.log(`Finalizando a sessão do usuário ${login}`);
	
    let nova_sessoes = []
	for (let u=0; u<sessao.length; u++) {
		if (sessao[u] != login) {
			nova_sessoes.push(sessao[u]);
		}
	}

	sessao = nova_sessoes;
	res.send(`Sessão do usuário ${login} finalizada com sucesso!`);
});

module.exports = router;