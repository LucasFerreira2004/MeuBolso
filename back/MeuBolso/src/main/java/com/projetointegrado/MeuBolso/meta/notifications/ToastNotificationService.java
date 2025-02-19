package com.projetointegrado.MeuBolso.meta.notifications;

import com.projetointegrado.MeuBolso.meta.Meta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class ToastNotificationService implements MetaObserver {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public ToastNotificationService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void atualizar(Meta meta, int threshold) {
        // Formata mensagem
        String mensagem = String.format("Meta '%s' atingiu %d%%!", meta.getDescricao(), threshold);

        // Envia a notificacao
        String destino = "/topic/metas/" + meta.getUsuario().getId();
        messagingTemplate.convertAndSend(destino, new NotificacaoDTO(mensagem, threshold, true, meta.getId()));
    }
}