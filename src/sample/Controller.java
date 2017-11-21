package sample;

import com.sun.xml.internal.bind.XmlAccessorFactory;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Controller {
    public Label Text1;
    public Label Text2;
    public Label Text3;
    private String CiudadSeleccionado;
    private String paisSeleccionado;
    public TextField txPaises;
    public ListView<String> lvPaises;
    public ListView<String> lvCiudades;
    public Label lbTitulo;
    public Label lbError;
    public Button Limpiar;

    private ObservableList<String> paises = FXCollections.observableArrayList();
    private ObservableList<String> listaCiudades = FXCollections.observableArrayList();
    private ObservableList<String> DetallesCiudades= FXCollections.observableArrayList();
    @FXML
    public void initialize() {
        lvPaises.setItems(paises);
        lvCiudades.setItems(listaCiudades);
        lvPaises.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println("Elemento viejo: " + oldValue + ", elemento nuevo:" + newValue);
                paisSeleccionado= newValue;
                listaCiudades.clear();
                try{
                    Connection con = Main.getConnection();
                    Statement stmt = con.createStatement();
                    String sql = "Select name from city where countryCode=(select Code from country where Name='"+ paisSeleccionado + "')";
                    ResultSet resultado = stmt.executeQuery(sql);
                    while (resultado.next()){
                        listaCiudades.add(resultado.getString(1));

                    }

                }catch (SQLException e){
                    System.out.println(e.getMessage());
                }
            }
        });

//        -------------------------


        lvCiudades.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println("Elemento viejo: " + oldValue + ", elemento nuevo:" + newValue);
               CiudadSeleccionado = newValue;
                try {
                    Connection con = Main.getConnection();
                    Statement stmt = con.createStatement();

                    String sql = "Select * from city where name='" + CiudadSeleccionado + "'";
                    ResultSet resultado = stmt.executeQuery(sql);
                    while (resultado.next()) {
                        Text1.setText(resultado.getString("id"));
                        Text2.setText(resultado.getString("CountryCode"));
                        Text3.setText(resultado.getString("District"));


                    }
                }catch (SQLException e){
                    System.out.println(e.getMessage());
                }
            }
        });









    }














    public void buscarPais(KeyEvent keyEvent) {

//        if (keyEvent.getCode() == KeyCode.ENTER){
//            paises.add(txPaises.getText());
//            txPaises.clear();
//        }
//lbTitulo.setText(String.valueOf(keyEvent.getCode()));
        paises.clear();
        String nombreBusqueda = txPaises.getText().trim();//el trim elimina los espacios que estan a la izquiera o derecha
        if (nombreBusqueda.length() >= 1) {//realiza la busqueda cuando aiga minimo un caracter escrito
            Connection con = Main.getConnection();
            try {
                Statement stmt = con.createStatement();
                String sql = " SELECT Name FROM country WHERE Name LIKE'" + nombreBusqueda + "%'";
                ResultSet resultado = stmt.executeQuery(sql);
                while (resultado.next()) {
                    paises.add(resultado.getString("Name"));
                }

                resultado.close();
            } catch (SQLException e) {
                lbError.setText(e.getMessage());
            }
        }
        if (nombreBusqueda.length() >= 1) {
            Connection con = Main.getConnection();
            try {
                Statement stmt = con.createStatement();
                String sql = " SELECT Name FROM country WHERE Name LIKE'" + nombreBusqueda + "%'";
                ResultSet resultado = stmt.executeQuery(sql);
                while (resultado.next()) {
                    paises.add(resultado.getString("Name"));
                }

                resultado.close();
            } catch (SQLException e) {
                lbError.setText(e.getMessage());
            }
        }


    }


    public void Limpia(ActionEvent actionEvent) {

    }
}
