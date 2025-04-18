package bd.northwind;

import Controlador.Grafico1Controller;
import Controlador.Grafico2Controller;
import Controlador.Grafico3Controller;
import Controlador.Grafico4Controller;
import Controlador.Grafico5Controller;
import Controlador.Grafico6Controller;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.Grafico1;
import modelo.Grafico2;
import modelo.Grafico3;
import modelo.Grafico4;
import modelo.Grafico5;
import modelo.Grafico6;
import modelo.GraficoSingleton;
import modelo.StageSingleton;
import javafx.animation.ScaleTransition;
import javafx.scene.chart.Chart;
import javafx.util.Duration;

public class MainController implements Initializable {

    @FXML private Button salir;
    @FXML private AnchorPane root;
    private double xOffset = 0;
    private double yOffset = 0;
    private Stage stage;
    
    private Grafico1Controller grafico1Controller = new Grafico1Controller();
    private Grafico2Controller grafico2Controller = new Grafico2Controller();
    private Grafico3Controller grafico3Controller = new Grafico3Controller();
    private Grafico4Controller grafico4Controller = new Grafico4Controller();
    private Grafico5Controller grafico5Controller = new Grafico5Controller();
    private Grafico6Controller grafico6Controller = new Grafico6Controller();
    private GraficoSingleton singleton = GraficoSingleton.getInstancia();
    private StageSingleton stagesingleton = StageSingleton.getInstancia();
    
    @FXML private AreaChart<String, Number> grafico1;
    @FXML private PieChart grafico2;
    @FXML private BarChart<String, Number> grafico3;
    @FXML private BarChart<String, Number> grafico4;
    @FXML private LineChart<String,Number> grafico5;
    @FXML private ScatterChart<String, Number> grafico6;
    private ArrayList<Chart> graficosMain = new ArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        salir.setOnAction(event -> Platform.exit());
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            stage = (Stage) salir.getScene().getWindow();
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
        
        cargarGrafico1();
        grafico1.setOnMouseClicked(e -> {
            singleton.setGrafico(1);
            ventanaNueva();
            stage = (Stage) salir.getScene().getWindow();
            stagesingleton.setStage(stage);
            stage.hide();
        });
        cargarGrafico2();
        grafico2.setOnMouseClicked(e -> {
            singleton.setGrafico(2);
            ventanaNueva();
            stage = (Stage) salir.getScene().getWindow();
            stagesingleton.setStage(stage);
            stage.hide();
        });
        cargarGrafico3();
        grafico3.setOnMouseClicked(e -> {
            singleton.setGrafico(3);
            ventanaNueva();
            stage = (Stage) salir.getScene().getWindow();
            stagesingleton.setStage(stage);
            stage.hide();
        });
        cargarGrafico4();
        grafico4.setOnMouseClicked(e -> {
            singleton.setGrafico(4);
            ventanaNueva();
            stage = (Stage) salir.getScene().getWindow();
            stagesingleton.setStage(stage);
            stage.hide();
        });
        cargarGrafico5();
        grafico5.setOnMouseClicked(e -> {
            singleton.setGrafico(5);
            ventanaNueva();
            stage = (Stage) salir.getScene().getWindow();
            stagesingleton.setStage(stage);
            stage.hide();
        });
        cargarGrafico6();
        grafico6.setOnMouseClicked(e -> {
            singleton.setGrafico(6);
            ventanaNueva();
            stage = (Stage) salir.getScene().getWindow();
            stagesingleton.setStage(stage);
            stage.hide();
        });
        
        graficosMain.add(grafico1);
        graficosMain.add(grafico2);
        graficosMain.add(grafico3);
        graficosMain.add(grafico4);
        graficosMain.add(grafico5);
        graficosMain.add(grafico6);
        for(Chart c : graficosMain){
            ScaleTransition hoverIn = new ScaleTransition(Duration.millis(200), c);
            hoverIn.setToX(1.05);
            hoverIn.setToY(1.05);
            ScaleTransition hoverOut = new ScaleTransition(Duration.millis(200), c);
            hoverOut.setToX(1.0);
            hoverOut.setToY(1.0);
            c.setOnMouseEntered(event -> {
                hoverIn.playFromStart();
                c.setStyle("-fx-background-color: #3F427D; -fx-cursor: hand;");
            });
            c.setOnMouseExited(event -> {
                hoverOut.playFromStart();
                c.setStyle("-fx-background-color: #272952; -fx-cursor: hand;");
            });   
        }
    }
    
    private void cargarGrafico1() {
        ArrayList<Grafico1> datos = grafico1Controller.Read();
        Map<Integer, List<Grafico1>> datosPorAnio = datos.stream().collect(Collectors.groupingBy(Grafico1::getAnio));

        for (Map.Entry<Integer, List<Grafico1>> entrada : datosPorAnio.entrySet()) {
            Integer anio = entrada.getKey();
            List<Grafico1> datosAnio = entrada.getValue();

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Año " + anio);

            for (Grafico1 grafico : datosAnio) {
                XYChart.Data<String, Number> dataPoint =
                        new XYChart.Data<>(grafico.getTrimestre(), grafico.getVentas());
                series.getData().add(dataPoint);
            
                Label label = new Label(String.valueOf(grafico.getVentas()));
                label.getStyleClass().add("chart-label");
                label.setStyle("-fx-font-size: 6px");
                dataPoint.setNode(label);
            }
            grafico1.getData().add(series);
            grafico1.setTitle("Ventas por Trimestre (1996-1998)");
        }
    }
    
    private void cargarGrafico2() {
        ArrayList<Grafico2> datos = grafico2Controller.Read();
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        for (Grafico2 grafico : datos) {
            PieChart.Data data = new PieChart.Data(
                grafico.getCategoria() + " (" + String.format("%.1f", grafico.getPorcentaje()) + "%)",
                grafico.getPorcentaje()
            );
            pieChartData.add(data);
        }
        grafico2.setData(pieChartData);
        grafico2.setTitle("Dominio de Ventas por Categorías");
        grafico2.setLegendVisible(false);
    }
    
    private void cargarGrafico3() {
        ArrayList<Grafico3> datos = grafico3Controller.Read();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Países más consumistas (1996-1998)");

        for (Grafico3 grafico : datos) {
            XYChart.Data<String, Number> dataPoint = 
                    new XYChart.Data<>(grafico.getPais(), grafico.getTotal());
            series.getData().add(dataPoint);
        }
        grafico3.getData().add(series);
        grafico3.setTitle("Países más consumistas (1996-1998)");
        grafico3.setLegendVisible(false);
    }
    
    private void cargarGrafico4() {
        ArrayList<Grafico4> datos = grafico4Controller.Read(); 
        grafico4.setTitle("Clientes más recurrentes");
        grafico4.setLegendVisible(false);

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (Grafico4 grafico : datos) {
            String companyName = grafico.getCompanyname();
            if (companyName.length() > 5) {
                companyName = companyName.substring(0, 5) + "...";  // Agregar "..." si se recorta
            }
            XYChart.Data<String, Number> dataPoint = new XYChart.Data<>(companyName,grafico.getNum_ventas());
            series.getData().add(dataPoint);
        }
        grafico4.getData().add(series);
    }

    private void cargarGrafico5() {
        ArrayList<Grafico5> datos = grafico5Controller.Read();
        grafico5.setTitle("Ventas por Empleado por Años");

        XYChart.Series<String, Number> seriesMargaret = new XYChart.Series<>();
        seriesMargaret.setName("Margaret Peacock");
        XYChart.Series<String, Number> seriesJanet = new XYChart.Series<>();
        seriesJanet.setName("Janet Leverling");
        XYChart.Series<String, Number> seriesNancy = new XYChart.Series<>();
        seriesNancy.setName("Nancy Davolio");
        XYChart.Series<String, Number> seriesAndrew = new XYChart.Series<>();
        seriesAndrew.setName("Andrew Fuller");
        XYChart.Series<String, Number> seriesRobert = new XYChart.Series<>();
        seriesRobert.setName("Robert King");

        for (Grafico5 grafico : datos) {
            String year = String.valueOf(grafico.getAnio());
            Number totalVentas = grafico.getVentas(); 
            switch (grafico.getEmpleado()) {
                case "Margaret Peacock":
                    seriesMargaret.getData().add(new XYChart.Data<>(year, totalVentas));
                    break;
                case "Janet Leverling":
                    seriesJanet.getData().add(new XYChart.Data<>(year, totalVentas));
                    break;
                case "Nancy Davolio":
                    seriesNancy.getData().add(new XYChart.Data<>(year, totalVentas));
                    break;
                case "Andrew Fuller":
                    seriesAndrew.getData().add(new XYChart.Data<>(year, totalVentas));
                    break;
                case "Robert King":
                    seriesRobert.getData().add(new XYChart.Data<>(year, totalVentas));
                    break;
                default:
                    System.err.println("Empleado desconocido: " + grafico.getEmpleado());
                    break;
            }
        }
        grafico5.getData().addAll(seriesMargaret, seriesJanet, seriesNancy, seriesAndrew, seriesRobert);
    }
    
    private void cargarGrafico6() {
        ArrayList<Grafico6> datos = grafico6Controller.Read();
        grafico6.setTitle("Países con mayor diversidad de Proveedores");

        Map<String, XYChart.Series<String, Number>> seriesMap = new HashMap<>();

        for (Grafico6 grafico : datos) {
            String country = grafico.getCountry();
            Number totalProveedores = grafico.getTotal_proveedores();
            seriesMap.putIfAbsent(country, new XYChart.Series<>());
            XYChart.Series<String, Number> series = seriesMap.get(country);
            series.setName(country);
            XYChart.Data<String, Number> dataPoint = new XYChart.Data<>(country, totalProveedores);
            Text label = new Text(totalProveedores.toString());
            label.setFont(Font.font("Arial", 7));
            label.setFill(Color.BLACK);
            label.setTranslateY(-10);
            dataPoint.setNode(label);
            series.getData().add(dataPoint);
            series.getData().add(new XYChart.Data<>(country, totalProveedores));
        }
        grafico6.getData().addAll(seriesMap.values());
        grafico6.setLegendVisible(false);
    }
    
    private void ventanaNueva() {
       try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("grafico" + ".fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(javafx.stage.StageStyle.UNDECORATED);
            double x = 550;
            double y = 200;
            stage.setX(x);
            stage.setY(y);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML 
    private void opciones() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("opciones" + ".fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(javafx.stage.StageStyle.UNDECORATED);
            double x = 550;
            double y = 200;
            stage.setX(x);
            stage.setY(y);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
