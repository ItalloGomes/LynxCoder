const router = require("express").Router();
const db = require('../config/connectDatabase');
const Sprint = require("../models/Sprint");

router.post('/addSprint', (req, res) => {

    Sprint.create({
        id_trello: req.body.id_trello,
        descricao: req.body.descricao,
        ativa: req.body.ativa,
        fk_squad: req.body.fk_squad
    }).then(resultado => {

        console.log(`Registro criado: ${resultado}`)

        res.sendStatus(200);

    }).catch(erro => {
        console.error(erro);
        res.status(500).send(erro.message);
    });

});

router.get('/sprintsSquad/:fk_squad', (req, res) => {

    Sprint.findAndCountAll({
        where: {
            fk_squad: req.params.fk_squad
        },
        order: [
            ['id', 'DESC']
        ],
        limit: 5
    }).then(resultado => {
        console.log(resultado.rows);
        res.json(resultado.rows);
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

router.post('/fecharSprint/:id_sprint', (req, res) => {

    Sprint.update(
        {ativa: 0},
        {where: { id: req.params.id_sprint }}
        ).then(resultado => {
            console.log(resultado.rows);
            res.json(resultado.rows);
        }).catch(erro => {
            console.error(erro);
            res.status(500).send(erro.message);
        });

});

router.get('/', function (req, res, next) {
    console.log('Todas Sprints');

    Sprint.findAndCountAll().then(resultado => {

        console.log(`${resultado.count} registros`);

        res.json(resultado.rows);

    }).catch(erro => {
        console.error(erro);
        res.status(500).send(erro.message);
    });
});

router.get('/sprintAtivaUsuario/:userId', function(req, res, next) {

    sql = `select * from tb_sprint as sprint
                inner join tb_squad as squad
                on sprint.fk_squad = squad.id_squad 
                inner join tb_usuario as usuario
                on usuario.fk_squad = squad.id_squad
                where usuario.id_usuario = ${req.params.userId} 
                and sprint.ativa = 1`;
    
    db.sequelizeConnection.query(sql, {
        model: Sprint
    }).then(resultado => {

        console.log(`${resultado} registros`);

        res.json(resultado);

    }).catch(erro => {
        console.error(erro);
        res.status(500).send(erro.message);
    });

});

module.exports = router;
