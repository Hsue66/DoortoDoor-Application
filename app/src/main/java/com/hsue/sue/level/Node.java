package com.hsue.sue.level;

/**
 * Created by sue on 2015-07-21.
 */
public class Node
{

    private String name;
    private int x;
    private int y;

    // Cost
    private int f;
    private int g;
    private int h;

    private Node parent;


    /** Constructor - set instance of node **/
    public Node(int x, int y)
    {
        this.x = x;
        this.y = y;
        name = Integer.toString(x)+","+Integer.toString(y);
    }

    /** Getter and Setter - set Everything and return Everything **/

    public String getName()
    {
        return name;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }


    public int getF()
    {
        return f;
    }

    public void setF(int f)
    {
        this.f = f;
    }

    public int getG()
    {
        return g;
    }

    public void setG(int g)
    {
        this.g = g;
    }

    public int getH()
    {
        return h;
    }

    public void setH(int h)
    {
        this.h = h;
    }


    public Node getParent()
    {
        return parent;
    }

    public void setParent(Node parent)
    {
        this.parent = parent;
    }
}
