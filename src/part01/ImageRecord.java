package part01;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ImageRecord {
    private int id;
    private String title;
    private String description;
    private ImageType genre;
    private LocalDate dateTaken;
    private String thumbnail;

    public ImageRecord(int id, String title, String description, ImageType genre, LocalDate dateTaken, String thumbnail) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.genre = genre;
        this.dateTaken = dateTaken;
        this.thumbnail = thumbnail;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public ImageType getGenre() {
        return genre;
    }

    public LocalDate getDateTaken() {
        return dateTaken;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    // Setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setGenre(ImageType genre) {
        this.genre = genre;
    }

    public void setDateTaken(LocalDate dateTaken) {
        this.dateTaken = dateTaken;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        String formattedDate = dateTaken.format(formatter);
        return String.format("ID: %d, Title: %s, Description: %s, Genre: %s, Date Taken: %s, Thumbnail: %s",
                id, title, description, genre, formattedDate, thumbnail);
    }
}
