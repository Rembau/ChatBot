package test;

class He
{
public static void main(String[] args)
{
int[][] D = new int[4][];
D[0] = new int[4];
D[1] = new int[5];
D[2] = new int[6];
D[3] = new int[7];
for(int[] row:D)
{
for(int b:row)
System.out.print(b+"  ");
System.out.println();
}
}
}

