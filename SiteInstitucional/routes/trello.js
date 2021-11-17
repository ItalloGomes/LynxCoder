const { Router } = require("express");
const { add } = require("lodash");
const router = Router();
const db = require('../config/connectDatabase');
var id_admin = sessionStorage.getItem("user").id_admin;
var fk_empresa = sessionStorage.getItem("user").fk_empresa;
var key = sessionStorage.getItem("key").key_trello;
var token = sessionStorage.getItem("token").token_trello;

router.get('/', (req, res) => {
    res.render('trello', { title: 'Express' });
});

router.post('/cadastrar_squads', (req, res) => {

    console.log("Recuperando boards da empresa...");

    var url = new URL(`https://trello.com/1/members/${id_admin}/boards`),
        params = { key: key, token: token }
    Object.keys(params).forEach(key => url.searchParams.append(key, params[key]))

    fetch(url, {
        method: "GET"
    }).then(function (resultado) {

        if (resultado.ok) {

            resultado.json().then(json => {

                json.forEach(board => {
                    fetch('/addSquad', {
                        method: "POST",
                        body: board
                    }).then(response => {
                        if (response.ok) {
                            console.log("Squad cadastrada: " + board.name)
                        } else {
                            console.log('Erro ao cadastrar squad!');
                            console.error(texto);
                        }
                    });
                });

            });
            res.send(resultado);
        } else {
            console.log("Erro ao cadastrar boards da empresa");
            res.error(resultado.text().then(texto => {
                console.error(texto);
            }));
        }

    });

});

router.get('/listar_usuarios_squad/:squad', (req, res) => {

    console.log("Recuperando usuários da squad...");
    let squad = req.params.squad;
    let id_squad;

    let sql = `select id_squad from tb_squad where id_trello = ${squad.id}`;
    console.log(sql);

    sequelize.query(sql, { type: sequelize.QueryTypes.SELECT })
        .then(resultado => {
            id_squad = resultado[0].id_squad;
        }).catch(erro => {
            console.error(erro);
        });

    let members = [];

    squad.memberships.forEach(membership => {
        var url = new URL(`https://trello.com/1/members/${membership.idMember}`),
            params = { key: key, token: token }
        Object.keys(params).forEach(key => url.searchParams.append(key, params[key]))

        fetch(url, {
            method: "GET"
        }).then(function (resultado) {

            if (resultado.ok) {

                resultado.json().then(json => {

                    members.push({
                        id_trello: json.id,
                        nome: json.fullName,
                        foto: json.avatarUrl,
                        login: json.username,
                        senha: json.username + (Math.random() * (1_000_000 - 100_000) + 100_000),
                        is_gestor: null,
                        fk_supervisor: null,
                        fk_squad: id_squad,
                        fk_empresa: fk_empresa
                    });

                });
            } else {
                console.log("Erro ao recuperar membros da squad!");
                resultado.text().then(texto => {
                    console.error(texto);
                    res.status(500).send(texto);
                });
            }

        });
    });

    res.json(members);

});

router.get('/iniciar_sprint', (req, res) => {

    console.log("Iniciando sprint...");

    var fk_squad = sessionStorage.getItem("user").fk_squad;

    let sql = `select id_trello from tb_usuario join tb_squad on fk_squad = id_squad where fk_squad = ${fk_squad}`;
    console.log(sql);

    sequelize.query(sql, { type: sequelize.QueryTypes.SELECT })
        .then(resultado => {
            var url = new URL(`https://trello.com/1/boards/${resultado[0].id_trello}/lists`),
                params = { key: key, token: token }
            Object.keys(params).forEach(key => url.searchParams.append(key, params[key]))

            fetch(url, {
                method: "GET"
            }).then(function (resultado) {

                if (resultado.ok) {

                    resultado.json().then(lists => {

                        lists.forEach(list => {
                            if (list.name.includes("Sprint")) {

                            }
                        });

                    });
                } else {
                    console.log("Erro ao iniciar sprint!");
                    resultado.text().then(texto => {
                        console.error(texto);
                        res.status(500).send(texto);
                    });
                }

            });
        }).catch(erro => {
            console.error(erro);
        });



});

module.exports = router;