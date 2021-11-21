const db = require("../config/connectDatabase");

'use strict';

const Usuario = db.sequelizeConnection.define('Usuario', 
    {
        id: {
            field: 'id_usuario',
            primaryKey: true,
            autoIncrement: true,
            type: db.Sequelize.INTEGER,
        },
        id_trello: {
            field: 'id_trello',
            allowNull: false,
            type: db.Sequelize.STRING,
        },
        nome: {
            field: 'nome_usuario',
            allowNull: false,
            type: db.Sequelize.STRING
        },
        foto: {
            field: 'foto_usuario',
            allowNull: true,
            type: db.Sequelize.TEXT
        },
        login: {
            field: 'login',
            allowNull: false,
            type: db.Sequelize.STRING
        },
        senha: {
            field: 'senha',
            allowNull: false,
            type: db.Sequelize.STRING
        },
        is_gestor: {
            field: 'is_gestor',
            allowNull: false,
            type: db.Sequelize.TINYINT(1)
        },
        fk_supervisor: {
            field: 'fk_supervisor',
            allowNull: true,
            type: db.Sequelize.INTEGER
        },
        fk_squad: {
            field: 'fk_squad',
            allowNull: false,
            type: db.Sequelize.INTEGER
        },
        fk_empresa: {
            field: 'fk_empresa',
            allowNull: false,
            type: db.Sequelize.INTEGER
        }
    }, 
    {
        tableName: 'tb_usuario', 
        freezeTableName: true, 
        underscored: true,
        timestamps: false,
    }
);

module.exports = Usuario;