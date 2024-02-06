-- Passwords estão em formato: Password<UserLetter>123.
-- Encrypted using https://www.javainuse.com/onlineBcrypt
INSERT INTO usuario_local (email, first_name, last_name, password, username, email_verified)
    VALUES ('UserA@junit.com', 'UserA-FirstName', 'UserA-LastName', '$2a$10$hBn5gu6cGelJNiE6DDsaBOmZgyumCSzVwrOK/37FWgJ6aLIdZSSI2', 'UserA', true)
        , ('UserB@junit.com', 'UserB-FirstName', 'UserB-LastName', '$2a$10$TlYbg57fqOy/1LJjispkjuSIvFJXbh3fy0J9fvHnCpuntZOITAjVG', 'UserB', false)
        , ('UserC@junit.com', 'UserC-FirstName', 'UserC-LastName', '$2a$10$SYiYAIW80gDh39jwSaPyiuKGuhrLi7xTUjocL..NOx/1COWe5P03.', 'UserC', false);

INSERT INTO endereco (bairro, cep, cidade, complemento, estado, logradouro, numero, pais, usuario_id) VALUES ('Centro', '12345-678', 'São Paulo', 'Apto 101', 'SP', 'Rua ABC', '123', 'Brasil', 1);
INSERT INTO endereco (bairro, cep, cidade, complemento, estado, logradouro, numero, pais, usuario_id) VALUES ('Jardins', '54321-098', 'Rio de Janeiro', 'Casa 2', 'RJ', 'Av. XYZ', '456', 'Brasil', 3);

INSERT INTO produto (nome, descricao_curta, descricao_longa, preco)
    VALUES ('Product #1', 'Product one short description.', 'This is a very long description of product #1.', 5.50)
    , ('Product #2', 'Product two short description.', 'This is a very long description of product #2.', 10.56)
    , ('Product #3', 'Product three short description.', 'This is a very long description of product #3.', 2.74)
    , ('Product #4', 'Product four short description.', 'This is a very long description of product #4.', 15.69)
    , ('Product #5', 'Product five short description.', 'This is a very long description of product #5.', 42.59);

INSERT INTO inventario (produto_id, quantidade)
    VALUES (1, 5)
    , (2, 8)
    , (3, 12)
    , (4, 73)
    , (5, 2);

INSERT INTO pedido (endereco_id, usuario_id)
    VALUES (1, 1)
    , (1, 1)
    , (1, 1)
    , (2, 3)
    , (2, 3);

INSERT INTO pedido_quantidades (pedido_id, produto_id, quantidade)
    VALUES (1, 1, 5)
    , (1, 2, 5)
    , (2, 3, 5)
    , (2, 2, 5)
    , (2, 5, 5)
    , (3, 3, 5)
    , (4, 4, 5)
    , (4, 2, 5)
    , (5, 3, 5)
    , (5, 1, 5);