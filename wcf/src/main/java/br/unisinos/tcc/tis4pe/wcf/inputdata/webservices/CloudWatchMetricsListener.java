package br.unisinos.tcc.tis4pe.wcf.inputdata.webservices;

import java.util.Calendar;
import java.util.Date;

import br.unisinos.tcc.tis4pe.wcf.util.PropertieReaderUtil;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClient;
import com.amazonaws.services.cloudwatch.model.Datapoint;
import com.amazonaws.services.cloudwatch.model.Dimension;
import com.amazonaws.services.cloudwatch.model.GetMetricStatisticsRequest;
import com.amazonaws.services.cloudwatch.model.GetMetricStatisticsResult;
import com.amazonaws.services.elasticmapreduce.util.ResizeJobFlowStep.OnArrested;
import com.amazonaws.services.identitymanagement.model.GetAccessKeyLastUsedRequest;

public class CloudWatchMetricsListener {

	private static final long MILE_SECONDS = 1000;
	private static final long ONE_HOUR_IN_MILE_SECONDS = 1000 * 60 * 60;
	private static final int ONE_HOUR_IN_SECONDS = 60 * 60;
	private static final int SECOND = 60;
	
	public static void main(String[] args) {
		AWSCredentials credentials = AWSCredentials.buildCredentials();
		 
        final AmazonCloudWatchClient client = client(credentials);
        final GetMetricStatisticsRequest request = request(credentials); 
        final GetMetricStatisticsResult result = result(client, request);
        toStdOut(result, credentials.getInstanceId() );   

    }

	    private static AmazonCloudWatchClient client(AWSCredentials credentials) {
	    	 final AmazonCloudWatchClient client = 
	    			 new AmazonCloudWatchClient(
	    					 new BasicAWSCredentials(
	    							 credentials.getAwsAccessKey(), 
	    							 credentials.getAwsSecretKey()
	    					 )
	    			 );

	    	 client.setEndpoint( credentials.getEndPoint() ); 
	         return client;
	    }

	    private static GetMetricStatisticsRequest request(AWSCredentials credential) {

	        final long beginOfPeriod = MILE_SECONDS * getAmountOfSeconds();
	        		//ONE_HOUR_IN_MILE_SECONDS * getAmountOfHours();
	        final int period = SECOND * getAmountOfSecondsOfThePeriod(); 
	        		//ONE_HOUR_IN_SECONDS * getAmountOfHoursOfThePeriod();
	        
	        PropertieReaderUtil.getCloudWatchStatistics();
	        
	        return new GetMetricStatisticsRequest()
	            .withStartTime(new Date(new Date().getTime() - beginOfPeriod ))
	            .withNamespace( PropertieReaderUtil.getCloudWatchNameSpace() )
	            .withPeriod(period)
	            .withDimensions(new Dimension().withName( PropertieReaderUtil.getCloudWatchDimension() )
	            		.withValue(credential.getInstanceId()))
	            .withMetricName( PropertieReaderUtil.getCloudWatchMetric() )
	            .withStatistics( PropertieReaderUtil.getCloudWatchStatistics()[0],
	            		PropertieReaderUtil.getCloudWatchStatistics()[1] )
        		//.withStatistics( "Average", "Maximum")
	            .withEndTime(new Date(new Date().getTime()  ) );
	    }

		private static GetMetricStatisticsResult result(
	            final AmazonCloudWatchClient client, final GetMetricStatisticsRequest request) {
	         return client.getMetricStatistics(request);
	    }

	    private static void toStdOut(final GetMetricStatisticsResult result, final String instanceId) {
	        System.out.println(result); // outputs empty result: {Label: CPUUtilization,Datapoints: []}
	        for (final Datapoint dataPoint : result.getDatapoints()) {
	            System.out.printf("%s instance's average CPU utilization : %s%n", instanceId, dataPoint.getAverage());
	            System.out.println("\n");
	            System.out.printf("%s instance's max CPU utilization : %s%n", instanceId, dataPoint.getMaximum());
	            System.out.println("\n");
	            System.out.println(dataPoint.getUnit());
	        }
	    }
	    
	    private static int getAmountOfHours(){
	    	return Integer.parseInt( PropertieReaderUtil.getAmountOfHoursAgoForCloudWatch() );
	    }
	    
	    private static int getAmountOfSeconds(){
	    	return Integer.parseInt( PropertieReaderUtil.getAmountOfSecondsAgoForCloudWatch() );
	    }
	    
	    private static int getAmountOfHoursOfThePeriod() {
			return Integer.parseInt( PropertieReaderUtil.getAmountOfHoursOfThePeriod() ) ;
		}
	    
	    private static int getAmountOfSecondsOfThePeriod(){
	    	return Integer.parseInt( PropertieReaderUtil.getAmountOfSecondsOfThePeriod() ) ;
	    }
}

