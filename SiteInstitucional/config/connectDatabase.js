const Sequelize = require('sequelize');

//Conexão Sequelize sql server
const sequelizeConnection = new Sequelize('dbLynxCoder', 'lynxcoder', '#Gfgrupo3', {
    host: 'srvlynxcoder.database.windows.net',
    dialect: 'mssql'
});

//Conexão Sequelize MySql
// const sequelizeConnection = new Sequelize('dbLynxCoder', 'admLynxCoder', 'lynxcoder', {
//     host: 'localhost',
//     dialect: 'mysql'
// });

sequelizeConnection.authenticate().then( function() {
    console.log(`Database conectado com sucesso!`);
}).catch( erro => {
    console.log("Falha ao conectar ao Database: "+erro);
});

module.exports = {sequelizeConnection, Sequelize};