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
    ,logo_empresa text
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
    ,foto_usuario text
    ,cargo varchar(50) not null
    ,login varchar(100) not null
    ,senha varchar(100) not null
    ,isGestor boolean
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
    ,tipoCPU varchar(100)
    ,totalMemoria varchar(15)
    ,totalDisco varchar(15)
    ,sistemaOperacional varchar(150)
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
    ,null				  -- logo_empresa
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

insert into tb_squad
values (
	null			   -- id_squad
    ,"subadiquirência"   -- nome_squad
    ,"Uma squad pra lá de bagdá" -- descrição
);

insert into tb_usuario
values (
	null            --  id_usuario
    ,"Itallo Gomes" -- ,nome_usuario
    ,null			-- foto_usuario
    ,"Estagiário"   -- ,cargo
    ,"itallo.gomes" -- ,login
    ,"itallo.gomes@gmail.com" -- email
    ,"urubu100"     -- ,senha
    ,false			-- ,isGestor
    ,1              -- ,fk_supervisor
    ,1              -- ,fk_squad
    ,1              -- ,fk_empresa
);

insert into tb_usuario
values (
	null            --  id_usuario
    ,"Aleff Kelvin" -- ,nome_usuario
    ,null			-- foto_usuario
    ,"Estagiário"   -- ,cargo
    ,"aleff.stampini" -- ,login
    ,"aleff.stampini@gmail.com" -- email
    ,"asd123"     -- ,senha
    ,false			-- ,isGestor
    ,1              -- ,fk_supervisor
    ,2              -- ,fk_squad
    ,1              -- ,fk_empresa
);


select * from tb_empresa;
select * from tb_squad;
select * from tb_usuario;
select * from tb_maquina;
select * from tb_leitura;

SELECT * FROM tb_usuario where login = 'itallo.gomes' and senha = 'urubu100';

select count(id_usuario) from tb_usuario;