package br.unisinos.tcc.tis4pe.wcf.inputdata.webservices;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.joda.time.DateTime;

import br.unisinos.tcc.tis4pe.wcf.util.PropertieReaderUtil;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClient;
import com.amazonaws.services.cloudwatch.model.Datapoint;
import com.amazonaws.services.cloudwatch.model.Dimension;
import com.amazonaws.services.cloudwatch.model.GetMetricStatisticsRequest;
import com.amazonaws.services.cloudwatch.model.GetMetricStatisticsResult;
//import com.amazonaws.services.elasticmapreduce.util.ResizeJobFlowStep.OnArrested;
//import com.amazonaws.services.identitymanagement.model.GetAccessKeyLastUsedRequest;

public class CloudWatchMetricsListener extends Thread {

	// private static final long ONE_HOUR_IN_MILE_SECONDS = 1000 * 60 * 60;
	// private static final int ONE_HOUR_IN_SECONDS = 60 * 60;
	private static final long MILE_SECONDS = 1000;
	private static final int SECOND = 60;
	private AWSCredentials credentials; 
	private AmazonCloudWatchClient client;
	private Map<DateTime, Integer> averagesList;

	public CloudWatchMetricsListener() {
		this.init();
		this.averagesList = Collections.synchronizedMap(
				new TreeMap<DateTime, Integer>()
		);
	}
	
	@Override
	public void run() {
		while(true){
			int avg = this.getMetrics();
			if(avg > 0){
				this.averagesList.put( new DateTime().now(), avg);						
			}
			System.out.println("... " + this.averagesList.toString());//TODO apagar, debug
		}
	}
	
	private void init(){
		this.credentials = AWSCredentials.buildCredentials();
		this.client = this.getClient(this.credentials);
	}
	
	private synchronized int getMetrics(){
		final GetMetricStatisticsRequest request = this.request(this.credentials);
		final GetMetricStatisticsResult result = this.result(this.client, request);
		
		if( result.getDatapoints().isEmpty()){
			return 0;
		}
		//this.toStdOut(result, credentials.getInstanceId()); // debug
		return (int)(double) result.getDatapoints().get(0).getAverage();
	}

	private AmazonCloudWatchClient getClient(AWSCredentials credentials) {
		final AmazonCloudWatchClient client = new AmazonCloudWatchClient(
				new BasicAWSCredentials(credentials.getAwsAccessKey(),
						credentials.getAwsSecretKey()));

		client.setEndpoint(credentials.getEndPoint());
		return client;
	}

	private GetMetricStatisticsRequest request(AWSCredentials credential) {

		final long beginOfPeriod = MILE_SECONDS * getAmountOfSeconds();
		// ONE_HOUR_IN_MILE_SECONDS * getAmountOfHours();
		final int period = SECOND * getAmountOfSecondsOfThePeriod();
		// ONE_HOUR_IN_SECONDS * getAmountOfHoursOfThePeriod();

		PropertieReaderUtil.getCloudWatchStatistics();

		return new GetMetricStatisticsRequest()
				.withStartTime(new Date(new Date().getTime() - beginOfPeriod))
				.withNamespace(PropertieReaderUtil.getCloudWatchNameSpace())
				.withPeriod(period)
				.withDimensions(
						new Dimension().withName(
								PropertieReaderUtil.getCloudWatchDimension())
								.withValue(credential.getInstanceId()))
				.withMetricName(PropertieReaderUtil.getCloudWatchMetric())
				.withStatistics(
						PropertieReaderUtil.getCloudWatchStatistics()[0],
						PropertieReaderUtil.getCloudWatchStatistics()[1])
				// .withStatistics( "Average", "Maximum")
				.withEndTime(new Date(new Date().getTime()));
	}

	private GetMetricStatisticsResult result(
			final AmazonCloudWatchClient client,
			final GetMetricStatisticsRequest request) {
		return client.getMetricStatistics(request);
	}

	private int getAmountOfSeconds() {
		return Integer.parseInt(PropertieReaderUtil
				.getAmountOfSecondsAgoForCloudWatch());
	}

	private int getAmountOfSecondsOfThePeriod() {
		return Integer.parseInt(PropertieReaderUtil
				.getAmountOfSecondsOfThePeriod());
	}

	public synchronized Map<DateTime,Integer> getAveragesList() {
		return averagesList;
	}


	/*
	 * private static int getAmountOfHours(){ return Integer.parseInt(
	 * PropertieReaderUtil.getAmountOfHoursAgoForCloudWatch() ); }
	 * 
	 * private static int getAmountOfHoursOfThePeriod() { return
	 * Integer.parseInt( PropertieReaderUtil.getAmountOfHoursOfThePeriod() ) ; }
	 */
	
	/*
	 * DEBUG
	 * ------------------------------------------------------------------------------
	 * */
	
	
	public static void main(String[] args) {
		CloudWatchMetricsListener watch = new CloudWatchMetricsListener();
		final GetMetricStatisticsRequest request = watch.request(watch.credentials);
		final GetMetricStatisticsResult result = watch.result(watch.client, request);

		watch.toStdOut(result, watch.credentials.getInstanceId());
	}
	
	private void toStdOut(final GetMetricStatisticsResult result,
			final String instanceId) {
		System.out.println(result); // outputs empty result: {Label:
									// CPUUtilization,Datapoints: []}
		for (final Datapoint dataPoint : result.getDatapoints()) {
			System.out.println(dataPoint.getTimestamp() + " - " + dataPoint.toString());
			System.out.printf("%s instance's average CPU utilization : %s%n",
					instanceId, dataPoint.getAverage());
			System.out.println("\n");
			System.out.printf("%s instance's max CPU utilization : %s%n",
					instanceId, dataPoint.getMaximum());
			System.out.println("\n");
			System.out.println(dataPoint.getUnit());
		}
	}
	
}
