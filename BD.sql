-- Deletar o banco de dados, caso exista, e criar um novo
DROP DATABASE IF EXISTS SistemaFrota;
CREATE DATABASE SistemaFrota;
USE SistemaFrota;

-- Tabela Empresa
CREATE TABLE empresa (
    id BIGINT AUTO_INCREMENT PRIMARY KEY UNIQUE,
    empresa VARCHAR(255) NOT NULL,
    cnpj VARCHAR(14) NOT NULL,
    email VARCHAR(255) NOT NULL,
    senha VARCHAR(255) NOT NULL
);

-- Tabela Frota
CREATE TABLE frota (
    id BIGINT AUTO_INCREMENT PRIMARY KEY UNIQUE,
    veiculo VARCHAR(255) NOT NULL,
    modelo VARCHAR(255) NOT NULL,
    placa VARCHAR(10) UNIQUE NOT NULL,
    cor VARCHAR(50) NOT NULL,
    ano INT NOT NULL,
    ipva DECIMAL(10, 2) NOT NULL,
    seguro DECIMAL(10, 2) NOT NULL,
    licenciamento DECIMAL(10, 2) NOT NULL,
    obrigatorio DECIMAL(10, 2) NOT NULL,
    documentacao DATE NOT NULL,
    precoaluguel DECIMAL(10, 2) NOT NULL,
    status BOOLEAN NOT NULL DEFAULT 1,
    empresaid BIGINT,
    data_cadastro DATE NOT NULL,
    FOREIGN KEY (empresaid) REFERENCES empresa(id)
);

-- Tabela Motorista
CREATE TABLE motorista (
    id BIGINT AUTO_INCREMENT PRIMARY KEY UNIQUE,
    nome VARCHAR(255) NOT NULL,
    endereco VARCHAR(300) NOT NULL,
    telefone VARCHAR(11) NOT NULL,
    identidade VARCHAR(11) UNIQUE NOT NULL,
    habilitacao VARCHAR(9) UNIQUE NOT NULL,
    precohora DOUBLE NOT NULL,
    frotaid BIGINT NULL,
    empresaid BIGINT NOT NULL,
    FOREIGN KEY (frotaid) REFERENCES frota(id),
    FOREIGN KEY (empresaid) REFERENCES empresa(id)
);

-- Tabela Planejado
CREATE TABLE planejado (
    id BIGINT AUTO_INCREMENT PRIMARY KEY UNIQUE,
    origem VARCHAR(255) NOT NULL,
    destino VARCHAR(255) NOT NULL,
    volume DECIMAL(10, 2) NOT NULL,
    peso DECIMAL(10, 2) NOT NULL,
    inicio VARCHAR(255) NOT NULL,
    fim VARCHAR(255) NOT NULL,
    preco DECIMAL(10, 2) NOT NULL,
    contratado BOOLEAN NOT NULL DEFAULT FALSE,
    empresaid BIGINT NOT NULL,
    motoristaid BIGINT,
    frotaid BIGINT,
    FOREIGN KEY (empresaid) REFERENCES empresa(id),
    FOREIGN KEY (motoristaid) REFERENCES motorista(id),
    FOREIGN KEY (frotaid) REFERENCES frota(id)
);

-- Tabela Realizado
CREATE TABLE realizado (
    id BIGINT AUTO_INCREMENT PRIMARY KEY UNIQUE,
    planejadoid BIGINT NOT NULL,
    motoristaid BIGINT NOT NULL,
    frotaid BIGINT NOT NULL,
    realizado BOOLEAN NOT NULL DEFAULT FALSE,
    cancelado BOOLEAN DEFAULT FALSE,
    data_cancelamento DATE,
    data_inicial DATE,
    FOREIGN KEY (planejadoid) REFERENCES planejado(id),
    FOREIGN KEY (motoristaid) REFERENCES motorista(id),
    FOREIGN KEY (frotaid) REFERENCES frota(id)
);


