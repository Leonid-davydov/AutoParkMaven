package Vehicle;

import Vehicle.Vehicle;

import java.util.Comparator;

public class MyComparator implements Comparator<Vehicle> {

    @Override
    public int compare(Vehicle o1, Vehicle o2) {
        String name1 = o1.getModelName();
        String name2 = o2.getModelName();
        int minSize = Math.min( name1.length(), name2.length());
        for (int i = 0; i < minSize; i++ ) {
            if (name1.charAt(i) > name1.charAt(i))
                return 1;
            if (name1.charAt(i) < name1.charAt(i))
                return -1;
        }

        return name1.length() - name2.length();
    }
}
