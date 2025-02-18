import { ipcRenderer, contextBridge, IpcRendererEvent } from 'electron';

// Tipos para os métodos expostos
type Listener<T = unknown> = (event: IpcRendererEvent, ...args: T[]) => void;

// Expõe APIs seguras para o Renderer Process
contextBridge.exposeInMainWorld('ipcRenderer', {
  /**
   * Escuta eventos enviados do Main Process.
   * @param channel - Nome do canal.
   * @param listener - Função de callback que será chamada quando o evento for recebido.
   */
  on<T = unknown>(channel: string, listener: Listener<T>): void {
    ipcRenderer.on(channel, listener);
  },

  /**
   * Remove um listener específico de um canal.
   * @param channel - Nome do canal.
   * @param listener - Função de callback a ser removida.
   */
  off<T = unknown>(channel: string, listener: Listener<T>): void {
    ipcRenderer.off(channel, listener);
  },

  /**
   * Envia uma mensagem assíncrona para o Main Process.
   * @param channel - Nome do canal.
   * @param args - Argumentos a serem enviados.
   */
  send<T = unknown>(channel: string, ...args: T[]): void {
    ipcRenderer.send(channel, ...args);
  },

  /**
   * Envia uma mensagem assíncrona para o Main Process e aguarda uma resposta.
   * @param channel - Nome do canal.
   * @param args - Argumentos a serem enviados.
   * @returns Uma Promise que resolve com a resposta do Main Process.
   */
  invoke<T = unknown, R = unknown>(channel: string, ...args: T[]): Promise<R> {
    return ipcRenderer.invoke(channel, ...args);
  },

  /**
   * Remove todos os listeners de um canal específico.
   * @param channel - Nome do canal.
   */
  removeAllListeners(channel: string): void {
    ipcRenderer.removeAllListeners(channel);
  },
});