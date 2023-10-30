// leaky bucket

import java.util.ArrayList;
import java.util.Scanner;

class Packet {
	int size;
	int id;

	Packet(int size, int id) {
		this.size = size;
		this.id = id;
	}
}

class LeakyBucket {
	int bucketCapacity, outputRate, currentSize, noPackets;
	ArrayList<Packet> queue = new ArrayList<Packet>(noPackets);

	LeakyBucket(int bucketCapacity, int outputRate, int noPackets) {
		this.bucketCapacity = bucketCapacity;
		this.outputRate = outputRate;
		this.noPackets = noPackets;
		currentSize = 0;
	}

	void addPacket(Packet packet) {
		queue.add(packet);
		currentSize += packet.size;
	}

	void removePacket() {
		Packet packet = queue.remove(0);
		currentSize -= packet.size;
	}

	void stimulate() {
		Packet[] ipPackets = new Packet[noPackets];
		Scanner in = new Scanner(System.in);
		System.out.println("Enter the packet sizes : ");
		for (int i = 0; i < noPackets; i++)
			ipPackets[i] = new Packet(in.nextInt(), i + 1);
		System.out.println("Time\tPacket id\tPacket size\tSent\tRemaining\tDropped\tCurrent size");
		int time = 0;
		int i = 0;
		while (true) {
			time++;
			if (i < noPackets) {
				if (ipPackets[i].size + currentSize > bucketCapacity) {
					System.out.println(time + "\t" + ipPackets[i].id + "\t\t" + ipPackets[i].size + "\t\t" + "No" + "\t"
							+ "-" + "\t\t" + "Yes" + "\t" + currentSize);
					i++;
					continue;
				} else
					addPacket(ipPackets[i++]);
			}
			if (queue.isEmpty())
				break;
			Packet packet = queue.get(0);
			if (packet.size <= outputRate) {
				removePacket();
				System.out.println(time + "\t" + packet.id + "\t\t" + packet.size + "\t\t" + "Yes" + "\t" + currentSize
						+ "\t\t" + "-" + "\t" + currentSize);
			} else {
				currentSize -= outputRate;
				System.out.println(time + "\t" + packet.id + "\t\t" + packet.size + "\t\t" + "Yes" + "\t" + currentSize
						+ "\t\t" + "-" + "\t" + currentSize);
				packet.size -= outputRate;
				queue.set(0, packet);
			}
		}
	}
}

public class CN8L {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.print("Enter the bucket size : ");
		int bucketSize = in.nextInt();
		System.out.print("Enter the output rate : ");
		int outputRate = in.nextInt();
		System.out.print("Enter the number of packets : ");
		int noPackets = in.nextInt();
		LeakyBucket leakyBucket = new LeakyBucket(bucketSize, outputRate, noPackets);
		leakyBucket.stimulate();
	}
}