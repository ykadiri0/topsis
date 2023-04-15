package com.pfa.topsis.Fuzzy;

public class Ftopsis {
    CostFuzzy[][] matrice;

    double[] ahpweiths;

    public Ftopsis(CostFuzzy[][] matrice, double[] ahpweiths) {
        this.matrice = matrice;
        this.ahpweiths = ahpweiths;
    }

    public CostFuzzy[][] getMatrice() {
        return matrice;
    }

    public void setMatrice(CostFuzzy[][] matrice) {
        this.matrice = matrice;
    }

    public double[] calcul() {

        double[] cs = new double[this.matrice[0].length];
        double max = 0;
        double min = 9999999999999.0;
        for (int j = 0; j < this.matrice[0].length; j++) {
            if (this.matrice[0][j].getCostOrbenef() == true) {
                for (int i = 0; i < this.matrice.length; i++) {

                    if (this.matrice[i][j].getFuzzyNumber().getUpperBound() >= max) {
                        max = this.matrice[i][j].getFuzzyNumber().getUpperBound();
                    }

                }
                cs[j] = max;
            } else {
                for (int i = 0; i < this.matrice.length; i++) {

                    if (this.matrice[i][j].getFuzzyNumber().getLowerBound() <= min) {
                        min = this.matrice[i][j].getFuzzyNumber().getLowerBound();
                    }

                }
                cs[j] = min;
            }
            max = 0;
            min = 9999999999999.0;
        }

        for (int i = 0; i < cs.length; i++) {
            System.out.print(cs[i] + " ");
        }



        for (int j = 0; j < this.matrice[0].length; j++) {
            if (this.matrice[0][j].getCostOrbenef() == true) {
                for (int i = 0; i < this.matrice.length; i++) {

                    this.matrice[i][j].setFuzzyNumber(new FuzzyNumber(this.matrice[i][j].getFuzzyNumber().getLowerBound() / cs[j], this.matrice[i][j].getFuzzyNumber().getMidlbound() / cs[j], this.matrice[i][j].getFuzzyNumber().getUpperBound() / cs[j]));

                }
            } else {
                for (int i = 0; i < this.matrice.length; i++) {

                    this.matrice[i][j].setFuzzyNumber(new FuzzyNumber(cs[j] / this.matrice[i][j].getFuzzyNumber().getUpperBound(), cs[j] / this.matrice[i][j].getFuzzyNumber().getMidlbound(), cs[j] / this.matrice[i][j].getFuzzyNumber().getLowerBound()));

                }
            }
        }
        System.out.println("-------------------------------------------------------------------------------");
        for(int i=0;i<this.matrice.length;i++){
            for(int j=0;j<this.matrice[0].length;j++){
                System.out.println(this.matrice[i][j].getFuzzyNumber().getLowerBound()+","+this.matrice[i][j].getFuzzyNumber().getMidpoint()+","+this.matrice[i][j].getFuzzyNumber().getUpperBound()+";");
            }
            System.out.println("\n");
        }
        System.out.println("-------------------------------------------------------------------------------");



        for (int j = 0; j < this.matrice[0].length; j++) {
            for (int i = 0; i < this.matrice.length; i++) {

                this.matrice[i][j].setFuzzyNumber(new FuzzyNumber(this.matrice[i][j].getFuzzyNumber().getLowerBound() * ahpweiths[j], this.matrice[i][j].getFuzzyNumber().getMidlbound() * ahpweiths[j], this.matrice[i][j].getFuzzyNumber().getUpperBound() * ahpweiths[j]));

            }
        }

        System.out.println("-------------------------------------------------------------------------------");
        for(int i=0;i<this.matrice.length;i++){
            for(int j=0;j<this.matrice[0].length;j++){
                System.out.println(this.matrice[i][j].getFuzzyNumber().getLowerBound()+","+this.matrice[i][j].getFuzzyNumber().getMidpoint()+","+this.matrice[i][j].getFuzzyNumber().getUpperBound()+";");
            }
            System.out.println("\n");
        }
        System.out.println("-------------------------------------------------------------------------------");



        FuzzyNumber[] aplus = new FuzzyNumber[this.matrice[0].length];
        double max2 = 0;
        for (int j = 0; j < this.matrice[0].length; j++) {
            FuzzyNumber fn = new FuzzyNumber();
            for (int i = 0; i < this.matrice.length; i++) {
                if (this.matrice[i][j].getFuzzyNumber().getLowerBound() > max2) {
                    max2 = this.matrice[i][j].getFuzzyNumber().getLowerBound();
                }
            }
            fn.setLowerBound(max2);
            max2 = 0;
            for (int i = 0; i < this.matrice.length; i++) {
                if (this.matrice[i][j].getFuzzyNumber().getMidlbound() > max2) {
                    max2 = this.matrice[i][j].getFuzzyNumber().getMidlbound();
                }
            }
            fn.setMidlbound(max2);
            max2 = 0;
            for (int i = 0; i < this.matrice.length; i++) {
                if (this.matrice[i][j].getFuzzyNumber().getUpperBound() > max2) {
                    max2 = this.matrice[i][j].getFuzzyNumber().getUpperBound();
                }
            }
            fn.setUpperBound(max2);
            max2 = 0;


            aplus[j] = fn;
        }

        System.out.println("aplus");
        for (int i = 0; i < aplus.length; i++) {
            System.out.print(aplus[i] + " ");
        }



        FuzzyNumber[] amoin = new FuzzyNumber[this.matrice[0].length];
        double min2 = 99999999999999.0;
        for (int j = 0; j < this.matrice[0].length; j++) {
            FuzzyNumber fn = new FuzzyNumber();
            for (int i = 0; i < this.matrice.length; i++) {
                if (this.matrice[i][j].getFuzzyNumber().getLowerBound() < min2) {
                    min2 = this.matrice[i][j].getFuzzyNumber().getLowerBound();
                }
            }
            fn.setLowerBound(min2);
            min2 = 99999999999999.0;
            for (int i = 0; i < this.matrice.length; i++) {
                if (this.matrice[i][j].getFuzzyNumber().getMidlbound() < min2) {
                    min2 = this.matrice[i][j].getFuzzyNumber().getMidlbound();
                }
            }
            fn.setMidlbound(min2);
            min2 = 99999999999999.0;
            for (int i = 0; i < this.matrice.length; i++) {
                if (this.matrice[i][j].getFuzzyNumber().getUpperBound() < min2) {
                    min2 = this.matrice[i][j].getFuzzyNumber().getUpperBound();
                }
            }
            fn.setUpperBound(min2);
            min2 = 99999999999999.0;


            amoin[j] = fn;
        }
        System.out.println("\namois");
        for (int i = 0; i < amoin.length; i++) {
            System.out.print(amoin[i] + " ");
        }


        double[][] dmax = new double[this.matrice.length][this.matrice[0].length];
        for (int j = 0; j < this.matrice[0].length; j++) {
            for (int i = 0; i < this.matrice.length; i++) {
                dmax[i][j]=Math.sqrt(((this.matrice[i][j].getFuzzyNumber().getUpperBound()-aplus[j].getUpperBound())*(this.matrice[i][j].getFuzzyNumber().getUpperBound()-aplus[j].getUpperBound())+(this.matrice[i][j].getFuzzyNumber().getMidlbound()-aplus[j].getMidlbound())*(this.matrice[i][j].getFuzzyNumber().getMidlbound()-aplus[j].getMidlbound())+(this.matrice[i][j].getFuzzyNumber().getLowerBound()-aplus[j].getLowerBound())*(this.matrice[i][j].getFuzzyNumber().getLowerBound()-aplus[j].getLowerBound()))/3.0);


            }
        }
        System.out.println(); // move to the next line after each row
        System.out.println("dmaaaaaaaaaaaaaaaax");
        for (int i = 0; i < dmax.length; i++) {
            for (int j = 0; j < dmax[i].length; j++) {
                System.out.print(dmax[i][j] + " ");
            }
            System.out.println(); // move to the next line after each row
        }

        System.out.println("-------------------------------------------------------------------------------");
        for(int i=0;i<this.matrice.length;i++){
            for(int j=0;j<this.matrice[0].length;j++){
                System.out.println(this.matrice[i][j].getFuzzyNumber().getLowerBound()+","+this.matrice[i][j].getFuzzyNumber().getMidpoint()+","+this.matrice[i][j].getFuzzyNumber().getUpperBound()+";");
            }
            System.out.println("\n");
        }
        System.out.println("-------------------------------------------------------------------------------");





        double[][] dmin = new double[this.matrice.length][this.matrice[0].length];
        for (int j = 0; j < this.matrice[0].length; j++) {
            for (int i = 0; i < this.matrice.length; i++) {
                double a=Math.pow(this.matrice[i][j].getFuzzyNumber().getUpperBound()-amoin[j].getUpperBound(),2);
                double b=Math.pow(this.matrice[i][j].getFuzzyNumber().getMidlbound()-amoin[j].getMidlbound(),2);
                double c=Math.pow(this.matrice[i][j].getFuzzyNumber().getLowerBound()-amoin[j].getLowerBound(),2);
                dmin[i][j]=Math.sqrt((a+b+c)/3.0);


            }
        }
        System.out.println(); // move to the next line after each row
        System.out.println("dmiiiiiiiiiiiiiiiiiiiiiiiiiin");
        for (int i = 0; i < dmin.length; i++) {
            for (int j = 0; j < dmin[i].length; j++) {
                System.out.print(dmin[i][j] + " ");
            }
            System.out.println(); // move to the next line after each row
        }











        double[] dmaxetoile=new double[this.matrice.length];
        for(int i=0;i<dmax.length;i++){
            double somme=0;
            for(int j=0;j<dmax[0].length;j++){
                somme+=dmax[i][j];
            }
            dmaxetoile[i]=somme;
        }
        System.out.println("\n");
        for (int i = 0; i < dmaxetoile.length; i++) {
            System.out.print(dmaxetoile[i] + " ");
        }





        double[] dminetoile=new double[this.matrice.length];
        for(int i=0;i<dmax.length;i++){
            double somme=0;
            for(int j=0;j<dmin[0].length;j++){
                somme+=dmin[i][j];
            }
            dminetoile[i]=somme;
        }
        System.out.println("\n");
        for (int i = 0; i < dminetoile.length; i++) {
            System.out.print(dminetoile[i] + " ");
        }
        System.out.println("\n");



        double[] cci= new double[this.matrice.length];
        for(int i=0; i<this.matrice.length;i++){
            cci[i]=dminetoile[i]/(dminetoile[i]+dmaxetoile[i]);
        }

        System.out.println("/" +
                "/////////////////////////////////////////////////////////////////////////::result");

        for (int i=0; i<cci.length;i++){
            System.out.println(cci[i]+"     ");

        }
        return  cci;


    }

    public static void main(String[] args) {
        CostFuzzy[][] matrix4 = { { new CostFuzzy(new FuzzyNumber(3,5.6666666667,9),true), new CostFuzzy(new FuzzyNumber(5,8.33333,9),true) , new CostFuzzy(new FuzzyNumber(5,7,9),false)},
                { new CostFuzzy(new FuzzyNumber(5,7,9),true), new CostFuzzy(new FuzzyNumber(3,7,9),true) , new CostFuzzy(new FuzzyNumber(3,5,7),false)},
                { new CostFuzzy(new FuzzyNumber(5,8.3333333,9),true), new CostFuzzy(new FuzzyNumber(3,5,7),true) , new CostFuzzy(new FuzzyNumber(1,2.333333333,5),false)},
                { new CostFuzzy(new FuzzyNumber(1,2.3333333,5),true), new CostFuzzy(new FuzzyNumber(1,4.333333333,7),true) , new CostFuzzy(new FuzzyNumber(1,1,3),false)}};

        double[] t= {0.34426,0.49835,0.24590};
        Ftopsis fu=new Ftopsis(matrix4,t);
        fu.calcul();
    }
}
