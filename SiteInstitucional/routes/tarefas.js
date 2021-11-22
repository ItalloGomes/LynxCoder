const router = require("express").Router();
const db = require('../config/connectDatabase');
const Tarefa = require("../models/Tarefa");

router.post('/addTarefa', (req, res) => {

    Tarefa.create({
        id_trello: req.body.id_trello,
        nome: req.body.nome,
        pontos: req.body.pontos,
        total_concluido: req.body.total_concluido,
        prazo: req.body.prazo,
        fk_usuario: req.body.fk_usuario,
        fk_sprint: req.body.fk_sprint
    }).then(resultado => {
        
        console.log(`Registro criado: ${resultado}`)

        res.sendStatus(200);
        
    }).catch(erro => {
        console.error(erro);
        res.status(500).send(erro.message);
    });

});

router.delete('/removeTarefa/:idTarefa', (req, res) => {

    Tarefa.destroy({
        where: {
            id: req.params.idTarefa
         }
    }).then(resultado => {
        
        console.log(`Registro deletado: ${resultado}`)

        res.sendStatus(200);
        
    }).catch(erro => {
        console.error(erro);
        res.status(500).send(erro.message);
    });

});

router.get('/:userId/:sprintId', (req, res) => {

    console.log("Listando todas tarefas do usuario em uma determinada sprint");

    const params = {
        userId: req.params.userId,
        sprintId: req.params.sprintId
    }

    sql = `select * from tb_tarefa where fk_usuario='${params.userId}' 
                                        and fk_sprint='${params.sprintId}'`;
    
    db.sequelizeConnection.query(sql, {
        model: Tarefa
    }).then(resultado => {

        console.log(`Tarefas encontradas: ${resultado.length}`);

        res.json(resultado);
        
    }).catch(erro => {
		console.error(erro);
		res.status(500).send(erro.message);
  	});
    
});

router.get('/:sprintId', (req, res) => {

    console.log("Listando todas tarefas de uma determinada sprint");

    sql = `select * from tb_tarefa where fk_sprint='${req.params.sprintId} order by fk_usuario'`;
    
    db.sequelizeConnection.query(sql, {
        model: Tarefa
    }).then(resultado => {

        console.log(`Tarefas encontradas: ${resultado.length}`);

        res.json(resultado);
        
    }).catch(erro => {
		console.error(erro);
		res.status(500).send(erro.message);
  	});
    
});

router.post('/atualizarProgresso/:concluido/:idTarefa', (req, res) => {

    console.log("Atualizando progresso da tarefa");

    Tarefa.update(
        {total_concluido: req.params.concluido},
        {where: {id: req.params.idTarefa}}
        ).then(resultado => {
            console.log(resultado.rows);
            res.json(resultado.rows);
        }).catch(erro => {
            console.error(erro);
            res.status(500).send(erro.message);
        });
    
});


module.exports = router;