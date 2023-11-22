import java.util.Scanner;
import java.util.Arrays;

public class MemoryPlacementSimulator {
    static void bestFit(int blockSize[], int m, int processSize[], int n, int remBlockSize[]) {
        int allocation[] = new int[n];
        for (int i = 0; i < allocation.length; i++) {
            allocation[i] = -1;
        }
        for (int i = 0; i < n; i++) {
            int bestIdx = -1;
            for (int j = 0; j < m; j++) {
                if (blockSize[j] >= processSize[i]) {
                    if (bestIdx == -1)
                        bestIdx = j;
                    else if (blockSize[bestIdx] > blockSize[j])
                        bestIdx = j;
                }
            }
            if (bestIdx != -1) {
                allocation[i] = bestIdx;
                blockSize[bestIdx] -= processSize[i];
                remBlockSize[i] = blockSize[bestIdx];
            }
        }

        System.out.println("\nBest Fit:");
        displayMemoryStatus(allocation, processSize, remBlockSize);
    }

    static void firstFit(int blockSize[], int m, int processSize[], int n, int remBlockSize[]) {
        int allocation[] = new int[n];
        for (int i = 0; i < allocation.length; i++) {
            allocation[i] = -1;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (blockSize[j] >= processSize[i]) {
                    allocation[i] = j;
                    blockSize[j] -= processSize[i];
                    remBlockSize[i] = blockSize[j];
                    break;
                }
            }
        }

        System.out.println("\nFirst Fit:");
        displayMemoryStatus(allocation, processSize, remBlockSize);
    }

    static void nextFit(int blockSize[], int m, int processSize[], int n, int remBlockSize[]) {
        int allocation[] = new int[n];
        for (int i = 0; i < allocation.length; i++) {
            allocation[i] = -1;
        }
        int j = 0;
        for (int i = 0; i < n; i++) {
            int count = 0;
            while (count < m) {
                count++;
                if (blockSize[j] >= processSize[i]) {
                    allocation[i] = j;
                    blockSize[j] -= processSize[i];
                    remBlockSize[i] = blockSize[j];
                    break;
                }
                j = (j + 1) % m;
                count += 1;
            }
        }

        System.out.println("\nNext Fit:");
        displayMemoryStatus(allocation, processSize, remBlockSize);
    }

    static void worstFit(int blockSize[], int m, int processSize[], int n, int remBlockSize[]) {
        int allocation[] = new int[n];
        for (int i = 0; i < allocation.length; i++) {
            allocation[i] = -1;
        }
        for (int i = 0; i < n; i++) {
            int wstIdx = -1;
            for (int j = 0; j < m; j++) {
                if (blockSize[j] >= processSize[i]) {
                    if (wstIdx == -1)
                        wstIdx = j;
                    else if (blockSize[wstIdx] < blockSize[j])
                        wstIdx = j;
                }
            }
            if (wstIdx != -1) {
                allocation[i] = wstIdx;
                blockSize[wstIdx] -= processSize[i];
                remBlockSize[i] = blockSize[wstIdx];
            }
        }

        System.out.println("\nWorst Fit:");
        displayMemoryStatus(allocation, processSize, remBlockSize);
    }

    static void displayMemoryStatus(int allocation[], int processSize[], int remBlockSize[]) {
        System.out.println("Process No.\tProcess Size\tBlock no.\tRemaining Block Size");
        for (int i = 0; i < allocation.length; i++) {
            System.out.print(" " + (i + 1) + "\t\t" + processSize[i] + "\t\t");
            if (allocation[i] != -1) {
                System.out.print((allocation[i] + 1) + "\t\t" + remBlockSize[i]);
            } else {
                System.out.print("Not Allocated" + "\t" + remBlockSize[i]);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int m, n, num;
        Scanner in = new Scanner(System.in);

        System.out.print("Enter how many number of blocks you want to enter: ");
        m = in.nextInt();
        int remBlockSize[] = new int[m];
        int blockSize[] = new int[m];
        for (int i = 0; i < m; i++) {
            System.out.print("Enter Data " + (i + 1) + ": ");
            num = in.nextInt();
            blockSize[i] = num;
        }

        System.out.print("Enter how many number of processes you want to enter: ");
        n = in.nextInt();
        int processSize[] = new int[n];
        for (int i = 0; i < n; i++) {
            System.out.print("Enter Data " + (i + 1) + ": ");
            num = in.nextInt();
            processSize[i] = num;
        }

        int[] blockSizeCopy = Arrays.copyOf(blockSize, blockSize.length);
        int[] remBlockSizeCopy = Arrays.copyOf(blockSize, blockSize.length);

        bestFit(blockSizeCopy, m, processSize, n, remBlockSizeCopy);
        blockSizeCopy = Arrays.copyOf(blockSize, blockSize.length);
        remBlockSizeCopy = Arrays.copyOf(blockSize, blockSize.length);
        firstFit(blockSizeCopy, m, processSize, n, remBlockSizeCopy);
        blockSizeCopy = Arrays.copyOf(blockSize, blockSize.length);
        remBlockSizeCopy = Arrays.copyOf(blockSize, blockSize.length);
        nextFit(blockSizeCopy, m, processSize, n, remBlockSizeCopy);
        blockSizeCopy = Arrays.copyOf(blockSize, blockSize.length);
        remBlockSizeCopy = Arrays.copyOf(blockSize, blockSize.length);
        worstFit(blockSizeCopy, m, processSize, n, remBlockSizeCopy);
    }
}
