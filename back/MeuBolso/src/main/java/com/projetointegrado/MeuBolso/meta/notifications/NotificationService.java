package com.projetointegrado.MeuBolso.meta.notifications;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationService {

    @EventListener
    public void handleMetaProgressEvent(MetaProgressEvent event) {
        // Cria e salva uma notificação no banco, ou enviar uma mensagem para um serviço de push em tempo real.
        System.out.println("Meta " + event.getMetaDescricao() + " atingiu " + event.getThreshold() + "% de progresso.");
    }
}

