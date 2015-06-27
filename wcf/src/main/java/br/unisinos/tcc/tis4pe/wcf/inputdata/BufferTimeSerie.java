package br.unisinos.tcc.tis4pe.wcf.inputdata;

import java.util.Map;
import java.util.TreeMap;

import org.joda.time.DateTime;

public class BufferTimeSerie {

	private Map<DateTime, Integer> contentMap;
	private boolean available;
	
	public BufferTimeSerie(){
		this.available = false;
		this.contentMap = new TreeMap<DateTime, Integer>();
	}

	public synchronized void set(Map<DateTime, Integer> timeSerie) {
		while (this.available == true) {
			try {
				System.out.println("DataHandler esperando...");
				wait();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		System.out.println("DataHandler colocou " + timeSerie.size() + " itens");
		this.contentMap = timeSerie;
		this.available = true;
		notifyAll();
	}

	public synchronized Map<DateTime, Integer> get() {
		while (this.available == false) {
			try {
				System.out.println("Controller esperando...");
				wait();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("Controller leu: " + this.contentMap.size() + " itens");
		this.available = false;
		notifyAll();
		return this.contentMap;
	}
}
