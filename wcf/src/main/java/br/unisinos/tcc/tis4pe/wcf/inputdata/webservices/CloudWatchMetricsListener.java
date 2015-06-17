package br.unisinos.tcc.tis4pe.wcf.inputdata.webservices;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClient;
import com.amazonaws.services.cloudwatch.model.Datapoint;
import com.amazonaws.services.cloudwatch.model.Dimension;
import com.amazonaws.services.cloudwatch.model.GetMetricStatisticsRequest;
import com.amazonaws.services.cloudwatch.model.GetMetricStatisticsResult;

public class CloudWatchMetricsListener {

	 public static void main(String[] args) {
		Credentials credentials = Credentials.buildCredentials();
		 
        final AmazonCloudWatchClient client = client(credentials);
        final GetMetricStatisticsRequest request = request(credentials); 
        final GetMetricStatisticsResult result = result(client, request);
        toStdOut(result, credentials.getInstanceId() );   

    }

	    private static AmazonCloudWatchClient client(Credentials credentials) {
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

	    private static GetMetricStatisticsRequest request(Credentials credential) {
	        //final long twentyFourHrs = 1000 * 60 * 60 * 2;
	        final long fourHr= 1000 * 60 * 60 * 4;
	        final long twentyFourHrs = 1000 * 60 * 60 * 24;
	        final int oneHour = 60 * 60;
	        final int untilNow = 60 * 60 * 24;

	        return new GetMetricStatisticsRequest()
	            .withStartTime(new Date(new Date().getTime() - twentyFourHrs ))
	            .withNamespace("AWS/EC2")
	            .withPeriod(untilNow)
	            .withDimensions(new Dimension().withName("InstanceId").withValue(credential.getInstanceId()))
	            .withMetricName("CPUUtilization")
	            .withStatistics("Average", "Maximum")
	            .withEndTime(new Date(new Date().getTime()  ) );
	            //.withEndTime(new Date());
	    }

	    private static GetMetricStatisticsResult result(
	            final AmazonCloudWatchClient client, final GetMetricStatisticsRequest request) {
	         return client.getMetricStatistics(request);
	    }

	    private static void toStdOut(final GetMetricStatisticsResult result, final String instanceId) {
	        System.out.println(result); // outputs empty result: {Label: CPUUtilization,Datapoints: []}
	        for (final Datapoint dataPoint : result.getDatapoints()) {
	            System.out.printf("%s instance's average CPU utilization : %s%n", instanceId, dataPoint.getAverage());      
	            System.out.printf("%s instance's max CPU utilization : %s%n", instanceId, dataPoint.getMaximum());
	        }
	    }
}
