package com.tutorial.sid.spring;

import com.amazonaws.services.lambda.runtime.Context;
import com.tutorial.sid.spring.bean.ClinicalData;
import com.tutorial.sid.spring.bean.Patient;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * @author kunmu On 09-09-2026
 */
public class DataTypes {

    private Double instanceVariable = Math.random();
    private static Double staticVariable = Math.random();

    public DataTypes() {
        System.out.println("Inside Constructor");
    }

    static {
        System.out.println("Static Block executed");
    }

    /**
     * Instance: 0.13140530147567797 Static Variable: 0.5427329755500684 localVariable 0.5775289507589039
     * Instance: 0.13140530147567797 Static Variable: 0.5427329755500684 localVariable 0.2689008774255588
     * @throws InterruptedException
     */
    public void coldstartBasics() throws InterruptedException {
        Thread.sleep(5000);
        Double localVariable = Math.random();
        System.out.println("Instance: " + instanceVariable + " Static Variable: "
                + staticVariable + " localVariable " + localVariable);
    }

    public int getNumber(float number) {
        return (int) number;
    }

    public List<Integer> getListValue(List<String> names) {
        Map<String, Integer> student = new HashMap<>();
        student.put("Student-1", 100);
        student.put("Student-2", 101);
        student.put("Student-3", 103);
        student.put("Student-4", 104);
        return new ArrayList<>(student.values());
    }

    public void saveEmployeeData(Map<String, Integer> empData) {
        System.out.println(empData);
    }

    public Map<String, List<Integer>> getStudentScores() {
        Map<String, List<Integer>> studentScores = new HashMap<String, List<Integer>>();
        studentScores.put("John", Arrays.asList(80, 90, 100));
        studentScores.put("Bob", Arrays.asList(80, 70, 90));
        studentScores.put("Doug", Arrays.asList(80, 90, 20));
        return studentScores;

    }

    /**
     * {
     * "name": "Patient-1",
     * "ssn": "123456789"
     * }
     *
     * @param patient
     * @return
     */
    public ClinicalData getClinicals(Patient patient) {
        System.out.println(patient.getName());
        System.out.println(patient.getSsn());
        ClinicalData clinicalData = new ClinicalData();
        clinicalData.setBp("80/120");
        clinicalData.setHeartRate("80");
        return clinicalData;
    }

    public void getOutput(InputStream input, OutputStream output, Context context)
            throws IOException, InterruptedException {
        Thread.sleep(4000);
        System.out.println(System.getenv("restapiurl"));
        System.out.println(context.getAwsRequestId());
        System.out.println(context.getFunctionName());
        System.out.println(context.getRemainingTimeInMillis());
        System.out.println(context.getMemoryLimitInMB());
        System.out.println(context.getLogGroupName());

        int data;
        while ((data = input.read()) != -1) {
            output.write(Character.toLowerCase(data));
        }
    }
}
