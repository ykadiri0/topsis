package com.pfa.topsis.Models;

import java.util.Arrays;
import java.util.Random;

public class NumberTable {

    public static int getRandomNumberAndRemove(int[] table) {
        // Create a random number generator
        Random rand = new Random();

        // Get the index of a random number in the table
        int index = rand.nextInt(table.length);

        // Get the random number from the table
        int randomNumber = table[index];

        // Shift the remaining numbers in the array to fill the gap
        for (int i = index; i < table.length - 1; i++) {
            table[i] = table[i + 1];
        }

        // Resize the array to remove the last element (which is now duplicated)
        table = Arrays.copyOf(table, table.length - 1);

        // Return the randomly selected number
        return randomNumber;
    }

    public static void main(String[] args) {
        // Example usage
        // Example input data
        double[] inputData = {4.5, 2.1, 6.7, 3.8, 8.2, 1.0};

        // Create a new table to store ranks
        int[] rankData = new int[inputData.length];

        // Copy input data to a new array for sorting
        double[] sortedData = Arrays.copyOf(inputData, inputData.length);

        // Sort the input data in ascending order
        Arrays.sort(sortedData);

        // Loop through the input data and assign ranks
        for (int i = 0; i < inputData.length; i++) {
            double value = inputData[i];
            int rank = Arrays.binarySearch(sortedData, value) + 1;
            rankData[i] = rank;
        }

        // Display the ranks
        System.out.println("Input Data: " + Arrays.toString(inputData));
        System.out.println("Ranks: " + Arrays.toString(rankData));

    }
}
