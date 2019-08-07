insert into produto (id, nome, descricao, preco) values (1, 'Smartwatch', 'O relógio inteligente mais premiado do mundo.', 400.0);
insert into produto (id, nome, descricao, preco) values (2, 'Canon 80D', 'A câmera mais versátil do mundo.', 5000.0);
insert into produto (id, nome, descricao, preco) values (3, 'Notebook', 'O mais leve do mundo.', 2000.0);
insert into produto (id, nome, descricao, preco) values (4, 'Smartphone', 'A melhor processador do mundo.', 1800.0);
insert into produto (id, nome, descricao, preco) values (5, 'Smart TV', 'A melhor resolução do mundo.', 3000.0);
insert into produto (id, nome, descricao, preco) values (6, 'Caixa de Som Portátil', 'O som mais incrível do mundo.', 100.0);

insert into produto_tag (produto_id, tag) values (2, 'canon');
insert into produto_tag (produto_id, tag) values (2, 'vídeo');

insert into categoria (id, nome) values (1, 'Eletrônicos');
insert into categoria (id, nome) values (2, 'Áudio e vídeo');

insert into produto_categoria (categoria_id, produto_id) values (1, 1);
insert into produto_categoria (categoria_id, produto_id) values (1, 2);
insert into produto_categoria (categoria_id, produto_id) values (1, 3);
insert into produto_categoria (categoria_id, produto_id) values (2, 2);