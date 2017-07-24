CREATE TABLE pessoa (
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(50) NOT NULL,
	ativo BOOLEAN NOT NULL,
	logradouro VARCHAR(30),
	numero VARCHAR(30),
	complemento VARCHAR(30),
	bairro VARCHAR(30),
	cep VARCHAR(30),
	cidade VARCHAR(30),
	estado VARCHAR(30)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO pessoa (nome, ativo, logradouro, numero, complemento, bairro, cep, cidade, estado) values ('Paulo Roberto', true, 'Parque Paraúna', '79', 'Parque Verde', 'Nova esperança', '59.144-170', 'Parnamirim', 'RN');
INSERT INTO pessoa (nome, ativo, logradouro, numero, complemento, bairro, cep, cidade, estado) values ('Maria Fernanda', true, 'Parque Paraúna', '79', null, 'Nova Esperança', '59.144-170', 'Parnamirim', 'RN');