import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class Main {
    public static ArrayList<String> DivideInputFileIntoRuns(String Inputfilename, int runSize) throws IOException {

        int tempkey, tempoffset;
        ArrayList<String> fileNames = new ArrayList<String>();
        RandomAccessFile fileStore = new RandomAccessFile("index.bin", "rw");
        int intgersInTheFile = Math.toIntExact(fileStore.length() / 4);
        Map<Integer, Integer> records = new TreeMap<>();
        fileStore.seek(0);
        for (int i = 0; i < (intgersInTheFile / runSize)/2; i++) {
            String RunName = "run" + i;
            System.out.println(RunName);
            //EXCEPTIONNNNNNN
            for (int j=0; j<runSize; j++){
                tempkey=fileStore.readInt();
                tempoffset=fileStore.readInt();
                records.put(tempkey,tempoffset);
            }
            System.out.println(records+" "+records.size());
            RandomAccessFile runStore = new RandomAccessFile(RunName, "rw");
            for(Map.Entry<Integer,Integer> entry : records.entrySet()) {
                Integer key = entry.getKey();
                Integer value = entry.getValue();
                runStore.writeInt(key);
                runStore.writeInt(value);
            }
            runStore.close();

            //Test
            /*RandomAccessFile veiwrunStore = new RandomAccessFile(RunName, "rw");
            for(int j=0; j<records.size()*2;j++){
                System.out.println(RunName+ veiwrunStore.readInt());
            }
            veiwrunStore.close();
            */
            fileNames.add(RunName);
            records.clear();
        }
        fileStore.close();
        return fileNames;
    }


    public static int BinarySearchOnSortedFile(String Sortedfilename, int RecordKey) throws IOException {

        //KOL DA MLO4 LAZMAA
        int tempkey, tempoffset;
        RandomAccessFile fileStore = new RandomAccessFile("index.bin", "rw");
        int intgersInTheFile = Math.toIntExact(fileStore.length() / 4);
        Map<Integer, Integer> records = new TreeMap<>();
        fileStore.seek(0);
        for (int i = 0; i < intgersInTheFile/2; i++) {
            tempkey = fileStore.readInt();
            tempoffset = fileStore.readInt();
            records.put(tempkey, tempoffset);
            //System.out.println(records + " " + records.size());

            RandomAccessFile runStore = new RandomAccessFile("sortedfile.bin", "rw");
            for(Map.Entry<Integer,Integer> entry : records.entrySet()) {
                Integer key = entry.getKey();
                Integer value = entry.getValue();
                runStore.writeInt(key);
                runStore.writeInt(value);
            }
        }

        fileStore.close();
        /*
        RandomAccessFile veiwStore = new RandomAccessFile("sortedfile.bin", "rw");
        for(int j=0; j<intgersInTheFile/2;j++){
            System.out.println(veiwStore.readInt()+" "+veiwStore.readInt());
        }
        veiwStore.close();*/

        //MN AWL HNA DA AL MOHM

        RandomAccessFile raf = new RandomAccessFile("sortedfile.bin","r");
        int searchValue = RecordKey;
        int lineSize = 8;
        int numberOfLines = Math.toIntExact(raf.length() / lineSize);
        //System.out.println(numberOfLines);
        int bottom = 0;
        int top = numberOfLines;
        boolean found=false;
        int middle;
        while (bottom <= top){
            middle = (bottom+top)/2;
            //System.out.println(middle);
            //System.out.println(middle*lineSize);
            raf.seek(middle*lineSize); // jump to this line in the file
            int key=raf.readInt();

            if (key == searchValue){
                System.out.println(raf.readInt());
                found=true;
                break;
            }
            else if (key < searchValue){
                bottom = middle + 1;
            }
            else {
                top = middle - 1;
            }
        }
        if(found==false){System.out.println("Record is not found");}

        raf.close();

        return RecordKey;
    }


    public static void main(String[] args) throws Exception
    {
        //System.out.println(DivideInputFileIntoRuns("input.bin",3));
        BinarySearchOnSortedFile("index.bin",154);
    }

}


/*
    public static void main(String[] args) throws Exception
    {

        DivideInputFileIntoRuns
        /*
        RandomAccessFile fileStore = new RandomAccessFile("index.bin", "rw");
        int intgersInTheFile = Math.toIntExact(fileStore.length() / 4);
        //System.out.println("whats the memory's capcity? ");
        fileStore.seek(0);
        for(int i=0; i<intgersInTheFile/3;i++){
            String RunName="run"+i;
            Vector<Integer> records=new Vector<Integer>();
            System.out.println(RunName);
            records.add(fileStore.readInt());
            records.add(fileStore.readInt());
            records.add(fileStore.readInt());
            System.out.println(records);
            RandomAccessFile runStore = new RandomAccessFile(RunName, "rw");
            for(int j=0; j<records.size();j++){
                runStore.writeInt(records.get(j));
            }
            runStore.close();

            RandomAccessFile veiwrunStore = new RandomAccessFile(RunName, "rw");
            for(int j=0; j<records.size();j++){
               System.out.println(RunName+ veiwrunStore.readInt());
            }
            veiwrunStore.close();
        }
        fileStore.close();*/
