const express = require('express');
const path = require('path');
const app = express();
const porta = 8081;


// Routes consts
const indexRouter = require('./routes/index'); 
const loginRouter = require('./routes/login');


app.set('view engine', 'html');
app.engine('html', require('ejs').renderFile);

app.use(express.static(__dirname + '/views'));



app.use('/', indexRouter);
app.use('/login', loginRouter);



app.listen( porta, function() {
    console.log(`Servidor rodando em: http://localhost:${porta}`);
});