
package com.pfa.topsis.Fuzzy;

import javax.swing.text.StyledEditorKit;

public class MatrixAggregation {

    public static CostFuzzy[][] weightedAggregation(CostFuzzy[][][] cube) {
        int numMatrices = cube.length;
        int numRows = cube[0].length;
        int numcol = cube[0][0].length;
        double sum=0;
        double min=cube[0][0][0].getFuzzyNumber().getLowerBound();
        double max=0;
        System.out.println("//////////////////////////////////////////////////////////////:");
        System.out.println(numRows);
        System.out.println(numcol);

        Boolean cost=true;
        CostFuzzy[][] aggregatedMatrix= new CostFuzzy[numRows][numcol];
        for(int i=0;i<numRows;i++){
            for(int j=0;j<numcol;j++){
                System.out.println("indexx "+i+"  "+j);
                min=cube[0][i][j].getFuzzyNumber().getLowerBound();
                for (int k = 0; k < numMatrices; k++) {
                    CostFuzzy values = cube[k][i][j];
                    double first = values.getFuzzyNumber().getLowerBound();
                    double second = values.getFuzzyNumber().getMidlbound();
                    double third = values.getFuzzyNumber().getUpperBound();
                    cost=values.getCostOrbenef();
                    sum+=second;
                    System.out.println(first + "   "+ min );
                    if(min>first){
                        min=first;
                    }
                    if(max<third){
                        max=third;
                    }

                }
                sum=sum/numMatrices;
                aggregatedMatrix[i][j]=new CostFuzzy(new FuzzyNumber(min,sum,max),cost);
                sum=0;

                max=0;
            }


        }

        return aggregatedMatrix;
    }

    public static void main(String[] args) {
        // Example usage:

        // Three 2x2 matrices to aggregate
        CostFuzzy[][] matrix1 = { { new CostFuzzy(new FuzzyNumber(1,2,3),false), new CostFuzzy(new FuzzyNumber(4,5,6),true) , new CostFuzzy(new FuzzyNumber(6,7,8),true)},
                { new CostFuzzy(new FuzzyNumber(1,2,3),true), new CostFuzzy(new FuzzyNumber(4,5,6),true) , new CostFuzzy(new FuzzyNumber(6,7,8),true)},
                { new CostFuzzy(new FuzzyNumber(2,2,3),true), new CostFuzzy(new FuzzyNumber(4,5,6),true) , new CostFuzzy(new FuzzyNumber(6,7,8),true)} };
        CostFuzzy[][] matrix2 = { { new CostFuzzy(new FuzzyNumber(2,3,4),false), new CostFuzzy(new FuzzyNumber(4,5,6),true) , new CostFuzzy(new FuzzyNumber(6,7,8),true)},
                { new CostFuzzy(new FuzzyNumber(1,2,3),true), new CostFuzzy(new FuzzyNumber(4,5,6),true) , new CostFuzzy(new FuzzyNumber(6,7,8),true)},
                { new CostFuzzy(new FuzzyNumber(5,6,7),true), new CostFuzzy(new FuzzyNumber(4,5,6),true) , new CostFuzzy(new FuzzyNumber(6,7,8),true)} };
        CostFuzzy[][] matrix3 = { { new CostFuzzy(new FuzzyNumber(1,2,3),false), new CostFuzzy(new FuzzyNumber(4,5,6),true) , new CostFuzzy(new FuzzyNumber(6,7,8),true)},
                { new CostFuzzy(new FuzzyNumber(1,2,3),true), new CostFuzzy(new FuzzyNumber(4,5,6),true) , new CostFuzzy(new FuzzyNumber(6,7,8),true)},
                { new CostFuzzy(new FuzzyNumber(1,2,3),true), new CostFuzzy(new FuzzyNumber(4,5,6),true) , new CostFuzzy(new FuzzyNumber(6,7,8),true)} };
        CostFuzzy[][] matrix4 = { { new CostFuzzy(new FuzzyNumber(1,2,3),false), new CostFuzzy(new FuzzyNumber(4,5,6),true) , new CostFuzzy(new FuzzyNumber(1,666,99),true)},
                { new CostFuzzy(new FuzzyNumber(1,2,3),true), new CostFuzzy(new FuzzyNumber(4,5,6),true) , new CostFuzzy(new FuzzyNumber(6,7,8),true)},
                { new CostFuzzy(new FuzzyNumber(1,2,3),true), new CostFuzzy(new FuzzyNumber(4,5,6),true) , new CostFuzzy(new FuzzyNumber(6,7,8),true)} };

        // Create a 3D cube to hold the matrices
        CostFuzzy[][][] cube = { matrix1, matrix2,matrix3,matrix4};

        // Three weights corresponding to each dimension of the cube
        double[] weights = {0.2, 0.3, 0.5};
        CostFuzzy[][] mat= weightedAggregation(cube);
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                System.out.println(mat[i][j]);
                System.out.print("  ");
            }
            System.out.println();
        }

        // Create the aggregated matrix using the weighted aggregation function
        //double[][][] aggregatedMatrix = weightedAggregation(cube, weights);

        // Output the aggregated matrix

    }
}
