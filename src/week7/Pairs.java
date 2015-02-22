package week7;
public class Pairs implements Comparable<Pairs> 
{
    public String str = "";
    public int num = -1;

    public Pairs(String et1, int et2) 
    {
        this.str = et1;
        this.num = et2;
    }

    @Override
    public int compareTo(Pairs o) 
    {
        return -(this.num - o.num);
    }
}
