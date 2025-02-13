import ProgressBar from "../ProgressBar/progress-bar";

function TotalOrcamentos (){
    return(
        <div>
            <header>
            <h3>Resumo Geral</h3>
            <ProgressBar/>
        </header>
        <main>
            <li>
                <p>Planejado:</p>
                <p>R$ 12.000,00</p>
            </li>
            <li>
                <p>Gasto:</p>
                <p>R$ 2.000,00</p>
            </li>
            <li>
                <span>Execedeu:</span>
                <p>R$ 0,00</p>
            </li>
        </main>
        </div>
        
        
    );
}

export default TotalOrcamentos;