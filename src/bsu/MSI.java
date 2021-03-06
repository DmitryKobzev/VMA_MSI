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
        a=new double[][]{
                {0.6444, 0.0000, -0.1683, 0.1184, 0.1973},
                {-0.0395, 0.4208, 0.0000, -0.0802, 0.0263},
                {0.0132, -0.1184, 0.7627, 0.0145, 0.0460},
                {0.0395, 0.0000, -0.0960, 0.7627, 0.0000},
                {0.0263, -0.0395, 0.1907, -0.0158, 0.5523}
        };
        b=new double [N][N];
        g=new double [N];
        f=new double []{
                1.2677,
                1.6819,
                -2.3657,
                -6.5369,
                2.8351
        };
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
                System.out.print(m[i][j]+ " ");
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
        System.out.println("Норма ||B||: ");
        System.out.println(norm);
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

    ////////////////////Метод простой итерации
    public void simpleIterations(){
        System.out.println("Методом ПИ: ");
        int iter=0;
        double iterApri=0;
        while(true){
            for(int i=0; i < N;i++){
                x_i[i] = g[i];
                for(int j=0;j < N;j++){
                    x_i[i] += b[i][j] * x[j];
                }
            }
            iter++;

            if(isConverges(x,x_i,N) == true){
                break;
            }

            for(int i=0;i < N;i++){
                x[i] = x_i[i];
            }
        }

        for(int i=0;i < N;i++){
            x[i] = x_i[i];
        }
        iterApri = (Math.log(Epsilon * (1 - calculatorNormOfMatrix(b,N))/ calculatorNormOfVector(g,N))) / Math.log(calculatorNormOfMatrix(b,N)) + 1;
        System.out.println("Априорное количество итераций: ");
        System.out.println(Math.ceil(iterApri));
        System.out.println("Количество итераций: ");
        System.out.println(iter);
        System.out.println("Вектор решений: ");
        printVector(x,N);
        calculateVectorNevyazkiNorm();
        calculatorNormOfMatrix(b,N);
    }

    ////////////////////Метод Гаусса-Зейделя
    public void G_Z(){
        System.out.println("Методом Гаусса-Зейделя: ");
        int iter=0;
        double iterApri=0;

        for(int i=0;i < N;i++){
            x[i]=0;
        }
        while(true){
            for(int i=0; i < N;i++){
                x_i[i] = 0;
                for(int j=0;j < N;j++){
                    x_i[i] += b[i][j] * x_i[j];
                }
                x_i[i] += g[i];
            }

            iter++;

            if(isConverges(x,x_i,N) == true){
                break;
            }

            for(int i=0;i < N;i++){
                x[i] = x_i[i];
            }
        }

        for(int i=0;i < N;i++){
            x[i] = x_i[i];
        }

        iterApri = (Math.log(Epsilon * (1 - calculatorNormOfMatrix(b,N))/ calculatorNormOfVector(g,N))) / Math.log(calculatorNormOfMatrix(b,N)) + 1;
        System.out.println("Априорное количество итераций: ");
        System.out.println(Math.ceil(iterApri));
        System.out.println("Количество итераций: ");
        System.out.println(iter);
        System.out.println("Вектор решений: ");
        printVector(x,N);
        calculateVectorNevyazkiNorm();
        calculatorNormOfMatrix(b,N);
    }
}
