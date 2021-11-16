const router = require("express").Router();
const db = require('../config/connectDatabase');
const Usuario = require("../models/Usuario");

router.get('/', (req, res) => {
    res.render('login', { title: 'Express' });
});

router.post('/logar', (req, res) => {

    console.log("usuário logando...");

    const user = {
        login: req.body.login,
        senha: req.body.senha,
        tipo: req.body.tipoUsuario
    };

    var sql;
    switch(user.tipo) {
        case '0':
            sql = `select * from tb_administrador where login_admin='${user.login}' and senha_admin='${user.senha}'`;
            break;
        case '1':
            sql = `select * from tb_usuario where login='${user.login}' and senha='${user.senha}' and is_gestor=1`;
            break;
        case '2':
            sql = `select * from tb_usuario where login='${user.login}' and senha='${user.senha}' and is_gestor=0`;
            break;
        default:
            break;
    }

    db.sequelizeConnection.query(sql, {
        model: Usuario
    }).then(resultado => {

        console.log(`Encontrados: ${resultado.length}`);

        if (resultado.length == 1) {
            res.json(resultado[0]);
        } else {
            res.status(403).send('Login e/ou senha inválido(s)');
        }
        
    }).catch(erro => {
		console.error(erro);
		res.status(500).send(erro.message);
  	});
       
    
});

module.exports = router;