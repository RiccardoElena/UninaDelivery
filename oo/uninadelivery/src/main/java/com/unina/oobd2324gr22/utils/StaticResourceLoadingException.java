package com.unina.oobd2324gr22.utils;

public class StaticResourceLoadingException extends IllegalStateException {

  /** Default constructor. */
  public StaticResourceLoadingException() {
    super(
        "Errore nel caricamento di pagina o stile. Assicurarsi che i file FXML e CSS abbiano lo"
            + " stesso nome e che esso sia correttamente specificato come fileName di default o al"
            + " cambio di scena.");
  }
}
