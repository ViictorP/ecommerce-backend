-- Script to insert dummy dev data into the database.

-- You first need to register two users into the system before running this script.

-- Replace the id here with the first user id you want to have ownership of the orders.
SET @userId1 := 8;
-- Replace the id here with the second user id you want to have ownership of the orders.
SET @userId2 := 9;

DELETE FROM pedido_quantidades;
DELETE FROM pedido;
DELETE FROM inventario;
DELETE FROM produto;
DELETE FROM endereco;

INSERT INTO produto (`nome`, descricao_curta, descricao_longa, preco) VALUES ('Product #1', 'Product one short description.', 'This is a very long description of product #1.', 5.50);
INSERT INTO produto (`nome`, descricao_curta, descricao_longa, preco) VALUES ('Product #2', 'Product two short description.', 'This is a very long description of product #2.', 10.56);
INSERT INTO produto (`nome`, descricao_curta, descricao_longa, preco) VALUES ('Product #3', 'Product three short description.', 'This is a very long description of product #3.', 2.74);
INSERT INTO produto (`nome`, descricao_curta, descricao_longa, preco) VALUES ('Product #4', 'Product four short description.', 'This is a very long description of product #4.', 15.69);
INSERT INTO produto (`nome`, descricao_curta, descricao_longa, preco) VALUES ('Product #5', 'Product five short description.', 'This is a very long description of product #5.', 42.59);

SET @produto1 := (SELECT id FROM produto WHERE `nome` = 'Product #1');
SET @produto2 := (SELECT id FROM produto WHERE `nome` = 'Product #2');
SET @produto3 := (SELECT id FROM produto WHERE `nome` = 'Product #3');
SET @produto4 := (SELECT id FROM produto WHERE `nome` = 'Product #4');
SET @produto5 := (SELECT id FROM produto WHERE `nome` = 'Product #5');

INSERT INTO inventario (produto_id, quantidade) VALUES (@produto1, 5);
INSERT INTO inventario (produto_id, quantidade) VALUES (@produto2, 8);
INSERT INTO inventario (produto_id, quantidade) VALUES (@produto3, 12);
INSERT INTO inventario (produto_id, quantidade) VALUES (@produto4, 73);
INSERT INTO inventario (produto_id, quantidade) VALUES (@produto5, 2);

INSERT INTO endereco (bairro, cep, cidade, complemento, estado, logradouro, numero, pais, usuario_id) VALUES ('Centro', '12345-678', 'São Paulo', 'Apto 101', 'SP', 'Rua ABC', '123', 'Brasil', @userId1);
INSERT INTO endereco (bairro, cep, cidade, complemento, estado, logradouro, numero, pais, usuario_id) VALUES ('Jardins', '54321-098', 'Rio de Janeiro', 'Casa 2', 'RJ', 'Av. XYZ', '456', 'Brasil', @userId2);


SET @endereco1 := (SELECT id FROM endereco WHERE usuario_id = @userId1 ORDER BY id DESC LIMIT 1);
SET @endereco2 := (SELECT id FROM endereco WHERE usuario_id = @userId2 ORDER BY id DESC LIMIT 1);

INSERT INTO pedido (endereco_id, usuario_id) VALUES (@endereco1, @userId1);
INSERT INTO pedido (endereco_id, usuario_id) VALUES (@endereco1, @userId1);
INSERT INTO pedido (endereco_id, usuario_id) VALUES (@endereco1, @userId1);
INSERT INTO pedido (endereco_id, usuario_id) VALUES (@endereco2, @userId2);
INSERT INTO pedido (endereco_id, usuario_id) VALUES (@endereco2, @userId2);

SET @pedido1 := (SELECT id FROM pedido WHERE endereco_id = @endereco1 AND usuario_id = @userId1 ORDER BY id DESC LIMIT 1);
SET @pedido2 := (SELECT id FROM pedido WHERE endereco_id = @endereco1 AND usuario_id = @userId1 ORDER BY id DESC LIMIT 1 OFFSET 1);
SET @pedido3 := (SELECT id FROM pedido WHERE endereco_id = @endereco1 AND usuario_id = @userId1 ORDER BY id DESC LIMIT 1 OFFSET 2);
SET @pedido4 := (SELECT id FROM pedido WHERE endereco_id = @endereco2 AND usuario_id = @userId2 ORDER BY id DESC LIMIT 1);
SET @pedido5 := (SELECT id FROM pedido WHERE endereco_id = @endereco2 AND usuario_id = @userId2 ORDER BY id DESC LIMIT 1 OFFSET 1);

INSERT INTO pedido_quantidades (pedido_id, produto_id, quantidade) VALUES (@pedido1, @produto1, 5);
INSERT INTO pedido_quantidades (pedido_id, produto_id, quantidade) VALUES (@pedido1, @produto2, 5);
INSERT INTO pedido_quantidades (pedido_id, produto_id, quantidade) VALUES (@pedido2, @produto3, 5);
INSERT INTO pedido_quantidades (pedido_id, produto_id, quantidade) VALUES (@pedido2, @produto2, 5);
INSERT INTO pedido_quantidades (pedido_id, produto_id, quantidade) VALUES (@pedido2, @produto5, 5);
INSERT INTO pedido_quantidades (pedido_id, produto_id, quantidade) VALUES (@pedido3, @produto3, 5);
INSERT INTO pedido_quantidades (pedido_id, produto_id, quantidade) VALUES (@pedido4, @produto4, 5);
INSERT INTO pedido_quantidades (pedido_id, produto_id, quantidade) VALUES (@pedido4, @produto2, 5);
INSERT INTO pedido_quantidades (pedido_id, produto_id, quantidade) VALUES (@pedido5, @produto3, 5);
INSERT INTO pedido_quantidades (pedido_id, produto_id, quantidade) VALUES (@pedido5, @produto1, 5);
