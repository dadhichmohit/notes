package me.gowdru.notes.ir.vsm;

import java.util.TreeMap;

/**
 * Vector model class.
 * An instance of this represents a vector in n-dimensional space.
 * @author Thamme Gowda
 */
public class Vector {

    private final String vectorId;
    public long dimensions[];
    public double scales[];

    /**
     * Creates a vector
     * @param vectorId identifier for vector
     */
    public Vector(String vectorId) {
        this.vectorId = vectorId;
    }

    /**
     * Creates a vector
     * @param vectorId identifier for vector
     * @param features map of dimension -> magnitude entries. Requires entries to be sorted in ascending order of
     *                 dimension numbers
     */
    public Vector(String vectorId, TreeMap<Long, Double> features) {
        this(vectorId);
        this.dimensions = new long[features.size()];
        this.scales = new double[features.size()];
        int idx = 0;
        for (Long dimension : features.keySet()) {
            this.dimensions[idx] = dimension;
            this.scales[idx] = features.get(dimension);
            idx++;
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("(").append(vectorId).append("):{");
        if (dimensions != null && scales != null) {
            for (int i = 0; i < dimensions.length; i++) {
                builder.append(dimensions[i]).append(":").append(scales[i]).append(' ');
            }
        }
        builder.append("}");
        return builder.toString();
    }

    /**
     * Computes Dot product of this vector with another vector
     * @param anotherVector another vector
     * @return dot (.) product
     */
    public double dotProduct(Vector anotherVector) {
        // A = ax+by+cz
        // B = mx+ny+oz
        // A.B = a*m + b*n + c*o
        Vector v1 = this;
        Vector v2 = anotherVector;
        /* Vector dimensions are sparsely represented, so absent dimensions implies zero magnitude */
        //assumption : feature dimensions in vector are sorted
        double product = 0.0;
        int i = 0; //v1 feature index
        int j = 0; //v2 feature index
        while (i < v1.dimensions.length && j < v2.dimensions.length) {
            if (v1.dimensions[i] == v2.dimensions[j]) {
                //both dimensions are found
                product += v1.scales[i] * v2.scales[j];
                i++;
                j++;
            } else if (v1.dimensions[i] < v2.dimensions[j]){
                // skip i
                i++;
            } else {
                //skip j
                j++;
            }
        }
        return product;
    }
}
