const router = require("express").Router();
const db = require('../config/connectDatabase');
const Usuario = require("../models/Usuario");
const Administrador = require("../models/Administrador");

router.post('/addUsuario', (req, res) => {

    Usuario.create({
        id_trello: req.body.id_trello,
        nome: req.body.nome,
        foto: req.body.foto,
        login: req.body.login,
        senha: req.body.senha,
        is_gestor: req.body.is_gestor,
        fk_supervisor: req.body.fk_supervisor,
		fk_squad: req.body.fk_squad,
		fk_empresa: req.body.fk_empresa
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

router.get('/usuarioIdTrello/:id_trello', function(req, res, next) {
	console.log('Usuário com Id trello');
	
    Usuario.findAndCountAll({ 
        where: {
            id_trello: req.params.id_trello
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

router.get('/adminEmpresa/:fk_empresa', function(req, res, next) {
	console.log('Recuperando admin da empresa');
	
    Administrador.findAndCountAll({ 
        where: {
            fk_empresa: req.params.fk_empresa
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

router.get('/getUsuarioById/:id', function (req, res, next) {

    if(req.params.id == null) return;

    Usuario.findByPk(req.params.id).then( resultado => {
        console.log("Usuario: "+resultado.nome);
        res.json(resultado);
    }).catch(erro => {
        console.error(erro);
        res.status(500).send(erro.message);
    });
    
});

router.put('/updateUsuario', function (req, res, next) {

    if(req.body == null) return;

    Usuario.update(
        { 
            nome: req.body.nomeUsuario,
            foto: req.body.fotoUsuario,
            senha: req.body.senhaUsuario, 
        },
        { 
            where: { id: req.body.idUsuario } 
        }
    ).then( resultado => {
        console.log("Usuario: "+resultado.nome+" atualizado!");
        res.json(resultado);
    }).catch(erro => {
        console.error(erro);
        res.status(500).send(erro.message);
    });
    
});

module.exports = router;