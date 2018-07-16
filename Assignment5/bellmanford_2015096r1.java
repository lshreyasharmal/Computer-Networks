import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.Vector;

public class bellmanford_2015096r1 {

    int minDis(int dis[],Boolean sptset[],int g[][]){
        int min = Integer.MAX_VALUE, min_index = -1;
        int y = 8;

        for(int v=0;v<g.length;v++)
        {

            if(dis[v]<=min && sptset[v]==false )
            {
                min_index = v;
                y = y+7;
                min = dis[v];
            }
        }
        return min_index;
    }
    void display(int dis[], int prev[],int n,int g[][],int src, boolean show)
    {
        System.out.println("Vertex Distance from Src");
        for(int i=0;i<g.length;i++){
            char a = (char)(i+65);
            char b;
            if(prev[i]!=-1)
                 b = (char)(prev[i]+65);
            else
                b = '-';

            if(dis[i]!=Integer.MAX_VALUE)
                System.out.println(a + " min length from src =  "+ dis[i]);
            else
                System.out.println(a + " min length from src =  "+ '∞');
        }
        System.out.println("-----------------------------------------------------------------------");
//        if(!show)
//            return;
//        System.out.println("Routing Table");
//        System.out.println("From   To   Link");
//        for(int i=0;i<g.length;i++)
//        {
//            char a = (char)(65);
//            char b = (char)(65+i);
//            if(i==src) continue;
//            int y=i,t=-1;
//            int c1 = 0;
//            char c=b;
//            boolean is = true;
//            if(prev[y]==-1){
//                c = b;
//                is = false;
//            }
//
//            while(is && (y!=0 || c1==0))
//            {
//                t = y;
//                y = prev[t];
//                c1=1;
//            }
//            if(is)
//                c = (char)(t+65);
//            System.out.println(a + "   " + b + "   ("+a+","+c+")" );
//        }

    }
    int[] algo(int g[][], int src)
    {
        int dis[] = new int[g.length];
        int prev[] = new int[g.length];

        Boolean sptset[] = new Boolean[g.length];
        for(int i=0;i<g.length;i++)
        {
            dis[i] = Integer.MAX_VALUE;
            sptset[i]=false;
            prev[i] = -1;
        }

        dis[src] = 0;
        prev[src] = src;
        display(dis,prev,g.length,g,src,false);
        for(int a = 0;a<g.length-1;a++)
        {
            for(int i=0;i<g.length;i++)
            {
                for(int j=0;j<g.length;j++)
                {
                    if(g[i][j]!=0)
                    {
                        if(dis[j]>dis[i]+g[i][j])
                            dis[j]=dis[i]+g[i][j];
                    }
                }
            }
        }

        display(dis,prev,g.length,g,src,true);
        return dis;
    }

    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        String input_string = scan.nextLine();
        String[] input_string_array = input_string.split("\\s");
        System.out.println(input_string);
        Vector<int[]> dists = new Vector<int[]>();
        HashSet<String> mychar = new HashSet<>();
        int k = 1;

        for(int i=0;i<input_string_array.length;i++) {
            if(i==input_string_array.length-1)
                break;
            if(k==3){ k=1; continue;}
            mychar.add(input_string_array[i]);
            k++;
        }
        int l = mychar.size();
        System.out.println(mychar.size());
        int graph[][] = new int[l][l];
        k=1;
        for(int i=0;i<l;i++)
        {
            for(int j=0;j<l;j++)
            {
                graph[i][j] = 0;
            }
        }

        for(int i=0;i<input_string_array.length;i=i+3)
        {
            char temp1 = input_string_array[i].charAt(0);
            char temp2 = input_string_array[i+1].charAt(0);

            int x = temp1-65;
            int y = temp2-65;

            graph[x][y] = Integer.parseInt(input_string_array[i+2]);
            graph[y][x] = Integer.parseInt(input_string_array[i+2]);
        }
        System.out.println(graph.length);

        bellmanford_2015096r1 llr = new bellmanford_2015096r1();

        dists.add( llr.algo(graph,0));

        for(int i=1;i< graph.length;i++)
        {
            int[] temp= llr.algo(graph,i);
            dists.add(temp);
        }
        System.out.println("Routing Tables");
        System.out.print("- ");
        for(int i=0;i<graph.length;i++)
            System.out.print((char)(i+65) + " ");
        System.out.println();
        for(int i =0;i< dists.size();i++)
        {
            System.out.print( "A ");
            for(int j=0;j<dists.get(i).length;j++)
                System.out.print(dists.get(i)[j]+ " ");
            System.out.println();
        }

        while(true)
        {
            System.out.println("Any changes in edge value?");
            String check = scan.nextLine();
            if(check=="No")
                break;
            String[] yo = check.split("\\s");
            char temp1 = yo[0].charAt(0);
            char temp2 = yo[1].charAt(0);

            int x = temp1-65;
            int y = temp2-65;

            graph[x][y] = Integer.parseInt(yo[2]);
            graph[y][x] = Integer.parseInt(yo[2]);

            llr.algo(graph,0);
        }
    }
}
