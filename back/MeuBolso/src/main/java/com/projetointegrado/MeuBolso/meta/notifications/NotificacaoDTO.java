package com.projetointegrado.MeuBolso.meta.notifications;

public record NotificacaoDTO(String mensagem, Integer threshold, Boolean notificado, Long metaId) {}
