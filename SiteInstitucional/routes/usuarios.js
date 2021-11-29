const router = require("express").Router();
const db = require('../config/connectDatabase');
const Usuario = require("../models/Usuario");
const Administrador = require("../models/Administrador");

router.get('/usuariosSquad/:idSquad/:offSet', async function(req, res, next) {

    const usuarios = await Usuario.findAll({ 
        limit: 3,
        offset: req.params.offSet,
        where: {
            fk_squad: req.params.idSquad
        }
    });
    
    res.send(usuarios);

    // res.send(usuarios).then(response => {

    //     console.log(`${resultado.count} registros`);

	// 	res.json(resultado.rows);
    
    // }).catch(erro => {
    //     console.error(erro);
	// 	res.status(500).send(erro.message);
    // });

});

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

router.get('/colaboradoresSquad/:idSquad/:idSprint', function(req, res, next) {
	console.log('Todos os usuários de uma squad e seus pontos na sprint');

    var params = {
        squad: req.params.idSquad,
        sprint: req.params.idSprint
    }

    var sql = `select  u.id_usuario, u.nome_usuario, 
                (select count(*) from tb_tarefa as t where t.fk_usuario = u.id_usuario and t.fk_sprint = s.id_sprint and t.total_concluido = 100.00) as 'total concluidas',
                (select count(*) from tb_tarefa as t where t.fk_usuario = u.id_usuario and t.fk_sprint = s.id_sprint) as 'total',
                (select count(*) from tb_tarefa as t where t.fk_usuario = u.id_usuario and t.fk_sprint = s.id_sprint and t.total_concluido = 100.00) * 10 as 'pontuacao'
                from tb_usuario as u
                inner join tb_squad as sq
                    on sq.id_squad = u.fk_squad
                inner join tb_sprint as s
                    on s.fk_squad = sq.id_squad
                where sq.id_squad = ${params.squad} and s.id_sprint = ${params.sprint} and u.is_gestor = 0 order by 'pontuacao' desc`
	
    db.sequelizeConnection.query(sql, {
        model: Usuario
    }).then(resultado => {
		
		res.json(resultado);

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

router.put('/editUsuario/:idUser', function (req, res, next) {
    
    if(req.body == null) return res.status(500);

    var camposModifi = {};
    if(req.body.nomeUsuario.length > 2){
        camposModifi.nome = req.body.nomeUsuario;
    }
    if(req.body.fotoUsuario.length > 5){
        camposModifi.foto = req.body.fotoUsuario;
    }
    if(req.body.senhaUsuario.length > 5){
        camposModifi.senha = req.body.senhaUsuario;
    }

    Usuario.update(
        camposModifi,
        { 
            where: { id_usuario: req.params.idUser } 
        }
    ).then( resultado => {

        console.log("usuario: "+resultado);

        res.json(resultado);
    
    }).catch(erro => {
        console.error(erro);
        res.status(500).send(erro.message);
    });
    
});

module.exports = router;