const db = require("../config/connectDatabase");

'use strict';

const Administrador = db.sequelizeConnection.define('Administrador', 
    {
        id: {
            field: 'id_admin',
            primaryKey: true,
            autoIncrement: true,
            type: Sequelize.INTEGER,
        },
        id_trello: {
            field: 'id_trello',
            allowNull: false,
            type: Sequelize.STRING,
        },
        key_trello: {
            field: 'key_trello',
            allowNull: false,
            type: Sequelize.STRING
        },
        token_trello: {
            field: 'token_trello',
            allowNull: false,
            type: Sequelize.STRING
        },
        email_admin: {
            field: 'email_admin',
            allowNull: false,
            type: Sequelize.STRING
        },
        login_admin: {
            field: 'login_admin',
            allowNull: false,
            type: Sequelize.STRING
        },
        senha_admin: {
            field: 'senha_admin',
            allowNull: false,
            type: Sequelize.STRING
        },
        fk_empresa: {
            field: 'fk_empresa',
            allowNull: false,
            type: Sequelize.INTEGER
        }
    }, 
    {
        tableName: 'tb_administrador', 
        freezeTableName: true, 
        underscored: true,
        timestamps: false,
    }
);

module.exports = Administrador;