const router = require("express").Router();

router.get('/', (req, res) => {
    res.render('perfil', { title: 'Express' });
});

module.exports = router;