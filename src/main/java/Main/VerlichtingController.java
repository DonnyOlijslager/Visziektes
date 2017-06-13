package Main;

import Domain.Gebruiker;
import Domain.Verlichting;
import datasource.AquariumDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;

/**
 * Created by Cas on 13-6-2017.
 */
public class VerlichtingController implements Initializable {
    private Gebruiker gebruiker;
    private AquariumDAO aquariumDAO;
    private int aquariumId;
    @FXML
    TableView<Verlichting> VerlichtingTableView;
    @FXML
    TableColumn VerlichtingDatumToevoeging;
    @FXML TableColumn VerlichtingNaam;
    @FXML TableColumn VerlichtingAquariumID;
    @FXML TableColumn LichtSterkte;
    @FXML
    Button nieuweInvoer;
    @FXML Button terug;

    public VerlichtingController(int aquariumId,Gebruiker gebruiker){
        this.aquariumId = aquariumId;
        this.gebruiker = gebruiker;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        VerlichtingDatumToevoeging.setCellValueFactory(new PropertyValueFactory<Verlichting,Timestamp>("datumToevoeging"));
        VerlichtingNaam.setCellValueFactory(new PropertyValueFactory<Verlichting, String>("naamApparaat"));
        VerlichtingAquariumID.setCellValueFactory(new PropertyValueFactory<Verlichting, Integer>("aquariumId"));
        LichtSterkte.setCellValueFactory(new PropertyValueFactory<Verlichting, String>("lichtsterkte"));
        try {
            aquariumDAO = new AquariumDAO();
            VerlichtingTableView.getItems().setAll(aquariumDAO.getVerlichting(aquariumId,gebruiker));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        terug.setOnAction(event -> {
            DagboekController dagboekController = new DagboekController(aquariumId,gebruiker);
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("dagboek.fxml"));
            loader.setController(dagboekController);
            Stage stage = (Stage)terug.getScene().getWindow();
            Parent root = null;
            try {
                root = loader.load();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Scene scene = new Scene(root, 1280, 720);
            stage.setScene(scene);
            stage.show();
        });

        nieuweInvoer.setOnAction(event -> {
            VerlichtingToevoegenController controller = new VerlichtingToevoegenController(aquariumId);
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("verlichtingToevoegen.fxml"));
            loader.setController(controller);
            Parent root = null;
            try {
                root = loader.load();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Stage stage = new Stage();
            Scene scene = new Scene(root, 800, 480);
            stage.setScene(scene);
            stage.initOwner(nieuweInvoer.getScene().getWindow());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
        });
    }
}
