package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main extends Application {
    private static final String conexion = "jdbc:mysql://localhost:3306/world";
    private static final String usuario = "root";
    private static final String contra = "";
    private static Connection connection;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("vistas/inicio.fxml"));
        primaryStage.setResizable(false);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 750, 550));
        primaryStage.show();
        abrirCoxecion();
    }


    private static void abrirCoxecion() {
        try {
            connection = DriverManager.getConnection(conexion, usuario, contra);
            JOptionPane.showMessageDialog(null, "exito");
        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, "fallado");
        }

    }


    public  static  Connection getConnection(){
        return connection;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
