package com.manchau.creational;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/***
 * 
 * @author Maneesh Item2: Instead of having a telescope constructor pattern
 *         where we have multiple constructors, each with a different number of
 *         formal parameters, we should implement a static builder class for
 *         such a class.
 */

class EmployeeInformation {
	private final String name;
	private final Date dateOfBirth;
	private final Date dateOfJoining;
	private String jobTitle;
	private String department;

	public EmployeeInformation(EmployeeInformationBuilder builder) {
		this.name = builder.getName();
		this.dateOfBirth = builder.getDateOfBirth();
		this.dateOfJoining = builder.getDateOfJoining();
		this.jobTitle = builder.getJobTitle();
		this.department = builder.getDepartment();
	}

	// Static Builder
	public static class EmployeeInformationBuilder {
		private final String name;
		private final Date dateOfBirth;
		private final Date dateOfJoining;
		private String jobTitle = "Employee";
		private String department = "Office";

		// constructor
		public EmployeeInformationBuilder(String name, Date dateOfBirth, Date dateOfJoining) {
			this.name = name;
			this.dateOfBirth = dateOfBirth;
			this.dateOfJoining = dateOfJoining;
		}

		// setters
		public EmployeeInformationBuilder setJobTitle(String jobTitle) {
			this.jobTitle = jobTitle;
			return this;
		}

		public EmployeeInformationBuilder setDepartment(String department) {
			this.department = department;
			return this;
		}

		// builder
		public EmployeeInformation build() {
			return new EmployeeInformation(this);
		}

		// getters
		public String getName() {
			return name;
		}

		public Date getDateOfBirth() {
			return dateOfBirth;
		}

		public Date getDateOfJoining() {
			return dateOfJoining;
		}

		public String getJobTitle() {
			return jobTitle;
		}

		public String getDepartment() {
			return department;
		}
	}

	public void display() {
		System.out.println("Employee Name: " + this.name);
		System.out.println("Date Of Birth: " + this.dateOfBirth);
		System.out.println("Joined On: " + this.dateOfJoining);
		System.out.println("Job Title: " + this.jobTitle);
		System.out.println("Unit: " + this.department);
	}
}

public class ObjectBuilderDemo {

	public static void main(String[] args) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
		try {
			//builder pattern in action
			EmployeeInformation info = new EmployeeInformation.EmployeeInformationBuilder("John Doe",
					dateFormat.parse("1971-01-01"), dateFormat.parse("1995-08-02")).setJobTitle("Software Developer")
							.setDepartment("San Diego").build();
			info.display();

		} catch (ParseException e) {
			Logger logger = Logger.getLogger("ObjectBuilderDemo");
			logger.log(Level.INFO, "Improper date format", e);
		}
	}
}