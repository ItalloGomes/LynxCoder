const db = require("../config/connectDatabase");

'use strict';

const Usuario = db.sequelizeConnection.define('Usuario', 
    {
        id: {
            field: 'id_usuario',
            primaryKey: true,
            autoIncrement: true,
            type: Sequelize.INTEGER,
        },
        nome: {
            field: 'nome_usuario',
            allowNull: false,
            type: Sequelize.STRING
        },
        foto: {
            field: 'foto_usuario',
            allowNull: false,
            type: Sequelize.TEXT
        },
        cargo: {
            field: 'cargo',
            allowNull: false,
            type: Sequelize.STRING
        },
        login: {
            field: 'login',
            allowNull: false,
            type: Sequelize.STRING
        },
        email: {
            field: 'email',
            allowNull: false,
            type: Sequelize.STRING
        },
        senha: {
            field: 'senha',
            allowNull: false,
            type: Sequelize.STRING
        },
        isGestor: {
            field: 'isGestor',
            allowNull: false,
            type: Sequelize.BOOLEAN
        },
        fk_supervisor: {
            field: 'fk_supervisor',
            allowNull: false,
            type: Sequelize.INTEGER
        },
        fk_squad: {
            field: 'fk_squad',
            allowNull: false,
            type: Sequelize.INTEGER
        },
        fk_empresa: {
            field: 'fk_empresa',
            allowNull: false,
            type: Sequelize.INTEGER
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