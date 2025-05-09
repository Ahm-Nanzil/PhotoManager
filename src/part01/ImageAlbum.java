package part01;


import java.util.ArrayList;

public class ImageAlbum {
    private ArrayList<ImageRecord> images;
    private int currentIndex;

    public ImageAlbum() {
        images = new ArrayList<>();
        currentIndex = -1;
    }

    public void addImage(ImageRecord image) {
        images.add(image);
    }

    public ImageRecord getFirst() {
        if (images.isEmpty())
            return null;
        currentIndex = 0;
        return images.get(0);
    }

    public ImageRecord getNext() {
        if (currentIndex >= 0 && currentIndex < images.size() - 1) {
            currentIndex++;
            return images.get(currentIndex);
        }
        return null;
    }

    public ImageRecord getPrevious() {
        if (currentIndex > 0 && currentIndex < images.size()) {
            currentIndex--;
            return images.get(currentIndex);
        }
        return null;
    }
}
