const express = require('express');
const app = express();
const porta = 8081;


app.get("/", (req, res) => {
    res.send("Bem vindo!");
});

app.listen( porta, () => {
    console.log(`Servidor rodando em: http://localhost:${porta}`);
})