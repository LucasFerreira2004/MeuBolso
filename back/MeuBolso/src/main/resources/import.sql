/*TIPO CONTA*/
insert into tipo_conta(id, tipo_conta) values ('1', 'conta_corrente');
insert into tipo_conta(id, tipo_conta) values ('2', 'carteira');
insert into tipo_conta(id, tipo_conta) values ('3', 'poupanca');
insert into tipo_conta(id, tipo_conta) values ('4', 'investimentos');
insert into tipo_conta(id, tipo_conta) values ('5', 'outro');

/*CONTA*/
insert into conta(saldo, tipo_conta, nome_banco) values ('1412', '1', 'bradesco');
insert into conta(saldo, tipo_conta, nome_banco) values ('2000.50', '3', 'banco do brasil');


/*TRANSACAO*/
insert into transacao (valor, data_transacao, descricao, tipo, conta_origem) values ('1000', '2024-12-01', 'venda bicicleta', 'receita', '1');
insert into transacao (valor, data_transacao, descricao, tipo, conta_origem) values ('16.28', '2024-12-01', 'açaí', 'despesa', '1');
insert into transacao (valor, data_transacao, descricao, tipo, conta_origem) values ('1412', '2024-12-05', 'salario', 'receita', '2');
insert into transacao (valor, data_transacao, descricao, tipo, conta_origem) values ('123.55', '2024-12-10', 'conta luz', 'despesa', '1');
insert into transacao (valor, data_transacao, descricao, tipo, conta_origem) values ('76.20', '2024-12-11', 'conta água', 'despesa', '1');


/*CATEGORIAS*/
insert into categoria (nome, tipo_categoria, cor) values ('casa', 'despesa', '8755BC');
insert into categoria (nome, tipo_categoria, cor) values ('alimentacao', 'despesa', 'BA0707');
insert into categoria (nome, tipo_categoria, cor) values ('transporte', 'despesa', 'C19F13');
insert into categoria (nome, tipo_categoria, cor) values ('educacao', 'despesa', '33A841');
insert into categoria (nome, tipo_categoria, cor) values ('lazer', 'despesa', '57BEA1');
insert into categoria (nome, tipo_categoria, cor) values ('roupas', 'despesa', '5797BE');
insert into categoria (nome, tipo_categoria, cor) values ('saude', 'despesa', 'C92D78');
insert into categoria (nome, tipo_categoria, cor) values ('pagamentos', 'despesa', '818181');

insert into categoria (nome, tipo_categoria, cor) values ('salario', 'receita', '33A841');
insert into categoria (nome, tipo_categoria, cor) values ('investimentos', 'receita', '0775BA');
insert into categoria (nome, tipo_categoria, cor) values ('presente', 'receita', '8755BC');
insert into categoria (nome, tipo_categoria, cor) values ('renda extra', 'receita', 'CE601B');
insert into categoria (nome, tipo_categoria, cor) values ('outros', 'receita', 'BC7D18');