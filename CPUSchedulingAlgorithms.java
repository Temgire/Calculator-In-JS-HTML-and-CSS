import java.util.*;

class Process {
    int pid;
    int arrivalTime;
    int burstTime;
    int priority;
    int startTime;
    int completionTime;

    public Process(int pid, int arrivalTime, int burstTime, int priority) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.startTime = 0;
        this.completionTime = 0;
    }

    public void displayProcessTable() {
        System.out.format("| %-10d | %-12d | %-10d | %-14d | %-16d |%n",
                pid, arrivalTime, burstTime, startTime, completionTime);
    }
}

public class CPUSchedulingAlgorithms {

    public static void FCFS(List<Process> processes) {
        int currentTime = 0;
        for (Process process : processes) {
            currentTime = Math.max(currentTime, process.arrivalTime);
            process.startTime = currentTime;
            currentTime += process.burstTime;
            process.completionTime = currentTime;
        }
    }

    public static void SJF(List<Process> processes) {
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));

        int currentTime = 0;
        PriorityQueue<Process> queue = new PriorityQueue<>(Comparator.comparingInt(p -> p.burstTime));

        while (!processes.isEmpty() || !queue.isEmpty()) {
            while (!processes.isEmpty() && processes.get(0).arrivalTime <= currentTime) {
                queue.add(processes.remove(0));
            }
            if (queue.isEmpty()) {
                currentTime = processes.get(0).arrivalTime;
                continue;
            }
            Process current = queue.poll();
            current.startTime = currentTime;
            currentTime += current.burstTime;
            current.completionTime = currentTime;
        }
    }

    public static void priorityScheduling(List<Process> processes) {
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));

        int currentTime = 0;
        PriorityQueue<Process> queue = new PriorityQueue<>(Comparator.comparingInt(p -> p.priority));

        while (!processes.isEmpty() || !queue.isEmpty()) {
            while (!processes.isEmpty() && processes.get(0).arrivalTime <= currentTime) {
                queue.add(processes.remove(0));
            }
            if (queue.isEmpty()) {
                currentTime = processes.get(0).arrivalTime;
                continue;
            }
            Process current = queue.poll();
            current.startTime = currentTime;
            currentTime += current.burstTime;
            current.completionTime = currentTime;
        }
    }

    public static void roundRobin(List<Process> processes, int timeQuantum) {
        int currentTime = 0;
        int index = 0;
        Queue<Process> queue = new LinkedList<>();

        while (index < processes.size() || !queue.isEmpty()) {
            if (queue.isEmpty()) {
                if (index < processes.size() && processes.get(index).arrivalTime > currentTime) {
                    currentTime = processes.get(index).arrivalTime;
                }
                queue.add(processes.get(index));
                index++;
            }
            Process current = queue.poll();
            current.startTime = currentTime;
            if (current.burstTime > timeQuantum) {
                currentTime += timeQuantum;
                current.burstTime -= timeQuantum;
                while (index < processes.size() && processes.get(index).arrivalTime <= currentTime) {
                    queue.add(processes.get(index));
                    index++;
                }
                queue.add(current);
            } else {
                currentTime += current.burstTime;
                current.completionTime = currentTime;
            }
        }
    }

    public static void displayProcessesTable(List<Process> processes) {
        System.out.println("+------------+--------------+------------+----------------+------------------+");
        System.out.println("| Process ID | Arrival Time | Burst Time | Start Time     | Completion Time  |");
        System.out.println("+------------+--------------+------------+----------------+------------------+");
        for (Process process : processes) {
            process.displayProcessTable();
        }
        System.out.println("+------------+--------------+------------+----------------+------------------+");
    }

    public static void calculateAndDisplayAverages(List<Process> processes) {
        int totalTurnaroundTime = 0;
        int totalWaitingTime = 0;

        for (Process process : processes) {
            totalTurnaroundTime += process.completionTime - process.arrivalTime;
            totalWaitingTime += process.completionTime - process.arrivalTime - process.burstTime;
        }

        double averageTurnaroundTime = (double) totalTurnaroundTime / processes.size();
        double averageWaitingTime = (double) totalWaitingTime / processes.size();

        System.out.println("Average Turnaround Time: " + averageTurnaroundTime);
        System.out.println("Average Waiting Time: " + averageWaitingTime);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int numProcesses = scanner.nextInt();

        List<Process> processes = new ArrayList<>();
        for (int i = 1; i <= numProcesses; i++) {
            System.out.print("Enter arrival time for Process " + i + ": ");
            int arrivalTime = scanner.nextInt();
            System.out.print("Enter burst time for Process " + i + ": ");
            int burstTime = scanner.nextInt();
            System.out.print("Enter priority for Process " + i + ": ");
            int priority = scanner.nextInt();
            processes.add(new Process(i, arrivalTime, burstTime, priority));
        }

        System.out.println("Initial Processes:");
        displayProcessesTable(processes);

        System.out.println("\nFirst Come First Serve (FCFS) Scheduling:");
        FCFS(new ArrayList<>(processes));
        System.out.println("\nProcesses after FCFS Scheduling:");
        displayProcessesTable(processes);
        calculateAndDisplayAverages(processes);

        processes.forEach(p -> {
            p.startTime = 0;
            p.completionTime = 0;
        });

        System.out.println("\nShortest Job First (SJF) Scheduling:");
        SJF(new ArrayList<>(processes));
        System.out.println("\nProcesses after SJF Scheduling:");
        displayProcessesTable(processes);
        calculateAndDisplayAverages(processes);

        processes.forEach(p -> {
            p.startTime = 0;
            p.completionTime = 0;
        });

        System.out.println("\nPriority Scheduling (Non-Preemptive):");
        priorityScheduling(new ArrayList<>(processes));
        System.out.println("\nProcesses after Priority Scheduling:");
        displayProcessesTable(processes);
        calculateAndDisplayAverages(processes);

        processes.forEach(p -> {
            p.startTime = 0;
            p.completionTime = 0;
        });

        System.out.print("Enter time quantum for Round Robin: ");
        int timeQuantum = scanner.nextInt();
        System.out.println("\nRound Robin Scheduling (Preemptive):");
        roundRobin(new ArrayList<>(processes), timeQuantum);
        System.out.println("\nProcesses after Round Robin Scheduling:");
        displayProcessesTable(processes);
        calculateAndDisplayAverages(processes);
    }
}
