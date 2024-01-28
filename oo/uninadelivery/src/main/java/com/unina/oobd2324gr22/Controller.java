package demo;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import demo.entity.DAO.AccountDAO;
import demo.entity.DAO.AccountDAOPostgre;
import demo.entity.DTO.Account;
import demo.utils.SHA256;
import javafx.scene.Node;

public class Controller {

  @FXML
  private TextField emailTextField;

  @FXML
  private PasswordField passwordTextField;

  @FXML
  private Button loginButton;

  public void loginButtonAction(ActionEvent event) {
    if (!emailTextField.getText().isBlank() && !passwordTextField.getText().isBlank()) {
      //Account client = checkLogin(emailTextField.getText(), passwordTextField.getText());
      Account client = null; // TODO: rimuovere questa riga e decommentare la precedente
      if (client == null) { // TODO!: ricorda di cambiare in !=
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Ordini.fxml"));
        Parent root;
        try {
          // TODO : Trasformare questa parte di codice in un metodo per il cambio pagina con passaggio di account come dato
          root = loader.load();
          OrdiniController controller = loader.getController();
          LocalDate bdate = LocalDate.of(1999, 12, 31);
          client = new Account("Gennaro", "De Gregorio", "gdg@gmail.com",bdate , "gdg.jpg", "password", null);
          controller.setAccount(client);
          Scene scene = new Scene(root);
          Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
          stage.setScene(scene);
          stage.show();
        } catch (IOException e) {
          e.printStackTrace();
        }
      } else {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText("Errore di login");
        alert.setContentText("Email e/o password errati");
        alert.showAndWait();
      }
    } else {
      Alert alert = new Alert(AlertType.ERROR);
      alert.setTitle("Errore");
      alert.setHeaderText("Errore di login");
      alert.setContentText("Inserisci email e password");
      alert.showAndWait();
    }
  }

  @FXML
  private Button esciButton;

  public void esciButtonAction(ActionEvent event) {
    Stage stage = (Stage) esciButton.getScene().getWindow();
    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setTitle("Exit");
    alert.setHeaderText("Stai per uscire!");
    alert.setContentText("Confermi?");
    Optional<javafx.scene.control.ButtonType> result = alert.showAndWait();
    if (result.get() == javafx.scene.control.ButtonType.OK){
      stage.close();
    } else {
      alert.close();
    }
  }

  private Account checkLogin(String email, String password) throws Exception {
    SHA256 sha256 = new SHA256();
    AccountDAO AccountDAO = new AccountDAOPostgre();
    Account clientToCheck = AccountDAO.getAccountByEmail(email);
    try{
      if (clientToCheck.getEmail() == email && clientToCheck.getPassword() == sha256.toSHA256(password)) {
        return clientToCheck;
      } else {
        return null;
      }
    } catch (Exception e) {
      throw new Exception("Errore durante il login: " + e.getMessage());
    }
  }
}
