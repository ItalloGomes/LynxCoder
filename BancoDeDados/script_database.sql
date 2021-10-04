create database dbLynxCoder;

-- drop database dbLynxCoder;

use dbLynxCoder;

create table tb_squad(
	id_squad int primary key auto_increment
    ,nome_squad varchar(100) not null
    ,descricao_squad text
);

create table tb_sprint(
	id_sprint int primary key auto_increment
    ,descricao_sprint text
    ,fk_squad int
    ,foreign key(fk_squad) references tb_squad(id_squad)
);

create table tb_empresa(
	id_empresa int primary key auto_increment
    ,nome_empresa varchar(100) not null
    ,CNPJ_empresa varchar(100) not null
    ,telefone_empresa varchar(15)
    ,estado_empresa varchar(100)
    ,cidade_empresa varchar(100)
    ,CEP_empresa varchar(15)
    ,logradouro_empresa varchar(100)
    ,numero_empresa varchar(10)
);

create table tb_usuario(
	id_usuario int primary key auto_increment
    ,nome_usuario varchar(50) not null
    ,cargo varchar(50) not null
    ,login varchar(100) not null
    ,senha varchar(100) not null
    ,fk_supervisor int
    ,foreign key(fk_supervisor) references tb_usuario(id_usuario)
    ,fk_squad int
    ,foreign key(fk_squad) references tb_squad(id_squad)
    ,fk_empresa int
    ,foreign key(fk_empresa) references tb_empresa(id_empresa)
);

create table tb_feedback(
	id_feedback int primary key auto_increment
    ,tipo_feedback varchar(45)
    ,mensagem_feedback text
    ,aproveitamento_feedback decimal(4,2)
    ,fk_usuario int
    ,foreign key(fk_usuario) references tb_usuario(id_usuario)
    ,fk_sprint int
    ,foreign key(fk_sprint) references tb_sprint(id_sprint)
);

create table tb_maquina(
	id_maquina int primary key auto_increment
    ,marca varchar(45)
    ,modelo varchar(45)
    ,tipoCPU varchar(100)
    ,totalMemoria decimal(3,1)
    ,totalDisco decimal(5,1)
    ,sistemaOperacional varchar(100)
    ,fk_usuario int
    ,foreign key(fk_usuario) references tb_usuario(id_usuario)
);

create table tb_processo(
	id_processo int primary key auto_increment
    ,PID_processo varchar(10)
    ,nome_processo varchar(45) not null
    ,status_processo varchar(45) not null
    ,dataHorarioInicio_processo datetime
    ,dataHorarioFim_processo datetime
    ,fk_maquina int
    ,foreign key(fk_maquina) references tb_maquina(id_maquina)
);

create table tb_leitura(
	id_leitura int primary key auto_increment
	,porcentagemUsoCPU decimal(5,2)
    ,porcentagemUsoMemoria decimal(5,2)
    ,porcentagemUsoDisco decimal(5,2)
    ,dataHora datetime
    ,fk_maquina int
    ,foreign key(fk_maquina) references tb_maquina(id_maquina)
);

insert into tb_empresa 
values (
	null				  -- id_empresa
	,"Alpe"				  -- nome_empresa
    ,"05.889.172/0001-81" -- cnpj_empresa
    ,"(11) 2391 9634"     -- telefone
    ,"São Paulo"		  -- estado
    ,"São Paulo"		  -- cidade
    ,"04505-000"		  -- cep
    ,"Av. Santo Amaro"	  -- logradouro
    ,"48"				  -- numero
);

insert into tb_squad
values (
	null			   -- id_squad
    ,"securitização"   -- nome_squad
    ,"Uma squad braba" -- descrição
);

insert into tb_usuario
values (
	null            --  id_usuario
    ,"Itallo Gomes" -- ,nome_usuario
    ,"Estagiário"   -- ,cargo
    ,"itallo.gomes" -- ,login
    ,"urubu100"     -- ,senha
    ,1              -- ,fk_supervisor
    ,1              -- ,fk_squad
    ,1              -- ,fk_empresa
);


select * from tb_empresa;
select * from tb_squad;
select * from tb_usuario;





    