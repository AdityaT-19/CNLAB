//CRC-CCITT

import java.util.Scanner;

public class CN3 {
    static int calCRC(String data) {
        int crc = 0xFFFF;
        int polynomial = 0x1021;
        byte[] bytes = data.getBytes();
        for (byte b : bytes) {
            for (int i = 0; i < 8; i++) {
                boolean bit = ((b >> (7 - i) & 1) == 1);
                boolean c15 = ((crc >> 15 & 1) == 1);
                crc <<= 1;
                if (c15 ^ bit)
                    crc ^= polynomial;
            }
        }
        crc &= 0xffff;
        return crc;
    }

    static String encode(String data) {
        int crc = calCRC(data);
        return data + Integer.toHexString(crc);
    }

    static boolean verify(String data) {
        int crc = calCRC(data.substring(0, data.length() - 4));
        int crc2 = Integer.parseInt(data.substring(data.length() - 4), 16);
        return crc == crc2;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter the data to be sent : ");
        String data = in.next();
        String encoded = encode(data);
        System.out.println("Encoded data : " + encoded);
        System.out.print("Enter the data that was received : ");
        String received = in.next();
        if (verify(received))
            System.out.println("Data is correct.");
        else
            System.out.println("Data is incorrect.");
    }
}
