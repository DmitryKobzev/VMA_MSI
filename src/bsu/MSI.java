package bsu;

public class MSI {
    private final int N;
    private final double Epsilon;
    private double [][]a;
    private double [][]b;
    private double [] g;
    private double [] f;
    private double [] x;
    private double [] x_i;
    private double [] vector_nevyazki;
    private double normOfVectorNevyazki;
    MSI(){
        N=5;
        Epsilon=0.00001;
        a=new double[N][N];
        b=new double [N][N];
        g=new double [N];
        f=new double [N];
        x=new double [N];
        x_i=new double [N];
        vector_nevyazki=new double [N];
        normOfVectorNevyazki=0;
    }

    ////////////////////Вычисление матрицы B для метода ПИ
    public void findMatrixBforSimpleIterations(){
      //Транспонированная матрица
      double [][] aT=new double[N][N];
      for(int i=0;i<N;i++){
          for(int j=0;j<N;j++){
              aT[i][j]=a[j][i];
          }
      }

        System.out.println("Транспонированная матрица aT: ");
       printMatrix(aT,N);

        //умножаем транспонированную матрицу на исходную
        System.out.println("Симметричная матрица aT*a:");
        double [][] symmetricMatrix=new double[N][N];
        for(int i=0;i<N;i++){
            for(int j=0;j<N;j++){
                for(int k=0;k<N;k++){
                    symmetricMatrix[i][j] += aT[i][k]*a[k][j];
                }
            }
        }
       printMatrix(symmetricMatrix,N);

        double normSymmetricMatrix=calculatorNormOfMatrix(symmetricMatrix,N);
        for(int i=0;i<N;i++){
            for(int j=0;j<N;j++){
                if(i != j){
                    b[i][j] = (-1) * a[i][j] * (1/normSymmetricMatrix);
                }
                if(i == j){
                    b[i][j] = 1 - a[i][j] * (1/normSymmetricMatrix);
                }
            }
        }
        System.out.println("Матрица b:");
        printMatrix(b,N);
    }

    public void printMatrix(double [][]m, int n){
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                System.out.println(m[i][j]+ " ");
            }
            System.out.println();
        }
    }

    public void printVector(double [] v , int n){
        for(int i=0;i<n;i++){
            System.out.println(v[i]+ " ");
        }
    }

    ////////////////////Вычисление вектора g для метода ПИ
    public void findVectorGforSimpleIterations(){
        //Транспонированная матрица
        double [][] aT=new double[N][N];
        for(int i=0;i<N;i++){
            for(int j=0;j<N;j++){
                aT[i][j]=a[j][i];
            }
        }

        //умножаем транспонированную матрицу на исходную
        double [][] symmetricMatrix=new double[N][N];
        for(int i=0;i<N;i++){
            for(int j=0;j<N;j++){
                for(int k=0;k<N;k++){
                    symmetricMatrix[i][j] += aT[i][k]*a[k][j];
                }
            }
        }

        double  normSymmetricMatrix =calculatorNormOfMatrix(symmetricMatrix,N);
        for(int i=0; i < N;i++){
            g[i]=f[i] * (1/normSymmetricMatrix);
        }
        System.out.println("Вектор g: ");
        printVector(g,N);
    }

    ////////////////////Вычисление матрицы B для метода Г-З
    public void findMatrixBforG_Z(){

        for(int i=0; i < N; i++){
            for(int j=0;j<N;j++){
                if(i == j){
                    b[i][i]= 0;
                }
                if(i != j){
                    b[i][j] = -a[i][j] / a[i][i];
                }
            }
        }

        System.out.println("Матрица b: ");
        printMatrix(b,N);
    }

    ////////////////////Вычисление вектора g для метода Г-З
    public void findVectorGforG_Z(){
        for(int i=0;i<N;i++){
            g[i] = f[i] / a[i][i];
        }

        System.out.println("Вектор g: ");
        printVector(g,N);
    }

    ////////////////////Вычисление нормы вектора
    public double calculatorNormOfVector(double []v ,int n ){
        double norma = 0;
        for(int i=0;i<n;i++){
            if(Math.abs(v[i]) >= norma){
                norma=v[i];
            }
        }
        return norma;
    }

    ////////////////////Вычисление нормы матрицы
    public double calculatorNormOfMatrix(double [][] m,int n){
        double norm=0;
        double tmp;
        for(int i=0;i<n;i++){
            tmp=0;
            for(int j=0;j<n;j++){
                tmp += Math.abs(m[i][j]);
            }
            if(tmp>norm){
                norm=tmp;
            }
        }
        return norm;
    }

    ////////////////////Вычисление вектора невязки и его нормы
    private void calculateVectorNevyazkiNorm(){
        //вектор невязки
        for(int i=0;i<N;i++){
            vector_nevyazki[i]=-f[i];
            for(int j=0;j<N;j++){
                vector_nevyazki[i] += a[i][j] * x[j];
            }
        }
        System.out.println("Вектор невязки: ");
        printVector(vector_nevyazki,N);

        //норма вектора невязки
        normOfVectorNevyazki = 0;
        for(int i=0;i<N;i++){
            if(Math.abs(vector_nevyazki[i]) >= normOfVectorNevyazki){
                normOfVectorNevyazki= vector_nevyazki[i];
            }
        }
        System.out.println("Норма вектора невязки: ");
        System.out.println(normOfVectorNevyazki);
    }




    ////////////////////Проверка на сходимость
    public void stableCheck(){
        boolean flag = false;
        if(Math.abs(calculatorNormOfMatrix(b,N))<1){
            System.out.println("Метод сходится");
        }
        else{
            System.out.println("Метод не сходится");
        }
    }

    ////////////////////Условие продолжения итераций
    private boolean isConverges(double [] v1, double [] v2, int n){
        double v[] = new double [n];
        double norm =0;
        for(int i=0; i<n; i++){
            v[i]=v2[i] - v1[i];
        }
        for(int i=0;i<n;i++){
            if(Math.abs(v[i]) >= norm){
                norm= v[i];
            }
        }

        if(Math.abs(norm) < Epsilon){
            return true;
        }
        else{return false;}
    }
}
