import com.mpatric.mp3agic.*;

import java.io.File;
import java.io.IOException;

public class MetadataHandler {

    public void AddMetadata(String mp3, String title, String rank) throws InvalidDataException, IOException, UnsupportedTagException, NotSupportedException {

        File oldFile = new File(mp3);
        String year = title.substring(title.length() - 5, title.length() - 1 );

        Mp3File mp3file = new Mp3File(mp3);
        ID3v2 id3v2Tag;
        if (mp3file.hasId3v2Tag()) {
            id3v2Tag = mp3file.getId3v2Tag();
        } else {
            // mp3 does not have an ID3v2 tag, let's create one..
            id3v2Tag = new ID3v24Tag();
            mp3file.setId3v2Tag(id3v2Tag);
        }
        id3v2Tag.setTrack(rank);
        id3v2Tag.setAlbum("Nederlandse TOP 40 " + year + " * " + title + " *");
        String artist = id3v2Tag.getArtist();
        id3v2Tag.setAlbumArtist(artist);
        id3v2Tag.setYear(year);
        id3v2Tag.setGenreDescription("Top 40");
        String trackTitle = id3v2Tag.getTitle();

        String tempMp3 = mp3.substring(0, mp3.indexOf("-") + 2) + trackTitle + ".mp3.new";
        String newMp3 = mp3.substring(0, mp3.indexOf("-") + 2) + trackTitle + ".mp3";

        mp3file.save(tempMp3);
        File tempFile = new File(tempMp3);
        File newFile = new File(newMp3);
        oldFile.delete();
        tempFile.renameTo(newFile);


    }
}
