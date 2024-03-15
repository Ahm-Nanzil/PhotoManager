package part01;

import java.time.LocalDate;
import java.util.ArrayList;

public class ImageManager {
    private ArrayList<ImageRecord> imageRecords;

    public ImageManager() {
        imageRecords = new ArrayList<>();
    }

    public void addImage(ImageRecord image) {
        imageRecords.add(image);
    }

    public ImageRecord searchId(int id) {
        for (ImageRecord image : imageRecords) {
            if (image.getId() == id) {
                return image;
            }
        }
        return null;
    }

    public ImageAlbum searchTitle(String str) {
        ImageAlbum result = new ImageAlbum();
        for (ImageRecord image : imageRecords) {
            if (image.getTitle().contains(str)) {
                result.addImage(image);
            }
        }
        return result;
    }

    public ImageAlbum searchDescription(String str) {
        ImageAlbum result = new ImageAlbum();
        for (ImageRecord image : imageRecords) {
            if (image.getDescription().contains(str)) {
                result.addImage(image);
            }
        }
        return result;
    }

    public ImageAlbum searchGenre(ImageType type) {
        ImageAlbum result = new ImageAlbum();
        for (ImageRecord image : imageRecords) {
            if (image.getGenre() == type) {
                result.addImage(image);
            }
        }
        return result;
    }

    public ImageAlbum searchDates(LocalDate start, LocalDate end) {
        ImageAlbum result = new ImageAlbum();
        for (ImageRecord image : imageRecords) {
            if (image.getDateTaken().compareTo(start) >= 0 && image.getDateTaken().compareTo(end) <= 0) {
                result.addImage(image);
            }
        }
        return result;
    }

    public ImageAlbum getAllImages() {
        ImageAlbum result = new ImageAlbum();
        for (ImageRecord image : imageRecords) {
            result.addImage(image);
        }
        return result;
    }
}
