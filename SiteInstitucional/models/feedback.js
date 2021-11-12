const db = require("../config/connectDatabase");

'use strict';

const FeedBack = db.sequelizeConnection.define('FeedBack', 
    {
        id: {
            field: 'id_feedback',
            primaryKey: true,
            autoIncrement: true,
            type: Sequelize.INTEGER
        },
        tipoFeedback: {
            field: 'tipo_feedback',
            allowNull: false,
            type: Sequelize.STRING
        },
        mensagem: {
            field: 'mensagem_feedback',
            allowNull: false,
            type: Sequelize.TEXT
        },
        aproveitamento: {
            field: 'aproveitamento_feedback',
            allowNull: false,
            type: Sequelize.DECIMAL
        },
        fk_usuario: {
            field: 'fk_usuario',
            allowNull: false,
            type: Sequelize.INTEGER,
            foreignKey: true
        },
        fk_tarefa: {
            field: 'fk_tarefa',
            allowNull: false,
            type: Sequelize.INTEGER,
            foreignKey: true
        },
        facilidade_feedback: {
            field: 'facilidade_feedback',
            allowNull: false,
            type: Sequelize.DECIMAL
        },
    }, 
    {
        tableName: 'tb_feedback', 
        freezeTableName: true, 
        underscored: true,
        timestamps: false,
    }
);

module.exports = FeedBack;