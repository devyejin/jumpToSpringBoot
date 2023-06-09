public class Three {

    public static void main(String[] args) {

//    for(int dan = 2; dan <=9; dan++) {
//        for (int i = 1; i <=9; i++) {
//            System.out.printf("%d * %d= %d\t", dan, i, dan*i);
//        }
//        System.out.println();
//    }


        for(int k=0; k<9; k+=3) {
            for (int i = 1; i <= 9; i++) {
                for (int j = 1; j <= 3; j++) {
                    System.out.printf("%d * %d = %d\t", j+k, i,  (j+k) * i);
                }
                System.out.println();
            }
            System.out.println("*****************************");
        }
    }
}
