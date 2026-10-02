import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PlayerStats {

    public static void main(String[] args) {
        final int MAX_PLAYERS = 80;
        String[] names = new String[MAX_PLAYERS];
        int[] scores = new int[MAX_PLAYERS];

        // 1. Read input file into parallel arrays
        readFile(names, scores, "playerdata.txt");

        // 2. Output parallel arrays to console
        System.out.println("--- All Player Statistics ---");
        displayPlayers(names, scores);

        // 3. Calculate and display average high score
        double avg = getAverageScore(names, scores);
        System.out.println("\nAverage High Score: " + String.format("%.2f", avg) + "\n");

        // 4. Sort in decreasing order by score
        sortByScoreDescending(names, scores);

        // 5. Output top players
        System.out.println("--- Top 10 Players ---");
        displayTopX(names, scores, 10);
    }

    /**
     * Reads player data line-by-line from playerdata.txt into parallel arrays.
     */
    public static void readFile(String[] names, int[] scores, String fileName) {
        int count = 0;
        try (Scanner fileScanner = new Scanner(new File(fileName))) {
            while (fileScanner.hasNextLine() && count < names.length) {
                String line = fileScanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }

                int lastSpace = line.lastIndexOf(' ');
                if (lastSpace != -1) {
                    names[count] = line.substring(0, lastSpace).trim();
                    scores[count] = Integer.parseInt(line.substring(lastSpace + 1).trim());
                    count++;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: Could not find file '" + fileName + "'.");
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid score format found in file.");
        }
    }

    /**
     * Displays all non-null players and their scores.
     */
    public static void displayPlayers(String[] names, int[] scores) {
        System.out.println(String.format("%-20s %-10s", "Player Name", "High Score"));
        System.out.println("----------------------------------");
        for (int i = 0; i < names.length; i++) {
            if (names[i] != null) {
                System.out.println(String.format("%-20s %-10d", names[i], scores[i]));
            }
        }
    }

    /**
     * Calculates and returns average high score.
     */
    public static double getAverageScore(String[] names, int[] scores) {
        int sum = 0;
        int count = 0;
        for (int i = 0; i < names.length; i++) {
            if (names[i] != null) {
                sum += scores[i];
                count++;
            }
        }
        return count == 0 ? 0.0 : (double) sum / count;
    }

    /**
     * Selection Sort in descending order keeping parallel arrays aligned.
     */
    public static void sortByScoreDescending(String[] names, int[] scores) {
        int count = 0;
        while (count < names.length && names[count] != null) {
            count++;
        }

        for (int i = 0; i < count - 1; i++) {
            int maxIdx = i;
            for (int j = i + 1; j < count; j++) {
                if (scores[j] > scores[maxIdx]) {
                    maxIdx = j;
                }
            }
            // Swap scores
            int tempScore = scores[i];
            scores[i] = scores[maxIdx];
            scores[maxIdx] = tempScore;

            // Swap names
            String tempName = names[i];
            names[i] = names[maxIdx];
            names[maxIdx] = tempName;
        }
    }

    /**
     * Displays top X players.
     */
    public static void displayTopX(String[] names, int[] scores, int topX) {
        System.out.println(String.format("%-20s %-10s", "Player Name", "High Score"));
        System.out.println("----------------------------------");
        int count = 0;
        for (int i = 0; i < names.length && count < topX; i++) {
            if (names[i] != null) {
                System.out.println(String.format("%-20s %-10d", names[i], scores[i]));
                count++;
            }
        }
    }
}