package br.unisinos.tcc.tis4pe.wcf.inputdata.webservices;

import java.util.Map;
import java.util.TreeMap;

import org.joda.time.DateTime;

public class BufferCloudWatch {

	private Map<DateTime, Integer> contentMap;
	private boolean available;
	
	public BufferCloudWatch(){
		this.available = false;
		this.contentMap = new TreeMap<DateTime, Integer>();
	}

	public synchronized void set(int idProducer, int avg) {
		while (this.available == true) {
			try {
				//System.out.println(":::Produtor #" + idProducer + " esperando...");
				wait();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		System.out.println("**** Percentual Médio de Uso da CPU: " + avg + " *****");
		this.contentMap.put(new DateTime().now(), avg);
		this.available = true;
		notifyAll();
	}

	public synchronized Map<DateTime, Integer> get(int idConsumidor) {
		while (this.available == false) {
			try {
				//System.out.println("Consumidor #" + idConsumidor
					//	+ " esperando...");
				wait();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//System.out.println("Consumidor #" + idConsumidor + " consumiu: "
			//	+ this.contentMap.size() + " itens");
		this.available = false;
		notifyAll();
		return this.contentMap;
	}
}
