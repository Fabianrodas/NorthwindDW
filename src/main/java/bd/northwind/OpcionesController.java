package bd.northwind;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import modelo.Graficos;
import modelo.StageSingleton;

public class OpcionesController implements Initializable{
    
    private Graficos graficosS = Graficos.getInstancia();
    @FXML Button salir;
    @FXML AnchorPane root;
    private double xOffset = 0;
    private double yOffset = 0;
    private Stage stage;
    private Stage mainStage;
    private StageSingleton stagesingleton = StageSingleton.getInstancia();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        root.setOnMouseEntered(e -> {
            stage = (Stage) salir.getScene().getWindow();
        });
        
        salir.setOnMouseClicked(e -> {
                stage.close();
                mainStage = stagesingleton.getStage();
                if(mainStage != null){
                    mainStage.show();
                }
        });
        
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            stage = (Stage) salir.getScene().getWindow();
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
    }
    
    @FXML
    private void descargarPDF() {
        if (graficosS.getGraficos() == null) {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setHeaderText("Espera un momento");
            alerta.setContentText("Primero mira algún gráfico antes de descargarme :)");
            alerta.showAndWait();
        } else {
            ArrayList<ImageView> imagenes = graficosS.getGraficos();
            try {
                String home = System.getProperty("user.home");
                String downloadsPath = home + File.separator +
                    (new File(home + File.separator + "Descargas").exists() ? "Descargas" : "Downloads");
                String outputFilePath = downloadsPath + File.separator + "NorthwindDashboard.pdf";
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
                for (ImageView imageView : imagenes) {
                    javafx.scene.image.Image fxImage = imageView.getImage();
                    BufferedImage bufferedImage = SwingFXUtils.fromFXImage(fxImage, null);
                    File tempFile = File.createTempFile("image", ".png");
                    ImageIO.write(bufferedImage, "png", tempFile);
                    Image pdfImage = new Image(com.itextpdf.io.image.ImageDataFactory.create(tempFile.getAbsolutePath()));
                    document.add(pdfImage);
                    tempFile.delete();
                }
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

    @FXML
    private void descargarArchivo() {
        InputStream inputStream = getClass().getResourceAsStream("/northwindDW.sql");
        if (inputStream == null) {
            System.err.println("Archivo no encontrado en el classpath.");
            return;
        }
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar archivo como...");
        fileChooser.setInitialFileName("northwindDW.sql");
        File archivoDestino = fileChooser.showSaveDialog(new Stage());
        if (archivoDestino != null) {
            try (FileOutputStream outputStream = new FileOutputStream(archivoDestino)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                System.out.println("Archivo descargado con éxito.");
            } catch (IOException e) {
                System.err.println("Error al descargar el archivo: " + e.getMessage());
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    System.err.println("Error al cerrar el InputStream: " + e.getMessage());
                }
            }
        }
    }
    
    @FXML
    private void generarQR() {
        String qrContent = "https://drive.google.com/file/d/1YnJoutkM3zXiPy4Nfnfj8VCNIi167Rb-/view?usp=sharing";
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(qrContent, BarcodeFormat.QR_CODE, 300, 300);
            BufferedImage bufferedImage = new BufferedImage(300, 300, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < 300; x++) {
                for (int y = 0; y < 300; y++) {
                    bufferedImage.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
                }
            }
            WritableImage qrImage = SwingFXUtils.toFXImage(bufferedImage, null);
            ImageView qrImageView = new ImageView(qrImage);
            VBox root = new VBox(qrImageView);
            root.setStyle("-fx-alignment: center; -fx-padding: 20;");
            Stage qrStage = new Stage();
            qrStage.setTitle("QR Code Viewer");
            qrStage.setScene(new Scene(root));
            qrStage.show();
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}
