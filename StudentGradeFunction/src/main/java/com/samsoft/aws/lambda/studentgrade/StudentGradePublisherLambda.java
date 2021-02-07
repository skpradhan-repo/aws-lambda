/**
 * 
 */
package com.samsoft.aws.lambda.studentgrade;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.samsoft.aws.lambda.studentgrade.entity.Student;

/**
 * @author SAMARESH
 *
 */
public class StudentGradePublisherLambda {
	private static final String STUDENT_GRADE_TOPIC = System.getenv("STUDENT_GRADE_TOPIC");
	private final ObjectMapper mapper = new ObjectMapper();
	private final AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
	private final AmazonSNS sns = AmazonSNSClientBuilder.defaultClient();

	public void publish(S3Event event) {
		event.getRecords().forEach(record -> {
			S3ObjectInputStream s3LocalObj = s3
					.getObject(record.getS3().getBucket().getName(), record.getS3().getObject().getKey())
					.getObjectContent();
			try {
				List<Student> studentList = Arrays.asList(mapper.readValue(s3LocalObj, Student[].class));
				s3LocalObj.close();
				System.out.println(studentList);
				studentList.stream().forEach(student -> {
					if (student.getMarks() >= 90) {
						student.setGrade('A');
					} else if (student.getMarks() >= 70) {
						student.setGrade('B');
					} else {
						student.setGrade('C');
					}
				});
				publishStudentToSNA(studentList);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	private void publishStudentToSNA(List<Student> studentList) {
		studentList.forEach(item -> {
			try {
				sns.publish(STUDENT_GRADE_TOPIC, mapper.writeValueAsString(item));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		});
	}
}
