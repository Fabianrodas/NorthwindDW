package bd.northwind;

import Controlador.Grafico1Controller;
import Controlador.Grafico2Controller;
import Controlador.Grafico3Controller;
import Controlador.Grafico4Controller;
import Controlador.Grafico5Controller;
import Controlador.Grafico6Controller;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.imageio.ImageIO;
import modelo.Grafico1;
import modelo.Grafico2;
import modelo.Grafico3;
import modelo.Grafico4;
import modelo.Grafico5;
import modelo.Grafico6;
import modelo.GraficoSingleton;
import modelo.Graficos;
import modelo.StageSingleton;
import modelo.VecesFoto;

public class VentanaController implements Initializable {
    @FXML private Button salir;
    @FXML private AnchorPane root;
    private double xOffset = 0;
    private double yOffset = 0;
    private Stage stage;
    private Stage mainStage;
    @FXML private StackPane stack;
    
    private Grafico1Controller grafico1Controller = new Grafico1Controller();
    private Grafico2Controller grafico2Controller = new Grafico2Controller();
    private Grafico3Controller grafico3Controller = new Grafico3Controller();
    private Grafico4Controller grafico4Controller = new Grafico4Controller();
    private Grafico5Controller grafico5Controller = new Grafico5Controller();
    private Grafico6Controller grafico6Controller = new Grafico6Controller();
    private GraficoSingleton singleton = GraficoSingleton.getInstancia();
    private Graficos graficosS = Graficos.getInstancia();
    private StageSingleton stagesingleton = StageSingleton.getInstancia();
    private VecesFoto veces = VecesFoto.getInstancia();

    @FXML private AreaChart<String, Number> grafico1;
    @FXML private PieChart grafico2;
    @FXML private BarChart<String, Number> grafico3;
    @FXML private BarChart<String, Number> grafico4;
    @FXML private LineChart<String,Number> grafico5;
    @FXML private ScatterChart<String, Number> grafico6;
    @FXML private Label labelScreen;
    @FXML private Button btnGrafico;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        root.setOnMouseEntered(e -> {
            stage = (Stage) salir.getScene().getWindow();
        });
        
        if(veces.isCiclo()){
            labelScreen.setVisible(false);
            btnGrafico.setVisible(true);
            if(singleton.getGrafico() == 1) {
                cargarGrafico1();
                grafico1.setVisible(true);
            }
            if(singleton.getGrafico() == 2) {
                cargarGrafico2();
                grafico2.setVisible(true);
            }
            if(singleton.getGrafico() == 3) {
                cargarGrafico3();
                grafico3.setVisible(true);
            }
            if(singleton.getGrafico() == 4) {
                cargarGrafico4();
                grafico4.setVisible(true);
            }
            if(singleton.getGrafico() == 5) {
                cargarGrafico5();
                grafico5.setVisible(true);
            }
            if(singleton.getGrafico() == 6) {
                cargarGrafico6();
                grafico6.setVisible(true);
            } 
        } else {
            graficosS.setGraficos(obtenerGraficos());
        }
        
        salir.setOnMouseClicked(e -> {
                stage.close();
                mainStage = stagesingleton.getStage();
                mainStage.show();
            });
        
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
        
        ScaleTransition hoverIn = new ScaleTransition(Duration.millis(200), btnGrafico);
        hoverIn.setToX(1.05);
        hoverIn.setToY(1.05);
        ScaleTransition hoverOut = new ScaleTransition(Duration.millis(200), btnGrafico);
        hoverOut.setToX(1.0);
        hoverOut.setToY(1.0);
        btnGrafico.setOnMouseEntered(event -> {
            hoverIn.playFromStart();
            btnGrafico.setStyle("-fx-background-color: black; -fx-text-fill: white;");
        });
        btnGrafico.setOnMouseExited(event -> {
            hoverOut.playFromStart();
            btnGrafico.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-border-color: black;");
        });
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
        grafico2.setTitle("Dominio de Ventas por Categorías (1996-1998)");
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
            XYChart.Data<String, Number> dataPoint = new XYChart.Data<>(grafico.getCompanyname(),grafico.getNum_ventas());
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
            label.setFont(Font.font("Arial", 16));
            label.setFill(Color.BLACK); 
            label.setTranslateY(-15);
            dataPoint.setNode(label);
            series.getData().add(dataPoint);
            series.getData().add(new XYChart.Data<>(country, totalProveedores));
        }
        grafico6.getData().addAll(seriesMap.values());
        grafico6.setLegendVisible(false);
    }
    
    private ArrayList<ImageView> obtenerGraficos() {
        ArrayList<ImageView> imagenes = new ArrayList<>();
        int singletonActual = singleton.getGrafico();
        for (int i = 1; i <= 6; i++) {
            singleton.setGrafico(i);
            switch (singleton.getGrafico()) {
                case 1:
                    cargarGrafico1();
                    Platform.runLater(() -> {
                        grafico1.setVisible(true);
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Bounds bounds = grafico1.getBoundsInLocal();
                        WritableImage snapshot = new WritableImage((int) bounds.getWidth(), (int) bounds.getHeight());
                        grafico1.snapshot(null, snapshot);
                        ImageView imagenGrafico1 = new ImageView(snapshot);
                        imagenes.add(imagenGrafico1);
                        grafico1.setVisible(false);
                    });
                    break;
                case 2:
                    cargarGrafico2();
                    Platform.runLater(() -> {
                        grafico2.setVisible(true);
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Bounds bounds = grafico2.getBoundsInLocal();
                        WritableImage snapshot = new WritableImage((int) bounds.getWidth(), (int) bounds.getHeight());
                        grafico2.snapshot(null, snapshot);
                        ImageView imagenGrafico2 = new ImageView(snapshot);
                        imagenes.add(imagenGrafico2);
                        grafico2.setVisible(false);
                    });
                    break;
                case 3:
                    cargarGrafico3();
                    Platform.runLater(() -> {
                        grafico3.setVisible(true);
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Bounds bounds = grafico3.getBoundsInLocal();
                        WritableImage snapshot = new WritableImage((int) bounds.getWidth(), (int) bounds.getHeight());
                        grafico3.snapshot(null, snapshot);
                        ImageView imagenGrafico3 = new ImageView(snapshot);
                        imagenes.add(imagenGrafico3);
                        grafico3.setVisible(false);
                    });
                    break;
                case 4:
                    cargarGrafico4();
                    Platform.runLater(() -> {
                        grafico4.setVisible(true);
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Bounds bounds = grafico4.getBoundsInLocal();
                        WritableImage snapshot = new WritableImage((int) bounds.getWidth(), (int) bounds.getHeight());
                        grafico4.snapshot(null, snapshot);
                        ImageView imagenGrafico4 = new ImageView(snapshot);
                        imagenes.add(imagenGrafico4);
                        grafico4.setVisible(false);
                    });
                    break;
                case 5:
                    cargarGrafico5();
                    Platform.runLater(() -> {
                        grafico5.setVisible(true);
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Bounds bounds = grafico5.getBoundsInLocal();
                        WritableImage snapshot = new WritableImage((int) bounds.getWidth(), (int) bounds.getHeight());
                        grafico5.snapshot(null, snapshot);
                        ImageView imagenGrafico5 = new ImageView(snapshot);
                        imagenes.add(imagenGrafico5);
                        grafico5.setVisible(false);
                    });
                    break;
                case 6:
                    cargarGrafico6();
                    Platform.runLater(() -> {
                        grafico6.setVisible(true);
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Bounds bounds = grafico6.getBoundsInLocal();
                        WritableImage snapshot = new WritableImage((int) bounds.getWidth(), (int) bounds.getHeight());
                        grafico6.snapshot(null, snapshot);
                        ImageView imagenGrafico6 = new ImageView(snapshot);
                        imagenes.add(imagenGrafico6);
                        grafico6.setVisible(false);
                    });
                    break;
            }
        }
        singleton.setGrafico(singletonActual);
        veces.setCiclo(true);
        return imagenes;
    }
    
    @FXML
    private void descargarGrafico() {
        try {
            ArrayList<ImageView> imagenes = graficosS.getGraficos();
            ImageView grafico = imagenes.get(singleton.getGrafico() - 1);
            String home = System.getProperty("user.home");
            String downloadsPath = home + File.separator +
                (new File(home + File.separator + "Descargas").exists() ? "Descargas" : "Downloads");
            String outputFilePath = downloadsPath + File.separator + "Northwindgrafico" + String.valueOf(singleton.getGrafico()) + ".pdf";
            PdfWriter writer = new PdfWriter(new FileOutputStream(outputFilePath));
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            String titulo = "Northwind Dashboard";
            String universidad = "Universidad de Especialidades Espíritu Santo";
            String materia = "Base de Datos II" + " - " +"Periodo Ordinario II 2024";
            String integrantes = "Integrantes: Fabián Rodas, Paula Benalcázar y Juan Mateus\n";
            Paragraph tituloParagraph = new Paragraph(titulo)
                .setFontSize(20)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(10);
            Paragraph universidadParagraph = new Paragraph(universidad)
                .setFontSize(14)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(5);
            Paragraph materiaParagraph = new Paragraph(materia)
                .setFontSize(12)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(20);
            Paragraph integrantesParagraph = new Paragraph(integrantes)
                .setFontSize(12)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(50);
            document.add(tituloParagraph);
            document.add(universidadParagraph);
            document.add(materiaParagraph);
            document.add(integrantesParagraph);
            javafx.scene.image.Image fxImage = grafico.getImage();
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(fxImage, null);
            File tempFile = File.createTempFile("image", ".png");
            ImageIO.write(bufferedImage, "png", tempFile);
            Image pdfImage = new Image(com.itextpdf.io.image.ImageDataFactory.create(tempFile.getAbsolutePath()));
            document.add(pdfImage);
            tempFile.delete();
            document.close();
            System.out.println("PDF creado con éxito en: " + outputFilePath);
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setHeaderText("PDF Creado exitosamente.");
            alerta.setContentText("Puedes consultar el documento en Descargas.");
            alerta.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al crear el PDF: " + e.getMessage());
        }
    }
}
