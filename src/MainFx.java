import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainFx extends Application {
    private static final int PANE_ROOT_HEIGHT = 800;
    private static final int PANE_ROOT_WIDTH = 800;
    public static final String SAVE_TO = "files/cache/";


    public static void main(String[] args) {
        launch(args);
    }

    public void player (Pane root) throws IOException {

        FilesDownload filesDownload = new FilesDownload();
        filesDownload.fileLinksReading();
        for (int i = 0; i < filesDownload.urls.size(); i++) {
            Button shows = new Button("SHOW");
            shows.setTranslateY(60*(i+1));
            filesDownload.shortNames();

            ImageView imageView = new ImageView();
            imageView.setFitWidth(500);
            imageView.setFitHeight(500);
            int finalI = i;
            shows.setOnAction(event-> {
                URL path = new URL(SAVE_TO + filesDownload.shortNames().get(finalI));
                        File file = new File(SAVE_TO + filesDownload.shortNames().get(finalI));
                        if (file.exists()) {
                            System.out.println("FileExits");
                            Image image = new Image(.openStream());
                            Platform.runLater(() -> {imageView.setImage(image);});

                            root.getChildren().add(imageView);

                        } else try {
                            filesDownload.filesDownload(filesDownload.urls.get(finalI), filesDownload.shortNames().get(finalI));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                root.getChildren().addAll(shows);
        }
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Pane root = new Pane();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setHeight(PANE_ROOT_HEIGHT);
        primaryStage.setWidth(PANE_ROOT_WIDTH);
        primaryStage.setTitle("Files Player");
        primaryStage.show();
        player(root);
    }
}
