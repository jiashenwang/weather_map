package com.test;

import java.util.Random;
import java.security.InvalidParameterException;
import sun.net.*;
import java.net.*;
import java.lang.Integer;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.IOException;


public class MulticastListenerThread extends Thread
{
	private static final Random r = new Random();
	private MulticastSocket s;
	private InetAddress address;
	private static boolean sentinel = true;
	private OutputStream out;

	public MulticastListenerThread(String host, String group, int port, OutputStream out) throws InvalidParameterException, IOException, UnknownHostException
	{
		if(port <= 1000)
		{
			throw new InvalidParameterException("Illegal port value");
		}

		//address is likely to be same for all threads.
		//it would be nice if it could be static.
		//need to make sure its initialized once and only once
		//run() cannot catch checkedExceptions.
		address = InetAddress.getByName(host);
		s = new MulticastSocket(port);
		s.joinGroup(InetAddress.getByName(group));
		this.out = out;
	}

	public static void stopListening()
	{
		sentinel = false;
	}	

	public void run()
	{
		try
		{
			System.out.println("Starting\n");
			while(true == sentinel)
			{
				byte buf[] = new byte[1024];
				DatagramPacket pack = new DatagramPacket(buf, buf.length);
				s.receive(pack);
				System.out.println(pack.getAddress().toString() + ":" + pack.getPort());
                		out.write(pack.getData(),0,pack.getLength());
			}
			System.out.println("Stopping\n");
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

	public static void main(String args[])
	{
        /*
         fos1: HWB's Geiger Counter
         fos2: HWB's GPS Clock
         fos3: WXT-520 @ Lyons Peak
         fos4: WXT-510 @ HWB's (West Ramona)
         fos5: National Weather Service's sensors @ Cabrillo National Monument
         fos6: WXT-520 @ MW
         fos7: NOTHING
         fos8: WXT-520 @ Cabrillo National Monument, Zone 3
         fos9: WXT-520 @ TP
         fos10: ALL 150+ SDGE Sensors
         fos11: WXT-520 @ PA
         fos12: NOTHING
         fos13: WXT-520 @ RM
         fos14: WXT-520 @ SCI
         fos15: DAVIS @ SDSC (WEST'S ROOF)
         fos16: WXT-520 @ ML
         fos17: CR-10X @ LP
         fos18: WXT-520 @ SO
         fos19: WXT-520 @ BH
         fos20: WXT-520 @ WS
         fos21: WXT-520 @ PLC
         fos22: WXT-520 @ BMR
         fos23: WXT-520 @ SMERNS
         */
        
		//Test streams using three quicker and one longer multicasts
		try
		{
			//FileOutputStream fos1 = new FileOutputStream("fos1");
			//FileOutputStream fos2 = new FileOutputStream("fos2");
			FileOutputStream fos3 = new FileOutputStream("fos3");
			FileOutputStream fos4 = new FileOutputStream("fos4");
			FileOutputStream fos5 = new FileOutputStream("fos5");
			FileOutputStream fos6 = new FileOutputStream("fos6");
			//FileOutputStream fos7 = new FileOutputStream("fos7");
			FileOutputStream fos8 = new FileOutputStream("fos8");
			FileOutputStream fos9 = new FileOutputStream("fos9");
			FileOutputStream fos10 = new FileOutputStream("fos10");
			FileOutputStream fos11 = new FileOutputStream("fos11");
			//FileOutputStream fos12 = new FileOutputStream("fos12");
			FileOutputStream fos13 = new FileOutputStream("fos13");
			FileOutputStream fos14 = new FileOutputStream("fos14");
			FileOutputStream fos15 = new FileOutputStream("fos15");
			FileOutputStream fos16 = new FileOutputStream("fos16");
			FileOutputStream fos17 = new FileOutputStream("fos17");
			FileOutputStream fos18 = new FileOutputStream("fos18");
			FileOutputStream fos19 = new FileOutputStream("fos19");
			FileOutputStream fos20 = new FileOutputStream("fos20");
			FileOutputStream fos21 = new FileOutputStream("fos21");
			FileOutputStream fos22 = new FileOutputStream("fos22");
			FileOutputStream fos23 = new FileOutputStream("fos23");

			//MulticastListenerThread test1 = new MulticastListenerThread("172.16.42.86", "233.7.117.79", 4010, fos1);
			//MulticastListenerThread test2 = new MulticastListenerThread("172.16.42.86", "233.7.117.79", 4011, fos2);
			MulticastListenerThread test3 = new MulticastListenerThread("172.16.42.86", "233.7.117.79", 4020, fos3);
			MulticastListenerThread test4 = new MulticastListenerThread("172.16.42.86", "233.7.117.79", 4021, fos4);
			MulticastListenerThread test5 = new MulticastListenerThread("172.16.42.86", "233.7.117.79", 4030, fos5);
			MulticastListenerThread test6 = new MulticastListenerThread("172.16.42.86", "233.7.117.79", 4024, fos6);
			//MulticastListenerThread test7 = new MulticastListenerThread("172.16.42.86", "233.7.117.81", 4025, fos7);
			MulticastListenerThread test8 = new MulticastListenerThread("172.16.42.86", "233.7.117.82", 4026, fos8);
			MulticastListenerThread test9 = new MulticastListenerThread("172.16.42.86", "233.7.117.102", 4027, fos9);
			MulticastListenerThread test10 = new MulticastListenerThread("172.16.42.86", "233.7.117.103", 4444, fos10);
			MulticastListenerThread test11 = new MulticastListenerThread("172.16.42.86", "233.7.117.104", 4031, fos11);
			//MulticastListenerThread test12 = new MulticastListenerThread("172.16.42.86", "233.7.117.105", 4032, fos12);
			MulticastListenerThread test13 = new MulticastListenerThread("172.16.42.86", "233.7.117.106", 4033, fos13);
			MulticastListenerThread test14 = new MulticastListenerThread("172.16.42.86", "233.7.117.107", 4034, fos14);
			MulticastListenerThread test15 = new MulticastListenerThread("172.16.42.86", "233.7.117.110", 4038, fos15);
			MulticastListenerThread test16 = new MulticastListenerThread("172.16.42.86", "233.7.117.111", 4039, fos16);
			MulticastListenerThread test17 = new MulticastListenerThread("172.16.42.86", "233.7.117.112", 4040, fos17);
			MulticastListenerThread test18 = new MulticastListenerThread("172.16.42.86", "233.7.117.113", 4041, fos18);
			MulticastListenerThread test19 = new MulticastListenerThread("172.16.42.86", "233.7.117.114", 4042, fos19);
			MulticastListenerThread test20 = new MulticastListenerThread("172.16.42.86", "233.7.117.119", 4043, fos20);
			MulticastListenerThread test21 = new MulticastListenerThread("172.16.42.86", "233.7.117.123", 4044, fos21);
			MulticastListenerThread test22 = new MulticastListenerThread("172.16.42.86", "233.7.117.125", 4045, fos22);
			MulticastListenerThread test23 = new MulticastListenerThread("172.16.42.86", "233.7.117.128", 4046, fos23);

			//test1.start();
			//test2.start();
			test3.start();
			test4.start();
			test5.start();
			test6.start();
			//test7.start();
			test8.start();
			test9.start();
			test10.start();
			test11.start();
			//test12.start();
			test13.start();
			test14.start();
			test15.start();
			test16.start();
			test17.start();
			test18.start();
			test19.start();
			test20.start();
			test21.start();
			test22.start();
			test23.start();

			Thread.sleep(60000 * 10);
			MulticastListenerThread.stopListening();

			//fos1.close();
			//fos2.close();
			fos3.close();
			fos4.close();
			fos5.close();
			fos6.close();
			//fos7.close();
			fos8.close();
			fos9.close();
			fos10.close();
			fos11.close();
			//fos12.close();
			fos13.close();
			fos14.close();
			fos15.close();
			fos16.close();
			fos17.close();
			fos18.close();
			fos19.close();
			fos20.close();
			fos21.close();
			fos22.close();
			fos23.close();

		}catch(Exception e)
		{
			//catch different exceptions to handle errors in parameters
			System.out.println(e.getMessage());
		}
	}
}
