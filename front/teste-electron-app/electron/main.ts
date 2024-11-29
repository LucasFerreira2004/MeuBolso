import { app, BrowserWindow } from 'electron'
//import { createRequire } from 'node:module'
import { fileURLToPath } from 'node:url'
import path from 'node:path'
import { spawn } from 'node:child_process'

//const require = createRequire(import.meta.url)
const __dirname = path.dirname(fileURLToPath(import.meta.url))

process.env.APP_ROOT = path.join(__dirname, '..')

// 🚧 Use ['ENV_NAME'] avoid vite:define plugin - Vite@2.x
export const VITE_DEV_SERVER_URL = process.env['VITE_DEV_SERVER_URL']
export const MAIN_DIST = path.join(process.env.APP_ROOT, 'dist-electron')
export const RENDERER_DIST = path.join(process.env.APP_ROOT, 'dist')

process.env.VITE_PUBLIC = VITE_DEV_SERVER_URL ? path.join(process.env.APP_ROOT, 'public') : RENDERER_DIST

let win: BrowserWindow | null
let apiProcess: any // Referência para o processo da API

function startAPI() {
  const apiPath = path.join(process.env.APP_ROOT, 'api', 'dslist.jar')
  
  // Lança o .jar usando o Java instalado no sistema
  apiProcess = spawn('java', ['-jar', apiPath], { stdio: 'inherit' })
  apiProcess.on('error', (err: any) => { //verificar se isso tá certo, desse tipo any
    console.error('Erro ao iniciar a API:', err)
  })

  apiProcess.on('exit', (code: any) => {
    console.log('Processo da API foi encerrado com código:', code)
  })
}

function stopAPI() {
  if (apiProcess) {
    apiProcess.kill() // Finaliza o processo da API ao fechar o app
    apiProcess = null
  }
}

function createWindow() {
  win = new BrowserWindow({
    icon: path.join(process.env.VITE_PUBLIC, 'electron-vite.svg'),
    webPreferences: {
      preload: path.join(__dirname, 'preload.mjs'),
    },
  })

  // Test active push message to Renderer-process.
  win.webContents.on('did-finish-load', () => {
    win?.webContents.send('main-process-message', new Date().toLocaleString())
  })

  if (VITE_DEV_SERVER_URL) {
    win.loadURL(VITE_DEV_SERVER_URL)
  } else {
    win.loadFile(path.join(RENDERER_DIST, 'index.html'))
  }
}

// Eventos do Electron
app.on('window-all-closed', () => {
  if (process.platform !== 'darwin') {
    app.quit()
    win = null
  }
})

app.on('activate', () => {
  if (BrowserWindow.getAllWindows().length === 0) {
    createWindow()
  }
})

app.on('before-quit', () => {
  stopAPI() // Certifica-se de parar a API antes de sair
})

app.whenReady().then(() => {
  startAPI() // Inicia a API
  createWindow() // Cria a janela principal
})
