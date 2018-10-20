import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;


import javax.management.timer.Timer;
import java.net.URLEncoder;
import java.util.List;
import java.util.Scanner;

public class GetInfo {


    public static void main(String[] args) {
        YoutubeDl youtubeDl = new YoutubeDl();
        MetadataHandler metadataHandler = new MetadataHandler();

        WebClient client = new WebClient();
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);
        client.getOptions().setUseInsecureSSL(true);

        String rank = null;
        String previousRank = null;
        String link = null;
        String title = null;

        Scanner scanner = new Scanner(System.in);
        System.out.print("Specify path to save new files: ");
        String path = scanner.next();

        try {
            String searchUrl = "https://www.top40.nl/top40";
            HtmlPage page = client.getPage(searchUrl);
            HtmlSpan htmlSpan = page.getFirstByXPath("/html/body/div[1]/div[3]/div[4]/div/div/div[1]/div[1]/div/div[2]/span");
            title = htmlSpan.asText();
            System.out.println(title);
            for (int j =2; j<45; j++) {
                if (j == 12 || j == 23 || j == 34) {
                    System.out.println("ads");
                } else {
                    for (int i = 1; i < 4; i++) {
                        if (i == 1) {
                            HtmlDivision htmlDivision = page.getFirstByXPath("/html/body/div[1]/div[3]/div[4]/div/div/div[2]/div[" + j + "]/div/div[1]");
                            rank = htmlDivision.asText();
                            System.out.println(rank);
                        } else if (i == 2) {
                            HtmlStrong htmlStrong = page.getFirstByXPath("/html/body/div[1]/div[3]/div[4]/div/div/div[2]/div[" + j + "]/div/div[" + i + "]/strong");
                            previousRank = htmlStrong.asText();
                            System.out.println(previousRank);
                        }else {
                            HtmlAnchor htmlAnchor = page.getFirstByXPath("/html/body/div[1]/div[3]/div[4]/div/div/div[2]/div[" + j + "]/div/a");
                            link = htmlAnchor.getHrefAttribute();
                            System.out.println(link);
                        }
                    }
                }

                if (previousRank.equals("-")){
                    String identifier = link.substring(link.length() - 11);
                    metadataHandler.AddMetadata(youtubeDl.YoutubeDownloader(identifier, rank, path), title, rank);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }

}
