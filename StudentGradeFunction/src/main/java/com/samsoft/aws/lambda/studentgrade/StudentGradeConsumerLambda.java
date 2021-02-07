/**
 * 
 */
package com.samsoft.aws.lambda.studentgrade;

import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.samsoft.aws.lambda.studentgrade.entity.Student;

/**
 * @author SAMARESH
 *
 */
public class StudentGradeConsumerLambda {
	private final ObjectMapper mapper = new ObjectMapper();

	public void consume(SNSEvent event) {
		event.getRecords().forEach(record -> {
			try {
				Student studentObj = mapper.readValue(record.getSNS().getMessage(), Student.class);
				System.out.println(studentObj);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		});
	}

}
