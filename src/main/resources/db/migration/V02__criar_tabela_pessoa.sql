CREATE TABLE IF NOT EXISTS pessoa (
codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
nome VARCHAR(50) NOT NULL,
ativo BOOLEAN NOT NULL,
logradouro VARCHAR(60),
numero VARCHAR(10),
complemento VARCHAR(50),
bairro VARCHAR(50),
cep VARCHAR(10),
cidade VARCHAR(50),
estado VARCHAR(2)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO pessoa (nome, ativo, logradouro, numero , complemento, bairro , cep, cidade, estado )
 VALUES ('Silvia',true,'Rua Caetano Moura','25','Em frente casa','Federação','38.400-121','Salvador','BA');
 INSERT INTO pessoa (nome, ativo, logradouro, numero , complemento, bairro , cep, cidade, estado )
 VALUES ('Maria',true,'Rua José Ramos','10','ao lado da igreja','Vasco da Gama','11.400-121','Salvador','BA');
 INSERT INTO pessoa (nome, ativo,logradouro, numero , complemento, bairro , cep, cidade, estado )
 VALUES ('Marcia',true,'Rua Paraná','22','Em frente lachonente','Trancredo Neves','54.212-121','Salvador','BA');
 INSERT INTO pessoa (nome, ativo,logradouro, numero , complemento, bairro , cep, cidade, estado )
 VALUES ('Silvana',true,'Rua Alagoinhas','43','Em frente curso','Rio vermelho','38.400-12','Salvador','BA');
 INSERT INTO pessoa (nome, ativo,logradouro, numero , complemento, bairro , cep, cidade, estado )
 VALUES ('Matilde',false,'Alto de Ondina','50','Em frente mata','ondina','56.400-121','Salvador','BA');
 INSERT INTO pessoa (nome, ativo,logradouro, numero , complemento, bairro , cep, cidade, estado )
 VALUES ('carla',false,'Rua Silveira Martins','55','a direita do material de construção','Pernambues','77.400-121','Salvador','BA');
 INSERT INTO pessoa (nome, ativo,logradouro, numero , complemento, bairro , cep, cidade, estado )
 VALUES ('vitor',true,'Rua Oito de Dezembro','66','Em frente policia','barra','12.400-121','Salvador','BA');
 INSERT INTO pessoa (nome, ativo,logradouro, numero , complemento, bairro , cep, cidade, estado )
 VALUES ('Ramos',true,'Rua Pedro Gama','75','Em frente hospital','Federação','38.400-00','Salvador','BA');
 INSERT INTO pessoa (nome, ativo,logradouro, numero , complemento, bairro , cep, cidade, estado )
 VALUES ('Soltinho',true,'Rua Fernando Falcão','67','Em frente medeiros','Sao Cristovão','78.400-325','Salvador','BA');
 INSERT INTO pessoa (nome, ativo,logradouro, numero , complemento, bairro , cep, cidade, estado )
 VALUES ('Katia',true,'Rua Patativas','27','Em frente ao restaurante','Imbuí','86.400-235','Salvador','BA');