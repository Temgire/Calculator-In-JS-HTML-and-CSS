import java.util.*;

class PageReplacementSimulator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of frames: ");
        int numFrames = scanner.nextInt();

        System.out.print("Enter the reference string (comma-separated page numbers): ");
        String inputString = scanner.next();
        String[] inputArray = inputString.split(",");

        List<Integer> referenceString = new ArrayList<>();
        for (String pageNumber : inputArray) {
            referenceString.add(Integer.parseInt(pageNumber));
        }

        List<Integer> frames = new ArrayList<>();
        Map<Integer, Integer> pageTable = new HashMap<>();
        int pageFaults = 0;
        int currentIndex = 0;

        System.out.println("\nSimulating Page Replacement Algorithms:");

        System.out.println("+--------------+------------------------+");
        System.out.println("| Algorithm    | Memory Status          |");
        System.out.println("+--------------+------------------------+");

        while (currentIndex < referenceString.size()) {
            int page = referenceString.get(currentIndex);

            if (!frames.contains(page)) {
                pageFaults++;
                if (frames.size() < numFrames) {
                    frames.add(page);
                } else {
                    if (frames.isEmpty()) {
                        System.out.println("Error: Frames list is empty.");
                        break;
                    }

                    int replacedPage;
                    // Replace page based on the selected algorithm
                    System.out.println("| Page fault   | Replacing a page       |");
                    System.out.println("| Algorithm    | Memory Status          |");
                    System.out.println("| ------------ | ------------------------ |");
                    System.out.println("| 1. FIFO      |                        |");
                    System.out.println("| 2. LRU       |                        |");
                    System.out.println("| 3. SJF       |                        |");
                    System.out.print("Enter your choice: ");
                    int choice = scanner.nextInt();
                    switch (choice) {
                        case 1:
                            replacedPage = frames.get(0);
                            frames.remove(0);
                            break;
                        case 2:
                            replacedPage = getLRUPage(frames, pageTable);
                            frames.remove((Object) replacedPage);
                            break;
                        case 3:
                            replacedPage = getShortestJob(frames, referenceString.subList(currentIndex, referenceString.size()));
                            frames.remove((Object) replacedPage);
                            break;
                        default:
                            System.out.println("Invalid choice. Using FIFO.");
                            replacedPage = frames.get(0);
                            frames.remove(0);
                            break;
                    }
                    frames.add(page);
                }
            }

            pageTable.put(page, currentIndex);
            currentIndex++;

            displayMemoryStatus("Page fault", frames);
        }

        System.out.println("+--------------+------------------------+");
        System.out.println("\nPage Faults: " + pageFaults);
    }

    private static int getLRUPage(List<Integer> frames, Map<Integer, Integer> pageTable) {
        int minIndex = Integer.MAX_VALUE;
        int lruPage = -1;

        for (int frame : frames) {
            int lastUsedIndex = pageTable.get(frame);
            if (lastUsedIndex < minIndex) {
                minIndex = lastUsedIndex;
                lruPage = frame;
            }
        }
        return lruPage;
    }

    private static int getShortestJob(List<Integer> frames, List<Integer> remainingReferenceString) {
        Map<Integer, Integer> pageToNextReferenceIndex = new HashMap<>();

        for (int frame : frames) {
            if (remainingReferenceString.contains(frame)) {
                int nextIndex = remainingReferenceString.indexOf(frame);
                pageToNextReferenceIndex.put(frame, nextIndex);
            } else {
                pageToNextReferenceIndex.put(frame, Integer.MAX_VALUE);
            }
        }

        int shortestJobPage = Collections.min(pageToNextReferenceIndex.entrySet(),
                Comparator.comparing(Map.Entry::getValue)).getKey();

        return shortestJobPage;
    }

    private static void displayMemoryStatus(String algorithm, List<Integer> frames) {
        System.out.print("| " + algorithm + "   | ");
        for (int frame : frames) {
            System.out.print(frame + " ");
        }
        System.out.println("  |");
    }
}
