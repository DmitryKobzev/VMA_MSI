package bsu;

public class Main {
    public static void main(String[] args) {
        MSI call=new MSI();
        call.findMatrixBforSimpleIterations();
      //  call.findVectorGforSimpleIterations();
      //  call.simpleIterations();
        call.findMatrixBforG_Z();
        call.findVectorGforG_Z();
        call.G_Z();
    }
}
