package part01;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.time.LocalDate;

public class QUBImages {
    private static ImageManager imageManager = new ImageManager();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeDefaultImages();

        while (true) {
            printMainMenu();
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline


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
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid choice.");
                scanner.nextLine(); // Consume the invalid input
            }
        }

    }

    private static void printMainMenu() {
        System.out.println("Main Menu");
        System.out.println("1. Add Image");
        System.out.println("2. Search");
        System.out.println("3. Display All");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");
    }

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");

    private static void addImage() {
        System.out.println("Enter Image ID:");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // Check if ID already exists
        if (imageManager.searchId(id) != null) {
            System.out.println("Image with this ID already exists. Please enter a unique ID.");
            return; // Return to main menu
        }

        System.out.println("Enter Title:");
        String title = scanner.nextLine();

        System.out.println("Enter Description:");
        String description = scanner.nextLine();

        // Input validation for Genre
        ImageType genre = null;
        while (genre == null) {
            try {
                System.out.println("Enter Genre:");
                String genreStr = scanner.nextLine().toUpperCase(); // Convert to uppercase for enum matching
                genre = ImageType.valueOf(genreStr);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid genre. Please enter a valid genre.");
            }
        }

        System.out.println("Enter Date Taken (MM-dd-yyyy):"); // Modified format
        LocalDate dateTaken = null;
        while (dateTaken == null) {
            try {
                dateTaken = LocalDate.parse(scanner.nextLine(), dateFormatter); // Parse with formatter
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please enter date in MM-dd-yyyy format.");
            }
        }

        System.out.println("Enter Thumbnail:");
        String thumbnail = scanner.nextLine();

        ImageRecord newImage = new ImageRecord(id, title, description, genre, dateTaken, thumbnail);
        imageManager.addImage(newImage);
        System.out.println("Image added successfully.");
    }


    private static void search() {
        System.out.println("Search Menu");
        System.out.println("1. Search by ID");
        System.out.println("2. Search by Title");
        System.out.println("3. Search by Description");
        System.out.println("4. Search by Genre");
        System.out.println("5. Search by Dates");
        System.out.print("Enter your choice: ");
        int searchChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        ImageAlbum searchResult = null;

        switch (searchChoice) {
            case 1:
                System.out.println("Enter Image ID:");
                int id = scanner.nextInt();
                ImageRecord resultById = imageManager.searchId(id);
                if (resultById != null) {
                    searchResult = new ImageAlbum();
                    searchResult.addImage(resultById);
                } else {
                    System.out.println("Image not found.");
                }
                break;
            case 2:
                System.out.println("Enter Title:");
                String title = scanner.nextLine();
                searchResult = imageManager.searchTitle(title);
                break;
            case 3:
                System.out.println("Enter Description:");
                String description = scanner.nextLine();
                searchResult = imageManager.searchDescription(description);
                break;
            case 4:
                System.out.println("Enter Genre:");
                String genreStr = scanner.nextLine().toUpperCase(); // Convert to uppercase for enum matching
                ImageType genre = ImageType.valueOf(genreStr);
                searchResult = imageManager.searchGenre(genre);
                break;
            case 5:
                System.out.println("Enter Start Date (YYYY-MM-DD):");
                LocalDate startDate = LocalDate.parse(scanner.nextLine());
                System.out.println("Enter End Date (YYYY-MM-DD):");
                LocalDate endDate = LocalDate.parse(scanner.nextLine());
                searchResult = imageManager.searchDates(startDate, endDate);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                return;
        }

        if (searchResult != null) {
            System.out.println("Search Results:");
            displayImageAlbum(searchResult);
        }
    }

    private static void displayAll() {
        System.out.println("All Images:");
        ImageAlbum allImages = imageManager.getAllImages();
        displayImageAlbum(allImages);
    }

    private static void displayImageAlbum(ImageAlbum album) {
        ImageRecord current = album.getFirst();
        while (current != null) {
            System.out.println(current.toString());
            current = album.getNext();
        }
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

