const router = require("express").Router();
const Squad = require("../models/Squad");

router.post('/addSquad', (req, res) => {

    Squad.create({
        id_trello: req.body.id,
        nome: req.body.name,
        descricao: req.body.desc
    }).then(resultado => {
        
        console.log(`Registro criado: ${resultado}`)

        res.sendStatus(200);
        
    }).catch(erro => {
        console.error(erro);
        res.status(500).send(erro.message);
    });

});

router.delete('/removeSquad:idSquad', (req, res) => {

    Squad.destroy({
        where: {
            id: req.params.idSquad
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
	console.log('Todas Squads');
	
    Squad.findAndCountAll().then(resultado => {
		
        console.log(`${resultado.count} registros`);

		res.json(resultado.rows);

	}).catch(erro => {
		console.error(erro);
		res.status(500).send(erro.message);
  	});
});