/*TIPO CONTA*/
insert into tipo_conta(id, tipo_conta) values (1, 'conta_corrente');
insert into tipo_conta(id, tipo_conta) values (2, 'carteira');
insert into tipo_conta(id, tipo_conta) values (3, 'poupanca');
insert into tipo_conta(id, tipo_conta) values (4, 'investimentos');
insert into tipo_conta(id, tipo_conta) values (5, 'outro');

/*CONTA*/
insert into conta(saldo, tipo_conta, nome_banco) values ('1412', '1', 'bradesco');
insert into conta(saldo, tipo_conta, nome_banco) values ('2000.50', '3', 'banco do brasil');


/*TRANSACAO*/
insert into transacao (valor, data_transacao, descricao, tipo) values ('1000', '2024-12-01', 'venda bicicleta', 'receita');
insert into transacao (valor, data_transacao, descricao, tipo) values ('16.28', '2024-12-01', 'açaí', 'despesa');
insert into transacao (valor, data_transacao, descricao, tipo) values ('1412', '2024-12-05', 'salario', 'receita');
insert into transacao (valor, data_transacao, descricao, tipo) values ('123.55', '2024-12-10', 'conta luz', 'despesa');
insert into transacao (valor, data_transacao, descricao, tipo) values ('76.20', '2024-12-11', 'conta água', 'despesa');