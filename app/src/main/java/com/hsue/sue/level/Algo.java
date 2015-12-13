package com.hsue.sue.level;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;

/**
 * Created by sue on 2015-07-21.
 */
public class Algo
{
    private int find = 0;					// Number of find count
    private int[][] Map = null;				// Map
    private Node Start;						// Start Node
    private Node Finish;					// Finish Node
    private Node Current;					// Current Node
    private int currentD = 0;				// Moving distance from Start
    private int[][] around = null;			// Coordinate of around
    private String finishName;				// Finish node's name
    private int cant = 0;					// If can't find path, cant = 1


    // Open List
    private List<Node> Open = new ArrayList<Node>();

    // Close List
    private Stack<Node> Close = new Stack<Node>();

    // Path List
    private Stack<Node> Path = new Stack<Node>();

    /** Constructor - set Everything at first **/
    public Algo(int startX, int startY, int finishX, int finishY, int[][] map)
    {
        System.out.println("$$$$$$$$$$$"+finishY);
        Map = map;
        Start =new Node(startX, startY);
        Finish = new Node(finishX, finishY);
        Current = Start;
        around = new int[8][2];
        finishName = Integer.toString(finishX)+","+Integer.toString(finishY);

    }

    /** Check Start and Finish point - if one of them is 1, can't find path **/
    public int CheckEndPoint()
    {
        int okay;

        if(Map[Start.getX()][Start.getY()] == 1 || Map[Finish.getX()][Finish.getY()] == 1 )
        {
            System.out.println("Wrong Start or Finish point");
            okay = 1;
        }
        else
            okay = 0;

        return okay;
    }

    /** Sorting - sort Open List **/
    public void sortList()
    {
        // sort ascending order
        Collections.sort(Open, new Comparator<Node>() {

            public int compare(Node obj1, Node obj2) {
                return (obj1.getF() < obj2.getF()) ? -1 : (obj1.getF() > obj2.getF()) ? 1 : 0;
            }
        });
    }

    /** Current Node Setter - set Current Node and Current distance, add previous Current to Close List **/
    public void setCurrent()
    {
        if(Open.isEmpty())					// Empty or not - if empty, don't need to search more
        {
            System.out.println("Can't Find The Path.");
            cant = 1;						// no path flag
        }
        else
        {
            Close.add(Current);				// Add previous to Close List
            Current = Open.get(0);			// The smallest F
            Open.remove(0);					// remove from Open list
            currentD = Current.getG();		// Set distance
        }
    }

    /** Set 8 direction's coordinate - set x, y **/
    public void setLocation(Node temp)
    {
        // TopLeft
        around[0][0] = temp.getX()-1;
        around[0][1] = temp.getY()-1;
        // TopMiddle
        around[1][0] = temp.getX()-1;
        around[1][1] = temp.getY();
        // TopRight
        around[2][0] = temp.getX()-1;
        around[2][1] = temp.getY()+1;
        // MiddleLeft
        around[3][0] = temp.getX();
        around[3][1] = temp.getY()-1;
        // MiddleRight
        around[4][0] = temp.getX();
        around[4][1] = temp.getY()+1;
        // BottomLeft
        around[5][0] = temp.getX()+1;
        around[5][1] = temp.getY()-1;
        // BottomMiddle
        around[6][0] = temp.getX()+1;
        around[6][1] = temp.getY();
        // BottomRight
        around[7][0] = temp.getX()+1;
        around[7][1] = temp.getY()+1;
    }

    /** Decide can go or not - decide by Map AND Close list  **/
    public int CanGoOrNot(int order, int x, int y)
    {
        int y1 = 0;
        int y2 = 0;

        // Map Based Go or Not //
        if(Map[x][y]==1)					// Wall
            y1 = 1;
        else								// Empty but Check diagonal direction
        {
            if(order == 1 || order == 3 || order == 4 || order == 6 )
                y1 = 0;
            else if((order == 0 && Map[x][y+1] == 1 && Map[x+1][y] == 1)	// Blocked by diagonal direction
                    || ( order == 2 && Map[x][y-1] == 1 && Map[x+1][y] == 1)
                    || ( order == 5 && Map[x-1][y] == 1 && Map[x][y+1] == 1)
                    || ( order == 7 && Map[x][y-1] == 1 && Map[x-1][y] == 1))
                y1 = 1;
            else
                y1 = 0;
        }

        // Exist in Close List or Not //
        String name = Integer.toString(x)+","+Integer.toString(y);

        int size = Close.size();
        for(int i = 0; i < size; i++)
        {
            if(Close.get(i).getName().equals(name))
            {
             //   System.out.println(Close.get(i).getName());
                y2 = 1;
            }
        }

        // return can go or not
        if(y1 == 1 || y2 == 1)
            return 1;
        else
            return 0;
    }

    /** Check the node exist in Open list - check by name and return index Or not exist **/
    public int ExistInOpenList(int x, int y)
    {
        int yes = -1;						// not exist in Open list
        String name = Integer.toString(x)+","+Integer.toString(y);

        int size = Open.size();
        for(int i = 0 ; i<size ; i++)		// find it is in list or not
        {
            if(Open.get(i).getName().equals(name))
            {
              //  System.out.println(Open.get(i).getName());
                yes = i;					// exit in Open list and return it's index
            }
        }

        return yes;
    }

    /** Check 8 direction -   **/
    public void CheckAround(Node temp)
    {
        setLocation(temp);								// set 8 direction location from center

        // Decide add Open list or Not //
        for(int i = 0 ; i < 8; i++ )
        {
            if(CanGoOrNot(i, around[i][0], around[i][1]) == 0)	// can go
            {
                int retValue = ExistInOpenList(around[i][0],around[i][1]);	// check exist in Open list or not
                if(retValue == -1)								// not exist in Open list
                {
                    // make new Node
                    Node newNode = new Node(around[i][0],around[i][1]);
                    makeNode(i,newNode);
                }
                else											// exit in Open list
                {
                    // compare g value
                    int tempG = calcG(i);
                    if(Open.get(retValue).getG() > tempG)
                    {
                        // change Node's instance
                        Open.remove(retValue);
                        Node changeNode = new Node(around[i][0],around[i][1]);
                        makeNode(i,changeNode);
                    }
                }
            }
            else												// can't go
                continue;
        }
        sortList();												// sort Open list
    }

    /** Make Node's Info - set f, g, h **/
    public void makeNode(int order, Node temp)
    {
        temp.setParent(Current);
        temp.setG(calcG(order));
        temp.setH(calcH(temp));
        temp.setF(temp.getG()+temp.getH());
        Open.add(temp);
    }

    /** Calculate G - in order to direction from Current Node**/
    public int calcG(int order)
    {
        int g = 0;
        int t = 0;

        if (order == 0 || order == 2 || order == 5 || order == 7 )	// diagonal direction
            t = 14;
        else														// straight direction
            t = 10;

        g = currentD + t;
        return g;
    }

    /** Calculate H - distance from self to Finish Node **/
    public int calcH(Node temp)
    {
        int h = 0;
        h = 10 * (Math.abs(temp.getX()-Finish.getX()) + Math.abs(temp.getY()-Finish.getY()));
        return h;
    }

    /** Check path finding finish or not - if Finish is in Open list, it means finished **/
    public int CheckFinish()
    {
        int check = 1;
        int size = Open.size();
        // Check Open list //
        for(int i = 0; i < size; i++)
        {
            if(Open.get(i).getName().equals(finishName))
            {
                System.out.println("FINISH");
                Finish.setParent(Current);
                Close.add(Finish);
                check = 0;
            }
        }

        return check;
    }

    /** Trace back path from Finish Node - check Node's Parent to backtracking **/
    public void TracebackPath()
    {
        Node finish = Close.pop();
       // System.out.println(finish.getName());
        Path.push(finish);

        Node parent = finish.getParent();
       // System.out.println(parent.getName());
        Path.push(parent);

        while(!parent.equals(Start))
        {
            Node temp = parent.getParent();
            //System.out.println(temp.getName());
            Path.push(temp);
            parent = temp;
        }
    }

    /** Path search - find the path **/
    public void searchPath()
    {
        if(CheckEndPoint()==0)					// Check Start and Finish point
        {
            while(CheckFinish() != 0)			// Check finish or not
            {
                if(find != 0)
                    setCurrent();
                if(cant == 1)				// if can't find the path, STOP
                    break;
                find++;
                CheckAround(Current);
                //showEverything();
            }
            if(cant != 1)
                TracebackPath();				// Show searched path
        }

    }

    /** Show information - show everything **/
    public void showEverything()
    {
        System.out.println("find : "+find);
        System.out.println("currentD : "+currentD);
        System.out.println("Current : "+Current.getX()+", "+Current.getY());
        int size = Open.size();
        System.out.println("@@ Open List @@");
        for(int i = 0 ; i<size ; i++)
        {
            System.out.println(Open.get(i).getName() + " "+ Open.get(i).getF()+ "="+ Open.get(i).getG()+ "+"+ Open.get(i).getH());
        }

        int s = Close.size();
        System.out.println("## Close List ##");
        for(int i = 0 ; i<s ; i++)
        {
            System.out.println(s+" "+i);
            System.out.println(Close.get(i).getName() + " "+ Close.get(i).getF()+ "="+ Close.get(i).getG()+ "+"+ Close.get(i).getH());
        }
        System.out.println();
    }

    public Stack<Node> getPath() {
        return Path;
    }
}
