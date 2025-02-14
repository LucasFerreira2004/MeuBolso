import styles from "./modal-add-orcamento.module.css";
import DatePicker from "../UI/DatePicker/date-picker";
import InputWithIcon from "../UI/InputsModal/input-modal";
import SelectedDespesas from "../UI/SelectedDespesa/selected-despesa";

type ModalAddOrcamentoProps = {
  valor: string;
  data: string; 
  onCloseAll: () => void;
  handleChangeValor: (e: React.ChangeEvent<HTMLInputElement>) => void;
  setCategoria: (categoriaId: number | null) => void;
  setData: (data: string) => void;
};

function ModalAddOrcamento({
  valor,
  data,
  onCloseAll,
  handleChangeValor,
  setCategoria,
  setData,
}: ModalAddOrcamentoProps) {
  return (
    <div
      className={styles.modalOverlay}
      onClick={(e: React.MouseEvent<HTMLDivElement>) => e.stopPropagation()}
    >
      <div className={styles.modalContent}>
        <div className={styles.headerModal}>
          <h3>Adicionar orçamento</h3>
          <button className={styles.closeButton} onClick={onCloseAll}>
            <img src="/assets/iconsModal/iconX.svg" alt="Fechar" />
          </button>
        </div>
        <InputWithIcon
          label="Valor: "
          type="text"
          iconSrc="/assets/iconsModalOrcamentos/money.svg"
          placeholder="R$ 0,00"
          value={valor}
          onChange={handleChangeValor}
        />
        <SelectedDespesas setCategoria={setCategoria} />
        <DatePicker
          label="Escolha uma data:"
          value={data}
          onChange={(date: string) => setData(date)} 
          iconsrc="/assets/iconsModalOrcamentos/date.svg"
        />
        <div className={styles.footerModal}>
          <button className={styles.saveButton}>Salvar</button>
        </div>
      </div>
    </div>
  );
}

export default ModalAddOrcamento;
