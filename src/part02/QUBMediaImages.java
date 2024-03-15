package part02;

import console.Console;
import part01.ImageAlbum;
import part01.ImageManager;
import part01.ImageRecord;
import part01.ImageType;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import java.time.format.DateTimeFormatter;

public class QUBMediaImages {
    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");

    private static ImageManager imageManager = new ImageManager();
    private static Console console = new Console(true);

    public static void main(String[] args) {
        initializeDefaultImages();
        // Make the console GUI visible
        console.setVisible(true);
        console.setSize(800, 600); // Set the width and height according to your preference

        console.setBgColour(Color.black); // Set background color
        console.setFont(new Font("Arial", Font.PLAIN, 14)); // Set font
        console.setColour(Color.WHITE); // Set font color


        File imagesFolder = new File("Images");
        File[] imageFiles = imagesFolder.listFiles();

        if (imageFiles != null && imageFiles.length > 0) {
            for (File imageFile : imageFiles) {
                if (imageFile.isFile()) { // Check if it's a file
                    console.println("Displaying image: " + imageFile.getName());

                    try {
                        BufferedImage image = ImageIO.read(imageFile);
                        ImageIcon imageIcon = new ImageIcon(image);
                        console.print(imageIcon); // Display the image
                        console.println(); // Move to the next line
                    } catch (IOException e) {
                        console.println("Error loading the image: " + e.getMessage());
                    }
                }
            }
        } else {
            if (imagesFolder.exists()) {
                console.println("No image files found in the Images folder.");
            } else {
                console.println("Images folder not found.");
            }
        }

        while (true) {
            printMainMenu();
            try {
                int choice = Integer.parseInt(console.readLn());
                switch (choice) {
                    case 1:
                        addImage();
                        break;
                    case 2:
                        search();
                        break;
                    case 3:
                        displayAll();
                        break;
                    case 4:
                        console.println("Exiting...");
                        console.setVisible(false);
                        return;
                    default:
                        console.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                console.println("Invalid input. Please enter a number.");
            }
        }
    }

    private static void printMainMenu() {
        console.println("Main Menu");
        console.println("1. Add Image");
        console.println("2. Search");
        console.println("3. Display All");
        console.println("4. Exit");
        console.print("Enter your choice: ");
    }

    private static void addImage() {
        console.println("Enter Image ID:");
        int id = Integer.parseInt(console.readLn());
        if (imageManager.searchId(id) != null) {
            console.println("Image with this ID already exists. Please enter a unique ID.");
            return;
        }

        console.println("Enter Title:");
        String title = console.readLn();

        console.println("Enter Description:");
        String description = console.readLn();

        ImageType genre = null;
        while (genre == null) {
            console.println("Enter Genre:");
            try {
                genre = ImageType.valueOf(console.readLn().toUpperCase());
            } catch (IllegalArgumentException e) {
                console.println("Invalid genre. Please enter a valid genre.");
            }
        }

        console.println("Enter Date Taken (MM-dd-yyyy):"); // Modified format
        LocalDate dateTaken = null;
        while (dateTaken == null) {
            try {
                dateTaken = LocalDate.parse(console.readLn(), dateFormatter); // Parse with formatter
            } catch (DateTimeParseException e) {
                console.println("Invalid date format. Please enter date in MM-dd-yyyy format.");
            }
        }


        console.println("Enter Thumbnail:");
        String thumbnail = console.readLn();

        ImageRecord newImage = new ImageRecord(id, title, description, genre, dateTaken, thumbnail);
        imageManager.addImage(newImage);
        console.println("Image added successfully.");
    }

    private static void search() {
        console.println("Search Menu");
        console.println("1. Search by ID");
        console.println("2. Search by Title");
        console.println("3. Search by Description");
        console.println("4. Search by Genre");
        console.println("5. Search by Dates");
        console.print("Enter your choice: ");
        int searchChoice = Integer.parseInt(console.readLn());

        ImageAlbum searchResult = null;

        switch (searchChoice) {
            case 1:
                console.println("Enter Image ID:");
                int id = Integer.parseInt(console.readLn());
                ImageRecord resultById = imageManager.searchId(id);
                if (resultById != null) {
                    searchResult = new ImageAlbum();
                    searchResult.addImage(resultById);
                } else {
                    console.println("Image not found.");
                }
                break;
            case 2:
                console.println("Enter Title:");
                String title = console.readLn(); // Trim to remove leading/trailing whitespace
                System.out.println("Title entered: " + title); // Add this line for debugging
                searchResult = imageManager.searchTitle(title);
                break;
            case 3:
                console.println("Enter Description:");
                String description = console.readLn().trim(); // Trim to remove leading/trailing whitespace
                searchResult = imageManager.searchDescription(description);
                break;
            case 4:
                console.println("Enter Genre:");
                String genreStr = console.readLn().toUpperCase().trim(); // Convert to uppercase and trim
                ImageType genre = ImageType.valueOf(genreStr);
                searchResult = imageManager.searchGenre(genre);
                break;
            case 5:
                console.println("Enter Start Date (MM-dd-yyyy):");
                LocalDate startDate = LocalDate.parse(console.readLn());
                console.println("Enter End Date (MM-dd-yyyy):");
                LocalDate endDate = LocalDate.parse(console.readLn());
                searchResult = imageManager.searchDates(startDate, endDate);
                break;
            default:
                console.println("Invalid choice. Please try again.");
                return;
        }

        if (searchResult != null) {
            console.println("Search Results:");
            displayImageAlbum(searchResult);
        }
    }





    private static void displayImageAlbum(ImageAlbum album) {
        ImageRecord current = album.getFirst();
        while (current != null) {
            console.println(current.toString());
            current = album.getNext();
        }
    }

    private static void displayAll() {
        console.println("All Images:");
        ImageAlbum allImages = imageManager.getAllImages();
        displayImageAlbum(allImages);
    }

    private static void initializeDefaultImages() {
        // Adding default images
        imageManager.addImage(new ImageRecord(1, "Andromeda Galaxy", "Image of the Andromeda galaxy.", ImageType.ASTRONOMY, LocalDate.parse("01-01-2023", dateFormatter), "Andromeda.png"));
        imageManager.addImage(new ImageRecord(2, "Lanyon QUB", "An image of the QUB Lanyon building.", ImageType.ARCHITECTURE, LocalDate.parse("01-02-2023", dateFormatter), "LanyonQUB.png"));
        imageManager.addImage(new ImageRecord(3, "Kermit Plays Golf", "An image of Kermit the frog playing golf.", ImageType.SPORT, LocalDate.parse("01-03-2023", dateFormatter), "KermitGolf.png"));
        imageManager.addImage(new ImageRecord(4, "Mourne Mountains", "A panoramic view of the Mourne mountains.", ImageType.LANDSCAPE, LocalDate.parse("01-04-2023", dateFormatter), "Mournes.png"));
        imageManager.addImage(new ImageRecord(5, "Homer Simpson", "Homer Simpson- A portrait of the man.", ImageType.PORTRAIT, LocalDate.parse("01-03-2023", dateFormatter), "Homer.png"));
        imageManager.addImage(new ImageRecord(6, "Red Kite", "A Red Kite bird of prey in flight.", ImageType.NATURE, LocalDate.parse("01-04-2023", dateFormatter), "RedKite.png"));
        imageManager.addImage(new ImageRecord(7, "Central Park", "An overhead view of Central Park New York USA.", ImageType.AERIAL, LocalDate.parse("01-05-2023", dateFormatter), "CentralPark.png"));
        imageManager.addImage(new ImageRecord(8, "Apples", "A bunch of apples.", ImageType.FOOD, LocalDate.parse("01-06-2023", dateFormatter), "Apples.png"));
        imageManager.addImage(new ImageRecord(9, "Programming Meme", "A Chat GPT programming meme.", ImageType.OTHER, LocalDate.parse("01-07-2023", dateFormatter), "ChatGPT.png"));
    }
}
