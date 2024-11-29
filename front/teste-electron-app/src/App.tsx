import { useState, useEffect } from 'react';
import reactLogo from './assets/react.svg';
import viteLogo from '/electron-vite.animate.svg';
import './App.css';
import axios from 'axios';

interface game {
  id: number;
  year: string;
  title: string;
  shortDescription: string;
}
function App() {
  // Estado para armazenar os dados da API
  const [games, setGames] = useState<game[]>([]); // Substitua "any[]" pelo tipo correto, se souber a estrutura dos dados
  const [error, setError] = useState<string | null>(null); // Para capturar erros

  // Função para buscar os dados
  const fetchData = async () => {
    try {
      const response = await axios.get('http://localhost:8080/games'); // Substitua pelo endpoint correto
      setGames(response.data); // Armazena os dados no estado
    } catch (err) {
      console.error('Erro ao buscar os dados:', err);
      setError('Erro ao buscar os dados da API.');
    }
  };

  // useEffect para carregar os dados ao montar o componente
  useEffect(() => {
    fetchData();
  }, []);

  return (
    <>
      <div>
        <a href="https://electron-vite.github.io" target="_blank">
          <img src={viteLogo} className="logo" alt="Vite logo" />
        </a>
        <a href="https://react.dev" target="_blank">
          <img src={reactLogo} className="logo react" alt="React logo" />
        </a>
      </div>
      <h1>Vite + React & Lucas F.</h1>
      <div className="card">
        <p>Lista de Jogos:</p>
        {/* Mostra mensagem de erro, se houver */}
        {error && <p style={{ color: 'red' }}>{error}</p>}

        {/* Renderiza os dados da API */}
        {games.length > 0 ? (
          <ul>
            {games.map((game, index) => (
              <li key={index}>
                {game.title} - {game.year}
              </li>
            ))}
          </ul>
        ) : (
          !error && <p>Carregando dados...</p>
        )}
      </div>
      <p className="read-the-docs">
        Click on the Vite and React logos to learn more
      </p>
      <div className='div-teste'>
        AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
      </div>
    </>
  );
}

export default App;
