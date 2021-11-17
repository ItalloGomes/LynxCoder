const router = require("express").Router();
const db = require('../config/connectDatabase');
const Usuario = require("../models/Usuario");
// var fk_empresa = sessionStorage.getItem("user").fk_empresa;

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

router.get('/empresa_tem_usuarios', (req, res) => {

    console.log("Verificando se empresa tem usuários...");

    let sql = `select * from tb_usuario where fk_empresa = ${fk_empresa}`;
    console.log(sql);

    sequelize.query(sql, { type: sequelize.QueryTypes.SELECT })
        .then(resultado => {
            res.json(resultado);
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

module.exports = router;