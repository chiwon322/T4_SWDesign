package com.team4.finding_sw_developers;

import com.team4.finding_sw_developers.Models.Chatlist;

import java.util.Comparator;

public class ListComparator implements Comparator {

    @Override
    public int compare(Object o, Object t1) {
        long testlong1 = ((Chatlist)o).getTimestamp();
        long testlong2 = ((Chatlist)t1).getTimestamp();

        if(testlong1 > testlong2){
            return -1;
        }
        else if(testlong1 < testlong2){
            return 1;
        }
        else return 0;
    }
}
