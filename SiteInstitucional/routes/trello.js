const { Router } = require("express");
const router = Router();
const Usuario = require("../models/Usuario");
const Squad = require("../models/Squad");

router.get('/', (req, res) => {
    res.render('trello', { title: 'Express' });
});

// router.get('/iniciar_sprint', (req, res) => {

//     console.log("Iniciando sprint...");

//     var fk_squad = sessionStorage.getItem("user").fk_squad;

//     Usuario.findAll({
//         join: {
//             Squad
//         },
//         on: {
//             fk_squad = id_squad
//         },
//         where: {
//             id_trello: squad.id
//         }
//     }).then(resultado => {
//         var url = new URL(`https://trello.com/1/boards/${resultado[0].id_trello}/lists`),
//             params = { key: key, token: token }
//         Object.keys(params).forEach(key => url.searchParams.append(key, params[key]))

//         fetch(url, {
//             method: "GET"
//         }).then(function (resultado) {

//             if (resultado.ok) {

//                 resultado.json().then(lists => {

//                     lists.forEach(list => {
//                         if (list.name.includes("Sprint")) {

//                         }
//                     });

//                 });
//             } else {
//                 console.log("Erro ao iniciar sprint!");
//                 resultado.text().then(texto => {
//                     console.error(texto);
//                     res.status(500).send(texto);
//                 });
//             }

//         });
//     }).catch(erro => {
//         console.error(erro);
//     });



// });

module.exports = router;