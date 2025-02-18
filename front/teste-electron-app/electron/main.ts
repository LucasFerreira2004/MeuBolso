import { app, BrowserWindow } from 'electron';
import { fileURLToPath } from 'node:url';
import path from 'node:path';
import { spawn } from 'node:child_process';

const __dirname = path.dirname(fileURLToPath(import.meta.url));

process.env.APP_ROOT = path.join(__dirname, '..');

export const VITE_DEV_SERVER_URL = process.env['VITE_DEV_SERVER_URL'];
export const MAIN_DIST = path.join(process.env.APP_ROOT, 'dist-electron');
export const RENDERER_DIST = path.join(process.env.APP_ROOT, 'dist');

process.env.VITE_PUBLIC = VITE_DEV_SERVER_URL ? path.join(process.env.APP_ROOT, 'public') : RENDERER_DIST;

let win: BrowserWindow | null;
let apiProcess: any; // Referência para o processo da API

function startAPI() {
  const apiPath = path.join(process.env.APP_ROOT, 'api', 'dslist.jar');

  apiProcess = spawn('java', ['-jar', apiPath], { stdio: 'inherit' });
  apiProcess.on('error', (err: unknown) => {
    if (err instanceof Error) {
      console.error('Erro ao iniciar a API:', err.message);
    } else {
      console.error('Erro desconhecido ao iniciar a API:', err);
    }
  });

  apiProcess.on('exit', (code: any) => {
    console.log('Processo da API foi encerrado com código:', code);
  });
}

function stopAPI() {
  if (apiProcess) {
    apiProcess.kill(); // Finaliza o processo da API ao fechar o app
    apiProcess = null;
  }
}

function createWindow() {
  win = new BrowserWindow({
    icon: path.join(process.env.VITE_PUBLIC, 'electron-vite.svg'),
    webPreferences: {
      preload: path.join(__dirname, 'preload.mjs'),
    },
  });

  // Adicionando a Content Security Policy
  win.webContents.session.webRequest.onHeadersReceived((details, callback) => {
    callback({
      responseHeaders: {
        ...details.responseHeaders,
        "Content-Security-Policy": [
          "default-src 'self';" +
          " connect-src 'self' http://localhost:8080;" + // Adicionando sua API se necessário
          " script-src 'self' 'unsafe-inline';" +
          " style-src 'self' 'unsafe-inline' https://fonts.googleapis.com;" + // Permite carregar fontes do Google
          " font-src 'self' https://fonts.gstatic.com;" + // Permite carregar fontes do Google
          " img-src 'self' https://play-lh.googleusercontent.com https://t.ctcdn.com.br;" + // Permite imagens externas
          " object-src 'none';"
        ],
      },
    });
  });

  // Teste para enviar mensagem para o renderer process
  win.webContents.on('did-finish-load', () => {
    win?.webContents.send('main-process-message', new Date().toLocaleString());
  });

  if (VITE_DEV_SERVER_URL) {
    win.loadURL(VITE_DEV_SERVER_URL);
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

app.on('before-quit', () => {
  stopAPI(); // Certifica-se de parar a API antes de sair
});

app.whenReady().then(() => {
  startAPI(); // Inicia a API
  createWindow(); // Cria a janela principal
});
