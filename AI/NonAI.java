import java.util.Scanner;

public class NonAI {

    public static void printGameBoard(int size, String[] gb , int[] ms, String u1, String u2)
    {
        int c=0;
        System.out.println("Game Board : ");
        
        for(int i=0; i<size; i++)
        {
            
                System.out.print(" | "+ gb[i]+" |");
                
            if(i==size/3-1 || i==size/3*2-1 || i==size-1)       // to print the lines of tice-tac-toe
                System.out.println("\n--------------------\n");

            if(gb[i].equals("X") || gb[i].equals("O"))
                c++;
            
        }
        if(c==9)
        {
            int result_user = checkGameBoard(size,gb,ms,u1,u2); //checking user moves
            int result_comp = checkGameBoard(size,gb,ms,u2,u1); //checking computer moves

            if(result_user==-1)
            {
                System.out.println("*_* "+u1+" is the Winner  :)");
                System.exit(0);

            }
            else if(result_comp==-1)
            {
                System.out.println("*_* "+u2+" is the Winner  :)"); 
                System.exit(0);
            }
            else
            {
                System.out.println("-------- TIE --------");
                System.exit(0);
            }

        }
    }

     public static void computerTurn(int size, String[] gb, String u1, int[] ms,String u2)
    {
        int c=0, flag=0;
        // System.out.println("Game Board : ");
        for(int i=0; i<size; i++)   
        { 
                // System.out.print(" | "+ gb[i][j]+" |");
                if(gb[i]==u1)       
                    c++;    // how many times user has played
        }

        if(c==1) // checking if user has played for 1st time
        {
            for(int i=size-1;i>=0;i--)
            {
                if(!gb[i].equals(u1))
                {
                    gb[i] = u2;     // computer marks the biggest no.
                    break;
                }
            }
        }
        else{
            int result_user = checkGameBoard(size,gb,ms,u1,u2); //checking user moves
            int result_comp = checkGameBoard(size,gb,ms,u2,u1); //checking computer moves
            
            if(result_user==-1) // user is winner
            {
                System.out.println("*_* "+u1+" is the Winner --------- 1 :)"); 
                System.out.println("User : ");
                printGameBoard(size, gb, ms, u1,u2);
                System.exit(0);
            }
            else if(result_comp == -1)  // Computer is winner
            {
                System.out.println("*_* "+u2+" is the Winner --------- 2 :)");
                flag=1;
            }
            else if(result_comp!=-2 && (gb[result_comp]!=u1))    // Computer is winning in next round
            {
                gb[result_comp]=u2;
                System.out.println("*_* "+u2+" is the winner --------- 3 :)");
                flag=1;
            }
            else if(result_user!= -2)
            {
                System.out.println("-----------"+result_user);
                gb[result_user]=u2;  // computer is blocking user from winning
            }
            else
            {
                for(int i=size-1;i>=0;i--)
                {
                    if((!gb[i].equals(u1)) && (!gb[i].equals(u2)))
                    {
                        gb[i] = u2;     // computer marks the biggest no.
                        break;
                    }
                        
                }
            }
        }

        System.out.println("Computer : ");
        printGameBoard(size, gb, ms,u1,u2);

        if(flag==1)
            System.exit(0);

    }


    public static int checkGameBoard(int size, String[]gb, int[]ms, String u, String x)
    {
        ///// Winning starts ------------------------------------

        for(int i=0;i<3;i++)
        {
        	if((gb[i]==gb[i+3]) && (gb[i+3]==gb[i+6]) && (gb[i+6]==u) && ((ms[i]+ms[i+3]+ms[i+6])==15))	
        	{
        		return -1;   //vertical won
        	}
        }

        for(int i=0;i<gb.length;i=i+3)
        {
        	if((gb[i]==gb[i+1]) && (gb[i+1]==gb[i+2]) && (gb[i+2]==u)  && ((ms[i]+ms[i+1]+ms[i+2])==15))
        	{
        		return -1;   //horizontal won
        	}   
        }

        if((gb[0]==gb[4]) && (gb[4]==gb[8]) &&  (gb[8]==u) && ((ms[0]+ms[4]+ms[8])==15))
        {
        	return -1;       //left diagonal won
        }

        if((gb[2]==gb[4]) && (gb[4]==gb[6]) && (gb[6]==u) && ((ms[2]+ms[4]+ms[6])==15))
        {
        	return -1;       // right diagonal won
        }

        ///// Winning ends ------------------------------------

        // REturn position of upcoming winner starts ---------------------------

        for(int i=0;i<3;i++)     //----vertically
        {
        	if((gb[i]==gb[i+3]) && (gb[i+3]==u) && (gb[i+6]!=x))	
        	{
        		return (i+6);
        	}
        	if((gb[i+3]==gb[i+6]) && (gb[i+6]==u) && (gb[i]!=x))
        	{
        		return (i);
        	}
        	if((gb[i]==gb[i+6]) && (gb[i+6]==u) && (gb[i+3]!=x))
        	{
        		return (i+3);
        	}
        }

        for(int i=0;i<8;i=i+3)      //----horizontally
        {
        	if((gb[i]==gb[i+1]) && (gb[i+1]==u) && (gb[i+2]!=x))	
        	{
        		return (i+2);
        	}
        	if((gb[i+1]==gb[i+2]) && (gb[i+2]==u) && (gb[i]!=x))
        	{
        		return i;
        	}
        	if((gb[i]==gb[i+2]) && (gb[i+2]==u) && (gb[i+1]!=x))
        	{
        		return (i+1);
        	}
        }

        //-- left diag
        if((gb[0]==gb[4]) && (gb[4]==u) && (gb[8]!=x))
        	return 8;
        if((gb[4]==gb[8]) && (gb[8]==u) && (gb[0]!=x))
            return 0;
        if((gb[0]==gb[8]) && (gb[8]==u) && (gb[4]!=x))
            return 4;

        //--right diag
        if((gb[2]==gb[4]) && (gb[4]==u) && (gb[6]!=x))
        	return 6;
        if((gb[4]==gb[6]) && (gb[6]==u) && (gb[2]!=x))
        	return 2;
        if((gb[2]==gb[6]) && (gb[6]==u) && (gb[4]!=x))
            return 4;

        return -2;

        // REturn position of upcoming winner ends ---------------------------
    }

    public static void main(String [] args)
    {
        Scanner sc = new Scanner(System.in);
        int ch;
        int size=9;

        System.out.println("--------Tic Tac Toe-------");
        System.out.println("User 1 : Enter your choice ( X or O ) : ");
        String u1 = sc.nextLine();
        String u2="";

        if(u1.equals("X"))
            u2 = "O";
        else if(u1.equals("O"))
            u2 = "X";
        else
        {
            System.out.println("Invalid Choice");
            System.exit(0);
        }
        
        System.out.println("User 1 : "+u1);
        System.out.println("User 2 : "+u2);

        int[] ms = new int[size];
        String[] gb = new String[size];

        ms[0] = 8;
        ms[1] = 3;
        ms[2] = 4;
        ms[3] = 1;
        ms[4] = 5;
        ms[5] = 9;
        ms[6] = 6;
        ms[7] = 7;
        ms[8] = 2;

        for(int i=0; i<size; i++)
        {
                gb[i] = ""+i ;
        }

        do{
            System.out.println("1)Play \n2)Exit");
            System.out.println("\nEnter your choice : ");
            ch = sc.nextInt();

            switch(ch)
            {
                case 1 : printGameBoard(size, gb, ms,u1,u2);
                        System.out.println("\nEnter the number u wanna insert for "+u1+" : ");
                         int row = sc.nextInt();

                        if(gb[row] != u1 && gb[row] != u2)      // checking if the position is not occupied by X or O
                            gb[row] = u1;
                        else
                        {
                            System.out.println("Invalid Move");
                            break;
                        }

                        System.out.println("User : ");  // user game board
                        printGameBoard(size, gb , ms, u1,u2);

                        computerTurn(size, gb, u1, ms, u2);

                         
            }
            
    
        }while(ch!=2);
    }
    
}
