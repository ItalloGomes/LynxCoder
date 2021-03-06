const express = require('express');
const app = express();

const bodyParser = require('body-parser');
var logger = require('morgan');

// Routes consts
const indexRouter = require('./routes/index'); 
const loginRouter = require('./routes/logins');
const dasboardRouter = require('./routes/dashboard');
const empresaRouter = require('./routes/empresas');
const feedbackRouter = require('./routes/feedbacks');
const sprintRouter = require('./routes/sprints');
const squadRouter = require('./routes/squads');
const tarefaRouter = require('./routes/tarefas');
const usuarioRouter = require('./routes/usuarios');
const trelloRouter = require('./routes/trello');
const maquinasRouter = require('./routes/maquinas');
// const relatoriosRouter = require('./routes/relatorios');


// Preset de engine
app.use(logger('dev')); 
app.set('view engine', 'html');
app.engine('html', require('ejs').renderFile);
app.use(express.static(__dirname + '/views'));

// Body parser
app.use(bodyParser.urlencoded({extended: false}));
app.use(bodyParser.json());

// Rotas "Mãe"
app.use('/', indexRouter);
app.use('/login', loginRouter);
app.use('/dashboard', dasboardRouter);
app.use('/empresas', empresaRouter);
app.use('/feedbacks', feedbackRouter);
app.use('/sprints', sprintRouter);
app.use('/squads', squadRouter);
app.use('/tarefas', tarefaRouter);
app.use('/usuarios', usuarioRouter);
app.use('/trello', trelloRouter);
app.use('/maquinas', maquinasRouter);
// app.use('/relatorios', relatoriosRouter);

module.exports = app;