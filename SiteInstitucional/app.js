const express = require('express');
const app = express();
const porta = 8081;

const bodyParser = require('body-parser');

// Routes consts
const indexRouter = require('./routes/index'); 
const loginRouter = require('./routes/login');
const trelloRouter = require('./routes/trello');

// Preset de engine
app.set('view engine', 'html');
app.engine('html', require('ejs').renderFile);
app.use(express.static(__dirname + '/views'));

// Body parser
app.use(bodyParser.urlencoded({extended: false}));
app.use(bodyParser.json());

// Rotas "Mãe"
app.use('/', indexRouter);
app.use('/login', loginRouter);
app.use('/trello', trelloRouter);



app.listen( porta, function() {
    console.log(`Servidor rodando em: http://localhost:${porta}`);
});