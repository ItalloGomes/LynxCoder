const { Router } = require("express");
const router = Router();
const Usuario = require("../models/Usuario");
const Squad = require("../models/Squad");

router.get('/', (req, res) => {
    res.render('trello', { title: 'Express' });
});

module.exports = router;