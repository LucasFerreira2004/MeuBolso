import { app, BrowserWindow } from 'electron';
import { fileURLToPath } from 'node:url';
import path from 'node:path';

// Configuração de caminhos
const __dirname = path.dirname(fileURLToPath(import.meta.url));
process.env.APP_ROOT = path.join(__dirname, '..');

export const VITE_DEV_SERVER_URL = process.env['VITE_DEV_SERVER_URL'];
export const MAIN_DIST = path.join(process.env.APP_ROOT, 'dist-electron');
export const RENDERER_DIST = path.join(process.env.APP_ROOT, 'dist');
process.env.VITE_PUBLIC = VITE_DEV_SERVER_URL ? path.join(process.env.APP_ROOT, 'public') : RENDERER_DIST;

// Variáveis globais
let win: BrowserWindow | null = null;

/**
 * Cria a janela principal do Electron.
 */
function createWindow(): void {
  win = new BrowserWindow({
    icon: path.join(process.env.VITE_PUBLIC, 'electron-vite.svg'),
    webPreferences: {
      preload: path.join(__dirname, 'preload.mjs'),
      contextIsolation: true, // Habilita o isolamento de contexto
      sandbox: true, // Habilita o modo sandbox para segurança
    },
  });

  // Configuração da Content Security Policy (CSP)
  win.webContents.session.webRequest.onHeadersReceived((details, callback) => {
    callback({
      responseHeaders: {
        ...details.responseHeaders,
        'Content-Security-Policy': [
          "default-src 'self';" +
          " connect-src 'self' http://localhost:8080;" + // Permite conexões com a API
          " script-src 'self' 'unsafe-inline';" + // Permite scripts inline (cuidado com segurança)
          " style-src 'self' 'unsafe-inline' https://fonts.googleapis.com;" + // Permite estilos inline e fontes do Google
          " font-src 'self' https://fonts.gstatic.com;" + // Permite fontes do Google
          " img-src 'self' https://play-lh.googleusercontent.com https://t.ctcdn.com.br;" + // Permite imagens externas
          " object-src 'none';" // Bloqueia objetos como Flash
        ],
      },
    });
  });

  // Envia uma mensagem para o renderer process após carregar a página
  win.webContents.on('did-finish-load', () => {
    win?.webContents.send('main-process-message', new Date().toLocaleString());
  });

  // Carrega a aplicação
  if (VITE_DEV_SERVER_URL) {
    win.loadURL(VITE_DEV_SERVER_URL);
    win.webContents.openDevTools(); // Abre o DevTools em modo de desenvolvimento
  } else {
    win.loadFile(path.join(RENDERER_DIST, 'index.html'));
  }
}

// Eventos do Electron
app.on('window-all-closed', () => {
  if (process.platform !== 'darwin') {
    app.quit();
    win = null;
  }
});

app.on('activate', () => {
  if (BrowserWindow.getAllWindows().length === 0) {
    createWindow();
  }
});

// Inicialização do Electron
app.whenReady().then(() => {
  createWindow(); // Cria a janela principal
});