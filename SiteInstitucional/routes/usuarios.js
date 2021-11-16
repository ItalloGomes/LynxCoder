const router = require("express").Router();
const db = require('../config/connectDatabase');
const Usuario = require("../models/Usuario");

router.post('/addUsuario', (req, res) => {

    Usuario.create({
        id_trello: req.body.idTrello,
        nome: req.body.nomeUsuario,
        foto: req.body.fotoUsuario,
        login: req.body.login,
        senha: req.body.senha,
        is_gestor: req.body.isGestor,
        fk_supervisor: req.body.idSupervisor,
		fk_squad: req.body.idSquad,
		fk_empresa: req.body.idEmpresa,
    }).then(resultado => {
        
        console.log(`Registro criado: ${resultado}`)

        res.sendStatus(200);
        
    }).catch(erro => {
        console.error(erro);
        res.status(500).send(erro.message);
    });

});

router.delete('/removeUsuario/:idUsuario', (req, res) => {

    Usuario.destroy({
        where: {
            id: req.params.idUsuario
         }
    }).then(resultado => {
        
        console.log(`Registro deletado: ${resultado}`)

        res.sendStatus(200);
        
    }).catch(erro => {
        console.error(erro);
        res.status(500).send(erro.message);
    });

});

router.get('/', function(req, res, next) {
	console.log('Todos os usuários');
	
    Usuario.findAndCountAll().then(resultado => {
		
        console.log(`${resultado.count} registros`);

		res.json(resultado.rows);

	}).catch(erro => {
		console.error(erro);
		res.status(500).send(erro.message);
  	});
});

router.get('/usuariosEmpresa/:idEmpresa', function(req, res, next) {
	console.log('Todos os usuários de uma empresa');
	
    Usuario.findAndCountAll({ 
        where: {
            fk_empresa: req.params.idEmpresa
        }
    }).then(resultado => {
		
        console.log(`${resultado.count} registros`);

		res.json(resultado.rows);

	}).catch(erro => {
		console.error(erro);
		res.status(500).send(erro.message);
  	});
});

router.get('/usuariosSquad/:idSquad', function(req, res, next) {
	console.log('Todos os usuários de uma squad');
	
    Usuario.findAndCountAll({ 
        where: {
            fk_squad: req.params.idSquad
        }
    }).then(resultado => {
		
        console.log(`${resultado.count} registros`);

		res.json(resultado.rows);

	}).catch(erro => {
		console.error(erro);
		res.status(500).send(erro.message);
  	});
});

router.get('/UsuariosGestores', function(req, res, next) {
	console.log('Todos os Gestores');
	
    Usuario.findAndCountAll({ 
        where: {
            is_gestor: 1
        }
    }).then(resultado => {
		
        console.log(`${resultado.count} registros`);

		res.json(resultado.rows);

	}).catch(erro => {
		console.error(erro);
		res.status(500).send(erro.message);
  	});
});