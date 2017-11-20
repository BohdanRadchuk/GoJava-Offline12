import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.Scanner;



public class FilesDownload {
public static final String LOAD_FROM = "files/links.txt";
public static final String SAVE_TO = "files/cache/";
public ArrayList<URL> urls = new ArrayList<>();
public ArrayList<String> names = new ArrayList<>();

    public void fileLinksReading() throws FileNotFoundException, MalformedURLException {
        File file = new File(LOAD_FROM);
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            this.names.add(scanner.nextLine());
        }
        for (int i = 0; i<names.size(); i++){
            URL url = new URL(names.get(i));
            urls.add(url);
        }
    }

    public ArrayList<String> shortNames (){
        ArrayList<String> shortNames = new ArrayList<>();
        for (int i = 0; i<urls.size(); i++) {
            String[] strings = names.get(i).split("/");
            shortNames.add(strings[strings.length - 1]);
        }
    return shortNames;
    }


    public void filesDownload (URL url, String name) throws IOException {

            long timer = System.currentTimeMillis();
            ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
            FileOutputStream fileOutputStream = new FileOutputStream(SAVE_TO+name);
            fileOutputStream.getChannel().transferFrom(readableByteChannel, 0 , Long.MAX_VALUE);
            timer = (-1)*(timer - System.currentTimeMillis());
            System.out.println("Файл: " + name + " загружен за " + timer + " милисикунд" );


    }
}
