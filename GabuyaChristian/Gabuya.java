/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package gabuya;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

/**
 * Spotify Playlist Manager with Undo/Redo
 */
public class Gabuya {

    private static ArrayList<String> playlist = new ArrayList<>();
    private static Stack<ArrayList<String>> undoStack = new Stack<>();
    private static Stack<ArrayList<String>> redoStack = new Stack<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;

        do {
            displayMenu();
            choice = getChoice();

            switch (choice) {
                case 1 -> addSong();
                case 2 -> removeLastSong();
                case 3 -> undo();
                case 4 -> redo();
                case 5 -> viewPlaylist();
                case 6 -> System.out.println("Exiting... Goodbye!");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 6);
    }

    private static void displayMenu() {
        System.out.println("\n--- Spotify Playlist Menu ---");
        System.out.println("1. Add song");
        System.out.println("2. Remove last song");
        System.out.println("3. Undo");
        System.out.println("4. Redo");
        System.out.println("5. View playlist");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
    }

    private static int getChoice() {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Enter a number between 1 and 6.");
            scanner.next(); // discard invalid input
        }
        return scanner.nextInt();
    }

    private static void addSong() {
        scanner.nextLine(); // consume leftover newline
        System.out.print("Enter song name to add: ");
        String song = scanner.nextLine();

        pushToUndoStack();
        redoStack.clear();
        playlist.add(song);

        System.out.println("Added \"" + song + "\" to the playlist.");
    }

    private static void removeLastSong() {
        if (playlist.isEmpty()) {
            System.out.println("Playlist is already empty. Nothing to remove.");
            return;
        }

        pushToUndoStack();
        redoStack.clear();
        String removed = playlist.remove(playlist.size() - 1);

        System.out.println("Removed \"" + removed + "\" from the playlist.");
    }

    private static void undo() {
        if (undoStack.isEmpty()) {
            System.out.println("Nothing to undo.");
            return;
        }

        pushToRedoStack();
        playlist = undoStack.pop();
        System.out.println("Undo performed.");
    }

    private static void redo() {
        if (redoStack.isEmpty()) {
            System.out.println("Nothing to redo.");
            return;
        }

        pushToUndoStack();
        playlist = redoStack.pop();
        System.out.println("Redo performed.");
    }

    private static void viewPlaylist() {
        if (playlist.isEmpty()) {
            System.out.println("Playlist is empty.");
            return;
        }

        System.out.println("\n--- Current Playlist ---");
        for (int i = 0; i < playlist.size(); i++) {
            System.out.println((i + 1) + ". " + playlist.get(i));
        }
    }

    private static void pushToUndoStack() {
        undoStack.push(new ArrayList<>(playlist));
    }

    private static void pushToRedoStack() {
        redoStack.push(new ArrayList<>(playlist));
    }
}
