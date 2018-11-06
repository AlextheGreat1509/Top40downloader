import java.io.*;

public class YoutubeDl {
    public String YoutubeDownloader(String identifier, String rank, String path) throws Exception {
        ProcessBuilder builder = new ProcessBuilder(
                "cmd.exe", "/c", "youtube-dl -i --extract-audio --audio-format mp3 --audio-quality 320 -o \"" + path + "/" + rank + " %(title)s.%(ext)s\" --add-metadata https://www.youtube.com/watch?v="+identifier);
        builder.redirectErrorStream(true);
        Process p = builder.start();
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        String mp3 = null;
        while (true) {
            line = r.readLine();
            if (line == null) {
                break;
            } else if(line.contains("Destination:")){
                mp3 = line.substring(line.indexOf(":") + 2);
                System.out.println(mp3);
            }
            System.out.println(line);
        }
        return mp3.trim();
    }
}
