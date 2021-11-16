const router = require("express").Router();
const Sprint = require("../models/Sprint");

router.post('/addSprint', (req, res) => {

    Sprint.create({
        id_trello: req.body.idTrello,
        descricao: req.body.descricao,
        ativa: req.body.ativa,
        fk_squad: req.body.idSquad
    }).then(resultado => {
        
        console.log(`Registro criado: ${resultado}`)

        res.sendStatus(200);
        
    }).catch(erro => {
        console.error(erro);
        res.status(500).send(erro.message);
    });

});

router.delete('/removeSprint:idSprint', (req, res) => {

    Sprint.destroy({
        where: {
            id: req.params.idSprint
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
	console.log('Todas Sprints');
	
    Sprint.findAndCountAll().then(resultado => {
		
        console.log(`${resultado.count} registros`);
        
		res.json(resultado.rows);

	}).catch(erro => {
		console.error(erro);
		res.status(500).send(erro.message);
  	});
});