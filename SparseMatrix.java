// package matrixcomputation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class SparseMatrix {

    // implement all pre-defined methods below and add your own methods as needed.
    
    private Node[] rowHeads;
    private Node[] colHeads;
    private static int size;

    public SparseMatrix(Node[] r, Node[] c) {
        rowHeads = r;
        colHeads = c;
    }

    public static SparseMatrix initializeByInput(File file) throws IOException {
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        size = Integer.parseInt(br.readLine());
        Node[] rowHeader = new Node[size];
        Node[] colHeader = new Node[size];
        SparseMatrix result = new SparseMatrix(rowHeader, colHeader);
        for (int i=0; i<size; i++) {
            rowHeader[i] = new Node();
            colHeader[i] = new Node();
            rowHeader[i].rowLink = rowHeader[i];
            colHeader[i].colLink = colHeader[i];
        }
        String line;
        while ((line = br.readLine()) != null) {
            String[] completeLine = line.split("\t", 0);
            int row = Integer.parseInt(completeLine[0]);
            int column = Integer.parseInt(completeLine[1]);
            int value = Integer.parseInt(completeLine[2]);
            result.addNode(value, row, column);
        }
        fr.close();
        return result;
    }

    // parameter n --> given matrix size n
    public static SparseMatrix[] initializeByFormula(int n) {
        Node[] rowHeaderOne = new Node[n];
        Node[] columnHeaderOne = new Node[n];
        SparseMatrix one = new SparseMatrix(rowHeaderOne, columnHeaderOne);
        for (int i=0; i<n; i++) {
            rowHeaderOne[i] = new Node();
            columnHeaderOne[i] = new Node();
            rowHeaderOne[i].rowLink = rowHeaderOne[i];
            columnHeaderOne[i].colLink = columnHeaderOne[i];
        }
        for (int x=0; x<n; x++) {
            for (int y=0; y<n; y++) {
                if ((x+1) == (y+1)) {
                    one.addNode((x+1), (x+1), (y+1));
                }
            }
        }
        Node[] rowHeaderTwo = new Node[n];
        Node[] columnHeaderTwo = new Node[n];
        SparseMatrix two = new SparseMatrix(rowHeaderTwo, columnHeaderTwo);
        for (int i=0; i<n; i++) {
            rowHeaderTwo[i] = new Node();
            columnHeaderTwo[i] = new Node();
            rowHeaderTwo[i].rowLink = rowHeaderTwo[i];
            columnHeaderTwo[i].colLink = columnHeaderTwo[i];
        }
        for (int x=0; x<n; x++) {
            for (int y=0; y<n; y++) {
                if ((x+1) == ((y+2)%n)) {
                    two.addNode(((-2*(y+1))-(x+1)), (x+1), (y+1));
                }
            }
        }
        Node[] rowHeaderThree = new Node[n];
        Node[] columnHeaderThree = new Node[n];
        SparseMatrix three = new SparseMatrix(rowHeaderThree, columnHeaderThree);
        for (int i=0; i<n; i++) {
            rowHeaderThree[i] = new Node();
            columnHeaderThree[i] = new Node();
            rowHeaderThree[i].rowLink = rowHeaderThree[i];
            columnHeaderThree[i].colLink = columnHeaderThree[i];
        }
        for (int x=0; x<n; x++) {
            for (int y=0; y<n; y++) {
                if (((x+1)%2) != 0 && ((y+1)%2) != 0 && (y+1) != 3) {
                    three.addNode(((x+1)+(y+1)), (x+1), (y+1));
                }
                else if ((y+1) == 3) {
                    three.addNode((-(x+1)), (x+1), (y+1));
                }
            }
        }
        SparseMatrix[] result = new SparseMatrix[3];
        result[0] = one;
        result[1] = two;
        result[2] = three;
        return result;
    }
    
    private void addNode(int value, int row, int column) {
        Node newNode = new Node(value, row, column);
        Node tempNode1;
        Node tempNode2;
        boolean flag = false;
        if (rowHeads[row-1].rowLink == rowHeads[row-1]) {
            rowHeads[row-1].rowLink = newNode;
            newNode.rowLink = rowHeads[row-1];
        }
        else {
            if (rowHeads[row-1].rowLink.col > newNode.col) {
                newNode.rowLink = rowHeads[row-1].rowLink;
                rowHeads[row-1].rowLink = newNode;
            }
            else {
                tempNode1 = rowHeads[row-1];
                tempNode2 = tempNode1.rowLink;
                while (tempNode2.rowLink != rowHeads[row-1]) {
                    tempNode1 = tempNode2;
                    tempNode2 = tempNode1.rowLink;
                    if (tempNode2.col > newNode.col) {
                        tempNode1.rowLink = newNode;
                        newNode.rowLink = tempNode2;
                        flag = true;
                        break;
                    }
                }
                if (flag != true) {
                    tempNode2.rowLink = newNode;
                    newNode.rowLink = rowHeads[row-1];
                }
            }
        }
        flag = false;
        if (colHeads[column-1].colLink == colHeads[column-1]) {
            colHeads[column-1].colLink = newNode;
            newNode.colLink = colHeads[column-1];
        }
        else {
            if (colHeads[column-1].colLink.row > newNode.row) {
                newNode.colLink = colHeads[column-1].colLink;
                colHeads[column-1].colLink = newNode;
            }
            else {
                tempNode1 = colHeads[column-1];
                tempNode2 = tempNode1.colLink;
                while (tempNode2.colLink != colHeads[column-1]) {
                    tempNode1 = tempNode2;
                    tempNode2 = tempNode1.colLink;
                    if (tempNode2.row > newNode.row) {
                        tempNode1.colLink = newNode;
                        newNode.colLink = tempNode2;
                        flag = true;
                        break;
                    }
                }
                if (flag != true) {
                    tempNode2.colLink = newNode;
                    newNode.colLink = colHeads[column-1];
                }
            }
        }
    }

    public void print() {
        Node tempNode;
        for (int x=0; x<size; x++) {
            tempNode = rowHeads[x];
            for (int y=0; y<size; y++) {
                if (tempNode.rowLink.col == (y+1)) {
                    System.out.print((int)tempNode.rowLink.value);
                    tempNode = tempNode.rowLink;
                }
                else {
                    System.out.print("0");
                }
                System.out.print("\t");
            }
            System.out.println();
        }
        System.out.println();
    }

    // parameter m --> another sparse matrix m
    public SparseMatrix add(SparseMatrix m) {
        Node[] rowHeader = new Node[size];
        Node[] colHeader = new Node[size];
        SparseMatrix result = new SparseMatrix(rowHeader, colHeader);
        for (int i=0; i<size; i++) {
            rowHeader[i] = new Node();
            colHeader[i] = new Node();
            rowHeader[i].rowLink = rowHeader[i];
            colHeader[i].colLink = colHeader[i];
        }
        Node tempNode1;
        Node tempNode2;
        for (int x=0; x<size; x++) {
            tempNode1 = rowHeads[x];
            tempNode2 = m.rowHeads[x];
            while (tempNode1.rowLink != rowHeads[x] && tempNode2.rowLink != m.rowHeads[x]) {
                if (tempNode1.rowLink.col == tempNode2.rowLink.col) {
                    double sum = (tempNode1.rowLink.value) + (tempNode2.rowLink.value);
                    result.addNode((int)sum, tempNode1.rowLink.row, tempNode1.rowLink.col);
                    tempNode1 = tempNode1.rowLink;
                    tempNode2 = tempNode2.rowLink;
                }
                else if (tempNode1.rowLink.col < tempNode2.rowLink.col) {
                    result.addNode((int)tempNode1.rowLink.value, tempNode1.rowLink.row, tempNode1.rowLink.col);
                    tempNode1 = tempNode1.rowLink;
                }
                else if (tempNode1.rowLink.col > tempNode2.rowLink.col) {
                    result.addNode((int)tempNode2.rowLink.value, tempNode2.rowLink.row, tempNode2.rowLink.col);
                    tempNode2 = tempNode2.rowLink;
                }
            }
            if (tempNode1.rowLink == rowHeads[x]) {
                while (tempNode2.rowLink != m.rowHeads[x]) {
                    result.addNode((int)tempNode2.rowLink.value, tempNode2.rowLink.row, tempNode2.rowLink.col);
                    tempNode2 = tempNode2.rowLink;
                }
            }
            else if (tempNode2.rowLink == m.rowHeads[x]) {
                while (tempNode1.rowLink != rowHeads[x]) {
                    result.addNode((int)tempNode1.rowLink.value, tempNode1.rowLink.row, tempNode1.rowLink.col);
                    tempNode1 = tempNode1.rowLink;
                }
            }
        }
        return result;
    }

    // parameter m --> another sparse matrix m
    public SparseMatrix subtract(SparseMatrix m) {
        Node[] rowHeader = new Node[size];
        Node[] colHeader = new Node[size];
        SparseMatrix result = new SparseMatrix(rowHeader, colHeader);
        for (int i=0; i<size; i++) {
            rowHeader[i] = new Node();
            colHeader[i] = new Node();
            rowHeader[i].rowLink = rowHeader[i];
            colHeader[i].colLink = colHeader[i];
        }
        Node tempNode1;
        Node tempNode2;
        for (int x=0; x<size; x++) {
            tempNode1 = rowHeads[x];
            tempNode2 = m.rowHeads[x];
            while (tempNode1.rowLink != rowHeads[x] && tempNode2.rowLink != m.rowHeads[x]) {
                if (tempNode1.rowLink.col == tempNode2.rowLink.col) {
                    double difference = (tempNode1.rowLink.value) - (tempNode2.rowLink.value);
                    result.addNode((int)difference, tempNode1.rowLink.row, tempNode1.rowLink.col);
                    tempNode1 = tempNode1.rowLink;
                    tempNode2 = tempNode2.rowLink;
                }
                else if (tempNode1.rowLink.col < tempNode2.rowLink.col) {
                    result.addNode((int)tempNode1.rowLink.value, tempNode1.rowLink.row, tempNode1.rowLink.col);
                    tempNode1 = tempNode1.rowLink;
                }
                else if (tempNode1.rowLink.col > tempNode2.rowLink.col) {
                    result.addNode(-(int)tempNode2.rowLink.value, tempNode2.rowLink.row, tempNode2.rowLink.col);
                    tempNode2 = tempNode2.rowLink;
                }
            }
            if (tempNode1.rowLink == rowHeads[x]) {
                while (tempNode2.rowLink != m.rowHeads[x]) {
                    result.addNode(-(int)tempNode2.rowLink.value, tempNode2.rowLink.row, tempNode2.rowLink.col);
                    tempNode2 = tempNode2.rowLink;
                }
            }
            else if (tempNode2.rowLink == m.rowHeads[x]) {
                while (tempNode1.rowLink != rowHeads[x]) {
                    result.addNode((int)tempNode1.rowLink.value, tempNode1.rowLink.row, tempNode1.rowLink.col);
                    tempNode1 = tempNode1.rowLink;
                }
            }
        }
        return result;
    }

    // integer a
    public SparseMatrix scalarMultiply(int a) {
        Node[] rowHeader = new Node[size];
        Node[] colHeader = new Node[size];
        SparseMatrix result = new SparseMatrix(rowHeader, colHeader);
        for (int i=0; i<size; i++) {
            rowHeader[i] = new Node();
            colHeader[i] = new Node();
            rowHeader[i].rowLink = rowHeader[i];
            colHeader[i].colLink = colHeader[i];
        }
        Node tempNode1;
        for (int x=0; x<size; x++) {
            tempNode1 = rowHeads[x];
            while (tempNode1.rowLink != rowHeads[x]) {
                result.addNode((int)(a*tempNode1.rowLink.value), tempNode1.rowLink.row, tempNode1.rowLink.col);
                tempNode1 = tempNode1.rowLink;
            }
        }
        return result;
    }

    // parameter m --> another sparse matrix m
    public SparseMatrix matrixMultiply(SparseMatrix m) {
        Node[] rowHeader = new Node[size];
        Node[] colHeader = new Node[size];
        SparseMatrix result = new SparseMatrix(rowHeader, colHeader);
        for (int i=0; i<size; i++) {
            rowHeader[i] = new Node();
            colHeader[i] = new Node();
            rowHeader[i].rowLink = rowHeader[i];
            colHeader[i].colLink = colHeader[i];
        }
        Node tempNode1;
        Node tempNode2;
        int total;
        for (int x=0; x<size; x++) {
            for (int y=0; y<size; y++) {
                total = 0;
                tempNode1 = rowHeads[x];
                tempNode2 = m.colHeads[y];
                while (tempNode1.rowLink != rowHeads[x] && tempNode2.colLink != m.colHeads[y]) {
                    if (tempNode1.rowLink.col == tempNode2.colLink.row) {
                        total += ((tempNode1.rowLink.value)*(tempNode2.colLink.value));
                        tempNode1 = tempNode1.rowLink;
                        tempNode2 = tempNode2.colLink;
                    }
                    else if (tempNode1.rowLink.col > tempNode2.colLink.row) {
                        tempNode2 = tempNode2.colLink;
                    }
                    else if (tempNode1.rowLink.col < tempNode2.colLink.row) {
                        tempNode1 = tempNode1.rowLink;
                    }
                }
                if (total != 0) {
                    result.addNode(total, (x+1), (y+1));
                }
            }
        }
        return result;
    }

    // integer c
    public SparseMatrix power(int c) {
        SparseMatrix result = new SparseMatrix(rowHeads, colHeads);
        SparseMatrix tempMatrix = new SparseMatrix(rowHeads, colHeads);
        boolean flag = false;
        String binarypower = Integer.toBinaryString(c);
        for (int i=binarypower.length()-1; i>=0; i--) {
            if (binarypower.charAt(i) == '1') {
                if (i == binarypower.length()-1) {
                    flag = true;
                    continue;
                }
                tempMatrix = tempMatrix.matrixMultiply(tempMatrix);
                if (!flag) {
                    result = tempMatrix;
                    flag = true;
                }
                else {
                    result = result.matrixMultiply(tempMatrix);
                }
            }
            else {
                if (i != binarypower.length()-1) {
                    tempMatrix = tempMatrix.matrixMultiply(tempMatrix);
                }
            }
        }
        return result;
    }
    
    // transpose matrix itself
    public SparseMatrix transpose() {
        Node[] rowHeader = new Node[size];
        Node[] colHeader = new Node[size];
        SparseMatrix result = new SparseMatrix(rowHeader, colHeader);
        for (int i=0; i<size; i++) {
            rowHeader[i] = new Node();
            colHeader[i] = new Node();
            rowHeader[i].rowLink = rowHeader[i];
            colHeader[i].colLink = colHeader[i];
        }
        Node tempNode1;
        for (int x=0; x<size; x++) {
            tempNode1 = rowHeads[x];
            for (int y=0; y<size; y++) {
                while (tempNode1.rowLink != rowHeads[x]) {
                    if (tempNode1.rowLink.row == tempNode1.rowLink.col) {
                        result.addNode((int)tempNode1.rowLink.value, tempNode1.rowLink.row, tempNode1.rowLink.col);
                        tempNode1 = tempNode1.rowLink;
                    }
                    else {
                        result.addNode((int)tempNode1.rowLink.value, tempNode1.rowLink.col, tempNode1.rowLink.row);
                        tempNode1 = tempNode1.rowLink;
                    }
                }
            }
        }
        return result;
    }

    public int getSize(){
        return size;
    }
    
}
