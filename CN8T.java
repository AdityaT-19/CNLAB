import java.util.Scanner;

public class CN8T {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter The Number of Packets : ");
        int n = scanner.nextInt();
        int tokens = 0;
        int bsize = 0;
        System.out.print("Enter The Bucket Size : ");
        bsize = scanner.nextInt();
        tokens = bsize;
        System.out.print("Enter The Outgoing Rate : ");
        int outrate = scanner.nextInt();
        int[] packets = new int[n];
        System.out.println("Enter The Packet Sizes in Order ");
        for (int i = 0; i < n; i++) {
            packets[i] = scanner.nextInt();
        }
        int i = 0, cycle = 0, remains = 0, sent = 0;
        boolean flag = false;
        System.out.println("Cycle\tPackets\tSent\tRemains");
        while (true) {
            cycle++;
            tokens = bsize - remains;
            if (packets[i] <= tokens) {
                if (remains + packets[i] <= outrate) {
                    sent = remains + packets[i];
                    remains = 0;
                } else {
                    remains += (packets[i] - outrate);
                    sent = outrate;
                }
                if (!flag) {
                    System.out.println(cycle + "\t" + packets[i] + "\t" + sent + "\t" + remains);
                    packets[i] = 0;
                } else
                    System.out.println(cycle + "\t---\t" + sent + "\t" + remains);
            } else {
                remains = bsize;
                if (remains <= outrate) {
                    sent = remains;
                    remains = 0;
                } else {
                    remains -= outrate;
                    sent = outrate;
                }
                if (!flag) {
                    System.out.println(cycle + "\t" + packets[i] + "\t" + sent + "\t" + remains);
                    packets[i] -= tokens;
                } else
                    System.out.println(cycle + "\t---\t" + sent + "\t" + remains);
            }
            if (packets[i] != 0)
                continue;
            else if (i == (packets.length - 1)) {
                flag = true;
                if (remains == 0) {
                    break;
                }
            } else
                i++;
        }
        scanner.close();
    }
}