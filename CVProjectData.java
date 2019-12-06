package ec.Project4P82;

import ec.gp.*;

/**
 * @author Derek Hon
 * @version 1.0
 */

public class CVProjectData extends GPData {
    // return value

    public double d;

    public void copyTo(final GPData gpd) {

        ((CVProjectData)gpd).d = d;
    }
}