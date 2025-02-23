"use strict";
const electron = require("electron");
electron.contextBridge.exposeInMainWorld("ipcRenderer", {
  /**
   * Escuta eventos enviados do Main Process.
   * @param channel - Nome do canal.
   * @param listener - Função de callback que será chamada quando o evento for recebido.
   */
  on(channel, listener) {
    electron.ipcRenderer.on(channel, listener);
  },
  /**
   * Remove um listener específico de um canal.
   * @param channel - Nome do canal.
   * @param listener - Função de callback a ser removida.
   */
  off(channel, listener) {
    electron.ipcRenderer.off(channel, listener);
  },
  /**
   * Envia uma mensagem assíncrona para o Main Process.
   * @param channel - Nome do canal.
   * @param args - Argumentos a serem enviados.
   */
  send(channel, ...args) {
    electron.ipcRenderer.send(channel, ...args);
  },
  /**
   * Envia uma mensagem assíncrona para o Main Process e aguarda uma resposta.
   * @param channel - Nome do canal.
   * @param args - Argumentos a serem enviados.
   * @returns Uma Promise que resolve com a resposta do Main Process.
   */
  invoke(channel, ...args) {
    return electron.ipcRenderer.invoke(channel, ...args);
  },
  /**
   * Remove todos os listeners de um canal específico.
   * @param channel - Nome do canal.
   */
  removeAllListeners(channel) {
    electron.ipcRenderer.removeAllListeners(channel);
  }
});
