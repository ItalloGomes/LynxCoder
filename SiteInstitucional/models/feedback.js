const db = require("../config/connectDatabase");

'use strict';

const FeedBack = db.sequelizeConnection.define('FeedBack', 
    {
        id: {
            field: 'id_feedback',
            primaryKey: true,
            autoIncrement: true,
            type: db.Sequelize.INTEGER
        },
        mensagem: {
            field: 'mensagem_feedback',
            allowNull: false,
            type: db.Sequelize.TEXT
        },
        aproveitamento: {
            field: 'aproveitamento_feedback',
            allowNull: false,
            type: db.Sequelize.DECIMAL
        },
        facilidade: {
            field: 'facilidade_feedback',
            allowNull: false,
            type: db.Sequelize.DECIMAL
        },
        fk_usuario: {
            field: 'fk_usuario',
            allowNull: false,
            type: db.Sequelize.INTEGER,
            foreignKey: true
        },
        fk_tarefa: {
            field: 'fk_tarefa',
            allowNull: false,
            type: db.Sequelize.INTEGER,
            foreignKey: true
        }
    }, 
    {
        tableName: 'tb_feedback', 
        freezeTableName: true, 
        underscored: true,
        timestamps: false,
    }
);

module.exports = FeedBack;